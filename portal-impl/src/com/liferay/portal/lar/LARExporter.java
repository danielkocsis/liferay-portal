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
import com.liferay.portal.kernel.lar.PortletDataContext;
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
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import org.apache.commons.lang.time.StopWatch;

import java.util.*;

/**
 * @author Mate Thurzo
 */
public class LARExporter {

	public void export() {
		//doCreateDigest();
		doExport();
	}

	protected void doCreateDigest(
			LarDigest larDigest, long groupId, boolean privateLayout,
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
		boolean updateLastPublishDate = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (_log.isInfoEnabled()) {
			_log.info("Export categories " + exportCategories);
			_log.info("Export permissions " + exportPermissions);
			_log.info("Export portlet archived setups " +
				exportPortletArchivedSetups);
			_log.info("Export portlet user preferences " +
				exportPortletUserPreferences);
			_log.info("Export theme " + exportTheme);
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		LayoutExporter layoutExporter = new LayoutExporter();

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

		if (portletDataContext.hasDateRange()) {
			larDigest.setHeaderAttribute(
				"start-date",
				String.valueOf(portletDataContext.getStartDate()));
			larDigest.setHeaderAttribute(
				"end-date", String.valueOf(portletDataContext.getEndDate()));
		}

		larDigest.setHeaderAttribute("group-id", String.valueOf(groupId));
		larDigest.setHeaderAttribute(
			"private-layout", String.valueOf(privateLayout));

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

		List<Portlet> portlets = getAlwaysExportablePortlets(companyId);

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

		String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
							layoutSetPrototypeUuid, companyId);

			larDigest.addEntry(LarDigesterConstants.ACTION_ADD, "",
				LayoutSetPrototype.class.getName(),
				StringUtil.valueOf(
					layoutSetPrototype.getLayoutSetPrototypeId()));
		}

		for (Layout layout : layouts) {
			//toDo: develop createLayoutDigest based on exportlayout
			layoutExporter.createLayoutDigest(
				larDigest, portletDataContext, layoutConfigurationPortlet,
				layoutCache, portlets, portletIds, exportPermissions, layout);
		}

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

			boolean[] exportPortletControls = getExportPortletControls(
					companyId, portletId, portletDataContext, parameterMap);

			/*_portletExporter.exportPortlet(
					portletDataContext, layoutCache, portletId, layout,
					portletsElement, defaultUserId, exportPermissions,
					exportPortletArchivedSetups, exportPortletControls[0],
					exportPortletControls[1], exportPortletUserPreferences);*/
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		/*if (exportCategories) {
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

		_portletExporter.exportRatingsEntries(portletDataContext, rootElement);

		if (exportTheme && !portletDataContext.isPerformDirectBinaryImport()) {
			exportTheme(layoutSet, zipWriter);
		}
        */
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


	protected void doExport() {
		return;
	}

	private static LarDigest _larDigest = new LarDigest();

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(companyId);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (!portlet.isActive()) {
				itr.remove();

				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				!portletDataHandler.isAlwaysExportable()) {

				itr.remove();
			}
		}

		return portlets;
	}

	protected boolean[] getExportPortletControls(
		long companyId, String portletId,
		PortletDataContext portletDataContext,
		Map<String, String[]> parameterMap)
		throws Exception {

		boolean exportPortletData = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportPortletSetup = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean exportPortletSetupAll = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_SETUP_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug("Export portlet setup " + exportPortletSetup);
		}

		boolean exportCurPortletData = exportPortletData;
		boolean exportCurPortletSetup = exportPortletSetup;

		// If PORTLET_DATA_ALL is true, this means that staging has just been
		// activated and all data and setup must be exported. There is no
		// portlet export control to check in this case.

		if (exportPortletDataAll) {
			exportCurPortletData = true;
			exportCurPortletSetup = true;
		}
		else {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
					companyId, portletId);

			if (portlet != null) {
				String portletDataHandlerClass =
					portlet.getPortletDataHandlerClass();

				// Checking if the portlet has a data handler, if it doesn't,
				// the default values are the ones set in PORTLET_DATA and
				// PORTLET_SETUP. If it has a data handler, iterate over each
				// portlet export control.

				if (portletDataHandlerClass != null) {
					String rootPortletId = PortletConstants.getRootPortletId(
						portletId);

					// PORTLET_DATA and the PORTLET_DATA for this specific
					// data handler must be true

					exportCurPortletData =
						exportPortletData &&
							MapUtil.getBoolean(
								parameterMap,
								PortletDataHandlerKeys.PORTLET_DATA +
								StringPool.UNDERLINE + rootPortletId);

					// PORTLET_SETUP and the PORTLET_SETUP for this specific
					// data handler must be true

					exportCurPortletSetup =
						exportPortletSetup &&
							MapUtil.getBoolean(
								parameterMap,
								PortletDataHandlerKeys.PORTLET_SETUP +
								StringPool.UNDERLINE + rootPortletId);
				}
			}
		}

		if (exportPortletSetupAll) {
			exportCurPortletSetup = true;
		}

		return new boolean[] {exportCurPortletData, exportCurPortletSetup};
	}

	protected String getLayoutSetLogoPath(
		PortletDataContext portletDataContext) {

		return portletDataContext.getRootPath().concat("/logo/");
	}

	private static Log _log = LogFactoryUtil.getLog(LARExporter.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();
	private static LarDigest _larDigest = new LarDigest();
}
