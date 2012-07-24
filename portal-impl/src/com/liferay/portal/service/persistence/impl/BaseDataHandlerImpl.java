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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.DataHandlerContext;
import com.liferay.portal.kernel.lar.DataHandlerContextThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContextListener;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.portlet.ProtectedActionRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PrimitiveLongList;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.lar.DataHandlersUtil;
import com.liferay.portal.lar.LayoutCache;
import com.liferay.portal.lar.PermissionExporter;
import com.liferay.portal.lar.XStreamWrapper;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.lar.digest.LarDigesterConstants;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcedModel;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockPermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.persistence.BaseDataHandler;
import com.liferay.portal.service.persistence.lar.AssetCategoryDataHandler;
import com.liferay.portal.service.persistence.lar.AssetLinkDataHandler;
import com.liferay.portal.service.persistence.lar.AssetVocabularyDataHandler;
import com.liferay.portal.service.persistence.lar.BookmarksEntryDataHandler;
import com.liferay.portal.service.persistence.lar.BookmarksFolderDataHandler;
import com.liferay.portal.service.persistence.lar.BookmarksPortletDataHandler;
import com.liferay.portal.service.persistence.lar.ImageDataHandler;
import com.liferay.portal.service.persistence.lar.JournalArticleDataHandler;
import com.liferay.portal.service.persistence.lar.JournalStructureDataHandler;
import com.liferay.portal.service.persistence.lar.JournalTemplateDataHandler;
import com.liferay.portal.service.persistence.lar.LockDataHandler;
import com.liferay.portal.service.persistence.lar.PortletPreferencesDataHandler;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portal.service.persistence.lar.LayoutSetDataHandler;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public abstract class BaseDataHandlerImpl<T extends BaseModel<T>>
	implements BaseDataHandler<T> {

	public void addZipEntry(String path, T object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, byte[] bytes) throws SystemException {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, InputStream is)
		throws SystemException {

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, Object object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, String s) throws SystemException {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, s);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, StringBuilder sb)
			throws SystemException {

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, sb);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public ServiceContext createServiceContext(
		Element element, ClassedModel classedModel, String namespace) {

		return createServiceContext(element, null, classedModel, namespace);
	}

	public ServiceContext createServiceContext(
		String path, ClassedModel classedModel, String namespace) {

		return createServiceContext(null, path, classedModel, namespace);
	}

	public void digest(T object) throws Exception {
		sendUpdateMessage(MESSAGE_COMMAND_DIGEST, object);

		DataHandlerContext context = getDataHandlerContext();

		LarDigestItem item = doDigest(object);

		if (item != null) {
			context.getLarDigest().write(item);
		}

		if (isResourceMain(object)) {
			digestAssetLinks(object);
			digestLocks(object);

			boolean portletMetadataAll = context.getBooleanParameter(
				getNamespace(), PortletDataHandlerKeys.PORTLET_METADATA_ALL);

			if (portletMetadataAll ||
					context.getBooleanParameter(getNamespace(), "categories")) {

				digestAssetCategories(object);
			}

			if (portletMetadataAll ||
					context.getBooleanParameter(getNamespace(), "comments")) {

				digestComments(object);
			}

			if (portletMetadataAll ||
					context.getBooleanParameter(getNamespace(), "ratings")) {

				digestRatingsEntries(object);
			}

			if (portletMetadataAll ||
					context.getBooleanParameter(getNamespace(), "tags")) {

				digestAssetTags(object);
			}
		}

		String path = getEntityPath(object);

		getDataHandlerContext().addProcessedPath(path);
	}

	public abstract LarDigestItem doDigest(T object) throws Exception;

	public abstract void doImport(LarDigestItem item)throws Exception;

	public Object fromXML(byte[] bytes) {
		if ((bytes == null) || (bytes.length == 0)) {
			return null;
		}

		return getXstreamWrapper().fromXML(new String(bytes));
	}

	public Object fromXML(String xml) {
		if (Validator.isNull(xml)) {
			return null;
		}

		return getXstreamWrapper().fromXML(xml);
	}

	// TODO re-think this method
	public abstract T getEntity(String classPK);

	public String getEntityPath(T object) {
		if (object instanceof Portlet) {
			Portlet portlet = (Portlet)object;

			return getPortletPath(portlet.getPortletId());
		}

		if (object instanceof BaseModel) {
			BaseModel<T> baseModel = (BaseModel<T>)object;

			Map<String, Object> modelAttributes =
				baseModel.getModelAttributes();

			StringBundler sb = new StringBundler();

			sb.append(StringPool.FORWARD_SLASH);
			sb.append("group");
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(modelAttributes.get("groupId"));
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(baseModel.getModelClassName());
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(baseModel.getPrimaryKeyObj() + ".xml");

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	public XStreamWrapper getXstreamWrapper() {
		if (_xStreamWrapper == null) {
			Object o = PortalBeanLocatorUtil.locate("xStreamWrapper");

			if (o != null) {
				_xStreamWrapper = ((XStreamWrapper)o);
			}
		}

		return _xStreamWrapper;
	}

	public List<String> getZipEntries() {
		return getZipReader().getEntries();
	}

	public byte[] getZipEntryAsByteArray(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsByteArray(path);
	}

	public File getZipEntryAsFile(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsFile(path);
	}

	public InputStream getZipEntryAsInputStream(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsInputStream(path);
	}

	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	public String getZipEntryAsString(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsString(path);
	}

	public List<String> getZipFolderEntries() {
		return getZipFolderEntries(StringPool.SLASH);
	}

	public List<String> getZipFolderEntries(String path) {
		return getZipReader().getFolderEntries(path);
	}

	public ZipReader getZipReader() {
		DataHandlerContext context =
			DataHandlerContextThreadLocal.getDataHandlerContext();

		if (context != null) {
			return context.getZipReader();
		}

		return null;
	}

	public ZipWriter getZipWriter() {
		DataHandlerContext context =
			DataHandlerContextThreadLocal.getDataHandlerContext();

		if (context != null) {
			return context.getZipWriter();
		}

		return null;
	}

	public void importData(LarDigestItem item) {
		try {
			sendUpdateMessage(MESSAGE_COMMAND_IMPORT, item);

			doImport(item);
		}
		catch (Exception e) {
		}
	}

	public void serialize(LarDigestItem item, DataHandlerContext context) {
		T object = getEntity(item.getClassPK());

		sendUpdateMessage(MESSAGE_COMMAND_SERIALIZE, object);

		if (object == null) {
			return;
		}

		String path = getEntityPath(object);

		try {
			doSerialize(object);

			addPermissions(item.getPermissions(), context);
			addExpando(object);
		}
		catch (Exception e) {
		}

	}

	public void setXstreamWrapper(XStreamWrapper xStreamWrapper) {
		_xStreamWrapper = xStreamWrapper;
	}

	public String toXML(Object object) {
		return getXstreamWrapper().toXML(object);
	}

	protected void addExpando(T object) throws SystemException {
		if (!(object instanceof ClassedModel)) {
			return;
		}

		ClassedModel classedModel = (ClassedModel)object;

		String expandoPath = getEntityPath(object);
		expandoPath = StringUtil.replace(expandoPath, ".xml", "-expando.xml");

		ExpandoBridge expandoBridge = classedModel.getExpandoBridge();

		addZipEntry(expandoPath, expandoBridge.getAttributes());
	}

	protected void addPermissions(
			Map<String, List<String>> permissions, DataHandlerContext context)
		throws Exception {

		for (String roleName : permissions.keySet()) {
			Role role = RoleLocalServiceUtil.getRole(
				context.getCompanyId(), roleName);


			String path = getRolePath(role);

			addZipEntry(path, role);
		}
	}

	protected ServiceContext createServiceContext(
		Element element, String path, ClassedModel classedModel,
		String namespace) {

		Class<?> clazz = classedModel.getModelClass();
		long classPK = getClassPK(classedModel);

		DataHandlerContext context =
			DataHandlerContextThreadLocal.getDataHandlerContext();

		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		serviceContext.setCompanyId(context.getCompanyId());
		serviceContext.setScopeGroupId(getScopeGroupId());

		// Dates

		if (classedModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)classedModel;

			serviceContext.setCreateDate(auditedModel.getCreateDate());
			serviceContext.setModifiedDate(auditedModel.getModifiedDate());
		}

		// Permissions

		if (!MapUtil.getBoolean(
				context.getParameters(), PortletDataHandlerKeys.PERMISSIONS)) {

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
		}

		// Asset

		boolean portletMetadataAll = context.getBooleanParameter(
			namespace, PortletDataHandlerKeys.PORTLET_METADATA_ALL);

		if (isResourceMain(classedModel)) {
			if (portletMetadataAll ||
				context.getBooleanParameter(namespace, "categories")) {

				// toDo: add getAssetCategoryIds to context
				//long[] assetCategoryIds = getAssetCategoryIds(clazz, classPK);

				//serviceContext.setAssetCategoryIds(assetCategoryIds);
			}

			if (portletMetadataAll ||
				context.getBooleanParameter(namespace, "tags")) {

				// toDo: add getAssetTagNames to context
				//String[] assetTagNames = getAssetTagNames(clazz, classPK);

				//serviceContext.setAssetTagNames(assetTagNames);
			}
		}

		// Expando

		String expandoPath = null;

		if (element != null) {
			expandoPath = element.attributeValue("expando-path");
		}
		else {
			expandoPath = getExpandoPath(path);
		}

		if (Validator.isNotNull(expandoPath)) {
			try {
				Map<String, Serializable> expandoBridgeAttributes =
					(Map<String, Serializable>)getZipEntryAsObject(expandoPath);

				serviceContext.setExpandoBridgeAttributes(
					expandoBridgeAttributes);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		return serviceContext;
	}

	protected long getClassPK(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.getResourcePrimKey();
		}
		else {
			return (Long)classedModel.getPrimaryKeyObj();
		}
	}

	protected void digestAssetCategories(T object) throws Exception {
		long classPK = getClassPK(object);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getCategories(
				object.getClass().getName(), classPK);

		for (AssetCategory assetCategory : assetCategories) {
			assetCategoryDataHandler.digest(assetCategory);
		}
	}

	protected void digestAssetLinks(T object)
		throws Exception {

		if (!isResourceMain(object)) {
			return;
		}

		AssetEntry assetEntry = null;

		try {
			long classPK = getClassPK(object);

			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				object.getClass().getName(), classPK);
		}
		catch (Exception e) {
			return;
		}

		List<AssetLink> directAssetLinks =
			AssetLinkLocalServiceUtil.getDirectLinks(assetEntry.getEntryId());

		if (directAssetLinks.isEmpty()) {
			return;
		}

		Map<Integer, List<AssetLink>> assetLinksMap =
			new HashMap<Integer, List<AssetLink>>();

		for (AssetLink assetLink : directAssetLinks) {
			List<AssetLink> assetLinks = assetLinksMap.get(assetLink.getType());

			if (assetLinks == null) {
				assetLinks = new ArrayList<AssetLink>();

				assetLinksMap.put(assetLink.getType(), assetLinks);
			}

			assetLinks.add(assetLink);
		}

		for (Map.Entry<Integer, List<AssetLink>> entry :
				assetLinksMap.entrySet()) {

			int assetLinkType = entry.getKey();
			List<AssetLink> assetLinks = entry.getValue();

			List<String> assetLinkUuids = new ArrayList<String>(
				directAssetLinks.size());

			for (AssetLink assetLink : assetLinks) {
				assetLinkDataHandler.digest(assetLink);
			}
		}
	}

	protected void digestAssetTags(T object) throws Exception {
		long classPK = getClassPK(object);

		String[] tagNames = AssetTagLocalServiceUtil.getTagNames(
				object.getClass().getName(), classPK);

		for (String tagName : tagNames) {
			// TODO create AssetTagDataHandler
		}
	}

	protected void digestComments(T object) throws Exception {
		long classNameId = PortalUtil.getClassNameId(
				object.getClass().getName());

		long classPK = getClassPK(object);

		MBDiscussion discussion = MBDiscussionUtil.fetchByC_C(
			classNameId, classPK);

		if (discussion == null) {
			return;
		}

		List<MBMessage> messages = MBMessageLocalServiceUtil.getThreadMessages(
			discussion.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		if (messages.size() == 0) {
			return;
		}

		for (MBMessage message : messages) {
			// TODO create MBMessageDataHandler
		}
	}

	protected void digestLocks(T object) throws Exception {
		String key = String.valueOf(object.getPrimaryKeyObj());

		if (LockLocalServiceUtil.isLocked(object.getClass().getName(), key)) {

			Lock lock = LockLocalServiceUtil.getLock(
				object.getClass().getName(), key);

			LockDataHandler lockDataHandler =
				(LockDataHandler)DataHandlersUtil.getDataHandlerInstance(
					Lock.class.getName());

			lockDataHandler.digest(lock);
		}
	}

	protected Map<String, List<String>> digestPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, boolean portletActions)
		throws Exception {

		List<Role> roles = layoutCache.getGroupRoles_5(groupId, resourceName);

		List<String> actionIds = null;

		if (portletActions) {
			actionIds = ResourceActionsUtil.getPortletResourceActions(
				resourceName);
		}
		else {
			actionIds = ResourceActionsUtil.getModelResourceActions(
				resourceName);
		}

		if (actionIds.isEmpty()) {
			return null;
		}

		PrimitiveLongList roleIds = new PrimitiveLongList(roles.size());
		Map<Long, Role> roleIdsToRoles = new HashMap<Long, Role>();

		for (Role role : roles) {
			String name = role.getName();

			if (name.equals(RoleConstants.ADMINISTRATOR)) {
				continue;
			}

			roleIds.add(role.getRoleId());
			roleIdsToRoles.put(role.getRoleId(), role);
		}

		Map<Long, Set<String>> roleIdsToActionIds =
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
					resourcePrimKey, roleIds.getArray(), actionIds);

		HashMap<String, List<String>> roleMap =
			new HashMap<String, List<String>>();

		for (Map.Entry<Long, Set<String>> entry :
				roleIdsToActionIds.entrySet()) {

			List<String> values = ListUtil.fromCollection(entry.getValue());

			roleMap.put(String.valueOf(entry.getKey()), values);
		}

		return roleMap;
	}

	protected void digestRatingsEntries(T object) throws Exception {
		long classPK = getClassPK(object);

		List<RatingsEntry> ratingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(
					object.getClass().getName(), classPK);

		if (ratingsEntries.size() == 0) {
			return;
		}

		for (RatingsEntry entry : ratingsEntries) {
			// TODO create RatingsEntryDataHandler
		}
	}

	protected Map<String, List<String>> digestEntityPermissions(
			String resourceName, long resourcePK)
		throws Exception {

		DataHandlerContext context = getDataHandlerContext();

		HashMap<String, List<String>> permissions =
			new HashMap<String, List<String>>();

		Group group = GroupLocalServiceUtil.getGroup(context.getGroupId());

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			context.getCompanyId());

		PrimitiveLongList roleIds = new PrimitiveLongList(roles.size());
		Map<Long, String> roleIdsToNames = new HashMap<Long, String>();

		for (Role role : roles) {
			int type = role.getType();

			if ((type == RoleConstants.TYPE_REGULAR) ||
				((type == RoleConstants.TYPE_ORGANIZATION) &&
					group.isOrganization()) ||
				((type == RoleConstants.TYPE_SITE) &&
					(group.isLayoutSetPrototype() || group.isSite()))) {

				String name = role.getName();

				roleIds.add(role.getRoleId());
				roleIdsToNames.put(role.getRoleId(), name);
			}
			else if ((type == RoleConstants.TYPE_PROVIDER) && role.isTeam()) {
				Team team = TeamLocalServiceUtil.getTeam(role.getClassPK());

				if (team.getGroupId() == context.getGroupId()) {
					String name =
						PermissionExporter.ROLE_TEAM_PREFIX + team.getName();

					roleIds.add(role.getRoleId());
					roleIdsToNames.put(role.getRoleId(), name);
				}
			}
		}

		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			resourceName);

		Map<Long, Set<String>> roleIdsToActionIds = getActionIds(
			context.getCompanyId(), roleIds.getArray(), resourceName,
			resourcePK, actionIds);

		for (Map.Entry<Long, String> entry : roleIdsToNames.entrySet()) {
			long roleId = entry.getKey();
			String name = entry.getValue();

			Set<String> availableActionIds = roleIdsToActionIds.get(roleId);

			if ((availableActionIds == null) || availableActionIds.isEmpty()) {
				continue;
			}

			List<String> actionIdsList = ListUtil.fromCollection(
				availableActionIds);

			permissions.put(name, actionIdsList);
		}

		return permissions;
	}

	protected void doSerialize(T object) throws Exception {
		String path = getEntityPath(object);

		addZipEntry(path, object);
	}

	protected Map<Long, Set<String>> getActionIds(
			long companyId, long[] roleIds, String className, long primKey,
			List<String> actionIds)
		throws PortalException, SystemException {

		if (ResourceBlockLocalServiceUtil.isSupported(className)) {
			return ResourceBlockPermissionLocalServiceUtil.
				getAvailableResourceBlockPermissionActionIds(
					roleIds, className, primKey, actionIds);
		}
		else {
			return ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(primKey), roleIds, actionIds);
		}
	}

	protected int getDigestAction(T object) {
		DataHandlerContext context = getDataHandlerContext();

		if (object instanceof BaseModel) {
			BaseModel modelObj = (BaseModel)object;

			Map<String, Object> modelAttributes = modelObj.getModelAttributes();

			Date modifiedDate = (Date)modelAttributes.get("modifiedDate");

			if (context.getLastPublishDate().before(modifiedDate)) {
				return LarDigesterConstants.ACTION_UPDATE;
			}

			return LarDigesterConstants.ACTION_ADD;
		}

		return -1;
	}

	protected String getNamespace() {
		return StringPool.BLANK;
	}

	protected DataHandlerContext getDataHandlerContext() {
		return DataHandlerContextThreadLocal.getDataHandlerContext();
	}

	protected String getExpandoPath(String path) {
		if (!isValidPath(path)) {
			throw new IllegalArgumentException(
				path + " is located outside of the lar");
		}

		int pos = path.lastIndexOf(".xml");

		if (pos == -1) {
			throw new IllegalArgumentException(
				path + " does not end with .xml");
		}

		return path.substring(0, pos).concat("-expando").concat(
			path.substring(pos));
	}

	protected String getLayoutPath(long layoutId) {
		return getRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	protected String getPortletPath(String portletId) {
		StringBundler sb = new StringBundler();

		sb.append(getRootPath());
		sb.append(ROOT_PATH_PORTLETS);
		sb.append(portletId);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(portletId + ".xml");

		return sb.toString();
	}

	protected String getRolePath(Role role) {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.FORWARD_SLASH);
		sb.append("roles");
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(role.getName() + ".xml");

		return sb.toString();
	}

	protected String getRootPath() {
		return ROOT_PATH_GROUPS + getScopeGroupId();
	}

	protected long getScopeGroupId() {
		DataHandlerContext context = getDataHandlerContext();

		if (context != null) {
			return context.getGroupId();
		}

		return 0;
	}

	protected String getSourcePortletPath(String portletId) {
		return getSourceRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	protected String getSourceRootPath() {
		return ROOT_PATH_GROUPS + getSourceGroupId();
	}

	protected long getSourceGroupId() {
		DataHandlerContext context =
			getDataHandlerContext();

		if (context != null) {
			return context.getSourceGroupId();
		}

		return 0;
	}

	protected void importEntityPermissions(
			String resourceName, long resourcePK, long newResourcePK)
		throws PortalException, SystemException {

		DataHandlerContext context = getDataHandlerContext();

		if (!MapUtil.getBoolean(
			context.getParameters(), PortletDataHandlerKeys.PERMISSIONS)) {

			return;
		}

		List<KeyValuePair> permissions = null; /*_permissionsMap.get(
			getPrimaryKeyString(resourceName, resourcePK));*/

		if (permissions == null) {
			return;
		}

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		for (KeyValuePair permission : permissions) {
			String roleName = permission.getKey();

			Role role = null;

			Team team = null;

			if (roleName.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
				roleName = roleName.substring(
						PermissionExporter.ROLE_TEAM_PREFIX.length());

				try {
					team = TeamLocalServiceUtil.getTeam(
						context.getGroupId(), roleName);
				}
				catch (NoSuchTeamException nste) {
					if (_log.isWarnEnabled()) {
						_log.warn("Team " + roleName + " does not exist");
					}

					continue;
				}
			}

			try {
				if (team != null) {
					role = RoleLocalServiceUtil.getTeamRole(
						context.getCompanyId(), team.getTeamId());
				}
				else {
					role = RoleLocalServiceUtil.getRole(
						context.getCompanyId(), roleName);
				}
			}
			catch (NoSuchRoleException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn("Role " + roleName + " does not exist");
				}

				continue;
			}

			String[] actionIds = StringUtil.split(permission.getValue());

			roleIdsToActionIds.put(role.getRoleId(), actionIds);
		}

		if (roleIdsToActionIds.isEmpty()) {
			return;
		}

		if (ResourceBlockLocalServiceUtil.isSupported(resourceName)) {
			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
				context.getCompanyId(), context.getGroupId(), resourceName,
				newResourcePK, roleIdsToActionIds);
		}
		else {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				context.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(newResourcePK), roleIdsToActionIds);
		}
	}

	protected void importPermissions(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			String resourceName, String resourcePrimKey,
			Map<String, List<String>> permissions, boolean portletActions)
		throws Exception {

		Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();

		/*List<Element> roleElements = permissionsElement.elements("role");

		for (String role : permissions) {
			String name = roleElement.attributeValue("name");
			int type = GetterUtil.getInteger(
					roleElement.attributeValue("type"));

			Role role = null;

			if (name.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
				name = name.substring(
					PermissionExporter.ROLE_TEAM_PREFIX.length());

				String description = roleElement.attributeValue("description");

				Team team = null;

				try {
					team = TeamLocalServiceUtil.getTeam(groupId, name);
				}
				catch (NoSuchTeamException nste) {
					team = TeamLocalServiceUtil.addTeam(
							userId, groupId, name, description);
				}

				role = RoleLocalServiceUtil.getTeamRole(
					companyId, team.getTeamId());
			}
			else {
				role = layoutCache.getRole(companyId, name);
			}

			if (role == null) {
				String title = roleElement.attributeValue("title");

				Map<Locale, String> titleMap =
					LocalizationUtil.getLocalizationMap(title);

				String description = roleElement.attributeValue("description");

				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(description);

				role = RoleLocalServiceUtil.addRole(
					userId, companyId, name, titleMap, descriptionMap, type);
			}

			List<String> actions = permissions.get(roleElement);

			roleIdsToActionIds.put(
				role.getRoleId(), actions.toArray(new String[actions.size()]));
		}

		if (roleIdsToActionIds.isEmpty()) {
			return;
		}

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
			resourcePrimKey, roleIdsToActionIds);
		*/
	}

	protected boolean isResourceMain(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.isResourceMain();
		}

		return true;
	}

	protected boolean isValidPath(String path) {
		if (path.contains(StringPool.DOUBLE_PERIOD)) {
			return false;
		}

		return true;
	}

	protected void sendUpdateMessage(String messageCommand, Object payload) {
		Message message = new Message();

		message.put("command", messageCommand);
		message.put("payload", payload);

		MessageBusUtil.sendMessage(DestinationNames.LAR_EXPORT_IMPORT, message);
	}

	private Log _log = LogFactoryUtil.getLog(BaseDataHandlerImpl.class);
	private PortletDataContextListener _portletDataContextListener;
	private XStreamWrapper _xStreamWrapper;

	@BeanReference(type = JournalArticleDataHandler.class)
	protected JournalArticleDataHandler journalArticleDataHandler;
	@BeanReference(type = JournalStructureDataHandler.class)
	protected JournalStructureDataHandler journalStructureDataHandler;
	@BeanReference(type = JournalTemplateDataHandler.class)
	protected JournalTemplateDataHandler journalTemplateDataHandler;
	@BeanReference(type = ImageDataHandler.class)
	protected ImageDataHandler imageDataHandler;
	@BeanReference(type = BookmarksEntryDataHandler.class)
	protected BookmarksEntryDataHandler bookmarksEntryDataHandler;
	@BeanReference(type = BookmarksFolderDataHandler.class)
	protected BookmarksFolderDataHandler bookmarksFolderDataHandler;
	@BeanReference(type = BookmarksPortletDataHandler.class)
	protected BookmarksPortletDataHandler bookmarksPortletDataHandler;
	@BeanReference(type = PortletPreferencesDataHandler.class)
	protected PortletPreferencesDataHandler portletPreferencesDataHandler;
	@BeanReference(type = AssetVocabularyDataHandler.class)
	protected AssetVocabularyDataHandler assetVocabularyDataHandler;
	@BeanReference(type = AssetCategoryDataHandler.class)
	protected AssetCategoryDataHandler assetCategoryDataHandler;
	@BeanReference(type = AssetLinkDataHandler.class)
	protected AssetLinkDataHandler assetLinkDataHandler;
	@BeanReference(type = LayoutSetDataHandler.class)
	protected LayoutSetDataHandler layoutSetDataHandler;

}
