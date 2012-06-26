/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.lar;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ImportExportThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestImpl;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.model.*;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.*;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.lar.BookmarksEntryLarPersistence;
import com.liferay.portal.service.persistence.lar.BookmarksFolderLarPersistence;
import com.liferay.portal.service.persistence.lar.LayoutLarPersistence;
import com.liferay.portal.service.persistence.lar.LayoutLarPersistenceImpl;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.io.File;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class LARExporter {

	public void export(
		long groupId, boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {

		try {
			ImportExportThreadLocal.setLayoutExportInProcess(true);

			_larDigest = new LarDigestImpl();

			doCreateDigest(
				groupId, _larDigest, privateLayout, layoutIds, parameterMap,
				startDate, endDate);
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			try {
				_larDigest.close();
			}
			catch (XMLStreamException xse) {
				_log.error(xse);
			}
		}

		_larDigestFile = _larDigest.getDigestFile();

		try {
			doExport(groupId, privateLayout, parameterMap);
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			ImportExportThreadLocal.setLayoutExportInProcess(false);
		}
	}

	public File getDigestFile() {
		return _larDigestFile;
	}

	protected void doCreateDigest(
			long groupId, LarDigest larDigest, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception, PortalException {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean exportIgnoreLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE);
		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);
		boolean exportThemeSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME_REFERENCE);
		boolean exportLogo = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LOGO);
		boolean exportLayoutSetSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);
		boolean updateLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		long lastPublishDate = System.currentTimeMillis();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long companyId = group.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		if (endDate != null) {
			lastPublishDate = endDate.getTime();
		}

		if (exportIgnoreLastPublishDate) {
			endDate = null;
			startDate = null;
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		_portletDataContext = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), startDate,
			endDate, null);

		_portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(_portletDataContext));

		Portlet layoutConfigurationPortlet =
			PortletLocalServiceUtil.getPortletById(
				_portletDataContext.getCompanyId(),
				PortletKeys.LAYOUT_CONFIGURATION);

		Map<String, Object[]> portletIds =
			new LinkedHashMap<String, Object[]>();

		List<Layout> layouts = null;

		if ((layoutIds == null) || (layoutIds.length == 0)) {
			layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		}
		else {
			layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, layoutIds);
		}

		// TODO Always Exportable Portlets

		/*List<Portlet> portlets = LayoutExporter.getAlwaysExportablePortlets(
			companyId);

		long plid = LayoutConstants.DEFAULT_PLID;

		if (!layouts.isEmpty()) {
			Layout firstLayout = layouts.get(0);

			plid = firstLayout.getPlid();
		}

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		for (Portlet portlet : portlets) {
			String portletId = portlet.getRootPortletId();

			if (!group.isStagedPortlet(portletId)) {
				continue;
			}

			String key = PortletPermissionUtil.getPrimaryKey(0, portletId);

			if (portletIds.get(key) == null) {
				portletIds.put(
					key,
					new Object[] {
						portletId, plid, groupId, StringPool.BLANK,
						StringPool.BLANK
					});
			}
		}*/

		// TODO End Always exportable portlets

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		// Layouts

		Object layoutLarPersistenceObj = PortalBeanLocatorUtil.locate(
			"com.liferay.portal.service.persistence.lar.LayoutLarPersistence");

		LayoutLarPersistence layoutLarPersistence =
			(LayoutLarPersistence)layoutLarPersistenceObj;

		for (Layout layout : layouts) {
			layoutLarPersistence.digest(layout, larDigest, _portletDataContext);
		}

		if (_log.isInfoEnabled()) {
			if (stopWatch != null) {
				_log.info(
					"Exporting layouts takes " + stopWatch.getTime() + " ms");
			}
			else {
				_log.info("Exporting layouts is finished");
			}
		}

		if (updateLastPublishDate) {
			LayoutExporter.updateLastPublishDate(layoutSet, lastPublishDate);
		}
	}

	protected void doExport(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws Exception, PortalException {

		for (LarDigestItem item : _larDigest) {

			if (item.getType().equals(Layout.class.getName())) {
				LayoutLarPersistence layoutLarPersistence =
					(LayoutLarPersistence)PortalBeanLocatorUtil.locate(
						LayoutLarPersistence.class.getName());

				Layout layout = LayoutLocalServiceUtil.getLayout(
					Long.valueOf(item.getClassPK()));

				layoutLarPersistence.serialize(layout, _portletDataContext);
			}
			else if (item.getType().equals(BookmarksEntry.class.getName())) {
				BookmarksEntryLarPersistence bookmarksEntryLarPersistence =
					(BookmarksEntryLarPersistence)PortalBeanLocatorUtil.locate(
						BookmarksEntryLarPersistence.class.getName());

				BookmarksEntry bookmarksEntry =
					BookmarksEntryLocalServiceUtil.getBookmarksEntry(
						Long.valueOf(item.getClassPK()));

				bookmarksEntryLarPersistence.serialize(
					bookmarksEntry, _portletDataContext);
			}
			else if (item.getType().equals(BookmarksFolder.class.getName())) {
				BookmarksFolderLarPersistence bookmarksFolderLarPersistence =
					(BookmarksFolderLarPersistence)PortalBeanLocatorUtil.locate(
						BookmarksFolderLarPersistence.class.getName());

				BookmarksFolder bookmarksFolder =
					BookmarksFolderLocalServiceUtil.getBookmarksFolder(
						Long.valueOf(item.getClassPK()));

				bookmarksFolderLarPersistence.serialize(
					bookmarksFolder, _portletDataContext);
			}
		}
	}

	protected void exportAssetCategories(PortletDataContext portletDataContext)
			throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("categories-hierarchy");

		Element assetVocabulariesElement = rootElement.addElement(
			"vocabularies");

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyLocalServiceUtil.getGroupVocabularies(
				portletDataContext.getGroupId());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			_portletExporter.exportAssetVocabulary(
				portletDataContext, assetVocabulariesElement, assetVocabulary);
		}

		Element categoriesElement = rootElement.addElement("categories");

		List<AssetCategory> assetCategories = AssetCategoryUtil.findByGroupId(
				portletDataContext.getGroupId());

		for (AssetCategory assetCategory : assetCategories) {
			_portletExporter.exportAssetCategory(
				portletDataContext, assetVocabulariesElement, categoriesElement,
				assetCategory);
		}

		_portletExporter.exportAssetCategories(portletDataContext, rootElement);

		portletDataContext.addZipEntry(
			portletDataContext.getRootPath() + "/categories-hierarchy.xml",
			document.formattedString());
	}

	protected void exportTheme(LayoutSet layoutSet, ZipWriter zipWriter)
			throws Exception {

		Theme theme = layoutSet.getTheme();

		String lookAndFeelXML = ContentUtil.get(
			"com/liferay/portal/dependencies/liferay-look-and-feel.xml.tmpl");

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$TEMPLATE_EXTENSION$]", "[$VIRTUAL_PATH$]"
			},
			new String[] {
				theme.getTemplateExtension(), theme.getVirtualPath()
			}
		);

		String servletContextName = theme.getServletContextName();

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return;
		}

		File themeZip = new File(zipWriter.getPath() + "/theme.zip");

		ZipWriter themeZipWriter = ZipWriterFactoryUtil.getZipWriter(themeZip);

		themeZipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		File cssPath = null;
		File imagesPath = null;
		File javaScriptPath = null;
		File templatesPath = null;

		if (!theme.isLoadFromServletContext()) {
			ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
				servletContextName);

			if (themeLoader == null) {
				_log.error(
					servletContextName + " does not map to a theme loader");
			}
			else {
				String realPath =
					themeLoader.getFileStorage().getPath() + StringPool.SLASH +
						theme.getName();

				cssPath = new File(realPath + "/css");
				imagesPath = new File(realPath + "/images");
				javaScriptPath = new File(realPath + "/javascript");
				templatesPath = new File(realPath + "/templates");
			}
		}
		else {
			cssPath = new File(servletContext.getRealPath(theme.getCssPath()));
			imagesPath = new File(
				servletContext.getRealPath(theme.getImagesPath()));
			javaScriptPath = new File(
				servletContext.getRealPath(theme.getJavaScriptPath()));
			templatesPath = new File(
				servletContext.getRealPath(theme.getTemplatesPath()));
		}

		exportThemeFiles("css", cssPath, themeZipWriter);
		exportThemeFiles("images", imagesPath, themeZipWriter);
		exportThemeFiles("javascript", javaScriptPath, themeZipWriter);
		exportThemeFiles("templates", templatesPath, themeZipWriter);
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
			throws Exception {

		if ((dir == null) || !dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				exportThemeFiles(
					path + StringPool.SLASH + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
						path + StringPool.SLASH + file.getName(),
						FileUtil.getBytes(file));
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LARExporter.class);

	private File _larDigestFile;
	private LarDigest _larDigest;
	private PortletDataContext _portletDataContext;
	private LayoutExporter _layoutExporter = new LayoutExporter();
	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();

}