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
import com.liferay.portal.kernel.exception.NoSuchPortletPreferencesCTException;
import com.liferay.portal.kernel.model.PortletPreferencesCT;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPK;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesCTUtil;
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
public class PortletPreferencesCTPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PortletPreferencesCTUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PortletPreferencesCT> iterator =
			_portletPreferencesCTs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		PortletPreferencesCTPK pk = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		PortletPreferencesCT portletPreferencesCT = _persistence.create(pk);

		Assert.assertNotNull(portletPreferencesCT);

		Assert.assertEquals(portletPreferencesCT.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		_persistence.remove(newPortletPreferencesCT);

		PortletPreferencesCT existingPortletPreferencesCT =
			_persistence.fetchByPrimaryKey(
				newPortletPreferencesCT.getPrimaryKey());

		Assert.assertNull(existingPortletPreferencesCT);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortletPreferencesCT();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		PortletPreferencesCTPK pk = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		PortletPreferencesCT newPortletPreferencesCT = _persistence.create(pk);

		newPortletPreferencesCT.setMvccVersion(RandomTestUtil.nextLong());

		newPortletPreferencesCT.setPreferences(RandomTestUtil.randomString());

		_portletPreferencesCTs.add(
			_persistence.update(newPortletPreferencesCT));

		PortletPreferencesCT existingPortletPreferencesCT =
			_persistence.findByPrimaryKey(
				newPortletPreferencesCT.getPrimaryKey());

		Assert.assertEquals(
			existingPortletPreferencesCT.getMvccVersion(),
			newPortletPreferencesCT.getMvccVersion());
		Assert.assertEquals(
			existingPortletPreferencesCT.getClassPK(),
			newPortletPreferencesCT.getClassPK());
		Assert.assertEquals(
			existingPortletPreferencesCT.getCtCollectionId(),
			newPortletPreferencesCT.getCtCollectionId());
		Assert.assertEquals(
			existingPortletPreferencesCT.getPreferences(),
			newPortletPreferencesCT.getPreferences());
	}

	@Test
	public void testCountByClassPK() throws Exception {
		_persistence.countByClassPK(RandomTestUtil.nextLong());

		_persistence.countByClassPK(0L);
	}

	@Test
	public void testCountByCTCollectionId() throws Exception {
		_persistence.countByCTCollectionId(RandomTestUtil.nextLong());

		_persistence.countByCTCollectionId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_CArrayable() throws Exception {
		_persistence.countByC_C(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextLong());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		PortletPreferencesCT existingPortletPreferencesCT =
			_persistence.findByPrimaryKey(
				newPortletPreferencesCT.getPrimaryKey());

		Assert.assertEquals(
			existingPortletPreferencesCT, newPortletPreferencesCT);
	}

	@Test(expected = NoSuchPortletPreferencesCTException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		PortletPreferencesCTPK pk = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		PortletPreferencesCT existingPortletPreferencesCT =
			_persistence.fetchByPrimaryKey(
				newPortletPreferencesCT.getPrimaryKey());

		Assert.assertEquals(
			existingPortletPreferencesCT, newPortletPreferencesCT);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		PortletPreferencesCTPK pk = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		PortletPreferencesCT missingPortletPreferencesCT =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortletPreferencesCT);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		PortletPreferencesCT newPortletPreferencesCT1 =
			addPortletPreferencesCT();
		PortletPreferencesCT newPortletPreferencesCT2 =
			addPortletPreferencesCT();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferencesCT1.getPrimaryKey());
		primaryKeys.add(newPortletPreferencesCT2.getPrimaryKey());

		Map<Serializable, PortletPreferencesCT> portletPreferencesCTs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, portletPreferencesCTs.size());
		Assert.assertEquals(
			newPortletPreferencesCT1,
			portletPreferencesCTs.get(
				newPortletPreferencesCT1.getPrimaryKey()));
		Assert.assertEquals(
			newPortletPreferencesCT2,
			portletPreferencesCTs.get(
				newPortletPreferencesCT2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		PortletPreferencesCTPK pk1 = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		PortletPreferencesCTPK pk2 = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PortletPreferencesCT> portletPreferencesCTs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletPreferencesCTs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		PortletPreferencesCTPK pk = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferencesCT.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PortletPreferencesCT> portletPreferencesCTs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletPreferencesCTs.size());
		Assert.assertEquals(
			newPortletPreferencesCT,
			portletPreferencesCTs.get(newPortletPreferencesCT.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PortletPreferencesCT> portletPreferencesCTs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletPreferencesCTs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferencesCT.getPrimaryKey());

		Map<Serializable, PortletPreferencesCT> portletPreferencesCTs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletPreferencesCTs.size());
		Assert.assertEquals(
			newPortletPreferencesCT,
			portletPreferencesCTs.get(newPortletPreferencesCT.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortletPreferencesCT.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.classPK", newPortletPreferencesCT.getClassPK()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.ctCollectionId",
				newPortletPreferencesCT.getCtCollectionId()));

		List<PortletPreferencesCT> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortletPreferencesCT existingPortletPreferencesCT = result.get(0);

		Assert.assertEquals(
			existingPortletPreferencesCT, newPortletPreferencesCT);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortletPreferencesCT.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.classPK", RandomTestUtil.nextLong()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.ctCollectionId", RandomTestUtil.nextLong()));

		List<PortletPreferencesCT> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		PortletPreferencesCT newPortletPreferencesCT =
			addPortletPreferencesCT();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortletPreferencesCT.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.classPK"));

		Object newClassPK = newPortletPreferencesCT.getClassPK();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.classPK", new Object[] {newClassPK}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClassPK = result.get(0);

		Assert.assertEquals(existingClassPK, newClassPK);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortletPreferencesCT.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.classPK"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.classPK", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected PortletPreferencesCT addPortletPreferencesCT() throws Exception {
		PortletPreferencesCTPK pk = new PortletPreferencesCTPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		PortletPreferencesCT portletPreferencesCT = _persistence.create(pk);

		portletPreferencesCT.setMvccVersion(RandomTestUtil.nextLong());

		portletPreferencesCT.setPreferences(RandomTestUtil.randomString());

		_portletPreferencesCTs.add(_persistence.update(portletPreferencesCT));

		return portletPreferencesCT;
	}

	private List<PortletPreferencesCT> _portletPreferencesCTs =
		new ArrayList<PortletPreferencesCT>();
	private PortletPreferencesCTPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}