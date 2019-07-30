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
import com.liferay.change.tracking.definition.CTDefinitionRegistryUtil;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.client.dto.v1_0.AffectedEntry;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.resource.v1_0.AffectedEntryResource;
import com.liferay.change.tracking.rest.model.TestResourceModelClass;
import com.liferay.change.tracking.rest.model.TestVersionModelClass;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Zoltan Csaszi
 */
@RunWith(Arquillian.class)
public class AffectedEntryResourceTest
	extends BaseAffectedEntryResourceTestCase {

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

		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), _user.getUserId());

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
			Collections.emptyList(), o -> "TestString", o -> "TestString",
			o -> 1L
		).setEntityIdsFromVersionEntityFunctions(
			testVersion -> 0L, testVersion -> 0L
		).setVersionEntityStatusInfo(
			new Integer[] {WorkflowConstants.STATUS_APPROVED},
			testVersion -> WorkflowConstants.STATUS_APPROVED
		).build();

		_ctDefinitionRegistrar.register(_ctDefinition);

		AffectedEntryResource.Builder builder = AffectedEntryResource.builder();

		affectedEntryResource = builder.locale(
			LocaleUtil.getDefault()
		).build();

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());
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
	public void testGetCollectionEntryAffectedEntriesPage() throws Exception {
		_ctEngineManager.checkoutCTCollection(
			_user.getUserId(), _ctCollection.getCtCollectionId());

		CTEntry ownerCTEntry = _ctEntryLocalService.addCTEntry(
			_user.getUserId(), _testVersionClassName.getClassNameId(), (long)1,
			RandomTestUtil.nextLong(), CTConstants.CT_CHANGE_TYPE_ADDITION,
			_ctCollection.getCtCollectionId(), new ServiceContext());

		CTEntry relatedCTEntry = _ctEntryLocalService.addCTEntry(
			_user.getUserId(), _testVersionClassName.getClassNameId(), (long)2,
			RandomTestUtil.nextLong(), CTConstants.CT_CHANGE_TYPE_ADDITION,
			_ctCollection.getCtCollectionId(), new ServiceContext());

		_ctManager.addRelatedCTEntry(
			TestPropsValues.getCompanyId(), _user.getUserId(), ownerCTEntry,
			relatedCTEntry, true);

		Page<AffectedEntry> affectedEntriesPage =
			affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				_ctCollection.getCtCollectionId(),
				relatedCTEntry.getCtEntryId(), StringPool.BLANK, null);

		Assert.assertEquals(
			"Wrong number of entries", 1,
			GetterUtil.getLong(affectedEntriesPage.getTotalCount()));

		Collection<AffectedEntry> affectedEntries =
			affectedEntriesPage.getItems();

		Iterator<AffectedEntry> affectedEntriesIterator =
			affectedEntries.iterator();

		AffectedEntry affectedEntry = affectedEntriesIterator.next();

		String title = CTDefinitionRegistryUtil.getVersionEntityTitle(
			ownerCTEntry.getModelClassNameId(), ownerCTEntry.getModelClassPK());

		Assert.assertEquals(
			"Title must be the same", title, affectedEntry.getTitle());
	}

	@Override
	public void testGetCollectionEntryAffectedEntriesPageWithPagination()
		throws Exception {

		_ctEngineManager.checkoutCTCollection(
			_user.getUserId(), _ctCollection.getCtCollectionId());

		CTEntry relatedCTEntry = _ctEntryLocalService.addCTEntry(
			_user.getUserId(), _testVersionClassName.getClassNameId(),
			(long)101, RandomTestUtil.nextLong(),
			CTConstants.CT_CHANGE_TYPE_ADDITION,
			_ctCollection.getCtCollectionId(), new ServiceContext());

		for (int i = 1; i <= 10; i++) {
			CTEntry ownerCTEntry = _ctEntryLocalService.addCTEntry(
				_user.getUserId(), _testVersionClassName.getClassNameId(), i,
				RandomTestUtil.nextLong(), CTConstants.CT_CHANGE_TYPE_ADDITION,
				_ctCollection.getCtCollectionId(), new ServiceContext());

			_ctManager.addRelatedCTEntry(
				TestPropsValues.getCompanyId(), _user.getUserId(), ownerCTEntry,
				relatedCTEntry, true);
		}

		Page<AffectedEntry> affectedEntriesPage =
			affectedEntryResource.getCollectionEntryAffectedEntriesPage(
				_ctCollection.getCtCollectionId(),
				relatedCTEntry.getCtEntryId(), StringPool.BLANK,
				Pagination.of(2, 5));

		Collection<AffectedEntry> affectedEntryPageItems =
			affectedEntriesPage.getItems();

		Assert.assertEquals(
			"Wrong number of items", 5, affectedEntryPageItems.size());

		Assert.assertEquals(
			"Wrong number of pages", 2, affectedEntriesPage.getPage());
		Assert.assertEquals(
			"Wrong page size", 5, affectedEntriesPage.getPageSize());
		Assert.assertEquals(
			"Wrong total number of page items", 10,
			affectedEntriesPage.getTotalCount());
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