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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.model.*;
import com.liferay.portal.service.*;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.*;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class LARExporter {

	public void export(
		long groupId, boolean privateLayout,
		long[] layoutIds,Map<String, String[]> parameterMap, Date startDate,
		Date endDate) {

		try {
			ImportExportThreadLocal.setLayoutExportInProcess(true);

			doCreateDigest(
				groupId, privateLayout, layoutIds, parameterMap, startDate,
				endDate);

			if (_log.isInfoEnabled()) {
				_log.info(_larDigest.toString());
			}

			doExport();
		}
		catch (Exception ex) {
			_log.error(ex);
		}
		finally {
			ImportExportThreadLocal.setLayoutExportInProcess(false);
		}
	}

	public LarDigest getLarDigest() {
		return _larDigest;
	}

	protected void doCreateDigest(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
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

		java.util.Map<String, Object[]> portletIds =
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

		if (!layouts.isEmpty()) {
			Layout firstLayout = layouts.get(0);

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
							portletId, firstLayout.getPlid(), groupId,
							StringPool.BLANK, StringPool.BLANK
						});
				}
			}
		}

		// LayoutSetPrototype
		String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);

			_larDigest.addEntry(
				LarDigesterConstants.ACTION_ADD, "",
				LayoutSetPrototype.class.getName(),
				StringUtil.valueOf(
					layoutSetPrototype.getLayoutSetPrototypeId()));
		}

		// Layouts
		for (Layout layout : layouts) {
			_layoutExporter.createLayoutDigest(
				_larDigest, portletDataContext, layoutConfigurationPortlet,
				layoutCache, portlets, portletIds, exportPermissions, layout);
		}

		// Portlets
		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		for (Map.Entry<String, Object[]> portletIdsEntry :
			portletIds.entrySet()) {

			Object[] portletObjects = portletIdsEntry.getValue();

			String portletId = null;
			long plid = 0;
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

			portletDataContext.setPlid(layout.getPlid());
			portletDataContext.setOldPlid(layout.getPlid());
			portletDataContext.setScopeGroupId(scopeGroupId);
			portletDataContext.setScopeType(scopeType);
			portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);

			boolean[] exportPortletControls =
				_layoutExporter.getExportPortletControls(
					companyId, portletId, portletDataContext, parameterMap);

			_portletExporter.createPortletDigest(
				portletDataContext, layoutCache, portletId, layout,
				_larDigest, defaultUserId, exportPermissions,
				exportPortletArchivedSetups, exportPortletControls[0],
				exportPortletControls[1], exportPortletUserPreferences);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		// Additional objects
		// todo: CategoryExporter
		if (exportCategories) {
			_layoutExporter.exportAssetCategories(portletDataContext);
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

		/*
		_portletExporter.exportRatingsEntries(portletDataContext, rootElement);

		if (exportTheme && !portletDataContext.isPerformDirectBinaryImport()) {
			exportTheme(layoutSet, zipWriter);
		} */

		if (updateLastPublishDate) {
			LayoutExporter.updateLastPublishDate(layoutSet, lastPublishDate);
		}
	}

	protected void doExport() {
		return;
	}

	private static Log _log = LogFactoryUtil.getLog(LARExporter.class);
	private static LarDigest _larDigest = new LarDigest();

	private LayoutExporter _layoutExporter = new LayoutExporter();
	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();
}