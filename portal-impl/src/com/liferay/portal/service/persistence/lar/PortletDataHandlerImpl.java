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

package com.liferay.portal.service.persistence.lar;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.DataHandlerContext;
import com.liferay.portal.kernel.lar.ImportExportThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.LayoutCache;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.lar.digest.LarDigestItemImpl;
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.impl.BaseDataHandlerImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class PortletDataHandlerImpl
	extends BaseDataHandlerImpl<Portlet>
	implements PortletDataHandler {

	public LarDigestItem digest(
			Portlet portlet, DataHandlerContext context)
		throws Exception {

		long plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		long layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout layout = (Layout)context.getAttribute("layout");

		if (layout != null) {
			plid = layout.getPlid();
			layoutId = layout.getLayoutId();
		}

		String portletId = portlet.getPortletId();

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet " + portletId +
						" because the portlet does not exist");
			}

			return null;
		}

		if (!portlet.isInstanceable() &&
			!portlet.isPreferencesUniquePerLayout() &&
			context.hasNotUniquePerLayout(portletId)) {

			return null;
		}

		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			context.getParameters(),
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			context.getParameters(),
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);

		boolean[] exportPortletControls = getExportPortletControls(
			context.getCompanyId(), portletId, context);

		String path = getEntityPath(portlet);

		if (context.isPathProcessed(path)) {
			return null;
		}

		LarDigestItem item = new LarDigestItemImpl();

		Map<String, String> metadataMap = new HashMap<String, String>();

		if (context.getPlid() > 0) {
			//portlet is on a layout, add plid to metadata

			metadataMap.put("layoutPlid", String.valueOf(context.getPlid()));

			item.setMetadata(metadataMap);
		}

		item.setAction(LarDigesterConstants.ACTION_ADD);
		item.setType(portlet.getPortletId());
		item.setClassPK(portlet.getPortletId());
		item.setPath(path);

		metadataMap.put("portlet-id", portletId);
		metadataMap.put("root-portlet-id",
			PortletConstants.getRootPortletId(portletId));
		metadataMap.put("old-plid", String.valueOf(plid));
		metadataMap.put("scope-layout-type",
			StringUtil.valueOf(context.getAttribute("scopeType")));
		metadataMap.put("scope-layout-uuid",
			StringUtil.valueOf(context.getAttribute("scopeLayoutUuid")));

		metadataMap.put("portlet-id", portletId);
		metadataMap.put("layout-id", String.valueOf(layoutId));

		boolean exportPortletData = exportPortletControls[0];
		boolean exportPortletSetup = exportPortletControls[1];

		// Data

		if (exportPortletData) {
			javax.portlet.PortletPreferences jxPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			if (!portlet.isPreferencesUniquePerLayout()) {
				StringBundler sb = new StringBundler(5);

				sb.append(portletId);
				sb.append(StringPool.AT);
				sb.append(context.getScopeType());
				sb.append(StringPool.AT);
				sb.append(context.getScopeLayoutUuid());

				String dataKey = sb.toString();

				if (!context.hasNotUniquePerLayout(dataKey)) {
					context.putNotUniquePerLayout(dataKey);

					exportPortletData(
						context, portlet, layout, jxPreferences, item);
				}
			}
			else {
				exportPortletData(
					context, portlet, layout, jxPreferences, item);
			}
		}

		// Portlet preferences

		if (exportPortletSetup) {
			digestPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false,
				portlet.getPortletId(), metadataMap, context);

			digestPortletPreferences(
				context.getScopeGroupId(), PortletKeys.PREFS_OWNER_TYPE_GROUP,
				false, portlet.getPortletId(), metadataMap, context);

			digestPortletPreferences(
				context.getCompanyId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				false, portlet.getPortletId(), metadataMap, context);
		}

		// Portlet preferences

		if (exportPortletUserPreferences) {
			List<PortletPreferences> portletPreferencesList =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					PortletKeys.PREFS_OWNER_TYPE_USER, context.getPlid(),
					portlet.getPortletId());

			for (PortletPreferences portletPreferences :
					portletPreferencesList) {

				boolean defaultUser = false;

				if (portletPreferences.getOwnerId() ==
						PortletKeys.PREFS_OWNER_ID_DEFAULT) {

					defaultUser = true;
				}

				digestPortletPreferences(
					portletPreferences.getOwnerId(),
					PortletKeys.PREFS_OWNER_TYPE_USER, defaultUser,
					portlet.getPortletId(), metadataMap, context);
			}

			try {
				digestPortletPreference(
					portlet.getPortletId(), context.getScopeGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
					PortletKeys.PREFS_PLID_SHARED, metadataMap);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			List<PortletItem> portletItems =
				PortletItemLocalServiceUtil.getPortletItems(
					context.getGroupId(), rootPortletId,
					PortletPreferences.class.getName());

			for (PortletItem portletItem : portletItems) {
				digestPortletPreferences(
					portletItem.getPortletItemId(),
					PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, false,
					portletItem.getPortletId(), metadataMap, context);
			}
		}

		context.addPrimaryKey(String.class, path);

		LarDigest digest = context.getLarDigest();

		digest.addItem(item);

		context.addProcessedPath(path);

		return item;
	}

	@Override
	public LarDigestItem doDigest(
			Portlet object, DataHandlerContext context)
		throws Exception {

		return null;
	}

	protected LarDigestItem doDigestPortlet(
			Portlet portlet, LarDigestItem item, DataHandlerContext context)
		throws Exception {

		return null;
	}

	@Override
	public void doImportData(LarDigestItem item, DataHandlerContext context)
		throws Exception{

		Map<String, String> metadata = item.getMetadata();

		Map parameterMap = context.getParameters();

		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);

		String portletId = metadata.get("portlet-id");
		long layoutId = GetterUtil.getLong(metadata.get("layout-id"));
		long oldPlid = GetterUtil.getLong(metadata.get("old-plid"));

		long groupId = context.getGroupId();
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Map<Long, Layout> newLayoutsMap =
			(Map<Long, Layout>)context.getAttribute("newLayoutsMap");

		if (deletePortletData) {
			long plid = newLayoutsMap.get(layoutId).getPlid();

			context.setPlid(plid);

			deletePortletData(context, portletId, plid);
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			context.getCompanyId(), portletId);

		if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
			return;
		}

		Layout layout = newLayoutsMap.get(layoutId);

		long plid = LayoutConstants.DEFAULT_PLID;

		if (layout != null) {
			plid = layout.getPlid();
		}

		layout = LayoutUtil.fetchByPrimaryKey(plid);

		if ((layout == null) && !group.isCompany()) {
			return;
		}

		context.setPlid(plid);
		context.setOldPlid(oldPlid);

		// The order of the import is important. You must always import
		// the portlet preferences first, then the portlet data, then
		// the portlet permissions. The import of the portlet data
		// assumes that portlet preferences already exist.

		setPortletScope(context, item);

		long portletPreferencesGroupId = groupId;

		try {
			if ((layout != null) && !group.isCompany()) {
				portletPreferencesGroupId = layout.getGroupId();
			}

			// Portlet preferences

			importPortletPreferences(
				context, layout.getCompanyId(),
				portletPreferencesGroupId, layout, null, item,
				importPortletSetup, importPortletArchivedSetups,
				importPortletUserPreferences, false, importPortletData);

			if (importPortletData) {
				if (_log.isDebugEnabled()) {
					_log.debug("Importing portlet data");
				}

				importPortletData(context, portletId, plid);
			}
		}
		finally {
			resetPortletScope(context, portletPreferencesGroupId);
		}

		// Portlet permissions

		if (importPermissions) {
			importPortletPermissions(layout, portletId, item, context);
		}

		// Archived setups

		importPortletPreferences(
			context, layout.getCompanyId(), groupId, null,
			null, item, importPortletSetup, importPortletArchivedSetups,
			importPortletUserPreferences, false, importPortletData);
	}

	public String[] getDataPortletPreferences() {
		return new String[0];
	}

	public Portlet getEntity(String classPK) {
		return null;
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[0];
	}

	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[0];
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isAlwaysStaged() {
		return _ALWAYS_STAGED;
	}

	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected void deletePortletData(
			DataHandlerContext context, String portletId, long plid)
		throws Exception {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences portletPreferences =
			PortletPreferencesUtil.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			portletPreferences =
				new com.liferay.portal.model.impl.PortletPreferencesImpl();
		}

		String xml = deletePortletData(
			context, portletId, portletPreferences);

		if (xml != null) {
			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
	}

	protected String deletePortletData(
			DataHandlerContext context, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting data for " + portletId);
		}

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)
				PortletPreferencesFactoryUtil.fromDefaultXML(
					portletPreferences.getPreferences());

		try {
			portletPreferencesImpl =
				(PortletPreferencesImpl)doDeleteData(
					context, portletId, portletPreferencesImpl);
		}
		finally {
			context.setGroupId(context.getScopeGroupId());
		}

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	protected javax.portlet.PortletPreferences doDeleteData(
			DataHandlerContext context, String portletId,
			javax.portlet.PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	protected javax.portlet.PortletPreferences doImportData(
			DataHandlerContext context, String portletId,
			javax.portlet.PortletPreferences portletPreferences)
		throws Exception {

		return null;
	}

	@Override
	protected void doSerialize(Portlet portlet, DataHandlerContext context)
		throws Exception {

		String path = getEntityPath(portlet);

		PortletPreferences portletPreferences = getPortletPreferences(
			path, portlet.getPortletId());

		String preferencesXML = portletPreferences.getPreferences();

		if (Validator.isNull(preferencesXML)) {
			preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
		}

		Document document = SAXReaderUtil.read(preferencesXML);

		List<Node> nodes = document.selectNodes(
			"/portlet-preferences/preference[name/text() = " +
				"'last-publish-date']");

		for (Node node : nodes) {
			document.remove(node);
		}

		addZipEntry(
			context.getZipWriter(), path,
			document.formattedString(StringPool.TAB, false, false));
	}

	protected void exportPortletData(
			DataHandlerContext context, Portlet portlet,
			Layout layout, javax.portlet.PortletPreferences jxPreferences,
			LarDigestItem portletItem)
		throws Exception {

		if (portlet == null) {
			return;
		}

		String portletId = portlet.getPortletId();

		Group liveGroup = layout.getGroup();

		if (liveGroup.isStagingGroup()) {
			liveGroup = liveGroup.getLiveGroup();
		}

		boolean staged = liveGroup.isStagedPortlet(portlet.getRootPortletId());

		if (!staged && ImportExportThreadLocal.isLayoutExportInProcess()) {
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

		sb.append(getPortletPath(portletId));
		sb.append(StringPool.SLASH);

		if (portlet.isPreferencesUniquePerLayout()) {
			sb.append(layout.getPlid());
		}
		else {
			sb.append(context.getScopeGroupId());
		}

		sb.append("/portlet-data.xml");

		String path = sb.toString();

		if (context.isPathProcessed(path)) {
			return;
		}

		long lastPublishDate = GetterUtil.getLong(
			jxPreferences.getValue("last-publish-date", StringPool.BLANK));

		Date startDate = context.getStartDate();

		if ((lastPublishDate > 0) && (startDate != null) &&
				(lastPublishDate < startDate.getTime())) {

			context.setStartDate(new Date(lastPublishDate));
		}

		String data = null;

		long groupId = context.getGroupId();

		context.setGroupId(context.getScopeGroupId());

		try {
			portletItem = doDigestPortlet(portlet, portletItem, context);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			context.setGroupId(groupId);
			context.setStartDate(startDate);
		}

		if (Validator.isNull(data)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		/*Element portletDataElement = parentElement.addElement("portlet-data");

		portletDataElement.addAttribute("path", path);

		portletDataContext.addZipEntry(path, data);        */

		Date endDate = context.getEndDate();

		if (endDate != null) {
			try {
				jxPreferences.setValue(
					"last-publish-date", String.valueOf(endDate.getTime()));

				jxPreferences.store();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected boolean[] getExportPortletControls(
			long companyId, String portletId,DataHandlerContext context)
		throws Exception {

		Map parameterMap = context.getParameters();

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
				String portletDataHandlerClass = StringPool.BLANK;
				//portlet.getPortletDataHandlerClass();

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

	protected void digestPortletPreference(
			String portletId, long ownerId, int ownerType, boolean defaultUser,
			long plid, Map<String, String> metadataMap)
		throws Exception {

		String rootPotletId = PortletConstants.getRootPortletId(portletId);

		if (rootPotletId.equals(PortletKeys.ASSET_PUBLISHER)) {
			/*preferencesXML = updateAssetPublisherPortletPreferences(
				preferencesXML, plid);*/
		}

		metadataMap.put("owner-id", String.valueOf(ownerId));
		metadataMap.put("owner-type", String.valueOf(ownerType));
		metadataMap.put("default-user", String.valueOf(defaultUser));
		metadataMap.put("plid", String.valueOf(plid));
		metadataMap.put("portlet-id", portletId);

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			PortletItem portletItem =
				PortletItemLocalServiceUtil.getPortletItem(ownerId);

			metadataMap.put(
				"archive-user-uuid", portletItem.getUserUuid());
			metadataMap.put("archive-name", portletItem.getName());
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			User user = UserLocalServiceUtil.fetchUserById(ownerId);

			if (user == null) {
				return;
			}

			metadataMap.put("user-uuid", user.getUserUuid());
		}

		String path = getPortletPreferencesPath(
			portletId, ownerId, ownerType, plid);

		metadataMap.put("path", path);
	}

	protected void digestPortletPreferences(
			long ownerId, int ownerType, boolean defaultUser, String portletId,
			Map<String, String> metadataMap, DataHandlerContext context)
		throws Exception {

		long plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		Layout layout = null;

		try {
			plid = context.getPlid();
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("No plid is available from context: " +
					e.getMessage());
			}
		}

		if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED)) {

			plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		}

		try {
			PortletPreferences portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					ownerId, ownerType, plid, portletId);
		}
		catch (NoSuchPortletPreferencesException nsppe) {
			return;
		}

		LayoutTypePortlet layoutTypePortlet = null;

		if (layout != null) {
			layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
		}

		if ((layoutTypePortlet == null) ||
			layoutTypePortlet.hasPortletId(portletId)) {

			digestPortletPreference( portletId, ownerId, ownerType, defaultUser,
				plid, metadataMap);
		}

		return;
	}

	protected String getPortletPreferencesPath(
		String portletId, long ownerId, int ownerType, long plid) {

		StringBundler sb = new StringBundler(8);

		sb.append(getPortletPath(portletId));
		sb.append("/preferences/");

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
			sb.append("company/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
			sb.append("group/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
			sb.append("layout/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			sb.append("user/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			sb.append("archived/");
		}

		sb.append(ownerId);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(plid);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append("portlet-preferences.xml");

		return sb.toString();
	}

	protected PortletPreferences getPortletPreferences(
		String path, String portletId) {

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf("/preferences/");

		if (pos < 0) {
			return null;
		}

		path = path.substring(path.indexOf(pos));

		String[] parts = StringUtil.split(path, CharPool.FORWARD_SLASH);

		int ownerType = 0;

		String owner = parts[0];

		if (owner.equals("company")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		}
		else if (owner.equals("group")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
		}
		else if (owner.equals("layout")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
		}
		else if (owner.equals("user")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}
		else if (owner.equals("archived")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		}

		long ownerId = Long.valueOf(parts[1]);

		long plid = Long.valueOf(parts[2]);

		PortletPreferences portletPreferences = null;

		try {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					ownerId, ownerType, plid, portletId);
		}
		catch (Exception ex) {
			if (_log.isDebugEnabled()) {
				_log.debug("No plid is available from context: " +
					ex.getMessage());
			}
		}

		return portletPreferences;
	}

	protected void importPortletData(
			DataHandlerContext context, String portletId, long plid)
		throws Exception {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences portletPreferences =
			PortletPreferencesUtil.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		PortletPreferencesImpl portletPreferencesImpl = null;

		if (portletPreferences != null) {
			portletPreferencesImpl =
				(PortletPreferencesImpl)
					PortletPreferencesFactoryUtil.fromDefaultXML(
						portletPreferences.getPreferences());
		}
		else {
			portletPreferencesImpl = new PortletPreferencesImpl();
		}

		portletPreferencesImpl =
			(PortletPreferencesImpl)doImportData(
				context, portletId, portletPreferencesImpl);

		if (portletPreferencesImpl != null) {
			String xml = PortletPreferencesFactoryUtil.toXML(
				portletPreferencesImpl);

			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
	}

	protected void importPortletPreferences(
			DataHandlerContext context, long companyId, long groupId,
			Layout layout, String portletId, LarDigestItem item,
			boolean importPortletSetup, boolean importPortletArchivedSetups,
			boolean importPortletUserPreferences, boolean preserveScopeLayoutId,
			boolean importPortletData)
		throws Exception {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		long plid = 0;
		String scopeType = StringPool.BLANK;
		String scopeLayoutUuid = StringPool.BLANK;

		if (layout != null) {
			plid = layout.getPlid();

			if (preserveScopeLayoutId && (portletId != null)) {
				javax.portlet.PortletPreferences jxPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				scopeType = GetterUtil.getString(
					jxPreferences.getValue("lfrScopeType", null));
				scopeLayoutUuid = GetterUtil.getString(
					jxPreferences.getValue("lfrScopeLayoutUuid", null));

				// toDo: extend portletDataContext
				// with scopeType, scopeLayoutUuid
				//portletDataContext.setScopeType(scopeType);
				//portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}

		List<Element> portletPreferencesElements = null;

		// toDo: read digested portletpreferences
		//parentElement.elements("portlet-preferences");

		for (Element portletPreferencesElement : portletPreferencesElements) {
			String path = portletPreferencesElement.attributeValue("path");

			if (!context.isPathProcessed(path)) {
				String xml = null;

				Element element = null;

				try {
					xml = getZipEntryAsString(context.getZipReader(), path);

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

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
					continue;
				}

				if (((ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
					(ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) &&
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

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
					plid = PortletKeys.PREFS_PLID_SHARED;
					ownerId = context.getScopeGroupId();
				}

				boolean defaultUser = GetterUtil.getBoolean(
					element.attributeValue("default-user"));

				if (portletId == null) {
					portletId = element.attributeValue("portlet-id");
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
					portletId = PortletConstants.getRootPortletId(portletId);

					String userUuid = element.attributeValue(
						"archive-user-uuid");
					String name = element.attributeValue("archive-name");

					long userId = context.getUserId(userUuid);

					PortletItem portletItem =
						PortletItemLocalServiceUtil.updatePortletItem(
							userId, groupId, name, portletId,
							PortletPreferences.class.getName());

					plid = 0;
					ownerId = portletItem.getPortletItemId();
				}
				else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
					String userUuid = element.attributeValue("user-uuid");

					ownerId = context.getUserId(userUuid);
				}

				if (defaultUser) {
					ownerId = defaultUserId;
				}

				// toDo make updatePortletPreferences general!
				/*
				String rootPotletId = PortletConstants.getRootPortletId(
					portletId);


				if (rootPotletId.equals(PortletKeys.ASSET_PUBLISHER)) {
					xml = updateAssetPublisherPortletPreferences(
						portletDataContext, companyId, ownerId, ownerType, plid,
						portletId, xml);
				}*/

				updatePortletPreferences(
					context, ownerId, ownerType, plid, portletId,
					xml, importPortletData);
			}
		}

		if (preserveScopeLayoutId && (layout != null)) {
			javax.portlet.PortletPreferences jxPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			try {
				jxPreferences.setValue("lfrScopeType", scopeType);
				jxPreferences.setValue("lfrScopeLayoutUuid", scopeLayoutUuid);

				jxPreferences.store();
			}
			finally {
				//portletDataContext.setScopeType(scopeType);
				//portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}
	}

	protected void importPortletPermissions(
			Layout layout, String portletId, LarDigestItem item,
			DataHandlerContext context)
		throws Exception {

		Map<String, List<String>> permissions = item.getPermissions();

		if (permissions != null) {
			String resourceName = PortletConstants.getRootPortletId(portletId);

			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId);

			importPermissions(
				resourceName, resourcePrimKey, permissions, context);
		}
	}

	protected void readAssetLinks(DataHandlerContext context)
			throws Exception {

		String xml = getZipEntryAsString(
			context.getZipReader(),
			getSourceRootPath(context.getScopeGroupId()) + "/links.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetLinkElements = rootElement.elements("asset-link");

		for (Element assetLinkElement : assetLinkElements) {
			String sourceUuid = GetterUtil.getString(
				assetLinkElement.attributeValue("source-uuid"));
			String[] assetEntryUuidArray = StringUtil.split(
				GetterUtil.getString(
					assetLinkElement.attributeValue("target-uuids")));
			int assetLinkType = GetterUtil.getInteger(
				assetLinkElement.attributeValue("type"));

			List<Long> assetEntryIds = new ArrayList<Long>();

			for (String assetEntryUuid : assetEntryUuidArray) {
				try {
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
						context.getScopeGroupId(), assetEntryUuid);

					assetEntryIds.add(assetEntry.getEntryId());
				}
				catch (NoSuchEntryException nsee) {
				}
			}

			if (assetEntryIds.isEmpty()) {
				continue;
			}

			long[] assetEntryIdsArray = ArrayUtil.toArray(
				assetEntryIds.toArray(new Long[assetEntryIds.size()]));

			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
					context.getScopeGroupId(), sourceUuid);

				AssetLinkLocalServiceUtil.updateLinks(
					assetEntry.getUserId(), assetEntry.getEntryId(),
					assetEntryIdsArray, assetLinkType);
			}
			catch (NoSuchEntryException nsee) {
			}
		}
	}


	protected void resetPortletScope(
		DataHandlerContext context, long groupId) {

		context.setScopeGroupId(groupId);
		//context.setScopeLayoutUuid(StringPool.BLANK);
		//context.setScopeType(StringPool.BLANK);
	}

	protected void setPortletScope(
		DataHandlerContext context, LarDigestItem item) {

		// Portlet data scope

		Map<String, String> metadata = item.getMetadata();

		String scopeLayoutUuid = GetterUtil.getString(
			metadata.get("scope-layout-uuid"));
		String scopeLayoutType = GetterUtil.getString(
				metadata.get("scope-layout-type"));

		//context.setScopeLayoutUuid(scopeLayoutUuid);
		//context.setScopeType(scopeLayoutType);

		// Layout scope

		try {
			Group scopeGroup = null;

			if (scopeLayoutType.equals("company")) {
				scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
					context.getCompanyId());
			}
			else if (Validator.isNotNull(scopeLayoutUuid)) {
				Layout scopeLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, context.getGroupId());

				if (scopeLayout.hasScopeGroup()) {
					scopeGroup = scopeLayout.getScopeGroup();
				}
				else {
					String name = String.valueOf(scopeLayout.getPlid());

					scopeGroup = GroupLocalServiceUtil.addGroup(
						context.getUserId(null),
						GroupConstants.DEFAULT_PARENT_GROUP_ID,
						Layout.class.getName(), scopeLayout.getPlid(), name,
						null, 0, null, false, true, null);
				}

				Group group = scopeLayout.getGroup();

				if (group.isStaged() && !group.isStagedRemotely()) {
					try {
						Layout oldLayout =
							LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								context.getSourceGroupId());

						Group oldScopeGroup = oldLayout.getScopeGroup();

						oldScopeGroup.setLiveGroupId(scopeGroup.getGroupId());

						GroupLocalServiceUtil.updateGroup(oldScopeGroup, true);
					}
					catch (NoSuchLayoutException nsle) {
						if (_log.isWarnEnabled()) {
							_log.warn(nsle);
						}
					}
				}

				context.setScopeGroupId(scopeGroup.getGroupId());
			}
		}
		catch (PortalException pe) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void updatePortletPreferences(
			DataHandlerContext portletDataContext, long ownerId, int ownerType,
			long plid, String portletId, String xml, boolean importData)
		throws Exception {

		if (importData) {
			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
		else {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not update portlet preferences for " + portletId +
							" because the portlet does not exist");
				}

				return;
			}

			PortletDataHandler portletDataHandler = null;
			//portlet.getPortletDataHandlerInstance();

			if (portletDataHandler == null) {
				PortletPreferencesLocalServiceUtil.updatePreferences(
						ownerId, ownerType, plid, portletId, xml);

				return;
			}

			// Portlet preferences to be updated only when importing data

			String[] dataPortletPreferences = getDataPortletPreferences();

			// Current portlet preferences

			javax.portlet.PortletPreferences portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					portletDataContext.getCompanyId(), ownerId, ownerType, plid,
					portletId);

			// New portlet preferences

			javax.portlet.PortletPreferences jxPreferences =
				PortletPreferencesFactoryUtil.fromXML(
					portletDataContext.getCompanyId(), ownerId, ownerType, plid,
					portletId, xml);

			Enumeration<String> enu = jxPreferences.getNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!ArrayUtil.contains(dataPortletPreferences, name)) {
					String value = GetterUtil.getString(
						jxPreferences.getValue(name, null));

					portletPreferences.setValue(name, value);
				}
			}



			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, portletPreferences);
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(PortletDataHandlerImpl.class);

	private static final boolean _ALWAYS_EXPORTABLE = false;

	private static final boolean _ALWAYS_STAGED = false;

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = false;

	private static final boolean _DATA_LOCALIZED = false;

}
