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

package com.liferay.portal.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ImagePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutBranchPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutFinder;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.kernel.service.persistence.RecentLayoutSetBranchPersistence;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the layout set branch local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.LayoutSetBranchLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.LayoutSetBranchLocalServiceImpl
 * @see com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class LayoutSetBranchLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements LayoutSetBranchLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil} to access the layout set branch local service.
	 */

	/**
	 * Adds the layout set branch to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetBranch the layout set branch
	 * @return the layout set branch that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSetBranch addLayoutSetBranch(LayoutSetBranch layoutSetBranch) {
		layoutSetBranch.setNew(true);

		return layoutSetBranchPersistence.update(layoutSetBranch);
	}

	/**
	 * Creates a new layout set branch with the primary key. Does not add the layout set branch to the database.
	 *
	 * @param layoutSetBranchId the primary key for the new layout set branch
	 * @return the new layout set branch
	 */
	@Override
	@Transactional(enabled = false)
	public LayoutSetBranch createLayoutSetBranch(long layoutSetBranchId) {
		return layoutSetBranchPersistence.create(layoutSetBranchId);
	}

	/**
	 * Deletes the layout set branch with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetBranchId the primary key of the layout set branch
	 * @return the layout set branch that was removed
	 * @throws PortalException if a layout set branch with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutSetBranch deleteLayoutSetBranch(long layoutSetBranchId)
		throws PortalException {
		return layoutSetBranchPersistence.remove(layoutSetBranchId);
	}

	/**
	 * Deletes the layout set branch from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetBranch the layout set branch
	 * @return the layout set branch that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutSetBranch deleteLayoutSetBranch(
		LayoutSetBranch layoutSetBranch) throws PortalException {
		return layoutSetBranchPersistence.remove(layoutSetBranch);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return layoutSetBranchPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return layoutSetBranchPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return layoutSetBranchPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return layoutSetBranchPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return layoutSetBranchPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public LayoutSetBranch fetchLayoutSetBranch(long layoutSetBranchId) {
		return layoutSetBranchPersistence.fetchByPrimaryKey(layoutSetBranchId);
	}

	/**
	 * Returns the layout set branch with the primary key.
	 *
	 * @param layoutSetBranchId the primary key of the layout set branch
	 * @return the layout set branch
	 * @throws PortalException if a layout set branch with the primary key could not be found
	 */
	@Override
	public LayoutSetBranch getLayoutSetBranch(long layoutSetBranchId)
		throws PortalException {
		return layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(layoutSetBranchLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutSetBranch.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("layoutSetBranchId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(layoutSetBranchLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(LayoutSetBranch.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"layoutSetBranchId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(layoutSetBranchLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(LayoutSetBranch.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("layoutSetBranchId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return layoutSetBranchLocalService.deleteLayoutSetBranch((LayoutSetBranch)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return layoutSetBranchPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the layout set branchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutSetBranchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set branchs
	 * @param end the upper bound of the range of layout set branchs (not inclusive)
	 * @return the range of layout set branchs
	 */
	@Override
	public List<LayoutSetBranch> getLayoutSetBranchs(int start, int end) {
		return layoutSetBranchPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of layout set branchs.
	 *
	 * @return the number of layout set branchs
	 */
	@Override
	public int getLayoutSetBranchsCount() {
		return layoutSetBranchPersistence.countAll();
	}

	/**
	 * Updates the layout set branch in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetBranch the layout set branch
	 * @return the layout set branch that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSetBranch updateLayoutSetBranch(
		LayoutSetBranch layoutSetBranch) {
		return layoutSetBranchPersistence.update(layoutSetBranch);
	}

	/**
	 * Returns the layout set branch local service.
	 *
	 * @return the layout set branch local service
	 */
	public LayoutSetBranchLocalService getLayoutSetBranchLocalService() {
		return layoutSetBranchLocalService;
	}

	/**
	 * Sets the layout set branch local service.
	 *
	 * @param layoutSetBranchLocalService the layout set branch local service
	 */
	public void setLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {
		this.layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	/**
	 * Returns the layout set branch persistence.
	 *
	 * @return the layout set branch persistence
	 */
	public LayoutSetBranchPersistence getLayoutSetBranchPersistence() {
		return layoutSetBranchPersistence;
	}

	/**
	 * Sets the layout set branch persistence.
	 *
	 * @param layoutSetBranchPersistence the layout set branch persistence
	 */
	public void setLayoutSetBranchPersistence(
		LayoutSetBranchPersistence layoutSetBranchPersistence) {
		this.layoutSetBranchPersistence = layoutSetBranchPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the image local service.
	 *
	 * @return the image local service
	 */
	public com.liferay.portal.kernel.service.ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	/**
	 * Sets the image local service.
	 *
	 * @param imageLocalService the image local service
	 */
	public void setImageLocalService(
		com.liferay.portal.kernel.service.ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	/**
	 * Returns the image persistence.
	 *
	 * @return the image persistence
	 */
	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	/**
	 * Sets the image persistence.
	 *
	 * @param imagePersistence the image persistence
	 */
	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	/**
	 * Returns the layout local service.
	 *
	 * @return the layout local service
	 */
	public com.liferay.portal.kernel.service.LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	/**
	 * Sets the layout local service.
	 *
	 * @param layoutLocalService the layout local service
	 */
	public void setLayoutLocalService(
		com.liferay.portal.kernel.service.LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	/**
	 * Returns the layout persistence.
	 *
	 * @return the layout persistence
	 */
	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	/**
	 * Sets the layout persistence.
	 *
	 * @param layoutPersistence the layout persistence
	 */
	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	/**
	 * Returns the layout finder.
	 *
	 * @return the layout finder
	 */
	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	/**
	 * Sets the layout finder.
	 *
	 * @param layoutFinder the layout finder
	 */
	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	/**
	 * Returns the layout branch local service.
	 *
	 * @return the layout branch local service
	 */
	public com.liferay.portal.kernel.service.LayoutBranchLocalService getLayoutBranchLocalService() {
		return layoutBranchLocalService;
	}

	/**
	 * Sets the layout branch local service.
	 *
	 * @param layoutBranchLocalService the layout branch local service
	 */
	public void setLayoutBranchLocalService(
		com.liferay.portal.kernel.service.LayoutBranchLocalService layoutBranchLocalService) {
		this.layoutBranchLocalService = layoutBranchLocalService;
	}

	/**
	 * Returns the layout branch persistence.
	 *
	 * @return the layout branch persistence
	 */
	public LayoutBranchPersistence getLayoutBranchPersistence() {
		return layoutBranchPersistence;
	}

	/**
	 * Sets the layout branch persistence.
	 *
	 * @param layoutBranchPersistence the layout branch persistence
	 */
	public void setLayoutBranchPersistence(
		LayoutBranchPersistence layoutBranchPersistence) {
		this.layoutBranchPersistence = layoutBranchPersistence;
	}

	/**
	 * Returns the layout revision local service.
	 *
	 * @return the layout revision local service
	 */
	public com.liferay.portal.kernel.service.LayoutRevisionLocalService getLayoutRevisionLocalService() {
		return layoutRevisionLocalService;
	}

	/**
	 * Sets the layout revision local service.
	 *
	 * @param layoutRevisionLocalService the layout revision local service
	 */
	public void setLayoutRevisionLocalService(
		com.liferay.portal.kernel.service.LayoutRevisionLocalService layoutRevisionLocalService) {
		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

	/**
	 * Returns the layout revision persistence.
	 *
	 * @return the layout revision persistence
	 */
	public LayoutRevisionPersistence getLayoutRevisionPersistence() {
		return layoutRevisionPersistence;
	}

	/**
	 * Sets the layout revision persistence.
	 *
	 * @param layoutRevisionPersistence the layout revision persistence
	 */
	public void setLayoutRevisionPersistence(
		LayoutRevisionPersistence layoutRevisionPersistence) {
		this.layoutRevisionPersistence = layoutRevisionPersistence;
	}

	/**
	 * Returns the layout set local service.
	 *
	 * @return the layout set local service
	 */
	public com.liferay.portal.kernel.service.LayoutSetLocalService getLayoutSetLocalService() {
		return layoutSetLocalService;
	}

	/**
	 * Sets the layout set local service.
	 *
	 * @param layoutSetLocalService the layout set local service
	 */
	public void setLayoutSetLocalService(
		com.liferay.portal.kernel.service.LayoutSetLocalService layoutSetLocalService) {
		this.layoutSetLocalService = layoutSetLocalService;
	}

	/**
	 * Returns the recent layout set branch local service.
	 *
	 * @return the recent layout set branch local service
	 */
	public com.liferay.portal.kernel.service.RecentLayoutSetBranchLocalService getRecentLayoutSetBranchLocalService() {
		return recentLayoutSetBranchLocalService;
	}

	/**
	 * Sets the recent layout set branch local service.
	 *
	 * @param recentLayoutSetBranchLocalService the recent layout set branch local service
	 */
	public void setRecentLayoutSetBranchLocalService(
		com.liferay.portal.kernel.service.RecentLayoutSetBranchLocalService recentLayoutSetBranchLocalService) {
		this.recentLayoutSetBranchLocalService = recentLayoutSetBranchLocalService;
	}

	/**
	 * Returns the recent layout set branch persistence.
	 *
	 * @return the recent layout set branch persistence
	 */
	public RecentLayoutSetBranchPersistence getRecentLayoutSetBranchPersistence() {
		return recentLayoutSetBranchPersistence;
	}

	/**
	 * Sets the recent layout set branch persistence.
	 *
	 * @param recentLayoutSetBranchPersistence the recent layout set branch persistence
	 */
	public void setRecentLayoutSetBranchPersistence(
		RecentLayoutSetBranchPersistence recentLayoutSetBranchPersistence) {
		this.recentLayoutSetBranchPersistence = recentLayoutSetBranchPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portal.kernel.model.LayoutSetBranch",
			layoutSetBranchLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.kernel.model.LayoutSetBranch");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return LayoutSetBranchLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return LayoutSetBranch.class;
	}

	protected String getModelClassName() {
		return LayoutSetBranch.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = layoutSetBranchPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = LayoutSetBranchLocalService.class)
	protected LayoutSetBranchLocalService layoutSetBranchLocalService;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.ImageLocalService.class)
	protected com.liferay.portal.kernel.service.ImageLocalService imageLocalService;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutLocalService layoutLocalService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutBranchLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutBranchLocalService layoutBranchLocalService;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutRevisionLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutRevisionLocalService layoutRevisionLocalService;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.RecentLayoutSetBranchLocalService.class)
	protected com.liferay.portal.kernel.service.RecentLayoutSetBranchLocalService recentLayoutSetBranchLocalService;
	@BeanReference(type = RecentLayoutSetBranchPersistence.class)
	protected RecentLayoutSetBranchPersistence recentLayoutSetBranchPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}