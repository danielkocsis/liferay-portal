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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetResourceException;
import com.liferay.portal.kernel.model.LayoutSetResource;
import com.liferay.portal.kernel.service.persistence.LayoutSetResourcePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetResourceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class LayoutSetResourcePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutSetResourceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSetResource> iterator = _layoutSetResources.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetResource layoutSetResource = _persistence.create(pk);

		Assert.assertNotNull(layoutSetResource);

		Assert.assertEquals(layoutSetResource.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		_persistence.remove(newLayoutSetResource);

		LayoutSetResource existingLayoutSetResource = _persistence.fetchByPrimaryKey(newLayoutSetResource.getPrimaryKey());

		Assert.assertNull(existingLayoutSetResource);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSetResource();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetResource newLayoutSetResource = _persistence.create(pk);

		newLayoutSetResource.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSetResource.setGroupId(RandomTestUtil.nextLong());

		newLayoutSetResource.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSetResource.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSetResource.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSetResource.setPageCount(RandomTestUtil.nextInt());

		newLayoutSetResource.setPrivateLayout(RandomTestUtil.randomBoolean());

		_layoutSetResources.add(_persistence.update(newLayoutSetResource));

		LayoutSetResource existingLayoutSetResource = _persistence.findByPrimaryKey(newLayoutSetResource.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetResource.getMvccVersion(),
			newLayoutSetResource.getMvccVersion());
		Assert.assertEquals(existingLayoutSetResource.getLayoutSetResourceId(),
			newLayoutSetResource.getLayoutSetResourceId());
		Assert.assertEquals(existingLayoutSetResource.getGroupId(),
			newLayoutSetResource.getGroupId());
		Assert.assertEquals(existingLayoutSetResource.getCompanyId(),
			newLayoutSetResource.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetResource.getCreateDate()),
			Time.getShortTimestamp(newLayoutSetResource.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetResource.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSetResource.getModifiedDate()));
		Assert.assertEquals(existingLayoutSetResource.getPageCount(),
			newLayoutSetResource.getPageCount());
		Assert.assertEquals(existingLayoutSetResource.isPrivateLayout(),
			newLayoutSetResource.isPrivateLayout());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_P() throws Exception {
		_persistence.countByC_P(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByC_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		LayoutSetResource existingLayoutSetResource = _persistence.findByPrimaryKey(newLayoutSetResource.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetResource, newLayoutSetResource);
	}

	@Test(expected = NoSuchLayoutSetResourceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<LayoutSetResource> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutSetResource",
			"mvccVersion", true, "layoutSetResourceId", true, "groupId", true,
			"companyId", true, "createDate", true, "modifiedDate", true,
			"pageCount", true, "privateLayout", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		LayoutSetResource existingLayoutSetResource = _persistence.fetchByPrimaryKey(newLayoutSetResource.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetResource, newLayoutSetResource);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetResource missingLayoutSetResource = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSetResource);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutSetResource newLayoutSetResource1 = addLayoutSetResource();
		LayoutSetResource newLayoutSetResource2 = addLayoutSetResource();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetResource1.getPrimaryKey());
		primaryKeys.add(newLayoutSetResource2.getPrimaryKey());

		Map<Serializable, LayoutSetResource> layoutSetResources = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSetResources.size());
		Assert.assertEquals(newLayoutSetResource1,
			layoutSetResources.get(newLayoutSetResource1.getPrimaryKey()));
		Assert.assertEquals(newLayoutSetResource2,
			layoutSetResources.get(newLayoutSetResource2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSetResource> layoutSetResources = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetResources.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetResource.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSetResource> layoutSetResources = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetResources.size());
		Assert.assertEquals(newLayoutSetResource,
			layoutSetResources.get(newLayoutSetResource.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSetResource> layoutSetResources = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetResources.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetResource.getPrimaryKey());

		Map<Serializable, LayoutSetResource> layoutSetResources = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetResources.size());
		Assert.assertEquals(newLayoutSetResource,
			layoutSetResources.get(newLayoutSetResource.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetResource.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetResourceId",
				newLayoutSetResource.getLayoutSetResourceId()));

		List<LayoutSetResource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSetResource existingLayoutSetResource = result.get(0);

		Assert.assertEquals(existingLayoutSetResource, newLayoutSetResource);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetResource.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetResourceId",
				RandomTestUtil.nextLong()));

		List<LayoutSetResource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetResource.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetResourceId"));

		Object newLayoutSetResourceId = newLayoutSetResource.getLayoutSetResourceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetResourceId",
				new Object[] { newLayoutSetResourceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSetResourceId = result.get(0);

		Assert.assertEquals(existingLayoutSetResourceId, newLayoutSetResourceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetResource.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetResourceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetResourceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSetResource newLayoutSetResource = addLayoutSetResource();

		_persistence.clearCache();

		LayoutSetResource existingLayoutSetResource = _persistence.findByPrimaryKey(newLayoutSetResource.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingLayoutSetResource.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutSetResource,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Boolean.valueOf(
				existingLayoutSetResource.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(existingLayoutSetResource,
				"getOriginalPrivateLayout", new Class<?>[0]));
	}

	protected LayoutSetResource addLayoutSetResource()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetResource layoutSetResource = _persistence.create(pk);

		layoutSetResource.setMvccVersion(RandomTestUtil.nextLong());

		layoutSetResource.setGroupId(RandomTestUtil.nextLong());

		layoutSetResource.setCompanyId(RandomTestUtil.nextLong());

		layoutSetResource.setCreateDate(RandomTestUtil.nextDate());

		layoutSetResource.setModifiedDate(RandomTestUtil.nextDate());

		layoutSetResource.setPageCount(RandomTestUtil.nextInt());

		layoutSetResource.setPrivateLayout(RandomTestUtil.randomBoolean());

		_layoutSetResources.add(_persistence.update(layoutSetResource));

		return layoutSetResource;
	}

	private List<LayoutSetResource> _layoutSetResources = new ArrayList<LayoutSetResource>();
	private LayoutSetResourcePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}