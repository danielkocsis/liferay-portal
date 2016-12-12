/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.internal.exportimport.data.handler;

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_EXPORT_SUCCEEDED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_FAILED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_STARTED;
import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_SUCCEEDED;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.lar.LayoutCache;
import com.liferay.exportimport.lar.PermissionExporter;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessorRegistryUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletItemLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.internal.exportimport.staged.model.repository.StagedPortletStagedModelRepository;
import com.liferay.portlet.model.adapter.StagedPortlet;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedPortletStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedPortlet> {

	public static final String[] CLASS_NAMES = {StagedPortlet.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedPortlet stagedPortlet) {
		return stagedPortlet.getPortletName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, StagedPortlet stagedPortlet)
		throws Exception {

		Map<String, Boolean> exportPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				portletDataContext.getCompanyId(), stagedPortlet.getPortletId(),
				portletDataContext.getParameterMap(),
				portletDataContext.getType());

		Element portletElement = portletDataContext.getExportDataElement(
			stagedPortlet);

		boolean permissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		try {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_EXPORT_STARTED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			exportPortlet(
				portletDataContext, stagedPortlet, portletDataContext.getPlid(),
				portletElement, permissions,
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

			exportService(portletDataContext, stagedPortlet);

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_EXPORT_SUCCEEDED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));
		}
		catch (Throwable t) {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_EXPORT_FAILED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}

		// Serialize the portlet

		portletElement.addAttribute("portletId", stagedPortlet.getPortletId());

		portletDataContext.addClassedModel(
			portletElement, ExportImportPathUtil.getModelPath(stagedPortlet),
			stagedPortlet);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, StagedPortlet stagedPortlet)
		throws Exception {

		// Initialize the import context

		Element portletElement = portletDataContext.getImportDataElement(
			stagedPortlet);

		Layout layout = _layoutLocalService.fetchLayout(
			portletDataContext.getPlid());

		if (layout == null) {
			long layoutId = GetterUtil.getLong(
				portletElement.attributeValue("layout-id"));

			Map<Long, Layout> layouts =
				(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class + ".layout");

			layout = layouts.get(layoutId);
		}

		boolean layoutSetPrototypeLinkEnabled = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED);

		if (layoutSetPrototypeLinkEnabled &&
			Validator.isNotNull(
				portletDataContext.getLayoutSetPrototypeUuid()) &&
			SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {

			return;
		}

		String portletId = portletDataContext.getPortletId();

		if (Validator.isNull(portletId)) {
			portletId = portletElement.attributeValue("portlet-id");
		}

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
			return;
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"portlet", portletId, portletDataContext.getManifestSummary());
		}

		String portletManifestPath = portletElement.attributeValue(
			"portlet-manifest-path");

		Document portletManifest = SAXReaderUtil.read(
			portletDataContext.getZipEntryAsString(portletManifestPath));

		Element portletManifestRootElementElement =
			portletManifest.getRootElement();

		long plid = portletDataContext.getPlid();

		long oldPlid = GetterUtil.getLong(
			portletManifestRootElementElement.attributeValue("old-plid"));

		if (plid == 0) {
			Map<Long, Long> plids =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Layout.class);

			plid = MapUtil.getLong(plids, oldPlid);
		}

		// Import the portlet

		long originalPlid = portletDataContext.getPlid();
		String originalPortletId = portletDataContext.getPortletId();

		portletDataContext.setPlid(plid);
		portletDataContext.setOldPlid(oldPlid);
		portletDataContext.setPortletId(portletId);

		try {
			importPortlet(
				portletDataContext, portletManifestRootElementElement, layout);
		}
		finally {
			portletDataContext.setPlid(originalPlid);
			portletDataContext.setPortletId(originalPortletId);
		}

		// Portlet permissions

		boolean permissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (permissions) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			_permissionImporter.importPortletPermissions(
				new LayoutCache(), portletDataContext.getCompanyId(),
				portletDataContext.getGroupId(), serviceContext.getUserId(),
				layout, portletManifestRootElementElement, portletId);
		}
	}

	protected void exportPortlet(
			PortletDataContext portletDataContext, StagedPortlet portlet,
			long plid, Element portletElement, boolean exportPermissions,
			boolean exportPortletArchivedSetups, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletUserPreferences)
		throws Exception {

		long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout != null) {
			plid = layout.getPlid();
			layoutId = layout.getLayoutId();
		}

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet " +
						portletDataContext.getPortletId() +
							" because the portlet is not deployed");
			}

			return;
		}

		if (!portlet.isInstanceable() &&
			!portlet.isPreferencesUniquePerLayout() &&
			portletDataContext.hasNotUniquePerLayout(
				portletDataContext.getPortletId())) {

			return;
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			PortletDataContext clonedPortletDataContext =
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext);

			ManifestSummary manifestSummary =
				clonedPortletDataContext.getManifestSummary();

			manifestSummary.resetCounters();

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			portletDataHandler.prepareManifestSummary(clonedPortletDataContext);

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"portlet", portletDataContext.getPortletId(), manifestSummary);
		}

		Document document = SAXReaderUtil.createDocument();

		Element portletRootElement = document.addElement("portlet");

		portletRootElement.addAttribute(
			"portlet-id", portletDataContext.getPortletId());
		portletRootElement.addAttribute(
			"root-portlet-id", portletDataContext.getRootPortletId());
		portletRootElement.addAttribute("old-plid", String.valueOf(plid));
		portletRootElement.addAttribute(
			"scope-group-id",
			String.valueOf(portletDataContext.getScopeGroupId()));
		portletRootElement.addAttribute(
			"scope-layout-type", portletDataContext.getScopeType());
		portletRootElement.addAttribute(
			"scope-layout-uuid", portletDataContext.getScopeLayoutUuid());
		portletRootElement.addAttribute(
			"private-layout",
			String.valueOf(portletDataContext.isPrivateLayout()));

		// Data

		if (exportPortletData) {
			javax.portlet.PortletPreferences jxPortletPreferences = null;

			if (ExportImportThreadLocal.isInitialLayoutStagingInProcess()) {
				if (layout != null) {
					Group liveGroup = layout.getGroup();

					Group stagingGroup = liveGroup.getStagingGroup();

					layout.setGroupId(stagingGroup.getGroupId());

					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							layout, portletDataContext.getPortletId());

					layout.setGroupId(liveGroup.getGroupId());
				}
				else {
					Group liveGroup = _groupLocalService.getGroup(
						portletDataContext.getGroupId());

					Group stagingGroup = liveGroup.getStagingGroup();

					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							portletDataContext.getCompanyId(),
							stagingGroup.getGroupId(),
							portletDataContext.getPortletId());
				}
			}
			else {
				if (layout != null) {
					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							layout, portletDataContext.getPortletId());
				}
				else {
					jxPortletPreferences =
						PortletPreferencesFactoryUtil.getStrictPortletSetup(
							portletDataContext.getCompanyId(),
							portletDataContext.getGroupId(),
							portletDataContext.getPortletId());
				}
			}

			if (!portlet.isPreferencesUniquePerLayout()) {
				StringBundler sb = new StringBundler(5);

				sb.append(portletDataContext.getPortletId());
				sb.append(StringPool.AT);
				sb.append(portletDataContext.getScopeType());
				sb.append(StringPool.AT);
				sb.append(portletDataContext.getScopeLayoutUuid());

				String dataKey = sb.toString();

				if (!portletDataContext.hasNotUniquePerLayout(dataKey)) {
					portletDataContext.putNotUniquePerLayout(dataKey);

					exportPortletData(
						portletDataContext, portlet, layout,
						jxPortletPreferences, portletRootElement);
				}
			}
			else {
				exportPortletData(
					portletDataContext, portlet, layout, jxPortletPreferences,
					portletRootElement);
			}
		}

		// Portlet preferences

		if (exportPortletSetup) {

			// Company

			exportPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, false, layout, plid,
				portlet.getRootPortletId(), portletRootElement);

			// Group

			exportPortletPreferences(
				portletDataContext, portletDataContext.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, false, layout,
				PortletKeys.PREFS_PLID_SHARED, portlet.getRootPortletId(),
				portletRootElement);

			// Layout

			exportPortletPreferences(
				portletDataContext, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout, plid,
				portletDataContext.getPortletId(), portletRootElement);
		}

		// Portlet user preferences

		if (exportPortletUserPreferences) {
			List<PortletPreferences> portletPreferencesList =
				_portletPreferencesLocalService.getPortletPreferences(
					PortletKeys.PREFS_OWNER_TYPE_USER, plid,
					portletDataContext.getPortletId());

			for (PortletPreferences portletPreferences :
					portletPreferencesList) {

				boolean defaultUser = false;

				if (portletPreferences.getOwnerId() ==
						PortletKeys.PREFS_OWNER_ID_DEFAULT) {

					defaultUser = true;
				}

				exportPortletPreferences(
					portletDataContext, portletPreferences.getOwnerId(),
					PortletKeys.PREFS_OWNER_TYPE_USER, defaultUser, layout,
					plid, portletDataContext.getPortletId(),
					portletRootElement);
			}

			try {
				PortletPreferences groupPortletPreferences =
					_portletPreferencesLocalService.getPortletPreferences(
						portletDataContext.getScopeGroupId(),
						PortletKeys.PREFS_OWNER_TYPE_GROUP,
						PortletKeys.PREFS_PLID_SHARED,
						portlet.getRootPortletId());

				exportPortletPreference(
					portletDataContext, portletDataContext.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
					groupPortletPreferences, portlet.getRootPortletId(),
					PortletKeys.PREFS_PLID_SHARED, portletRootElement);
			}
			catch (NoSuchPortletPreferencesException nsppe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nsppe, nsppe);
				}
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			List<PortletItem> portletItems =
				_portletItemLocalService.getPortletItems(
					portletDataContext.getGroupId(),
					portletDataContext.getRootPortletId(),
					PortletPreferences.class.getName());

			for (PortletItem portletItem : portletItems) {
				exportPortletPreferences(
					portletDataContext, portletItem.getPortletItemId(),
					PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, false, null, plid,
					portletItem.getPortletId(), portletRootElement);
			}
		}

		// Permissions

		if (exportPermissions) {
			_permissionExporter.exportPortletPermissions(
				portletDataContext, portletDataContext.getPortletId(), layout,
				portletRootElement);
		}

		// Zip

		StringBundler pathSB = new StringBundler(4);

		pathSB.append(ExportImportPathUtil.getPortletPath(portletDataContext));
		pathSB.append(StringPool.SLASH);
		pathSB.append(plid);
		pathSB.append("/portlet.xml");

		String portletManifestPath = pathSB.toString();

		portletElement.addAttribute(
			"portlet-id", portletDataContext.getPortletId());
		portletElement.addAttribute("layout-id", String.valueOf(layoutId));
		portletElement.addAttribute(
			"portlet-manifest-path", portletManifestPath);
		portletElement.addAttribute(
			"portlet-data", String.valueOf(exportPortletData));

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		portletElement.addAttribute(
			"schema-version", portletDataHandler.getSchemaVersion());

		StringBundler configurationOptionsSB = new StringBundler(6);

		if (exportPortletSetup) {
			configurationOptionsSB.append("setup");
			configurationOptionsSB.append(StringPool.COMMA);
		}

		if (exportPortletArchivedSetups) {
			configurationOptionsSB.append("archived-setups");
			configurationOptionsSB.append(StringPool.COMMA);
		}

		if (exportPortletUserPreferences) {
			configurationOptionsSB.append("user-preferences");
			configurationOptionsSB.append(StringPool.COMMA);
		}

		if (configurationOptionsSB.index() > 0) {
			configurationOptionsSB.setIndex(configurationOptionsSB.index() - 1);
		}

		portletElement.addAttribute(
			"portlet-configuration", configurationOptionsSB.toString());

		try {
			portletDataContext.addZipEntry(
				portletManifestPath, document.formattedString());
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe.getMessage());
			}
		}
	}

	protected void exportPortletData(
			PortletDataContext portletDataContext, Portlet portlet,
			Layout layout,
			javax.portlet.PortletPreferences jxPortletPreferences,
			Element parentElement)
		throws Exception {

		if (portlet == null) {
			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if ((portletDataHandler == null) ||
			portletDataHandler.isDataPortletInstanceLevel()) {

			return;
		}

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		long plid = LayoutConstants.DEFAULT_PLID;

		if (layout != null) {
			group = layout.getGroup();
			plid = layout.getPlid();
		}

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		String portletId = portlet.getPortletId();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!group.isStagedPortlet(portletId)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because it is configured not to be staged");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(
			ExportImportPathUtil.getPortletPath(portletDataContext, portletId));
		sb.append(StringPool.SLASH);

		if (portlet.isPreferencesUniquePerLayout()) {
			sb.append(plid);
		}
		else {
			sb.append(portletDataContext.getScopeGroupId());
		}

		sb.append("/portlet-data.xml");

		String path = sb.toString();

		if (portletDataContext.hasPrimaryKey(String.class, path)) {
			return;
		}

		Date originalStartDate = portletDataContext.getStartDate();

		Date portletLastPublishDate = ExportImportDateUtil.getLastPublishDate(
			portletDataContext, jxPortletPreferences);

		portletDataContext.setStartDate(portletLastPublishDate);

		long groupId = portletDataContext.getGroupId();

		portletDataContext.setGroupId(portletDataContext.getScopeGroupId());

		portletDataContext.clearScopedPrimaryKeys();

		String data = null;

		try {
			data = portletDataHandler.exportData(
				portletDataContext, portletId, jxPortletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			portletDataContext.setGroupId(groupId);
			portletDataContext.setStartDate(originalStartDate);
		}

		if (Validator.isNull(data)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		Element portletDataElement = parentElement.addElement("portlet-data");

		portletDataElement.addAttribute("path", path);

		portletDataContext.addZipEntry(path, data);

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			updateLastPublishDate) {

			DateRange adjustedDateRange = new DateRange(
				portletLastPublishDate, portletDataContext.getEndDate());

			ExportImportProcessCallbackRegistryUtil.registerCallback(
				new UpdatePortletLastPublishDateCallable(
					adjustedDateRange, portletDataContext.getEndDate(),
					portletDataContext.getGroupId(), plid, portletId));
		}
	}

	protected void exportPortletPreference(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			boolean defaultUser, PortletPreferences portletPreferences,
			String portletId, long plid, Element parentElement)
		throws Exception {

		String preferencesXML = portletPreferences.getPreferences();

		if (Validator.isNull(preferencesXML)) {
			preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		Element portletPreferencesElement = parentElement.addElement(
			"portlet-preferences");

		if ((portlet != null) &&
			(portlet.getPortletDataHandlerInstance() != null)) {

			Element exportDataRootElement =
				portletDataContext.getExportDataRootElement();

			try {
				portletDataContext.clearScopedPrimaryKeys();

				Element preferenceDataElement =
					portletPreferencesElement.addElement("preference-data");

				portletDataContext.setExportDataRootElement(
					preferenceDataElement);

				ExportImportPortletPreferencesProcessor
					exportImportPortletPreferencesProcessor =
						ExportImportPortletPreferencesProcessorRegistryUtil.
							getExportImportPortletPreferencesProcessor(
								portlet.getRootPortletId());

				if (exportImportPortletPreferencesProcessor != null) {
					List<Capability> exportCapabilities =
						exportImportPortletPreferencesProcessor.
							getExportCapabilities();

					if (ListUtil.isNotEmpty(exportCapabilities)) {
						for (Capability exportCapability : exportCapabilities) {
							exportCapability.process(
								portletDataContext, jxPortletPreferences);
						}
					}

					exportImportPortletPreferencesProcessor.
						processExportPortletPreferences(
							portletDataContext, jxPortletPreferences);
				}
				else {
					PortletDataHandler portletDataHandler =
						portlet.getPortletDataHandlerInstance();

					jxPortletPreferences =
						portletDataHandler.processExportPortletPreferences(
							portletDataContext, portletId,
							jxPortletPreferences);
				}
			}
			finally {
				portletDataContext.setExportDataRootElement(
					exportDataRootElement);
			}
		}

		Document document = SAXReaderUtil.read(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("owner-id", String.valueOf(ownerId));
		rootElement.addAttribute("owner-type", String.valueOf(ownerType));
		rootElement.addAttribute("default-user", String.valueOf(defaultUser));
		rootElement.addAttribute("plid", String.valueOf(plid));
		rootElement.addAttribute("portlet-id", portletId);

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			PortletItem portletItem = _portletItemLocalService.getPortletItem(
				ownerId);

			rootElement.addAttribute(
				"archive-user-uuid", portletItem.getUserUuid());
			rootElement.addAttribute("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = _userLocalService.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			rootElement.addAttribute("user-uuid", user.getUserUuid());
		}

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			node.detach();
		}

		String path = ExportImportPathUtil.getPortletPreferencesPath(
			portletDataContext, portletId, ownerId, ownerType, plid);

		portletPreferencesElement.addAttribute("path", path);

		portletDataContext.addZipEntry(
			path, document.formattedString(StringPool.TAB, false, false));
	}

	protected void exportPortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			boolean defaultUser, Layout layout, long plid, String portletId,
			Element parentElement)
		throws Exception {

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = getPortletPreferences(
				ownerId, ownerType, plid, portletId);
		}
		catch (NoSuchPortletPreferencesException nsppe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsppe, nsppe);
			}

			return;
		}

		LayoutTypePortlet layoutTypePortlet = null;

		if (layout != null) {
			layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
		}

		if ((layoutTypePortlet == null) ||
			layoutTypePortlet.hasPortletId(portletId)) {

			exportPortletPreference(
				portletDataContext, ownerId, ownerType, defaultUser,
				portletPreferences, portletId, plid, parentElement);
		}
	}

	protected void exportService(
			PortletDataContext portletDataContext, StagedPortlet portlet)
		throws Exception {

		Map<String, Boolean> exportPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				portletDataContext.getCompanyId(), portlet.getPortletId(),
				portletDataContext.getParameterMap(),
				portletDataContext.getType());

		if (!exportPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_SETUP)) {

			return;
		}

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				portletDataContext.getPortletId());

		if (portletDataHandler == null) {
			return;
		}

		String serviceName = portletDataHandler.getServiceName();

		if (Validator.isNotNull(serviceName)) {

			// Company service

			exportServicePortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, serviceName,
				portletDataContext.getExportDataRootElement());

			// Group service

			exportServicePortletPreferences(
				portletDataContext, portletDataContext.getScopeGroupId(),
				PortletKeys.PREFS_OWNER_TYPE_GROUP, serviceName,
				portletDataContext.getExportDataRootElement());
		}
	}

	protected void exportServicePortletPreference(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			PortletPreferences portletPreferences, String serviceName,
			Element parentElement)
		throws Exception {

		String preferencesXML = portletPreferences.getPreferences();

		if (Validator.isNull(preferencesXML)) {
			preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
		}

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

		Document document = SAXReaderUtil.read(
			PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

		Element rootElement = document.getRootElement();

		rootElement.addAttribute("owner-id", String.valueOf(ownerId));
		rootElement.addAttribute("owner-type", String.valueOf(ownerType));
		rootElement.addAttribute("default-user", String.valueOf(false));
		rootElement.addAttribute("service-name", serviceName);

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			PortletItem portletItem = _portletItemLocalService.getPortletItem(
				ownerId);

			rootElement.addAttribute(
				"archive-user-uuid", portletItem.getUserUuid());
			rootElement.addAttribute("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = _userLocalService.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			rootElement.addAttribute("user-uuid", user.getUserUuid());
		}

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			node.detach();
		}

		Element serviceElement = parentElement.addElement("service");

		String path = ExportImportPathUtil.getServicePortletPreferencesPath(
			portletDataContext, serviceName, ownerId, ownerType);

		serviceElement.addAttribute("path", path);

		serviceElement.addAttribute("service-name", serviceName);

		portletDataContext.addZipEntry(path, document.formattedString());
	}

	protected void exportServicePortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			String serviceName, Element parentElement)
		throws Exception {

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences = getPortletPreferences(
				ownerId, ownerType, LayoutConstants.DEFAULT_PLID, serviceName);
		}
		catch (NoSuchPortletPreferencesException nsppe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsppe, nsppe);
			}

			return;
		}

		exportServicePortletPreference(
			portletDataContext, ownerId, ownerType, portletPreferences,
			serviceName, parentElement);
	}

	protected PortletPreferences getPortletPreferences(
			long ownerId, int ownerType, long plid, String portletId)
		throws PortalException {

		PortletPreferences portletPreferences = null;

		if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP)) {

			portletPreferences =
				_portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, LayoutConstants.DEFAULT_PLID,
					portletId);
		}
		else {
			portletPreferences =
				_portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, plid, portletId);
		}

		return portletPreferences;
	}

	protected PortletPreferences getPortletPreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String serviceName)
		throws PortalException {

		PortletPreferences portletPreferences = null;

		try {
			if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) ||
				(ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
				(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP)) {

				portletPreferences =
					_portletPreferencesLocalService.getPortletPreferences(
						ownerId, ownerType, LayoutConstants.DEFAULT_PLID,
						serviceName);
			}
			else {
				portletPreferences =
					_portletPreferencesLocalService.getPortletPreferences(
						ownerId, ownerType, plid, serviceName);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsppe, nsppe);
			}

			portletPreferences =
				_portletPreferencesLocalService.addPortletPreferences(
					companyId, ownerId, ownerType, plid, serviceName, null,
					null);
		}

		return portletPreferences;
	}

	@Override
	protected StagedModelRepository<StagedPortlet> getStagedModelRepository() {
		return _stagedPortletStagedModelRepository;
	}

	protected void importPortlet(
			PortletDataContext portletDataContext, Element rootElement,
			Layout layout)
		throws Exception {

		// The order of the import is important. You must always import the
		// portlet preferences first, then the portlet data, then the
		// portlet permissions. The import of the portlet data assumes that
		// portlet preferences already exist.

		setPortletScope(portletDataContext, rootElement);

		long portletPreferencesGroupId = portletDataContext.getGroupId();

		Element portletDataElement = rootElement.element("portlet-data");

		Map<String, Boolean> importPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				portletDataContext.getCompanyId(),
				portletDataContext.getPortletId(),
				portletDataContext.getParameterMap(), portletDataElement,
				portletDataContext.getManifestSummary());

		if (layout != null) {
			portletPreferencesGroupId = layout.getGroupId();
		}

		try {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_STARTED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			// Portlet preferences

			importPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				portletPreferencesGroupId, layout, rootElement, false,
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

			// Portlet data

			if (importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA)) {

				importPortletData(portletDataContext, portletDataElement);
			}

			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_SUCCEEDED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));
		}
		catch (Throwable t) {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_FAILED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext),
				t);

			throw t;
		}
		finally {
			resetPortletScope(portletDataContext, portletPreferencesGroupId);
		}

		// Archived setups

		importPortletPreferences(
			portletDataContext, portletDataContext.getCompanyId(),
			portletDataContext.getGroupId(), null, rootElement, false,
			importPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
			importPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_DATA),
			importPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_SETUP),
			importPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
	}

	protected void importPortletData(
			PortletDataContext portletDataContext, Element portletDataElement)
		throws Exception {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		javax.portlet.PortletPreferences portletPreferences =
			_portletPreferencesLocalService.fetchPreferences(
				portletDataContext.getCompanyId(), ownerId, ownerType,
				portletDataContext.getPlid(),
				portletDataContext.getPortletId());

		if (portletPreferences == null) {
			portletPreferences = new PortletPreferencesImpl();
		}

		String xml = importPortletData(
			portletDataContext, portletPreferences, portletDataElement);

		if (Validator.isNotNull(xml)) {
			_portletPreferencesLocalService.updatePreferences(
				ownerId, ownerType, portletDataContext.getPlid(),
				portletDataContext.getPortletId(), xml);
		}
	}

	protected String importPortletData(
			PortletDataContext portletDataContext,
			javax.portlet.PortletPreferences portletPreferences,
			Element portletDataElement)
		throws Exception {

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				portletDataContext.getCompanyId(),
				portletDataContext.getPortletId());

		if ((portletDataHandler == null) ||
			portletDataHandler.isDataPortletInstanceLevel()) {

			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Do not import portlet data for ");
				sb.append(portletDataContext.getPortletId());
				sb.append(" because the portlet does not have a portlet data ");
				sb.append("handler");

				_log.debug(sb.toString());
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Importing data for " + portletDataContext.getPortletId());
		}

		String portletData = portletDataContext.getZipEntryAsString(
			portletDataElement.attributeValue("path"));

		if (Validator.isNull(portletData)) {
			return null;
		}

		portletPreferences = portletDataHandler.importData(
			portletDataContext, portletDataContext.getPortletId(),
			portletPreferences, portletData);

		if (portletPreferences == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void importPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long groupId,
			Layout layout, Element parentElement, boolean preserveScopeLayoutId,
			boolean importPortletArchivedSetups, boolean importPortletData,
			boolean importPortletSetup, boolean importPortletUserPreferences)
		throws Exception {

		long plid = LayoutConstants.DEFAULT_PLID;
		String scopeType = StringPool.BLANK;
		String scopeLayoutUuid = StringPool.BLANK;

		if (layout != null) {
			plid = layout.getPlid();

			if (preserveScopeLayoutId) {
				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletDataContext.getPortletId());

				scopeType = GetterUtil.getString(
					jxPortletPreferences.getValue("lfrScopeType", null));
				scopeLayoutUuid = GetterUtil.getString(
					jxPortletPreferences.getValue("lfrScopeLayoutUuid", null));

				portletDataContext.setScopeType(scopeType);
				portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}

		List<Element> portletPreferencesElements = parentElement.elements(
			"portlet-preferences");

		for (Element portletPreferencesElement : portletPreferencesElements) {
			String path = portletPreferencesElement.attributeValue("path");

			if (portletDataContext.isPathNotProcessed(path)) {
				String xml = null;

				Element element = null;

				try {
					xml = portletDataContext.getZipEntryAsString(path);

					Document preferencesDocument = SAXReaderUtil.read(xml);

					element = preferencesDocument.getRootElement();
				}
				catch (DocumentException de) {
					throw new SystemException(de);
				}

				long ownerId = GetterUtil.getLong(
					element.attributeValue("owner-id"));
				int ownerType = GetterUtil.getInteger(
					element.attributeValue("owner-type"));

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
					!importPortletSetup) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) &&
					!importPortletArchivedSetups) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) &&
					(ownerId != PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
					!importPortletUserPreferences) {

					continue;
				}

				long curPlid = plid;
				String curPortletId = portletDataContext.getPortletId();

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
					curPlid = PortletKeys.PREFS_PLID_SHARED;
					curPortletId = portletDataContext.getRootPortletId();
					ownerId = portletDataContext.getScopeGroupId();
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
					String userUuid = element.attributeValue(
						"archive-user-uuid");

					long userId = portletDataContext.getUserId(userUuid);

					String name = element.attributeValue("archive-name");

					curPortletId = portletDataContext.getRootPortletId();

					PortletItem portletItem =
						_portletItemLocalService.updatePortletItem(
							userId, groupId, name, curPortletId,
							PortletPreferences.class.getName());

					curPlid = LayoutConstants.DEFAULT_PLID;
					ownerId = portletItem.getPortletItemId();
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
					String userUuid = element.attributeValue("user-uuid");

					ownerId = portletDataContext.getUserId(userUuid);
				}

				boolean defaultUser = GetterUtil.getBoolean(
					element.attributeValue("default-user"));

				if (defaultUser) {
					ownerId = _userLocalService.getDefaultUserId(companyId);
				}

				javax.portlet.PortletPreferences jxPortletPreferences =
					PortletPreferencesFactoryUtil.fromXML(
						companyId, ownerId, ownerType, curPlid, curPortletId,
						xml);

				Element importDataRootElement =
					portletDataContext.getImportDataRootElement();

				try {
					Element preferenceDataElement =
						portletPreferencesElement.element("preference-data");

					if (preferenceDataElement != null) {
						portletDataContext.setImportDataRootElement(
							preferenceDataElement);
					}

					ExportImportPortletPreferencesProcessor
						exportImportPortletPreferencesProcessor =
							ExportImportPortletPreferencesProcessorRegistryUtil.
								getExportImportPortletPreferencesProcessor(
									PortletConstants.getRootPortletId(
										curPortletId));

					if (exportImportPortletPreferencesProcessor != null) {
						List<Capability> importCapabilities =
							exportImportPortletPreferencesProcessor.
								getImportCapabilities();

						if (ListUtil.isNotEmpty(importCapabilities)) {
							for (Capability importCapability :
									importCapabilities) {

								importCapability.process(
									portletDataContext, jxPortletPreferences);
							}
						}

						exportImportPortletPreferencesProcessor.
							processImportPortletPreferences(
								portletDataContext, jxPortletPreferences);
					}
					else {
						PortletDataHandler portletDataHandler =
							_portletDataHandlerProvider.provide(
								portletDataContext.getCompanyId(),
								curPortletId);

						if (portletDataHandler != null) {
							jxPortletPreferences =
								portletDataHandler.
									processImportPortletPreferences(
										portletDataContext, curPortletId,
										jxPortletPreferences);
						}
					}
				}
				finally {
					portletDataContext.setImportDataRootElement(
						importDataRootElement);
				}

				updatePortletPreferences(
					portletDataContext, ownerId, ownerType, curPlid,
					curPortletId,
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences),
					importPortletData);
			}
		}

		if (preserveScopeLayoutId && (layout != null)) {
			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletDataContext.getPortletId());

			try {
				jxPortletPreferences.setValue("lfrScopeType", scopeType);
				jxPortletPreferences.setValue(
					"lfrScopeLayoutUuid", scopeLayoutUuid);

				jxPortletPreferences.store();
			}
			finally {
				portletDataContext.setScopeType(scopeType);
				portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}
	}

	protected void resetPortletScope(
		PortletDataContext portletDataContext, long groupId) {

		portletDataContext.setScopeGroupId(groupId);
		portletDataContext.setScopeLayoutUuid(StringPool.BLANK);
		portletDataContext.setScopeType(StringPool.BLANK);
	}

	protected void setPortletScope(
		PortletDataContext portletDataContext, Element portletElement) {

		// Portlet data scope

		String scopeLayoutUuid = GetterUtil.getString(
			portletElement.attributeValue("scope-layout-uuid"));
		String scopeLayoutType = GetterUtil.getString(
			portletElement.attributeValue("scope-layout-type"));

		portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
		portletDataContext.setScopeType(scopeLayoutType);

		// Layout scope

		try {
			Group scopeGroup = null;

			if (scopeLayoutType.equals("company")) {
				scopeGroup = _groupLocalService.getCompanyGroup(
					portletDataContext.getCompanyId());
			}
			else if (Validator.isNotNull(scopeLayoutUuid)) {
				Layout scopeLayout =
					_layoutLocalService.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, portletDataContext.getGroupId(),
						portletDataContext.isPrivateLayout());

				scopeGroup = _groupLocalService.checkScopeGroup(
					scopeLayout, portletDataContext.getUserId(null));

				Group group = scopeLayout.getGroup();

				if (group.isStaged() && !group.isStagedRemotely()) {
					try {
						boolean privateLayout = GetterUtil.getBoolean(
							portletElement.attributeValue("private-layout"));

						Layout oldLayout =
							_layoutLocalService.getLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								portletDataContext.getSourceGroupId(),
								privateLayout);

						Group oldScopeGroup = oldLayout.getScopeGroup();

						if (group.isStagingGroup()) {
							scopeGroup.setLiveGroupId(
								oldScopeGroup.getGroupId());

							_groupLocalService.updateGroup(scopeGroup);
						}
						else {
							oldScopeGroup.setLiveGroupId(
								scopeGroup.getGroupId());

							_groupLocalService.updateGroup(oldScopeGroup);
						}
					}
					catch (NoSuchLayoutException nsle) {
						if (_log.isWarnEnabled()) {
							_log.warn(nsle);
						}
					}
				}
			}

			if (scopeGroup != null) {
				portletDataContext.setScopeGroupId(scopeGroup.getGroupId());

				Map<Long, Long> groupIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						Group.class);

				long oldScopeGroupId = GetterUtil.getLong(
					portletElement.attributeValue("scope-group-id"));

				groupIds.put(oldScopeGroupId, scopeGroup.getGroupId());
			}
		}
		catch (PortalException pe) {
			_log.warn(pe);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void updatePortletPreferences(
			PortletDataContext portletDataContext, long ownerId, int ownerType,
			long plid, String portletId, String xml, boolean importData)
		throws Exception {

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(
				portletDataContext.getCompanyId(), portletId);

		// Current portlet preferences

		javax.portlet.PortletPreferences portletPreferences =
			_portletPreferencesLocalService.getPreferences(
				portletDataContext.getCompanyId(), ownerId, ownerType, plid,
				portletId);

		// New portlet preferences

		javax.portlet.PortletPreferences jxPortletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				portletDataContext.getCompanyId(), ownerId, ownerType, plid,
				portletId, xml);

		if (importData || !MergeLayoutPrototypesThreadLocal.isInProgress()) {
			String currentLastPublishDate = portletPreferences.getValue(
				"last-publish-date", null);
			String newLastPublishDate = jxPortletPreferences.getValue(
				"last-publish-date", null);

			if (Validator.isNotNull(currentLastPublishDate)) {
				jxPortletPreferences.setValue(
					"last-publish-date", currentLastPublishDate);
			}
			else if (Validator.isNotNull(newLastPublishDate)) {
				jxPortletPreferences.reset("last-publish-date");
			}

			_portletPreferencesLocalService.updatePreferences(
				ownerId, ownerType, plid, portletId,
				PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

			return;
		}

		// Portlet preferences will be updated only when importing data

		String[] dataPortletPreferences =
			portletDataHandler.getDataPortletPreferences();

		Enumeration<String> enu = jxPortletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String scopeLayoutUuid = portletDataContext.getScopeLayoutUuid();
			String scopeType = portletDataContext.getScopeType();

			if (!ArrayUtil.contains(dataPortletPreferences, name) ||
				(Validator.isNull(scopeLayoutUuid) &&
				 scopeType.equals("company"))) {

				String[] values = jxPortletPreferences.getValues(name, null);

				portletPreferences.setValues(name, values);
			}
		}

		_portletPreferencesLocalService.updatePreferences(
			ownerId, ownerType, plid, portletId, portletPreferences);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedPortletStagedModelDataHandler.class);

	@Reference
	private ExportImportLifecycleManager _exportImportLifecycleManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private final PermissionExporter _permissionExporter =
		PermissionExporter.getInstance();
	private final PermissionImporter _permissionImporter =
		PermissionImporter.getInstance();

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	@Reference
	private PortletItemLocalService _portletItemLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private StagedPortletStagedModelRepository
		_stagedPortletStagedModelRepository;

	@Reference
	private UserLocalService _userLocalService;

	private class UpdatePortletLastPublishDateCallable
		implements Callable<Void> {

		public UpdatePortletLastPublishDateCallable(
			DateRange dateRange, Date endDate, long groupId, long plid,
			String portletId) {

			_dateRange = dateRange;
			_endDate = endDate;
			_groupId = groupId;
			_plid = plid;
			_portletId = portletId;
		}

		@Override
		public Void call() throws PortalException {
			Group group = _groupLocalService.getGroup(_groupId);

			Layout layout = _layoutLocalService.fetchLayout(_plid);

			if (group.hasStagingGroup()) {
				group = group.getStagingGroup();

				if (layout != null) {
					layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
						layout.getUuid(), group.getGroupId(),
						layout.isPrivateLayout());
				}
			}

			javax.portlet.PortletPreferences jxPortletPreferences = null;

			if (layout == null) {
				jxPortletPreferences =
					PortletPreferencesFactoryUtil.getStrictPortletSetup(
						group.getCompanyId(), group.getGroupId(), _portletId);
			}
			else {
				jxPortletPreferences =
					PortletPreferencesFactoryUtil.getStrictPortletSetup(
						layout, _portletId);
			}

			ExportImportDateUtil.updateLastPublishDate(
				_portletId, jxPortletPreferences, _dateRange, _endDate);

			return null;
		}

		private final DateRange _dateRange;
		private final Date _endDate;
		private final long _groupId;
		private final long _plid;
		private final String _portletId;

	}

}