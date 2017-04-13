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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.Dummy;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class DummyStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		stagedModelRepository = (StagedModelRepository<Dummy>)StagedModelRepositoryRegistryUtil.getStagedModelRepository(Dummy.class.getName());
	}

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected StagedModel addStagedModel(Group group, Map<String, List<StagedModel>> dependentStagedModelsMap) throws Exception {
		dummy = new Dummy(
			group.getCompanyId(), group.getGroupId(), TestPropsValues.getUser());

		return stagedModelRepository.addStagedModel(portletDataContext, dummy);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		return stagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Dummy.class;
	}

	protected Dummy dummy;
	protected StagedModelRepository<Dummy> stagedModelRepository;
}