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

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistrar;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.EntryResource;
import com.liferay.change.tracking.rest.model.TestResourceModelClass;
import com.liferay.change.tracking.rest.model.TestVersionModelClass;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Zoltan Csaszi
 */
@RunWith(Arquillian.class)
public class EntryResourceTest extends BaseEntryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_user = UserTestUtil.addUser();

		// If the test environment has change tracking enabled, then disable
		// change tracking for the first run

		if (_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId())) {

			_originallyEnabled = true;

			_ctEngineManager.disableChangeTracking(
				TestPropsValues.getCompanyId());
		}

		_testVersionClassName = _classNameLocalService.addClassName(
			TestVersionModelClass.class.getName());

		_testResourceClassName = _classNameLocalService.addClassName(
			TestResourceModelClass.class.getName());

		_ctDefinition = _ctDefinitionBuilder.setContentType(
			"Test Object"
		).setContentTypeLanguageKey(
			"test-object"
		).setEntityClasses(
			TestResourceModelClass.class, TestVersionModelClass.class
		).setResourceEntitiesByCompanyIdFunction(
			id -> Collections.emptyList()
		).setResourceEntityByResourceEntityIdFunction(
			id -> new TestResourceModelClass()
		).setEntityIdsFromResourceEntityFunctions(
			testResource -> 0L, testResource -> 0L
		).setVersionEntitiesFromResourceEntityFunction(
			testResource -> Collections.emptyList()
		).setVersionEntityByVersionEntityIdFunction(
			id -> new TestVersionModelClass()
		).setVersionEntityDetails(
			Collections.emptyList(), o -> RandomTestUtil.randomString(),
			o -> RandomTestUtil.randomString(), o -> 1L
		).setEntityIdsFromVersionEntityFunctions(
			testVersion -> 0L, testVersion -> 0L
		).setVersionEntityStatusInfo(
			new Integer[] {WorkflowConstants.STATUS_APPROVED},
			testVersion -> WorkflowConstants.STATUS_APPROVED
		).build();

		_ctDefinitionRegistrar.register(_ctDefinition);

		EntryResource.Builder builder = EntryResource.builder();

		entryResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		if (_ctDefinition != null) {
			_ctDefinitionRegistrar.unregister(_ctDefinition);
		}

		// If the change tracking was enabled originally, then leave it in the
		// same state

		if (_originallyEnabled) {
			_ctEngineManager.enableChangeTracking(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());
		}
		else {
			_ctEngineManager.disableChangeTracking(
				TestPropsValues.getCompanyId());
		}
	}

	@Override
	public void testGetCollectionEntriesPage() throws Exception {
		Set<Long> ctEntryIds = new HashSet<>();

		_createCollection();

		_createChangeEntry(_ctCollection.getCtCollectionId(), 1);

		ctEntryIds.add(_ctEntry.getCtEntryId());

		_createChangeEntry(_ctCollection.getCtCollectionId(), 2);

		ctEntryIds.add(_ctEntry.getCtEntryId());

		Page<Entry> entriesPage = entryResource.getCollectionEntriesPage(
			_ctCollection.getCtCollectionId(), null, null, null, null, null,
			null, null, null);

		Assert.assertEquals(
			"Wrong number of entries", 2,
			GetterUtil.getLong(entriesPage.getTotalCount()));

		Collection<Entry> entries = entriesPage.getItems();

		Stream<Entry> entryStream = entries.stream();

		entryStream.forEach(entry -> ctEntryIds.remove(entry.getId()));

		Assert.assertTrue("ctEntryIds should be empty", ctEntryIds.isEmpty());
	}

	@Override
	public void testGetCollectionEntriesPageWithPagination() throws Exception {
		_createCollection();

		for (int i = 1; i <= 20; i++) {
			_createChangeEntry(_ctCollection.getCtCollectionId(), i);
		}

		Page<Entry> entriesPage = entryResource.getCollectionEntriesPage(
			_ctCollection.getCtCollectionId(),
			new String[] {String.valueOf(CTConstants.CT_CHANGE_TYPE_ADDITION)},
			new String[] {
				String.valueOf(_testVersionClassName.getClassNameId())
			},
			false, null, WorkflowConstants.STATUS_DRAFT,
			new String[] {String.valueOf(_user.getUserId())},
			Pagination.of(2, 5), null);

		Assert.assertEquals(
			"Wrong page number", 2, GetterUtil.getLong(entriesPage.getPage()));

		Assert.assertEquals(
			"Wrong total count", 20, entriesPage.getTotalCount());
	}

	@Ignore
	@Override
	public void testGetCollectionEntriesPageWithSortDateTime()
		throws Exception {

		_createCollection();

		_createChangeEntry(_ctCollection.getCtCollectionId(), 1);

		_createChangeEntry(_ctCollection.getCtCollectionId(), 2);

		Page<Entry> entriesPage = entryResource.getCollectionEntriesPage(
			_ctCollection.getCtCollectionId(), null, null, null, null, null,
			null, null, "createDate:desc");

		Collection<Entry> items = entriesPage.getItems();

		Entry entry = (Entry)ArrayUtil.getValue(items.toArray(), 0);

		Assert.assertEquals(
			"Wrong order of items", _ctEntry.getCtEntryId(),
			GetterUtil.getLong(entry.getId()));
	}

	@Ignore
	@Override
	public void testGetCollectionEntriesPageWithSortInteger() throws Exception {
		Assert.assertTrue(true);
	}

	@Ignore
	@Override
	public void testGetCollectionEntriesPageWithSortString() throws Exception {
		Assert.assertTrue(true);
	}

	@Override
	public void testGetEntry() throws Exception {
		_createCollection();

		_createChangeEntry(_ctCollection.getCtCollectionId(), 1);

		Entry entry = entryResource.getEntry(_ctEntry.getCtEntryId());

		Assert.assertEquals(
			_ctEntry.getCtEntryId(), GetterUtil.getLong(entry.getId()));
	}

	private void _createChangeEntry(long ctCollectionId, long modelClassPK)
		throws Exception {

		long modelResourcePrimKey = RandomTestUtil.nextLong();

		_ctEntry = _ctEntryLocalService.addCTEntry(
			_user.getUserId(), _testVersionClassName.getClassNameId(),
			modelClassPK, modelResourcePrimKey,
			CTConstants.CT_CHANGE_TYPE_ADDITION, ctCollectionId,
			new ServiceContext());
	}

	private void _createCollection() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	private CTDefinition _ctDefinition;

	@Inject
	private CTDefinitionBuilder<TestResourceModelClass, TestVersionModelClass>
		_ctDefinitionBuilder;

	@Inject
	private CTDefinitionRegistrar _ctDefinitionRegistrar;

	@Inject
	private CTEngineManager _ctEngineManager;

	@DeleteAfterTestRun
	private CTEntry _ctEntry;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private CTManager _ctManager;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	private boolean _originallyEnabled;

	@DeleteAfterTestRun
	private ClassName _testResourceClassName;

	@DeleteAfterTestRun
	private ClassName _testVersionClassName;

	@DeleteAfterTestRun
	private User _user;

}