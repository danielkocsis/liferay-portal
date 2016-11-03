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

import com.liferay.exportimport.controller.PortletExportController;
import com.liferay.exportimport.controller.PortletImportController;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.lar.LayoutCache;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.internal.exportimport.staged.model.repository.StagedPortletStagedModelRepository;
import com.liferay.portlet.model.adapter.StagedPortlet;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.Map;

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

			_portletExportController.exportPortlet(
				portletDataContext, portletDataContext.getPlid(),
				portletElement, permissions,
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				exportPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

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

		Element portletElement = portletDataContext.getImportDataElement(
			stagedPortlet);

		portletElement = portletElement.element("portlet");

		long layoutId = GetterUtil.getLong(
			portletElement.attributeValue("layout-id"));

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		Layout layout = layouts.get(layoutId);

		if (layout == null) {
			return;
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

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String portletId = portletElement.attributeValue("portlet-id");

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
			return;
		}

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"portlet", portletId, portletDataContext.getManifestSummary());
		}

		String portletPath = portletElement.attributeValue("path");

		Document portletDocument = SAXReaderUtil.read(
			portletDataContext.getZipEntryAsString(portletPath));

		portletElement = portletDocument.getRootElement();

		long oldPlid = GetterUtil.getLong(
			portletElement.attributeValue("old-plid"));

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(plids, oldPlid);

		portletDataContext.setPlid(plid);

		portletDataContext.setOldPlid(oldPlid);
		portletDataContext.setPortletId(portletId);

		// The order of the import is important. You must always import the
		// portlet preferences first, then the portlet data, then the
		// portlet permissions. The import of the portlet data assumes that
		// portlet preferences already exist.

		setPortletScope(portletDataContext, portletElement);

		long portletPreferencesGroupId = portletDataContext.getGroupId();

		Element portletDataElement = portletElement.element("portlet-data");

		Map<String, Boolean> importPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				portletDataContext.getCompanyId(), portletId, parameterMap,
				portletDataElement, portletDataContext.getManifestSummary());

		if (layout != null) {
			portletPreferencesGroupId = layout.getGroupId();
		}

		try {
			_exportImportLifecycleManager.fireExportImportLifecycleEvent(
				EVENT_PORTLET_IMPORT_STARTED, getProcessFlag(),
				PortletDataContextFactoryUtil.clonePortletDataContext(
					portletDataContext));

			// Portlet preferences

			_portletImportController.importPortletPreferences(
				portletDataContext, portletDataContext.getCompanyId(),
				portletPreferencesGroupId, layout, portletElement, false,
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

				_portletImportController.importPortletData(
					portletDataContext, portletDataElement);
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
			_portletImportController.resetPortletScope(
				portletDataContext, portletPreferencesGroupId);
		}

		// Portlet permissions

		boolean permissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);

		if (permissions) {
			_permissionImporter.importPortletPermissions(
				new LayoutCache(), portletDataContext.getCompanyId(),
				portletDataContext.getGroupId(), serviceContext.getUserId(),
				layout, portletElement, portletId);
		}

		// Archived setups

		_portletImportController.importPortletPreferences(
			portletDataContext, portletDataContext.getCompanyId(),
			portletDataContext.getGroupId(), null, portletElement, false,
			importPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
			importPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_DATA),
			importPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_SETUP),
			importPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
	}

	@Override
	protected StagedModelRepository<StagedPortlet> getStagedModelRepository() {
		return _stagedPortletStagedModelRepository;
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

	private static final Log _log = LogFactoryUtil.getLog(
		StagedPortletStagedModelDataHandler.class);

	@Reference
	private ExportImportLifecycleManager _exportImportLifecycleManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private final PermissionImporter _permissionImporter =
		PermissionImporter.getInstance();

	@Reference
	private PortletExportController _portletExportController;

	@Reference
	private PortletImportController _portletImportController;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private StagedPortletStagedModelRepository
		_stagedPortletStagedModelRepository;

}