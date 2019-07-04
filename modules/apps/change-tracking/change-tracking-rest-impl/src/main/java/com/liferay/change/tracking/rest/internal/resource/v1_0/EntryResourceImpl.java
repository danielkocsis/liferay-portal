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

import com.liferay.change.tracking.definition.CTDefinitionRegistryUtil;
import com.liferay.change.tracking.display.CTDisplayHelper;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.internal.util.CTDisplayHelperUtil;
import com.liferay.change.tracking.rest.resource.v1_0.EntryResource;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
	properties = "OSGI-INF/liferay/rest/v1_0/entry.properties",
	scope = ServiceScope.PROTOTYPE, service = EntryResource.class
)
public class EntryResourceImpl extends BaseEntryResourceImpl {

	@Override
	public Page<Entry> getCollectionEntriesPage(
			Long collectionId, String[] changeTypesFilter,
			String[] classNameIdsFilter, Boolean collision,
			String[] groupIdsFilter, Integer status, String[] userIdsFilter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			GetterUtil.getLong(collectionId));

		if (ctCollection == null) {
			throw new IllegalArgumentException(
				"Unable to get change tacking collection " + collectionId);
		}

		QueryDefinition<CTEntry> queryDefinition =
			SearchUtil.getQueryDefinition(CTEntry.class, pagination, sorts);

		queryDefinition.setStatus(status);

		return Page.of(
			transform(
				_ctEngineManager.getCTEntries(
					ctCollection, GetterUtil.getLongValues(groupIdsFilter),
					GetterUtil.getLongValues(userIdsFilter),
					GetterUtil.getLongValues(classNameIdsFilter),
					GetterUtil.getIntegerValues(changeTypesFilter), collision,
					queryDefinition),
				this::_toEntry),
			pagination,
			_ctEngineManager.getCTEntriesCount(
				ctCollection, GetterUtil.getLongValues(groupIdsFilter),
				GetterUtil.getLongValues(userIdsFilter),
				GetterUtil.getLongValues(classNameIdsFilter),
				GetterUtil.getIntegerValues(changeTypesFilter), collision,
				queryDefinition));
	}

	@Override
	public Entry getEntry(Long entryId) throws Exception {
		return _toEntry(_ctEntryLocalService.getCTEntry(entryId));
	}

	private Entry _toEntry(CTEntry ctEntry) {
		CTDisplayHelper ctDisplayHelper =
			CTDisplayHelperUtil.getCTDisplayHelper();

		return new Entry() {
			{
				affectedByEntriesCount =
					_ctEntryLocalService.getRelatedOwnerCTEntriesCount(
						ctEntry.getCtEntryId(), new QueryDefinition<>());
				changeType = ctEntry.getChangeType();
				classNameId = ctEntry.getModelClassNameId();
				classPK = ctEntry.getModelClassPK();
				collision = ctEntry.isCollision();
				contentType = ctDisplayHelper.getDisplayName(
					ctEntry.getModelClassNameId(), LocaleUtil.getDefault());
				dateModified = ctEntry.getModifiedDate();
				entryId = ctEntry.getCtEntryId();
				key = ctEntry.getModelResourcePrimKey();
				siteName = ctDisplayHelper.getSiteDisplayName(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK(),
					LocaleUtil.getDefault());
				title = ctDisplayHelper.getDisplayName(
					ctEntry.getModelClassNameId(), ctEntry.getModelClassPK(),
					LocaleUtil.getDefault());
				userName = ctEntry.getUserName();
				version = String.valueOf(
					CTDefinitionRegistryUtil.getVersionEntityVersion(
						ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK()));
			}
		};
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}