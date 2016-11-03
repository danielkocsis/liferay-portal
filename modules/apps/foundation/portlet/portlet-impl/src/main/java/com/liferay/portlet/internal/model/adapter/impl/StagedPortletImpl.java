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

package com.liferay.portlet.internal.model.adapter.impl;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.expando.kernel.model.CustomAttributesDisplay;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.application.type.ApplicationType;
import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.PluginSetting;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletFilter;
import com.liferay.portal.kernel.model.PortletInfo;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.security.permission.PermissionPropagator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portlet.model.adapter.StagedPortlet;
import com.liferay.social.kernel.model.SocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialRequestInterpreter;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * @author Daniel Kocsis
 */
public class StagedPortletImpl implements StagedPortlet {

	public StagedPortletImpl() {
	}

	public StagedPortletImpl(Portlet portlet) {
		Objects.requireNonNull(
			portlet,
			"Unable to create a new staged portlet for a null portlet");

		_portlet = portlet;
	}

	@Override
	public Class<?> getModelClass() {
		return StagedPortlet.class;
	}

	@Override
	public String getModelClassName() {
		return StagedPortlet.class.getName();
	}

	@Override
	public void persist() {
		_portlet.persist();
	}

	@Override
	public Date getCreateDate() {
		return new Date();
	}

	@Override
	public void setCreateDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getModifiedDate() {
		return new Date();
	}

	@Override
	public void setModifiedDate(Date date) {
		throw new UnsupportedOperationException();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(StagedPortlet.class);
	}

	@Override
	public String getUuid() {
		return _portlet.getPortletId();
	}

	@Override
	public void setUuid(String uuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _portlet.getModelAttributes();
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		_portlet.setModelAttributes(attributes);
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _portlet.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _portlet.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_portlet.resetOriginalValues();
	}

	@Override
	public long getPrimaryKey() {
		return _portlet.getPrimaryKey();
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_portlet.setPrimaryKey(primaryKey);
	}

	@Override
	public long getMvccVersion() {
		return _portlet.getMvccVersion();
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_portlet.setMvccVersion(mvccVersion);
	}

	@Override
	public long getId() {
		return _portlet.getId();
	}

	@Override
	public void setId(long id) {
		_portlet.setId(id);
	}

	@Override
	public long getCompanyId() {
		return _portlet.getCompanyId();
	}

	@Override
	public void setCompanyId(long companyId) {
		_portlet.setCompanyId(companyId);
	}

	@Override
	public String getPortletId() {
		return _portlet.getPortletId();
	}

	@Override
	public void setPortletId(String portletId) {
		_portlet.setPortletId(portletId);
	}

	@Override
	public String getRoles() {
		return _portlet.getRoles();
	}

	@Override
	public void setRoles(String roles) {
		_portlet.setRoles(roles);
	}

	@Override
	public boolean getActionURLRedirect() {
		return _portlet.getActionURLRedirect();
	}

	@Override
	public boolean isActive() {
		return _portlet.isActive();
	}

	@Override
	public void setActive(boolean active) {
		_portlet.setActive(active);
	}

	@Override
	public boolean isNew() {
		return _portlet.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_portlet.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _portlet.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portlet.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _portlet.isEscapedModel();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _portlet.getPortletId();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_portlet.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _portlet.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_portlet.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_portlet.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_portlet.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void addApplicationType(ApplicationType applicationType) {
		_portlet.addApplicationType(applicationType);
	}

	@Override
	public void addProcessingEvent(QName processingEvent) {
		_portlet.addProcessingEvent(processingEvent);
	}

	@Override
	public CacheModel<Portlet> toCacheModel() {
		return _portlet.toCacheModel();
	}

	@Override
	public Portlet toEscapedModel() {
		return _portlet.toEscapedModel();
	}

	@Override
	public Portlet toUnescapedModel() {
		return _portlet.toUnescapedModel();
	}

	@Override
	public String toXmlString() {
		return _portlet.toXmlString();
	}

	@Override
	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_portlet.addPublicRenderParameter(publicRenderParameter);
	}

	@Override
	public void addPublishingEvent(QName publishingEvent) {
		_portlet.addPublishingEvent(publishingEvent);
	}

	@Override
	public void addSchedulerEntry(SchedulerEntry schedulerEntry) {
		_portlet.addSchedulerEntry(schedulerEntry);
	}

	@Override
	public Object clone() {
		return new StagedPortletImpl((Portlet)_portlet.clone());
	}

	@Override
	public int compareTo(Portlet portlet) {
		return _portlet.compareTo(portlet);
	}

	@Override
	public int getActionTimeout() {
		return _portlet.getActionTimeout();
	}

	@Override
	public void setActionTimeout(int actionTimeout) {
		_portlet.setActionTimeout(actionTimeout);
	}

	@Override
	public boolean getActive() {
		return _portlet.getActive();
	}

	@Override
	public boolean getAddDefaultResource() {
		return _portlet.getAddDefaultResource();
	}

	@Override
	public boolean getAjaxable() {
		return _portlet.getAjaxable();
	}

	@Override
	public Set<String> getAllPortletModes() {
		return _portlet.getAllPortletModes();
	}

	@Override
	public Set<String> getAllWindowStates() {
		return _portlet.getAllWindowStates();
	}

	@Override
	public Set<ApplicationType> getApplicationTypes() {
		return _portlet.getApplicationTypes();
	}

	@Override
	public void setApplicationTypes(Set<ApplicationType> applicationTypes) {
		_portlet.setApplicationTypes(applicationTypes);
	}

	@Override
	public List<String> getAssetRendererFactoryClasses() {
		return _portlet.getAssetRendererFactoryClasses();
	}

	@Override
	public void setAssetRendererFactoryClasses(
		List<String> assetRendererFactoryClasses) {

		_portlet.setAssetRendererFactoryClasses(assetRendererFactoryClasses);
	}

	@Override
	public List<AssetRendererFactory<?>> getAssetRendererFactoryInstances() {
		return _portlet.getAssetRendererFactoryInstances();
	}

	@Override
	public List<String> getAtomCollectionAdapterClasses() {
		return _portlet.getAtomCollectionAdapterClasses();
	}

	@Override
	public void setAtomCollectionAdapterClasses(
		List<String> atomCollectionAdapterClasses) {

		_portlet.setAtomCollectionAdapterClasses(atomCollectionAdapterClasses);
	}

	@Override
	public List<AtomCollectionAdapter<?>> getAtomCollectionAdapterInstances() {
		return _portlet.getAtomCollectionAdapterInstances();
	}

	@Override
	public Set<String> getAutopropagatedParameters() {
		return _portlet.getAutopropagatedParameters();
	}

	@Override
	public void setAutopropagatedParameters(
		Set<String> autopropagatedParameters) {

		_portlet.setAutopropagatedParameters(autopropagatedParameters);
	}

	@Override
	public Portlet getClonedInstance(String portletId) {
		return _portlet.getClonedInstance(portletId);
	}

	@Override
	public String getConfigurationActionClass() {
		return _portlet.getConfigurationActionClass();
	}

	@Override
	public void setConfigurationActionClass(String configurationActionClass) {
		_portlet.setConfigurationActionClass(configurationActionClass);
	}

	@Override
	public ConfigurationAction getConfigurationActionInstance() {
		return _portlet.getConfigurationActionInstance();
	}

	@Override
	public String getContextName() {
		return _portlet.getContextName();
	}

	@Override
	public String getContextPath() {
		return _portlet.getContextPath();
	}

	@Override
	public String getControlPanelEntryCategory() {
		return _portlet.getControlPanelEntryCategory();
	}

	@Override
	public void setControlPanelEntryCategory(String controlPanelEntryCategory) {
		_portlet.setControlPanelEntryCategory(controlPanelEntryCategory);
	}

	@Override
	public String getControlPanelEntryClass() {
		return _portlet.getControlPanelEntryClass();
	}

	@Override
	public void setControlPanelEntryClass(String controlPanelEntryClass) {
		_portlet.setControlPanelEntryClass(controlPanelEntryClass);
	}

	@Override
	public ControlPanelEntry getControlPanelEntryInstance() {
		return _portlet.getControlPanelEntryInstance();
	}

	@Override
	public double getControlPanelEntryWeight() {
		return _portlet.getControlPanelEntryWeight();
	}

	@Override
	public void setControlPanelEntryWeight(double controlPanelEntryWeight) {
		_portlet.setControlPanelEntryWeight(controlPanelEntryWeight);
	}

	@Override
	public String getCssClassWrapper() {
		return _portlet.getCssClassWrapper();
	}

	@Override
	public void setCssClassWrapper(String cssClassWrapper) {
		_portlet.setCssClassWrapper(cssClassWrapper);
	}

	@Override
	public List<String> getCustomAttributesDisplayClasses() {
		return _portlet.getCustomAttributesDisplayClasses();
	}

	@Override
	public void setCustomAttributesDisplayClasses(
		List<String> customAttributesDisplayClasses) {

		_portlet.setCustomAttributesDisplayClasses(
			customAttributesDisplayClasses);
	}

	@Override
	public List<CustomAttributesDisplay> getCustomAttributesDisplayInstances() {
		return _portlet.getCustomAttributesDisplayInstances();
	}

	@Override
	public PluginSetting getDefaultPluginSetting() {
		return _portlet.getDefaultPluginSetting();
	}

	@Override
	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_portlet.setDefaultPluginSetting(pluginSetting);
	}

	@Override
	public String getDefaultPreferences() {
		return _portlet.getDefaultPreferences();
	}

	@Override
	public void setDefaultPreferences(String defaultPreferences) {
		_portlet.setDefaultPreferences(defaultPreferences);
	}

	@Override
	public String getDisplayName() {
		return _portlet.getDisplayName();
	}

	@Override
	public void setDisplayName(String displayName) {
		_portlet.setDisplayName(displayName);
	}

	@Override
	public Integer getExpCache() {
		return _portlet.getExpCache();
	}

	@Override
	public void setExpCache(Integer expCache) {
		_portlet.setExpCache(expCache);
	}

	@Override
	public String getFacebookIntegration() {
		return _portlet.getFacebookIntegration();
	}

	@Override
	public void setFacebookIntegration(String facebookIntegration) {
		_portlet.setFacebookIntegration(facebookIntegration);
	}

	@Override
	public List<String> getFooterPortalCss() {
		return _portlet.getFooterPortalCss();
	}

	@Override
	public void setFooterPortalCss(List<String> footerPortalCss) {
		_portlet.setFooterPortalCss(footerPortalCss);
	}

	@Override
	public List<String> getFooterPortalJavaScript() {
		return _portlet.getFooterPortalJavaScript();
	}

	@Override
	public void setFooterPortalJavaScript(List<String> footerPortalJavaScript) {
		_portlet.setFooterPortalJavaScript(footerPortalJavaScript);
	}

	@Override
	public List<String> getFooterPortletCss() {
		return _portlet.getFooterPortletCss();
	}

	@Override
	public void setFooterPortletCss(List<String> footerPortletCss) {
		_portlet.setFooterPortletCss(footerPortletCss);
	}

	@Override
	public List<String> getFooterPortletJavaScript() {
		return _portlet.getFooterPortletJavaScript();
	}

	@Override
	public void setFooterPortletJavaScript(
		List<String> footerPortletJavaScript) {

		_portlet.setFooterPortalJavaScript(footerPortletJavaScript);
	}

	@Override
	public String getFriendlyURLMapperClass() {
		return _portlet.getFriendlyURLMapperClass();
	}

	@Override
	public void setFriendlyURLMapperClass(String friendlyURLMapperClass) {
		_portlet.setFriendlyURLMapperClass(friendlyURLMapperClass);
	}

	@Override
	public FriendlyURLMapper getFriendlyURLMapperInstance() {
		return _portlet.getFriendlyURLMapperInstance();
	}

	@Override
	public String getFriendlyURLMapping() {
		return _portlet.getFriendlyURLMapping();
	}

	@Override
	public void setFriendlyURLMapping(String friendlyURLMapping) {
		_portlet.setFriendlyURLMapping(friendlyURLMapping);
	}

	@Override
	public String getFriendlyURLRoutes() {
		return _portlet.getFriendlyURLRoutes();
	}

	@Override
	public void setFriendlyURLRoutes(String friendlyURLRoutes) {
		_portlet.setFriendlyURLRoutes(friendlyURLRoutes);
	}

	@Override
	public List<String> getHeaderPortalCss() {
		return _portlet.getHeaderPortalCss();
	}

	@Override
	public void setHeaderPortalCss(List<String> headerPortalCss) {
		_portlet.setHeaderPortalCss(headerPortalCss);
	}

	@Override
	public List<String> getHeaderPortalJavaScript() {
		return _portlet.getHeaderPortalJavaScript();
	}

	@Override
	public void setHeaderPortalJavaScript(List<String> headerPortalJavaScript) {
		_portlet.setHeaderPortalJavaScript(headerPortalJavaScript);
	}

	@Override
	public List<String> getHeaderPortletCss() {
		return _portlet.getHeaderPortletCss();
	}

	@Override
	public void setHeaderPortletCss(List<String> headerPortletCss) {
		_portlet.setHeaderPortletCss(headerPortletCss);
	}

	@Override
	public List<String> getHeaderPortletJavaScript() {
		return _portlet.getHeaderPortletJavaScript();
	}

	@Override
	public void setHeaderPortletJavaScript(
		List<String> headerPortletJavaScript) {

		_portlet.setHeaderPortletJavaScript(headerPortletJavaScript);
	}

	@Override
	public String getIcon() {
		return _portlet.getIcon();
	}

	@Override
	public void setIcon(String icon) {
		_portlet.setIcon(icon);
	}

	@Override
	public boolean getInclude() {
		return _portlet.getInclude();
	}

	@Override
	public List<String> getIndexerClasses() {
		return _portlet.getIndexerClasses();
	}

	@Override
	public void setIndexerClasses(List<String> indexerClasses) {
		_portlet.setIndexerClasses(indexerClasses);
	}

	@Override
	public List<Indexer<?>> getIndexerInstances() {
		return _portlet.getIndexerInstances();
	}

	@Override
	public Map<String, String> getInitParams() {
		return _portlet.getInitParams();
	}

	@Override
	public void setInitParams(Map<String, String> initParams) {
		_portlet.setInitParams(initParams);
	}

	@Override
	public boolean getInstanceable() {
		return _portlet.getInstanceable();
	}

	@Override
	public String getInstanceId() {
		return _portlet.getInstanceId();
	}

	@Override
	public boolean getLayoutCacheable() {
		return _portlet.getLayoutCacheable();
	}

	@Override
	public boolean getMaximizeEdit() {
		return _portlet.getMaximizeEdit();
	}

	@Override
	public boolean getMaximizeHelp() {
		return _portlet.getMaximizeHelp();
	}

	@Override
	public String getOpenSearchClass() {
		return _portlet.getOpenSearchClass();
	}

	@Override
	public void setOpenSearchClass(String openSearchClass) {
		_portlet.setOpenSearchClass(openSearchClass);
	}

	@Override
	public OpenSearch getOpenSearchInstance() {
		return _portlet.getOpenSearchInstance();
	}

	@Override
	public String getParentStrutsPath() {
		return _portlet.getParentStrutsPath();
	}

	@Override
	public void setParentStrutsPath(String parentStrutsPath) {
		_portlet.setParentStrutsPath(parentStrutsPath);
	}

	@Override
	public String getPermissionPropagatorClass() {
		return _portlet.getPermissionPropagatorClass();
	}

	@Override
	public void setPermissionPropagatorClass(String permissionPropagatorClass) {
		_portlet.setPermissionPropagatorClass(permissionPropagatorClass);
	}

	@Override
	public PermissionPropagator getPermissionPropagatorInstance() {
		return _portlet.getPermissionPropagatorInstance();
	}

	@Override
	public String getPluginId() {
		return _portlet.getPluginId();
	}

	@Override
	public PluginPackage getPluginPackage() {
		return _portlet.getPluginPackage();
	}

	@Override
	public void setPluginPackage(PluginPackage pluginPackage) {
		_portlet.setPluginPackage(pluginPackage);
	}

	@Override
	public String getPluginType() {
		return _portlet.getPluginType();
	}

	@Override
	public String getPollerProcessorClass() {
		return _portlet.getPollerProcessorClass();
	}

	@Override
	public void setPollerProcessorClass(String pollerProcessorClass) {
		_portlet.setPollerProcessorClass(pollerProcessorClass);
	}

	@Override
	public PollerProcessor getPollerProcessorInstance() {
		return _portlet.getPollerProcessorInstance();
	}

	@Override
	public String getPopMessageListenerClass() {
		return _portlet.getPopMessageListenerClass();
	}

	@Override
	public void setPopMessageListenerClass(String popMessageListenerClass) {
		_portlet.setPopMessageListenerClass(popMessageListenerClass);
	}

	@Override
	public MessageListener getPopMessageListenerInstance() {
		return _portlet.getPopMessageListenerInstance();
	}

	@Override
	public boolean getPopUpPrint() {
		return _portlet.getPopUpPrint();
	}

	@Override
	public PortletApp getPortletApp() {
		return _portlet.getPortletApp();
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portlet.setPortletApp(portletApp);
	}

	@Override
	public String getPortletClass() {
		return _portlet.getPortletClass();
	}

	@Override
	public void setPortletClass(String portletClass) {
		_portlet.setPortletClass(portletClass);
	}

	@Override
	public String getPortletDataHandlerClass() {
		return _portlet.getPortletDataHandlerClass();
	}

	@Override
	public void setPortletDataHandlerClass(String portletDataHandlerClass) {
		_portlet.setPortletDataHandlerClass(portletDataHandlerClass);
	}

	@Override
	public PortletDataHandler getPortletDataHandlerInstance() {
		return _portlet.getPortletDataHandlerInstance();
	}

	@Override
	public Map<String, PortletFilter> getPortletFilters() {
		return _portlet.getPortletFilters();
	}

	@Override
	public void setPortletFilters(Map<String, PortletFilter> portletFilters) {
		_portlet.setPortletFilters(portletFilters);
	}

	@Override
	public PortletInfo getPortletInfo() {
		return _portlet.getPortletInfo();
	}

	@Override
	public void setPortletInfo(PortletInfo portletInfo) {
		_portlet.setPortletInfo(portletInfo);
	}

	@Override
	public String getPortletLayoutListenerClass() {
		return _portlet.getPortletLayoutListenerClass();
	}

	@Override
	public void setPortletLayoutListenerClass(
		String portletLayoutListenerClass) {

		_portlet.setPortletLayoutListenerClass(portletLayoutListenerClass);
	}

	@Override
	public PortletLayoutListener getPortletLayoutListenerInstance() {
		return _portlet.getPortletLayoutListenerInstance();
	}

	@Override
	public Map<String, Set<String>> getPortletModes() {
		return _portlet.getPortletModes();
	}

	@Override
	public void setPortletModes(Map<String, Set<String>> portletModes) {
		_portlet.setPortletModes(portletModes);
	}

	@Override
	public String getPortletName() {
		return _portlet.getPortletClass();
	}

	@Override
	public void setPortletName(String portletName) {
		_portlet.setPortletName(portletName);
	}

	@Override
	public String getPortletURLClass() {
		return _portlet.getPortletURLClass();
	}

	@Override
	public void setPortletURLClass(String portletURLClass) {
		_portlet.setPortletURLClass(portletURLClass);
	}

	@Override
	public boolean getPreferencesCompanyWide() {
		return _portlet.getPreferencesCompanyWide();
	}

	@Override
	public boolean getPreferencesOwnedByGroup() {
		return _portlet.getPreferencesOwnedByGroup();
	}

	@Override
	public boolean getPreferencesUniquePerLayout() {
		return _portlet.getPreferencesUniquePerLayout();
	}

	@Override
	public String getPreferencesValidator() {
		return _portlet.getPreferencesValidator();
	}

	@Override
	public void setPreferencesValidator(String preferencesValidator) {
		_portlet.setPreferencesValidator(preferencesValidator);
	}

	@Override
	public boolean getPrivateRequestAttributes() {
		return _portlet.getPrivateRequestAttributes();
	}

	@Override
	public boolean getPrivateSessionAttributes() {
		return _portlet.getPrivateSessionAttributes();
	}

	@Override
	public QName getProcessingEvent(String uri, String localPart) {
		return _portlet.getProcessingEvent(uri, localPart);
	}

	@Override
	public Set<QName> getProcessingEvents() {
		return _portlet.getProcessingEvents();
	}

	@Override
	public void setProcessingEvents(Set<QName> processingEvents) {
		_portlet.setProcessingEvents(processingEvents);
	}

	@Override
	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _portlet.getPublicRenderParameter(identifier);
	}

	@Override
	public PublicRenderParameter getPublicRenderParameter(
		String uri, String localPart) {

		return _portlet.getPublicRenderParameter(uri, localPart);
	}

	@Override
	public Set<PublicRenderParameter> getPublicRenderParameters() {
		return _portlet.getPublicRenderParameters();
	}

	@Override
	public void setPublicRenderParameters(
		Set<PublicRenderParameter> publicRenderParameters) {

		_portlet.setPublicRenderParameters(publicRenderParameters);
	}

	@Override
	public Set<QName> getPublishingEvents() {
		return _portlet.getPublishingEvents();
	}

	@Override
	public void setPublishingEvents(Set<QName> publishingEvents) {
		_portlet.setPublishingEvents(publishingEvents);
	}

	@Override
	public boolean getReady() {
		return _portlet.getReady();
	}

	@Override
	public boolean getRemoteable() {
		return _portlet.getRemoteable();
	}

	@Override
	public int getRenderTimeout() {
		return _portlet.getRenderTimeout();
	}

	@Override
	public void setRenderTimeout(int renderTimeout) {
		_portlet.setRenderTimeout(renderTimeout);
	}

	@Override
	public int getRenderWeight() {
		return _portlet.getRenderWeight();
	}

	@Override
	public void setRenderWeight(int renderWeight) {
		_portlet.setRenderWeight(renderWeight);
	}

	@Override
	public String getResourceBundle() {
		return _portlet.getResourceBundle();
	}

	@Override
	public void setResourceBundle(String resourceBundle) {
		_portlet.setResourceBundle(resourceBundle);
	}

	@Override
	public boolean getRestoreCurrentView() {
		return _portlet.getRestoreCurrentView();
	}

	@Override
	public Map<String, String> getRoleMappers() {
		return _portlet.getRoleMappers();
	}

	@Override
	public void setRoleMappers(Map<String, String> roleMappers) {
		_portlet.setRoleMappers(roleMappers);
	}

	@Override
	public String[] getRolesArray() {
		return _portlet.getRolesArray();
	}

	@Override
	public void setRolesArray(String[] rolesArray) {
		_portlet.setRolesArray(rolesArray);
	}

	@Override
	public Portlet getRootPortlet() {
		return _portlet.getRootPortlet();
	}

	@Override
	public String getRootPortletId() {
		return _portlet.getRootPortletId();
	}

	@Override
	public List<SchedulerEntry> getSchedulerEntries() {
		return _portlet.getSchedulerEntries();
	}

	@Override
	public void setSchedulerEntries(List<SchedulerEntry> schedulerEntries) {
		_portlet.setSchedulerEntries(schedulerEntries);
	}

	@Override
	public boolean getScopeable() {
		return _portlet.getScopeable();
	}

	@Override
	public boolean getShowPortletAccessDenied() {
		return _portlet.getShowPortletAccessDenied();
	}

	@Override
	public boolean getShowPortletInactive() {
		return _portlet.getShowPortletInactive();
	}

	@Override
	public boolean getSinglePageApplication() {
		return _portlet.getSinglePageApplication();
	}

	@Override
	public List<String> getSocialActivityInterpreterClasses() {
		return _portlet.getSocialActivityInterpreterClasses();
	}

	@Override
	public void setSocialActivityInterpreterClasses(
		List<String> socialActivityInterpreterClasses) {

		_portlet.setSocialActivityInterpreterClasses(
			socialActivityInterpreterClasses);
	}

	@Override
	public List<SocialActivityInterpreter>
	getSocialActivityInterpreterInstances() {

		return _portlet.getSocialActivityInterpreterInstances();
	}

	@Override
	public String getSocialRequestInterpreterClass() {
		return _portlet.getSocialRequestInterpreterClass();
	}

	@Override
	public void setSocialRequestInterpreterClass(
		String socialRequestInterpreterClass) {

		_portlet.setSocialRequestInterpreterClass(
			socialRequestInterpreterClass);
	}

	@Override
	public SocialRequestInterpreter getSocialRequestInterpreterInstance() {
		return _portlet.getSocialRequestInterpreterInstance();
	}

	@Override
	public List<String> getStagedModelDataHandlerClasses() {
		return _portlet.getStagedModelDataHandlerClasses();
	}

	@Override
	public void setStagedModelDataHandlerClasses(
		List<String> stagedModelDataHandlerClasses) {

		_portlet.setStagedModelDataHandlerClasses(
			stagedModelDataHandlerClasses);
	}

	@Override
	public List<StagedModelDataHandler<?>>
	getStagedModelDataHandlerInstances() {

		return _portlet.getStagedModelDataHandlerInstances();
	}

	@Override
	public boolean getStatic() {
		return _portlet.getStatic();
	}

	@Override
	public boolean getStaticEnd() {
		return _portlet.getStaticEnd();
	}

	@Override
	public String getStaticResourcePath() {
		return _portlet.getStaticResourcePath();
	}

	@Override
	public boolean getStaticStart() {
		return _portlet.getStaticStart();
	}

	@Override
	public String getStrutsPath() {
		return _portlet.getStrutsPath();
	}

	@Override
	public void setStrutsPath(String strutsPath) {
		_portlet.setStrutsPath(strutsPath);
	}

	@Override
	public Set<String> getSupportedLocales() {
		return _portlet.getSupportedLocales();
	}

	@Override
	public void setSupportedLocales(Set<String> supportedLocales) {
		_portlet.setSupportedLocales(supportedLocales);
	}

	@Override
	public boolean getSystem() {
		return _portlet.getSystem();
	}

	@Override
	public String getTemplateHandlerClass() {
		return _portlet.getTemplateHandlerClass();
	}

	@Override
	public void setTemplateHandlerClass(String templateHandlerClass) {
		_portlet.setTemplateHandlerClass(templateHandlerClass);
	}

	@Override
	public TemplateHandler getTemplateHandlerInstance() {
		return _portlet.getTemplateHandlerInstance();
	}

	@Override
	public long getTimestamp() {
		return _portlet.getTimestamp();
	}

	@Override
	public List<String> getTrashHandlerClasses() {
		return _portlet.getTrashHandlerClasses();
	}

	@Override
	public void setTrashHandlerClasses(List<String> trashHandlerClasses) {
		_portlet.setTrashHandlerClasses(trashHandlerClasses);
	}

	@Override
	public List<TrashHandler> getTrashHandlerInstances() {
		return _portlet.getTrashHandlerInstances();
	}

	@Override
	public boolean getUndeployedPortlet() {
		return _portlet.getUndeployedPortlet();
	}

	@Override
	public Set<String> getUnlinkedRoles() {
		return _portlet.getUnlinkedRoles();
	}

	@Override
	public void setUnlinkedRoles(Set<String> unlinkedRoles) {
		_portlet.setUnlinkedRoles(unlinkedRoles);
	}

	@Override
	public String getURLEncoderClass() {
		return _portlet.getURLEncoderClass();
	}

	@Override
	public void setURLEncoderClass(String urlEncoderClass) {
		_portlet.setURLEncoderClass(urlEncoderClass);
	}

	@Override
	public URLEncoder getURLEncoderInstance() {
		return _portlet.getURLEncoderInstance();
	}

	@Override
	public boolean getUseDefaultTemplate() {
		return _portlet.getUseDefaultTemplate();
	}

	@Override
	public long getUserId() {
		return _portlet.getUserId();
	}

	@Override
	public String getUserNotificationDefinitions() {
		return _portlet.getUserNotificationDefinitions();
	}

	@Override
	public void setUserNotificationDefinitions(
		String userNotificationDefinitions) {

		_portlet.setUserNotificationDefinitions(userNotificationDefinitions);
	}

	@Override
	public List<String> getUserNotificationHandlerClasses() {
		return _portlet.getUserNotificationHandlerClasses();
	}

	@Override
	public void setUserNotificationHandlerClasses(
		List<String> userNotificationHandlerClasses) {

		_portlet.setUserNotificationHandlerClasses(
			userNotificationHandlerClasses);
	}

	@Override
	public List<UserNotificationHandler> getUserNotificationHandlerInstances() {
		return _portlet.getUserNotificationHandlerInstances();
	}

	@Override
	public String getUserPrincipalStrategy() {
		return _portlet.getUserPrincipalStrategy();
	}

	@Override
	public void setUserPrincipalStrategy(String userPrincipalStrategy) {
		_portlet.setUserPrincipalStrategy(userPrincipalStrategy);
	}

	@Override
	public String getVirtualPath() {
		return _portlet.getVirtualPath();
	}

	@Override
	public void setVirtualPath(String virtualPath) {
		_portlet.setVirtualPath(virtualPath);
	}

	@Override
	public String getWebDAVStorageClass() {
		return _portlet.getWebDAVStorageClass();
	}

	@Override
	public void setWebDAVStorageClass(String webDAVStorageClass) {
		_portlet.setWebDAVStorageClass(webDAVStorageClass);
	}

	@Override
	public WebDAVStorage getWebDAVStorageInstance() {
		return _portlet.getWebDAVStorageInstance();
	}

	@Override
	public String getWebDAVStorageToken() {
		return _portlet.getWebDAVStorageToken();
	}

	@Override
	public void setWebDAVStorageToken(String webDAVStorageToken) {
		_portlet.setWebDAVStorageToken(webDAVStorageToken);
	}

	@Override
	public Map<String, Set<String>> getWindowStates() {
		return _portlet.getWindowStates();
	}

	@Override
	public void setWindowStates(Map<String, Set<String>> windowStates) {
		_portlet.setWindowStates(windowStates);
	}

	@Override
	public List<String> getWorkflowHandlerClasses() {
		return _portlet.getWorkflowHandlerClasses();
	}

	@Override
	public void setWorkflowHandlerClasses(List<String> workflowHandlerClasses) {
		_portlet.setWorkflowHandlerClasses(workflowHandlerClasses);
	}

	@Override
	public List<WorkflowHandler<?>> getWorkflowHandlerInstances() {
		return _portlet.getWorkflowHandlerInstances();
	}

	@Override
	public String getXmlRpcMethodClass() {
		return _portlet.getXmlRpcMethodClass();
	}

	@Override
	public void setXmlRpcMethodClass(String xmlRpcMethodClass) {
		_portlet.setXmlRpcMethodClass(xmlRpcMethodClass);
	}

	@Override
	public Method getXmlRpcMethodInstance() {
		return _portlet.getXmlRpcMethodInstance();
	}

	@Override
	public boolean hasAddPortletPermission(long userId) {
		return _portlet.hasAddPortletPermission(userId);
	}

	@Override
	public boolean hasFooterPortalCss() {
		return _portlet.hasFooterPortalCss();
	}

	@Override
	public boolean hasFooterPortalJavaScript() {
		return _portlet.hasFooterPortalJavaScript();
	}

	@Override
	public boolean hasFooterPortletCss() {
		return _portlet.hasFooterPortletCss();
	}

	@Override
	public boolean hasFooterPortletJavaScript() {
		return _portlet.hasFooterPortletJavaScript();
	}

	@Override
	public boolean hasHeaderPortalCss() {
		return _portlet.hasHeaderPortalCss();
	}

	@Override
	public boolean hasHeaderPortalJavaScript() {
		return _portlet.hasHeaderPortalJavaScript();
	}

	@Override
	public boolean hasHeaderPortletCss() {
		return _portlet.hasHeaderPortletCss();
	}

	@Override
	public boolean hasHeaderPortletJavaScript() {
		return _portlet.hasHeaderPortletJavaScript();
	}

	@Override
	public boolean hasMultipleMimeTypes() {
		return _portlet.hasMultipleMimeTypes();
	}

	@Override
	public boolean hasRoleWithName(String roleName) {
		return _portlet.hasRoleWithName(roleName);
	}

	@Override
	public boolean isActionURLRedirect() {
		return _portlet.isActionURLRedirect();
	}

	@Override
	public void setActionURLRedirect(boolean actionURLRedirect) {
		_portlet.setActionURLRedirect(actionURLRedirect);
	}

	@Override
	public boolean isAddDefaultResource() {
		return _portlet.isAddDefaultResource();
	}

	@Override
	public void setAddDefaultResource(boolean addDefaultResource) {
		_portlet.setAddDefaultResource(addDefaultResource);
	}

	@Override
	public boolean isAjaxable() {
		return _portlet.isAjaxable();
	}

	@Override
	public void setAjaxable(boolean ajaxable) {
		_portlet.setAjaxable(ajaxable);
	}

	@Override
	public boolean isFullPageDisplayable() {
		return _portlet.isFullPageDisplayable();
	}

	@Override
	public boolean isInclude() {
		return _portlet.isInclude();
	}

	@Override
	public void setInclude(boolean include) {
		_portlet.setInclude(include);
	}

	@Override
	public boolean isInstanceable() {
		return _portlet.isInstanceable();
	}

	@Override
	public void setInstanceable(boolean instanceable) {
		_portlet.setInstanceable(instanceable);
	}

	@Override
	public boolean isLayoutCacheable() {
		return _portlet.isLayoutCacheable();
	}

	@Override
	public void setLayoutCacheable(boolean layoutCacheable) {
		_portlet.setLayoutCacheable(layoutCacheable);
	}

	@Override
	public boolean isMaximizeEdit() {
		return _portlet.isMaximizeEdit();
	}

	@Override
	public void setMaximizeEdit(boolean maximizeEdit) {
		_portlet.setMaximizeEdit(maximizeEdit);
	}

	@Override
	public boolean isMaximizeHelp() {
		return _portlet.isMaximizeHelp();
	}

	@Override
	public void setMaximizeHelp(boolean maximizeHelp) {
		_portlet.setMaximizeHelp(maximizeHelp);
	}

	@Override
	public boolean isPopUpPrint() {
		return _portlet.isPopUpPrint();
	}

	@Override
	public void setPopUpPrint(boolean popUpPrint) {
		_portlet.setPopUpPrint(popUpPrint);
	}

	@Override
	public boolean isPreferencesCompanyWide() {
		return _portlet.isPreferencesCompanyWide();
	}

	@Override
	public void setPreferencesCompanyWide(boolean preferencesCompanyWide) {
		_portlet.setPreferencesCompanyWide(preferencesCompanyWide);
	}

	@Override
	public boolean isPreferencesOwnedByGroup() {
		return _portlet.isPreferencesOwnedByGroup();
	}

	@Override
	public void setPreferencesOwnedByGroup(boolean preferencesOwnedByGroup) {
		_portlet.setPreferencesOwnedByGroup(preferencesOwnedByGroup);
	}

	@Override
	public boolean isPreferencesUniquePerLayout() {
		return _portlet.isPreferencesUniquePerLayout();
	}

	@Override
	public void setPreferencesUniquePerLayout(
		boolean preferencesUniquePerLayout) {

		_portlet.setPreferencesUniquePerLayout(preferencesUniquePerLayout);
	}

	@Override
	public boolean isPrivateRequestAttributes() {
		return _portlet.isPrivateRequestAttributes();
	}

	@Override
	public void setPrivateRequestAttributes(boolean privateRequestAttributes) {
		_portlet.setPrivateRequestAttributes(privateRequestAttributes);
	}

	@Override
	public boolean isPrivateSessionAttributes() {
		return _portlet.isPrivateSessionAttributes();
	}

	@Override
	public void setPrivateSessionAttributes(boolean privateSessionAttributes) {
		_portlet.setPrivateSessionAttributes(privateSessionAttributes);
	}

	@Override
	public boolean isReady() {
		return _portlet.isReady();
	}

	@Override
	public void setReady(boolean ready) {
		_portlet.setReady(ready);
	}

	@Override
	public boolean isRemoteable() {
		return _portlet.isRemoteable();
	}

	@Override
	public void setRemoteable(boolean remoteable) {
		_portlet.setRemoteable(remoteable);
	}

	@Override
	public boolean isRequiresNamespacedParameters() {
		return _portlet.isRequiresNamespacedParameters();
	}

	@Override
	public void setRequiresNamespacedParameters(
		boolean requiresNamespacedParameters) {

		_portlet.setRequiresNamespacedParameters(requiresNamespacedParameters);
	}

	@Override
	public boolean isRestoreCurrentView() {
		return _portlet.isRestoreCurrentView();
	}

	@Override
	public void setRestoreCurrentView(boolean restoreCurrentView) {
		_portlet.setRestoreCurrentView(restoreCurrentView);
	}

	@Override
	public boolean isScopeable() {
		return _portlet.isScopeable();
	}

	@Override
	public void setScopeable(boolean scopeable) {
		_portlet.setScopeable(scopeable);
	}

	@Override
	public boolean isShowPortletAccessDenied() {
		return _portlet.isShowPortletAccessDenied();
	}

	@Override
	public void setShowPortletAccessDenied(boolean showPortletAccessDenied) {
		_portlet.setShowPortletAccessDenied(showPortletAccessDenied);
	}

	@Override
	public boolean isShowPortletInactive() {
		return _portlet.isShowPortletInactive();
	}

	@Override
	public void setShowPortletInactive(boolean showPortletInactive) {
		_portlet.setShowPortletInactive(showPortletInactive);
	}

	@Override
	public boolean isSinglePageApplication() {
		return _portlet.isSinglePageApplication();
	}

	@Override
	public void setSinglePageApplication(boolean singlePageApplication) {
		_portlet.setSinglePageApplication(singlePageApplication);
	}

	@Override
	public boolean isStatic() {
		return _portlet.isStatic();
	}

	@Override
	public void setStatic(boolean staticPortlet) {
		_portlet.setStatic(staticPortlet);
	}

	@Override
	public boolean isStaticEnd() {
		return _portlet.isStaticEnd();
	}

	@Override
	public boolean isStaticStart() {
		return _portlet.isStaticStart();
	}

	@Override
	public void setStaticStart(boolean staticPortletStart) {
		_portlet.setStaticStart(staticPortletStart);
	}

	@Override
	public boolean isSystem() {
		return _portlet.isSystem();
	}

	@Override
	public void setSystem(boolean system) {
		_portlet.setSystem(system);
	}

	@Override
	public boolean isUndeployedPortlet() {
		return _portlet.isUndeployedPortlet();
	}

	@Override
	public void setUndeployedPortlet(boolean undeployedPortlet) {
		_portlet.setUndeployedPortlet(undeployedPortlet);
	}

	@Override
	public boolean isUseDefaultTemplate() {
		return _portlet.isUseDefaultTemplate();
	}

	@Override
	public void setUseDefaultTemplate(boolean useDefaultTemplate) {
		_portlet.setUseDefaultTemplate(useDefaultTemplate);
	}

	@Override
	public void linkRoles() {
		_portlet.linkRoles();
	}

	@Override
	public void unsetReady() {
		_portlet.unsetReady();
	}

	@Override
	public boolean hasWindowState(String mimeType, WindowState windowState) {
		return _portlet.hasWindowState(mimeType, windowState);
	}

	@Override
	public boolean hasPortletMode(String mimeType, PortletMode portletMode) {
		return _portlet.hasPortletMode(mimeType, portletMode);
	}

	private Portlet _portlet;

}