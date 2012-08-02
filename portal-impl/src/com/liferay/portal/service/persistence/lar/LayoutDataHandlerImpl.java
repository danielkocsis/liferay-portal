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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.lar.DataHandlerContext;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.lar.DataHandlersUtil;
import com.liferay.portal.lar.LARExporter;
import com.liferay.portal.lar.LayoutCache;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.lar.digest.LarDigestItemImpl;
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.BaseDataHandler;
import com.liferay.portal.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.impl.BaseDataHandlerImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class LayoutDataHandlerImpl extends BaseDataHandlerImpl<Layout>
	implements LayoutDataHandler {

	@Override
	public LarDigestItem doDigest(
			Layout layout, DataHandlerContext context)
		throws Exception {

		context.setPlid(layout.getPlid());

		String path = getEntityPath(layout);

		if (context.isPathProcessed(path)) {
			return null;
		}

		LayoutRevision layoutRevision = null;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean exportLAR = ParamUtil.getBoolean(serviceContext, "exportLAR");

		if (!exportLAR && LayoutStagingUtil.isBranchingLayout(layout) &&
			!layout.isTypeURL()) {

			long layoutSetBranchId = ParamUtil.getLong(
				serviceContext, "layoutSetBranchId");

			if (layoutSetBranchId <= 0) {
				return null;
			}

			layoutRevision = LayoutRevisionUtil.fetchByL_H_P(
				layoutSetBranchId, true, layout.getPlid());

			if (layoutRevision == null) {
				return null;
			}

			LayoutStagingHandler layoutStagingHandler =
				LayoutStagingUtil.getLayoutStagingHandler(layout);

			layoutStagingHandler.setLayoutRevision(layoutRevision);
		}

		Map metadata = new HashMap<String, String>();

		if (layoutRevision != null) {
			metadata.put(
				"layout-revision-id",
				String.valueOf(layoutRevision.getLayoutRevisionId()));
			metadata.put(
				"layout-branch-id",
				String.valueOf(layoutRevision.getLayoutBranchId()));
			metadata.put(
				"layout-branch-name",
				String.valueOf(layoutRevision.getLayoutBranch().getName()));
		}

		metadata.put("layout-uuid", layout.getUuid());
		metadata.put("layout-id", String.valueOf(layout.getLayoutId()));

		long parentLayoutId = layout.getParentLayoutId();

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			Layout parentLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			if (parentLayout != null) {
				digest(parentLayout, context);
				metadata.put("parent-layout-uuid", parentLayout.getUuid());
			}
		}

		String layoutPrototypeUuid = layout.getLayoutPrototypeUuid();

		if (Validator.isNotNull(layoutPrototypeUuid)) {
			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.
					getLayoutPrototypeByUuidAndCompanyId(
						layoutPrototypeUuid, context.getCompanyId());

			metadata.put("layout-prototype-uuid", layoutPrototypeUuid);
			metadata.put(
				"layout-prototype-name",
				layoutPrototype.getName(LocaleUtil.getDefault()));
		}

		boolean deleteLayout = MapUtil.getBoolean(
			context.getParameters(),
			"delete_" + layout.getPlid());

		LarDigest digest = context.getLarDigest();

		LarDigestItem digestItem = new LarDigestItemImpl();

		if (deleteLayout) {
			digestItem.setAction(LarDigesterConstants.ACTION_DELETE);
			digestItem.setPath(path);
			digestItem.setType(Layout.class.getName());
			digestItem.setClassPK(StringUtil.valueOf(layout.getLayoutId()));

			return digestItem;
		}

		if (layout.isIconImage()) {
			Image image = ImageLocalServiceUtil.getImage(
				layout.getIconImageId());

			if (image != null) {
				LarDigestItem item = imageDataHandler.digest(image, context);

				metadata.put("icon-image-path", item.getPath());
			}
		}

		digestItem.setMetadata(metadata);
		digestItem.setAction(LarDigesterConstants.ACTION_ADD);
		digestItem.setPath(path);
		digestItem.setType(Layout.class.getName());
		digestItem.setClassPK(String.valueOf(layout.getPlid()));

		if (layout.isTypeArticle()) {
			exportJournalArticle(layout, context);
		}
		else if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			if (linkToLayoutId > 0) {
				Layout linkedToLayout = LayoutLocalServiceUtil.getLayout(
					context.getScopeGroupId(), layout.isPrivateLayout(),
					linkToLayoutId);

				digest(linkedToLayout, context);
			}
		}
		else if (layout.isTypePortlet()) {
			for (Portlet portlet : LARExporter.getAlwaysExportablePortlets(
					context.getCompanyId())) {

				if (portlet.isScopeable() && layout.hasScopeGroup()) {
					String key = PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), portlet.getPortletId());


					context.setPlid(layout.getPlid());
					context.setOldPlid(layout.getPlid());
					context.setScopeGroupId(
						layout.getScopeGroup().getGroupId());
					context.setAttribute("scopeType", StringPool.BLANK);
					context.setAttribute("scopeLayoutUuid", layout.getUuid());
					context.setAttribute("layout", layout);

					BaseDataHandler portletDataHandler =
						DataHandlersUtil.getDataHandlerInstance(
							portlet.getPortletId());

					if (portletDataHandler != null) {
						portletDataHandler.digest(portlet, context);
					}
				}
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			for (String portletId : layoutTypePortlet.getPortletIds()) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					portletId);

				javax.portlet.PortletPreferences jxPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				String scopeType = GetterUtil.getString(
					jxPreferences.getValue("lfrScopeType", null));
				String scopeLayoutUuid = GetterUtil.getString(
					jxPreferences.getValue("lfrScopeLayoutUuid", null));

				long scopeGroupId = context.getScopeGroupId();

				if (Validator.isNotNull(scopeType)) {
					Group scopeGroup = null;

					if (scopeType.equals("company")) {
						scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
							layout.getCompanyId());
					}
					else if (scopeType.equals("layout")) {
						Layout scopeLayout = null;

						scopeLayout = LayoutLocalServiceUtil.
							fetchLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								context.getGroupId());

						if (scopeLayout == null) {
							continue;
						}

						scopeGroup = scopeLayout.getScopeGroup();
					}
					else {
						throw new IllegalArgumentException(
							"Scope type " + scopeType + " is invalid");
					}

					if (scopeGroup != null) {
						scopeGroupId = scopeGroup.getGroupId();
					}
				}

				PortletDataHandler portletDataHandler =
					(PortletDataHandler)DataHandlersUtil.getDataHandlerInstance(
						portlet.getPortletId());

				context.setPlid(layout.getPlid());
				context.setOldPlid(layout.getPlid());
				context.setScopeGroupId(scopeGroupId);
				context.setAttribute("scopeType", scopeType);
				context.setAttribute("scopeLayoutUuid", scopeLayoutUuid);
				context.setAttribute("layout", layout);

				if (portlet == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Do not export portlet " + portletId +
								" because the portlet does not exist");
					}

					continue;
				}


				if (portletDataHandler != null) {
					portletDataHandler.digest(portlet, context);
				}
			}
		}

		context.resetAttribute(DataHandlerContext.ATTRIBUTE_NAME_PLID);

		return digestItem;
	}

	public static final String SAME_GROUP_FRIENDLY_URL =
		"/[$SAME_GROUP_FRIENDLY_URL$]";

	@Override
	public void doImportData(LarDigestItem item, DataHandlerContext context)
		throws Exception {

		Map parameterMap = context.getParameters();

		LarDigest digest = context.getLarDigest();

		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importPublicLayoutPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PUBLIC_LAYOUT_PERMISSIONS);
		boolean importThemeSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME_REFERENCE);

		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);
		String portletsMergeMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE);

		long groupId = context.getGroupId();

		Map<Long, Layout> newLayoutsMap =
			(Map<Long, Layout>)context.getNewPrimaryKeysMap(Layout.class);

		Layout layout = (Layout)getZipEntryAsObject(
			context.getZipReader(), item.getPath());

		String layoutUuid = layout.getUuid();

		long layoutId = layout.getLayoutId();

		long oldLayoutId = layoutId;

		User user = context.getUser();

		boolean deleteLayout =
			(item.getAction() == LarDigesterConstants.ACTION_DELETE);

		if (deleteLayout) {
			Layout existingLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					layoutUuid, groupId);

			if (existingLayout != null) {
				newLayoutsMap.put(oldLayoutId, existingLayout);

				ServiceContext serviceContext =
					ServiceContextThreadLocal.getServiceContext();

				LayoutLocalServiceUtil.deleteLayout(
					existingLayout, false, serviceContext);
			}

			return;
		}

		String path = item.getPath();

		if (context.isPathProcessed(path)) {
			return;
		}

		Layout existingLayout = null;
		Layout importedLayout = null;

		String friendlyURL = layout.getFriendlyURL();

		boolean privateLayout = context.isPrivateLayout();

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW)) {

			layoutId = LayoutLocalServiceUtil.getNextLayoutId(
				groupId, privateLayout);
			friendlyURL = StringPool.SLASH + layoutId;
		}
		else if (layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME)) {

			Locale locale = LocaleUtil.getDefault();

			String localizedName = layout.getName(locale);

			List<Layout> previousLayouts = (List<Layout>)context.getAttribute(
				"previousLayouts");

			for (Layout curLayout : previousLayouts) {
				if (localizedName.equals(curLayout.getName(locale)) ||
					friendlyURL.equals(curLayout.getFriendlyURL())) {

					existingLayout = curLayout;

					break;
				}
			}

			if (existingLayout == null) {
				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
		}
		else if (layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			existingLayout = LayoutUtil.fetchByG_P_SPLU(
				groupId, privateLayout, layout.getUuid());

			if (SitesUtil.isLayoutModifiedSinceLastMerge(existingLayout)) {
				newLayoutsMap.put(oldLayoutId, existingLayout);

				return;
			}
		}
		else {

			// The default behaviour of import mode is
			// PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID

			existingLayout = LayoutUtil.fetchByUUID_G(
				layout.getUuid(), groupId);

			if (existingLayout == null) {
				existingLayout = LayoutUtil.fetchByG_P_F(
					groupId, privateLayout, friendlyURL);
			}

			if (existingLayout == null) {
				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
		}

		if (_log.isDebugEnabled()) {
			if (existingLayout == null) {
				_log.debug(
					"Layout with {groupId=" + groupId + ",privateLayout=" +
						privateLayout + ",layoutId=" + layoutId +
							"} does not exist");
			}
			else {
				_log.debug(
					"Layout with {groupId=" + groupId + ",privateLayout=" +
						privateLayout + ",layoutId=" + layoutId + "} exists");
			}
		}

		if (existingLayout == null) {
			long plid = CounterLocalServiceUtil.increment();

			importedLayout = LayoutUtil.create(plid);

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

				importedLayout.setSourcePrototypeLayoutUuid(layout.getUuid());

				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
			}
			else {
				importedLayout.setUuid(layout.getUuid());
				importedLayout.setCreateDate(layout.getCreateDate());
				importedLayout.setModifiedDate(layout.getModifiedDate());
				importedLayout.setLayoutPrototypeUuid(
					layout.getLayoutPrototypeUuid());
				importedLayout.setLayoutPrototypeLinkEnabled(
					layout.isLayoutPrototypeLinkEnabled());
				importedLayout.setSourcePrototypeLayoutUuid(
					layout.getSourcePrototypeLayoutUuid());
			}

			importedLayout.setGroupId(groupId);
			importedLayout.setPrivateLayout(privateLayout);
			importedLayout.setLayoutId(layoutId);

			// Resources

			boolean addGroupPermissions = true;

			Group group = importedLayout.getGroup();

			if (privateLayout && group.isUser()) {
				addGroupPermissions = false;
			}

			boolean addGuestPermissions = false;

			if (!privateLayout || layout.isTypeControlPanel()) {
				addGuestPermissions = true;
			}

			ResourceLocalServiceUtil.addResources(
				user.getCompanyId(), groupId, user.getUserId(),
				Layout.class.getName(), importedLayout.getPlid(), false,
				addGroupPermissions, addGuestPermissions);

			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, privateLayout);

			importedLayout.setLayoutSet(layoutSet);
		}
		else {
			importedLayout = existingLayout;
		}

		newLayoutsMap.put(oldLayoutId, importedLayout);

		long parentLayoutId = layout.getParentLayoutId();

		List<LarDigestItem> resultItems = digest.findDigestItems(
			0, null, Layout.class.getName(),
			StringUtil.valueOf(parentLayoutId));

		LarDigestItem parentLayoutItem = null;
		String parentLayoutUuid = null;

		if (resultItems != null && !resultItems.isEmpty()) {
			parentLayoutItem = resultItems.get(0);

			Map<String, String> metadata = parentLayoutItem.getMetadata();
			parentLayoutUuid = metadata.get("parent-layout-uuid");
		}

		if ((parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) &&
			(parentLayoutItem != null)) {

			importData(parentLayoutItem, context);

			Layout parentLayout = newLayoutsMap.get(parentLayoutId);

			parentLayoutId = parentLayout.getLayoutId();
		}
		else if (Validator.isNotNull(parentLayoutUuid)) {
			Layout parentLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					parentLayoutUuid, groupId);

			parentLayoutId = parentLayout.getLayoutId();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Importing layout with layout id " + layoutId +
					" and parent layout id " + parentLayoutId);
		}

		importedLayout.setCompanyId(user.getCompanyId());
		importedLayout.setParentLayoutId(parentLayoutId);
		importedLayout.setName(layout.getName());
		importedLayout.setTitle(layout.getTitle());
		importedLayout.setDescription(layout.getDescription());
		importedLayout.setKeywords(layout.getKeywords());
		importedLayout.setRobots(layout.getRobots());
		importedLayout.setType(layout.getType());

		if (layout.isTypeArticle()) {
			importJournalArticle(null, layout);

			importedLayout.setTypeSettings(layout.getTypeSettings());
		}
		else if (layout.isTypePortlet() &&
				Validator.isNotNull(layout.getTypeSettings()) &&
				!portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE)) {

			mergePortlets(
				importedLayout, layout.getTypeSettings(), portletsMergeMode);
		}
		else if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			if (linkToLayoutId > 0) {
				resultItems = digest.findDigestItems(
					0, null, Layout.class.getName(),
					StringUtil.valueOf(linkToLayoutId));

				LarDigestItem linekdLayoutItem = resultItems.get(0);

				if (linekdLayoutItem != null) {
					importData(linekdLayoutItem, context);

					Layout linkedLayout = newLayoutsMap.get(linkToLayoutId);

					typeSettingsProperties.setProperty(
						"privateLayout",
						String.valueOf(linkedLayout.getPrivateLayout()));
					typeSettingsProperties.setProperty(
						"linkToLayoutId",
						String.valueOf(linkedLayout.getLayoutId()));
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

						sb.append("Unable to link layout with friendly URL ");
						sb.append(layout.getFriendlyURL());
						sb.append(" and layout id ");
						sb.append(layout.getLayoutId());
						sb.append(" to layout with layout id ");
						sb.append(linkToLayoutId);

						_log.warn(sb.toString());
					}
				}
			}

			importedLayout.setTypeSettings(layout.getTypeSettings());
		}
		else {
			importedLayout.setTypeSettings(layout.getTypeSettings());
		}

		importedLayout.setHidden(layout.isHidden());
		importedLayout.setFriendlyURL(friendlyURL);

		if (importThemeSettings) {
			importedLayout.setThemeId(layout.getThemeId());
			importedLayout.setColorSchemeId(layout.getColorSchemeId());
		}
		else {
			importedLayout.setThemeId(StringPool.BLANK);
			importedLayout.setColorSchemeId(StringPool.BLANK);
		}

		importedLayout.setWapThemeId(layout.getWapThemeId());
		importedLayout.setWapColorSchemeId(layout.getWapColorSchemeId());
		importedLayout.setCss(layout.getCss());
		importedLayout.setPriority(layout.getPriority());
		importedLayout.setLayoutPrototypeUuid(layout.getLayoutPrototypeUuid());
		importedLayout.setLayoutPrototypeLinkEnabled(
			layout.isLayoutPrototypeLinkEnabled());

		// toDo: review StagingUtil.updateLastImportSettings
		/*StagingUtil.updateLastImportSettings(
			layoutElement, importedLayout, portletDataContext);*/

		fixTypeSettings(importedLayout);

		importedLayout.setIconImage(false);

		if (layout.isIconImage()) {
			Map<String, String> metadata = item.getMetadata();

			String iconImagePath = metadata.get("icon-image-path");

			byte[] iconBytes = getZipEntryAsByteArray(
				context.getZipReader(), iconImagePath);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				importedLayout.setIconImage(true);

				if (importedLayout.getIconImageId() == 0) {
					long iconImageId = CounterLocalServiceUtil.increment();

					importedLayout.setIconImageId(iconImageId);
				}

				ImageLocalServiceUtil.updateImage(
					importedLayout.getIconImageId(), iconBytes);
			}
		}
		else {
			ImageLocalServiceUtil.deleteImage(importedLayout.getIconImageId());
		}

		// toDo: review expando export-import
		/*ServiceContext serviceContext = createServiceContext(
			getExpandoPath(path), importedLayout, null);

		importedLayout.setExpandoBridgeAttributes(serviceContext);  */

		LayoutUtil.update(importedLayout, false);

		context.setPlid(importedLayout.getPlid());
		context.setOldPlid(layout.getPlid());

		List<Layout> newLayouts = (List<Layout>)context.getAttribute(
			"newLayouts");

		Set<Long> newLayoutIds = (Set<Long>)context.getAttribute(
			"newLayoutIds");

		newLayoutIds.add(importedLayout.getLayoutId());

		newLayouts.add(importedLayout);

		if (importPermissions) {
			importLayoutPermissions(importedLayout, item, context);
		}

		if (importPublicLayoutPermissions) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(importedLayout.getPlid());

			Role guestRole = RoleLocalServiceUtil.getRole(
				importedLayout.getCompanyId(), RoleConstants.GUEST);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				importedLayout.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
				guestRole.getRoleId(), new String[]{ActionKeys.VIEW});
		}

		context.setAttribute("newLayoutsMap", newLayoutsMap);
	}

	@Override
	public void doSerialize(Layout layout, DataHandlerContext context)
		throws Exception {

		String path = getEntityPath(layout);

		fixTypeSettings(layout);

		addZipEntry(context.getZipWriter(), path, layout);
	}

	public Layout getEntity(String classPK) {
		if (Validator.isNull(classPK)) {
			return null;
		}

		try {
			Long plid = Long.valueOf(classPK);

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			return layout;
		}
		catch (Exception e) {
			return null;
		}
	}

	private String[] appendPortletIds(
			String[] portletIds, String[] newPortletIds, String portletsMergeMode) {

		for (String portletId : newPortletIds) {
			if (ArrayUtil.contains(portletIds, portletId)) {
				continue;
			}

			if (portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM)) {

				portletIds = ArrayUtil.append(portletIds, portletId);
			}
			else {
				portletIds = ArrayUtil.append(
					new String[] {portletId}, portletIds);
			}
		}

		return portletIds;
	}

	private void exportJournalArticle(
			Layout layout, DataHandlerContext context)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String articleId = typeSettingsProperties.getProperty(
			"article-id", StringPool.BLANK);

		long articleGroupId = layout.getGroupId();

		if (Validator.isNull(articleId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No article id found in typeSettings of layout " +
						layout.getPlid());
			}
		}

		JournalArticle article = null;

		article = JournalArticleLocalServiceUtil.getLatestArticle(
			articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (article == null) {
			return;
		}

		journalArticleDataHandler.digest(article, context);

		// toDo: link the article to the layout
	}

	private void fixTypeSettings(Layout layout) throws Exception {
		if (!layout.isTypeURL()) {
			return;
		}

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = GetterUtil.getString(typeSettings.getProperty("url"));

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		if (!url.startsWith(friendlyURLPrivateGroupPath) &&
			!url.startsWith(friendlyURLPrivateUserPath) &&
			!url.startsWith(friendlyURLPublicPath)) {

			return;
		}

		int x = url.indexOf(CharPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		int y = url.indexOf(CharPool.SLASH, x + 1);

		if (y == -1) {
			return;
		}

		String friendlyURL = url.substring(x, y);
		String groupFriendlyURL = layout.getGroup().getFriendlyURL();

		if (!friendlyURL.equals(groupFriendlyURL)) {
			return;
		}

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + SAME_GROUP_FRIENDLY_URL + url.substring(y));
	}

	private void importJournalArticle(
			PortletDataContext portletDataContext, Layout layout)
		throws Exception {

		return;
	}

	private void importLayoutPermissions(
			Layout layout, LarDigestItem item, DataHandlerContext context)
		throws Exception {

		Map<String, List<String>> permissions = item.getPermissions();

		if (permissions != null) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(layout.getPlid());

			importPermissions(
				resourceName, resourcePrimKey, permissions, context);
		}
	}

	private void mergePortlets(
			Layout layout, String newTypeSettings, String portletsMergeMode) {

		try {
			UnicodeProperties previousTypeSettingsProperties =
				layout.getTypeSettingsProperties();

			LayoutTypePortlet previousLayoutType =
				(LayoutTypePortlet)layout.getLayoutType();

			LayoutTemplate previousLayoutTemplate =
				previousLayoutType.getLayoutTemplate();

			List<String> previousColumns = previousLayoutTemplate.getColumns();

			UnicodeProperties newTypeSettingsProperties = new UnicodeProperties(
				true);

			newTypeSettingsProperties.load(newTypeSettings);

			String layoutTemplateId = newTypeSettingsProperties.getProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

			previousTypeSettingsProperties.setProperty(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID,
				layoutTemplateId);

			String nestedColumnIds = newTypeSettingsProperties.getProperty(
				LayoutTypePortletConstants.NESTED_COLUMN_IDS);

			if (Validator.isNotNull(nestedColumnIds)) {
				previousTypeSettingsProperties.setProperty(
					LayoutTypePortletConstants.NESTED_COLUMN_IDS,
					nestedColumnIds);

				String[] nestedColumnIdsArray = StringUtil.split(
					nestedColumnIds);

				for (String nestedColumnId : nestedColumnIdsArray) {
					String nestedColumnValue =
						newTypeSettingsProperties.getProperty(nestedColumnId);

					previousTypeSettingsProperties.setProperty(
						nestedColumnId, nestedColumnValue);
				}
			}

			LayoutTemplate newLayoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, null);

			String[] newPortletIds = new String[0];

			for (String columnId : newLayoutTemplate.getColumns()) {
				String columnValue = newTypeSettingsProperties.getProperty(
					columnId);

				String[] portletIds = StringUtil.split(columnValue);

				if (!previousColumns.contains(columnId)) {
					newPortletIds = ArrayUtil.append(newPortletIds, portletIds);
				}
				else {
					String[] previousPortletIds = StringUtil.split(
						previousTypeSettingsProperties.getProperty(columnId));

					portletIds = appendPortletIds(
						previousPortletIds, portletIds, portletsMergeMode);

					previousTypeSettingsProperties.setProperty(
						columnId, StringUtil.merge(portletIds));
				}
			}

			// Add portlets in non-existent column to the first column

			String columnId = previousColumns.get(0);

			String[] portletIds = StringUtil.split(
				previousTypeSettingsProperties.getProperty(columnId));

			appendPortletIds(portletIds, newPortletIds, portletsMergeMode);

			previousTypeSettingsProperties.setProperty(
				columnId, StringUtil.merge(portletIds));

			layout.setTypeSettings(previousTypeSettingsProperties.toString());
		}
		catch (IOException ioe) {
			layout.setTypeSettings(newTypeSettings);
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(LayoutDataHandlerImpl.class);

}
