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

package com.liferay.exportimport.internal.lifecycle;

import static com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants.EVENT_STAGED_MODEL_EXPORT_SUCCEEDED;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.TransientValue;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = {ExportImportLifecycleListener.class})
public class StagedModelLastPublishDateExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		int eventCode = exportImportLifecycleEvent.getCode();

		if (eventCode != EVENT_STAGED_MODEL_EXPORT_SUCCEEDED) {
			return;
		}

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		PortletDataContext portletDataContext =
			(PortletDataContext)attributes.get(0);

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (!ExportImportThreadLocal.isStagingInProcess() ||
			!updateLastPublishDate) {

			return;
		}

		TransientValue<StagedModel> transientValue =
			(TransientValue<StagedModel>)attributes.get(1);

		StagedModel stagedModel = transientValue.getValue();

		if (!(stagedModel instanceof StagedGroupedModel)) {
			return;
		}

		ExportImportProcessCallbackRegistryUtil.registerCallback(
			new UpdateStagedModelLastPublishDateCallable(
				(StagedGroupedModel)stagedModel,
				portletDataContext.getDateRange()));
	}

	@Reference
	private GroupLocalService _groupLocalService;

	private class UpdateStagedModelLastPublishDateCallable
		implements Callable<Void> {

		public UpdateStagedModelLastPublishDateCallable(
			StagedGroupedModel stagedModel, DateRange dateRange) {

			_className = ExportImportClassedModelUtil.getClassName(stagedModel);
			_dateRange = dateRange;
			_groupId = stagedModel.getGroupId();
			_uuid = stagedModel.getUuid();
		}

		@Override
		public Void call() throws PortalException {
			StagedModelRepository stagedModelRepository =
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					_className);

			if (stagedModelRepository == null) {
				return null;
			}

			Group group = _groupLocalService.getGroup(_groupId);

			if (group.hasStagingGroup()) {
				Group stagingGroup = group.getStagingGroup();

				_groupId = stagingGroup.getGroupId();
			}

			StagedModel stagedModel =
				stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					_uuid, _groupId);

			if (stagedModel == null) {
				return null;
			}

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			ExportImportDateUtil.updateLastPublishDate(
				(StagedGroupedModel)stagedModel, _dateRange, endDate);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setModifiedDate(stagedModel.getModifiedDate());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			try {
				stagedModelRepository.saveStagedModel(stagedModel);
			}
			finally {
				ServiceContextThreadLocal.popServiceContext();
			}

			return null;
		}

		private final String _className;
		private final DateRange _dateRange;
		private long _groupId;
		private final String _uuid;

	}

}