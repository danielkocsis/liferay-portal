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

package com.liferay.portlet.exportimport.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ExportImportConfiguration. This utility wraps
 * {@link com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationLocalService
 * @see com.liferay.portlet.exportimport.service.base.ExportImportConfigurationLocalServiceBaseImpl
 * @see com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExportImportConfigurationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.exportimport.service.impl.ExportImportConfigurationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the export import configuration to the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was added
	*/
	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration addExportImportConfiguration(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration) {
		return getService()
				   .addExportImportConfiguration(exportImportConfiguration);
	}

	/**
	* Creates a new export import configuration with the primary key. Does not add the export import configuration to the database.
	*
	* @param exportImportConfigurationId the primary key for the new export import configuration
	* @return the new export import configuration
	*/
	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration createExportImportConfiguration(
		long exportImportConfigurationId) {
		return getService()
				   .createExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* Deletes the export import configuration from the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was removed
	*/
	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration deleteExportImportConfiguration(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration) {
		return getService()
				   .deleteExportImportConfiguration(exportImportConfiguration);
	}

	/**
	* Deletes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfigurationId the primary key of the export import configuration
	* @return the export import configuration that was removed
	* @throws PortalException if a export import configuration with the primary key could not be found
	*/
	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration deleteExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration fetchExportImportConfiguration(
		long exportImportConfigurationId) {
		return getService()
				   .fetchExportImportConfiguration(exportImportConfigurationId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Returns the export import configuration with the primary key.
	*
	* @param exportImportConfigurationId the primary key of the export import configuration
	* @return the export import configuration
	* @throws PortalException if a export import configuration with the primary key could not be found
	*/
	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration getExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* Returns a range of all the export import configurations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of export import configurations
	* @param end the upper bound of the range of export import configurations (not inclusive)
	* @return the range of export import configurations
	*/
	public static java.util.List<com.liferay.portlet.exportimport.model.ExportImportConfiguration> getExportImportConfigurations(
		int start, int end) {
		return getService().getExportImportConfigurations(start, end);
	}

	/**
	* Returns the number of export import configurations.
	*
	* @return the number of export import configurations
	*/
	public static int getExportImportConfigurationsCount() {
		return getService().getExportImportConfigurationsCount();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the export import configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was updated
	*/
	public static com.liferay.portlet.exportimport.model.ExportImportConfiguration updateExportImportConfiguration(
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration) {
		return getService()
				   .updateExportImportConfiguration(exportImportConfiguration);
	}

	public static ExportImportConfigurationLocalService getService() {
		if (_service == null) {
			_service = (ExportImportConfigurationLocalService)PortalBeanLocatorUtil.locate(ExportImportConfigurationLocalService.class.getName());

			ReferenceRegistry.registerReference(ExportImportConfigurationLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ExportImportConfigurationLocalService service) {
	}

	private static ExportImportConfigurationLocalService _service;
}