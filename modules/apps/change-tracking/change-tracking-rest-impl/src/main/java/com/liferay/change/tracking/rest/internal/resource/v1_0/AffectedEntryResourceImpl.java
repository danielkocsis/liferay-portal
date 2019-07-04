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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.display.CTDisplayHelper;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.AffectedEntry;
import com.liferay.change.tracking.rest.internal.util.CTDisplayHelperUtil;
import com.liferay.change.tracking.rest.resource.v1_0.AffectedEntryResource;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 * @author Zoltan Csaszi
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/affected-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = AffectedEntryResource.class
)
public class AffectedEntryResourceImpl extends BaseAffectedEntryResourceImpl {

	@Override
	public Page<AffectedEntry> getCollectionEntryAffectedEntriesPage(
			Long collectionId, Long entryId, String keywords,
			Pagination pagination)
		throws Exception {

		QueryDefinition<CTEntry> queryDefinition =
			SearchUtil.getQueryDefinition(CTEntry.class, pagination, null);

		queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

		if (Validator.isNotNull(keywords)) {
			CTEntry ownerCTEntry = _ctEntryLocalService.getCTEntry(entryId);

			queryDefinition.setOrderByComparator(
				OrderByComparatorFactoryUtil.create(
					"CTEntry", Field.TITLE, "asc"));

			return Page.of(
				transform(
					_ctEntryLocalService.getRelatedOwnerCTEntries(
						ownerCTEntry.getCompanyId(), collectionId, entryId,
						keywords, queryDefinition),
					this::_toAffectedEntry),
				pagination,
				_ctEntryLocalService.getRelatedOwnerCTEntriesCount(
					ownerCTEntry.getCompanyId(), collectionId, entryId,
					keywords, queryDefinition));
		}

		queryDefinition.setOrderByComparator(
			OrderByComparatorFactoryUtil.create("CTEntry", "createDate", true));

		return Page.of(
			transform(
				_ctEntryLocalService.getRelatedOwnerCTEntries(
					entryId, queryDefinition),
				this::_toAffectedEntry),
			pagination,
			_ctEntryLocalService.getRelatedOwnerCTEntriesCount(
				entryId, queryDefinition));
	}

	private AffectedEntry _toAffectedEntry(CTEntry ctEntry) {
		CTDisplayHelper ctDisplayHelper =
			CTDisplayHelperUtil.getCTDisplayHelper();

		return new AffectedEntry() {
			{
				contentType = ctDisplayHelper.getDisplayName(
					ctEntry.getModelClassNameId(), LocaleUtil.getDefault());
				title = ctDisplayHelper.getDisplayName(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK(),
					LocaleUtil.getDefault());
			}
		};
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}