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

package com.liferay.site.internal.exportimport.data.handler;

import com.liferay.exportimport.controller.PortletExportController;
import com.liferay.exportimport.controller.PortletImportController;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.exportimport.lar.PermissionImporter;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.model.adapter.StagedPortlet;
import com.liferay.site.internal.exportimport.staged.model.repository.StagedGroupStagedModelRepository;
import com.liferay.site.model.adapter.StagedGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedGroupStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedGroup> {

	public static final String[] CLASS_NAMES = {StagedGroup.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(StagedGroup stagedGroup) {
		return stagedGroup.getName();
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((groupId == 0) || groupIds.containsKey(groupId)) {
			return true;
		}

		Group existingGroup =
			_stagedGroupStagedModelRepository.fetchExistingGroup(
				portletDataContext, referenceElement);

		if (existingGroup == null) {
			return false;
		}

		groupIds.put(groupId, existingGroup.getGroupId());

		return true;
	}

	protected Set<String> checkDataSiteLevelPortlets(
			PortletDataContext portletDataContext, Group group)
		throws Exception {

		List<Portlet> dataSiteLevelPortlets =
			ExportImportHelperUtil.getDataSiteLevelPortlets(
				portletDataContext.getCompanyId());

		Group liveGroup = group;

		if (liveGroup.isStagingGroup()) {
			liveGroup = liveGroup.getLiveGroup();
		}

		Set<String> portletIds = new HashSet<>();

		for (Portlet portlet : dataSiteLevelPortlets) {
			String portletId = portlet.getRootPortletId();

			if (ExportImportThreadLocal.isStagingInProcess() &&
				!liveGroup.isStagedPortlet(portletId)) {

				continue;
			}

			// Calculate the amount of exported data

			if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
				Map<String, Boolean> exportPortletControlsMap =
					ExportImportHelperUtil.getExportPortletControlsMap(
						portletDataContext.getCompanyId(), portletId,
						portletDataContext.getParameterMap(),
						portletDataContext.getType());

				if (exportPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA)) {

					PortletDataHandler portletDataHandler =
						portlet.getPortletDataHandlerInstance();

					portletDataHandler.prepareManifestSummary(
						portletDataContext);
				}
			}

			// Add portlet ID to exportable portlets list

			portletIds.add(portletId);
		}

		return portletIds;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws Exception {

		// Collect site portlets and initialize the progress bar

		Set<String> dataSiteLevelPortletIds = checkDataSiteLevelPortlets(
			portletDataContext, stagedGroup);

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"layout", ArrayUtil.toStringArray(dataSiteLevelPortletIds),
				manifestSummary);

			manifestSummary.resetCounters();
		}

		long[] layoutIds = portletDataContext.getLayoutIds();

		if (stagedGroup.isLayoutPrototype()) {
			layoutIds = ExportImportHelperUtil.getAllLayoutIds(
				stagedGroup.getGroupId(), portletDataContext.isPrivateLayout());
		}

		// Export site data portlets

		exportSitePortlets(
			portletDataContext, stagedGroup, dataSiteLevelPortletIds,
			layoutIds);

		// Layout set with layouts

		List<? extends StagedModel> childStagedModels =
			_stagedGroupStagedModelRepository.fetchChildrenStagedModels(
				portletDataContext, stagedGroup);

		for (StagedModel stagedModel : childStagedModels) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedGroup, stagedModel,
				PortletDataContext.REFERENCE_TYPE_CHILD);
		}

		// Serialize the group

		Element groupElement = portletDataContext.getExportDataElement(
			stagedGroup);

		portletDataContext.addClassedModel(
			groupElement, ExportImportPathUtil.getModelPath(stagedGroup),
			stagedGroup);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		if ((groupId == 0) || groupIds.containsKey(groupId)) {
			return;
		}

		Group existingGroup =
			_stagedGroupStagedModelRepository.fetchExistingGroup(
				portletDataContext, referenceElement);

		groupIds.put(groupId, existingGroup.getGroupId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, StagedGroup group)
		throws Exception {

		Element rootElement = portletDataContext.getImportDataRootElement();

		List<Element> sitePortletElements =
			portletDataContext.getReferenceDataElements(
				group, StagedPortlet.class);

		// Initialize progress bar

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			List<String> portletIds = new ArrayList<>();

			for (Element portletElement : sitePortletElements) {
				String portletId = portletElement.attributeValue("portlet-id");

				Portlet portlet = _portletLocalService.getPortletById(
					portletDataContext.getCompanyId(), portletId);

				if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
					continue;
				}

				portletIds.add(portletId);
			}

			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"layout", ArrayUtil.toStringArray(portletIds),
				portletDataContext.getManifestSummary());
		}

		// Import site data portlets

		if (_log.isDebugEnabled() && !sitePortletElements.isEmpty()) {
			_log.debug("Importing portlets");
		}

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, group, StagedPortlet.class);

		// Import services

		Element siteServicesElement = rootElement.element("site-services");

		List<Element> siteServiceElements = siteServicesElement.elements(
			"service");

		if (_log.isDebugEnabled() && !siteServiceElements.isEmpty()) {
			_log.debug("Importing services");
		}

		importSiteServices(portletDataContext, siteServiceElements);

		// Import layout set

		Element layoutSetElement = portletDataContext.getImportDataGroupElement(
			StagedLayoutSet.class);

		for (Element groupElement : layoutSetElement.elements()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, groupElement);
		}
	}

	protected void exportPortlet(
			PortletDataContext portletDataContext, StagedGroup stagedGroup,
			String portletId, long plid, long scopeGroupId, String scopeType,
			String scopeLayoutUuid)
		throws Exception {

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		try {
			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(plid);
			portletDataContext.setPortletId(portletId);
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			Portlet portlet = _portletLocalService.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			StagedPortlet stagedPortlet = ModelAdapterUtil.adapt(
				portlet, Portlet.class, StagedPortlet.class);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedGroup, stagedPortlet,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
		finally {
			portletDataContext.setScopeGroupId(previousScopeGroupId);
		}
	}

	protected void exportSitePortlets(
			PortletDataContext portletDataContext, StagedGroup group,
			Set<String> portletIds, long[] layoutIds)
		throws Exception {

		// Export portlets

		List<Layout> layouts = _layoutLocalService.getLayouts(
			group.getGroupId(), portletDataContext.isPrivateLayout());

		for (String portletId : portletIds) {

			// Default scope

			exportPortlet(
				portletDataContext, group, portletId,
				LayoutConstants.DEFAULT_PLID, portletDataContext.getGroupId(),
				StringPool.BLANK, StringPool.BLANK);

			Portlet portlet = _portletLocalService.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isScopeable()) {
				continue;
			}

			// Scoped data

			for (Layout layout : layouts) {
				if (!ArrayUtil.contains(layoutIds, layout.getLayoutId()) ||
					!layout.isTypePortlet() || !layout.hasScopeGroup()) {

					continue;
				}

				Group scopeGroup = layout.getScopeGroup();

				exportPortlet(
					portletDataContext, group, portletId, layout.getPlid(),
					scopeGroup.getGroupId(), StringPool.BLANK,
					layout.getUuid());
			}
		}
	}

	@Override
	protected StagedModelRepository<StagedGroup> getStagedModelRepository() {
		return _stagedGroupStagedModelRepository;
	}

	@Override
	protected void importReferenceStagedModels(
			PortletDataContext portletDataContext, StagedGroup stagedModel)
		throws PortletDataException {
	}

	protected void importSiteServices(
			PortletDataContext portletDataContext,
			List<Element> siteServiceElements)
		throws Exception {

		for (Element serviceElement : siteServiceElements) {
			String path = serviceElement.attributeValue("path");

			Document siteServiceDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(path));

			serviceElement = siteServiceDocument.getRootElement();

			_portletImportController.importServicePortletPreferences(
				portletDataContext, serviceElement);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedGroupStagedModelDataHandler.class);

	@Reference
	private ExportImportLifecycleManager _exportImportLifecycleManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	private final PermissionImporter _permissionImporter =
		PermissionImporter.getInstance();

	@Reference
	private PortletExportController _portletExportController;

	@Reference
	private PortletImportController _portletImportController;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private StagedGroupStagedModelRepository _stagedGroupStagedModelRepository;

}