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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutCTException;
import com.liferay.portal.kernel.model.LayoutCT;
import com.liferay.portal.kernel.service.persistence.LayoutCTPK;
import com.liferay.portal.kernel.service.persistence.LayoutCTPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutCTUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class LayoutCTPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutCTUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutCT> iterator = _layoutCTs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		LayoutCTPK pk = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutCT layoutCT = _persistence.create(pk);

		Assert.assertNotNull(layoutCT);

		Assert.assertEquals(layoutCT.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutCT newLayoutCT = addLayoutCT();

		_persistence.remove(newLayoutCT);

		LayoutCT existingLayoutCT = _persistence.fetchByPrimaryKey(
			newLayoutCT.getPrimaryKey());

		Assert.assertNull(existingLayoutCT);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutCT();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		LayoutCTPK pk = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutCT newLayoutCT = _persistence.create(pk);

		newLayoutCT.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutCT.setTypeSettings(RandomTestUtil.randomString());

		_layoutCTs.add(_persistence.update(newLayoutCT));

		LayoutCT existingLayoutCT = _persistence.findByPrimaryKey(
			newLayoutCT.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutCT.getMvccVersion(), newLayoutCT.getMvccVersion());
		Assert.assertEquals(existingLayoutCT.getPlid(), newLayoutCT.getPlid());
		Assert.assertEquals(
			existingLayoutCT.getCtCollectionId(),
			newLayoutCT.getCtCollectionId());
		Assert.assertEquals(
			existingLayoutCT.getTypeSettings(), newLayoutCT.getTypeSettings());
	}

	@Test
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByCTCollectionId() throws Exception {
		_persistence.countByCTCollectionId(RandomTestUtil.nextLong());

		_persistence.countByCTCollectionId(0L);
	}

	@Test
	public void testCountByP_CT() throws Exception {
		_persistence.countByP_CT(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByP_CT(0L, 0L);
	}

	@Test
	public void testCountByP_CTArrayable() throws Exception {
		_persistence.countByP_CT(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextLong());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutCT newLayoutCT = addLayoutCT();

		LayoutCT existingLayoutCT = _persistence.findByPrimaryKey(
			newLayoutCT.getPrimaryKey());

		Assert.assertEquals(existingLayoutCT, newLayoutCT);
	}

	@Test(expected = NoSuchLayoutCTException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		LayoutCTPK pk = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutCT newLayoutCT = addLayoutCT();

		LayoutCT existingLayoutCT = _persistence.fetchByPrimaryKey(
			newLayoutCT.getPrimaryKey());

		Assert.assertEquals(existingLayoutCT, newLayoutCT);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		LayoutCTPK pk = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutCT missingLayoutCT = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutCT);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutCT newLayoutCT1 = addLayoutCT();
		LayoutCT newLayoutCT2 = addLayoutCT();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutCT1.getPrimaryKey());
		primaryKeys.add(newLayoutCT2.getPrimaryKey());

		Map<Serializable, LayoutCT> layoutCTs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, layoutCTs.size());
		Assert.assertEquals(
			newLayoutCT1, layoutCTs.get(newLayoutCT1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutCT2, layoutCTs.get(newLayoutCT2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		LayoutCTPK pk1 = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutCTPK pk2 = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutCT> layoutCTs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(layoutCTs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutCT newLayoutCT = addLayoutCT();

		LayoutCTPK pk = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutCT.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutCT> layoutCTs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, layoutCTs.size());
		Assert.assertEquals(
			newLayoutCT, layoutCTs.get(newLayoutCT.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutCT> layoutCTs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(layoutCTs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutCT newLayoutCT = addLayoutCT();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutCT.getPrimaryKey());

		Map<Serializable, LayoutCT> layoutCTs = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, layoutCTs.size());
		Assert.assertEquals(
			newLayoutCT, layoutCTs.get(newLayoutCT.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutCT newLayoutCT = addLayoutCT();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCT.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("id.plid", newLayoutCT.getPlid()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.ctCollectionId", newLayoutCT.getCtCollectionId()));

		List<LayoutCT> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutCT existingLayoutCT = result.get(0);

		Assert.assertEquals(existingLayoutCT, newLayoutCT);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCT.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("id.plid", RandomTestUtil.nextLong()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.ctCollectionId", RandomTestUtil.nextLong()));

		List<LayoutCT> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutCT newLayoutCT = addLayoutCT();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCT.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id.plid"));

		Object newPlid = newLayoutCT.getPlid();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("id.plid", new Object[] {newPlid}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPlid = result.get(0);

		Assert.assertEquals(existingPlid, newPlid);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCT.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id.plid"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.plid", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected LayoutCT addLayoutCT() throws Exception {
		LayoutCTPK pk = new LayoutCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutCT layoutCT = _persistence.create(pk);

		layoutCT.setMvccVersion(RandomTestUtil.nextLong());

		layoutCT.setTypeSettings(RandomTestUtil.randomString());

		_layoutCTs.add(_persistence.update(layoutCT));

		return layoutCT;
	}

	private List<LayoutCT> _layoutCTs = new ArrayList<LayoutCT>();
	private LayoutCTPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}