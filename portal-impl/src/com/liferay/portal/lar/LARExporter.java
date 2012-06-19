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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ImportExportThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.model.*;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.*;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.util.ContentUtil;

import java.io.File;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class LARExporter {

	public void export(
		long groupId, boolean privateLayout, long[] layoutIds,
		Map<String, String[]> parameterMap, Date startDate, Date endDate) {

		LarDigest larDigest = null;

		try {
			ImportExportThreadLocal.setLayoutExportInProcess(true);

			larDigest = new LarDigestImpl();

			doCreateDigest(
				groupId, larDigest, privateLayout, layoutIds, parameterMap,
				startDate, endDate);

		}
		catch (Exception ex) {
			_log.error(ex);
		}
		finally {
			ImportExportThreadLocal.setLayoutExportInProcess(false);
			larDigest.close(Boolean.TRUE);
		}

		_larDigest = larDigest.getDigestFile();
	}

	public File getDigestFile() {
		return _larDigest;
	}

	protected void doCreateDigest(
			long groupId, LarDigest larDigest, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception, PortalException {

		boolean exportIgnoreLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE);
		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean updateLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		long lastPublishDate = System.currentTimeMillis();

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

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), startDate,
			endDate, null);

		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));

		Group group = layoutSet.getGroup();

		Portlet layoutConfigurationPortlet =
			PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(),
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

		// Always Exportable Portlets
		List<Portlet> portlets = LayoutExporter.getAlwaysExportablePortlets(
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
		}

		// LayoutSetPrototype
		String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);

			larDigest.write(
				LarDigesterConstants.ACTION_ADD, "",
				LayoutSetPrototype.class.getName(),
				StringUtil.valueOf(
					layoutSetPrototype.getLayoutSetPrototypeId()));
		}

		// Layouts
		for (Layout layout : layouts) {
			_layoutExporter.createLayoutDigest(
				larDigest, portletDataContext, layoutConfigurationPortlet,
				layoutCache, portlets, portletIds, exportPermissions, layout);
		}

		// Portlets
		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			Object[] portletObjects = portletIdsEntry.getValue();

			String portletId = null;
			plid = LayoutConstants.DEFAULT_PLID;
			long scopeGroupId = 0;
			String scopeType = StringPool.BLANK;
			String scopeLayoutUuid = null;

			if (portletObjects.length == 4) {
				portletId = (String)portletIdsEntry.getValue()[0];
				plid = (Long)portletIdsEntry.getValue()[1];
				scopeGroupId = (Long)portletIdsEntry.getValue()[2];
				scopeLayoutUuid = (String)portletIdsEntry.getValue()[3];
			}
			else {
				portletId = (String)portletIdsEntry.getValue()[0];
				plid = (Long)portletIdsEntry.getValue()[1];
				scopeGroupId = (Long)portletIdsEntry.getValue()[2];
				scopeType = (String)portletIdsEntry.getValue()[3];
				scopeLayoutUuid = (String)portletIdsEntry.getValue()[4];
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (layout == null) {
				if (!group.isCompany() &&
					(plid <= LayoutConstants.DEFAULT_PLID)) {

					continue;
				}

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Assuming global scope because no layout was found");
				}

				layout = new LayoutImpl();

				layout.setGroupId(groupId);
				layout.setCompanyId(companyId);
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(plid);
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			boolean[] exportPortletControls =
				_layoutExporter.getExportPortletControls(
					companyId, portletId, portletDataContext, parameterMap);

			_portletExporter.createPortletDigest(
				portletDataContext, layoutCache, portletId, layout, larDigest,
				defaultUserId, exportPermissions, exportPortletArchivedSetups,
				exportPortletControls[0], exportPortletControls[1],
				exportPortletUserPreferences);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

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
			Group group, PortletDataContext portletDataContext,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws Exception, PortalException {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean exportPermissions = MapUtil.getBoolean(
		parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);

		if (exportCategories || group.isCompany()) {
			exportAssetCategories(portletDataContext);
		}

		_portletExporter.exportAssetLinks(portletDataContext);
		_portletExporter.exportAssetTags(portletDataContext);
		_portletExporter.exportComments(portletDataContext);
		_portletExporter.exportExpandoTables(portletDataContext);
		_portletExporter.exportLocks(portletDataContext);

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(
				portletDataContext);
		}

		_portletExporter.exportRatingsEntries(portletDataContext);

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), privateLayout);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		if (exportTheme && !portletDataContext.isPerformDirectBinaryImport()) {
			exportTheme(layoutSet, zipWriter);
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

	private File _larDigest;
	private LayoutExporter _layoutExporter = new LayoutExporter();
	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();

}