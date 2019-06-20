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

package com.liferay.layout.change.tracking.internal;

import com.liferay.portal.change.tracking.CTAdapter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutCT;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.persistence.LayoutCTPK;
import com.liferay.portal.kernel.service.persistence.LayoutCTPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = CTAdapter.class)
public class LayoutCTAdapter implements CTAdapter<Layout, LayoutCT> {

	@Override
	public LayoutCT createContextModel(Layout model, long ctCollectionId) {
		return _layoutCTPersistence.create(
			new LayoutCTPK(model.getPlid(), ctCollectionId));
	}

	@Override
	public Layout fetchByPrimaryKey(long primaryKey) {
		return _layoutLocalService.fetchLayout(primaryKey);
	}

	@Override
	public LayoutCT fetchContextModel(long primaryKey, long ctCollectionId) {
		return _layoutCTPersistence.fetchByPrimaryKey(
			new LayoutCTPK(primaryKey, ctCollectionId));
	}

	@Override
	public List<LayoutCT> fetchContextModels(
		long[] primaryKeys, long ctCollectionId) {

		return _layoutCTPersistence.findByP_CT(primaryKeys, ctCollectionId);
	}

	@Override
	public List<Layout> findByCTCollectionId(long ctCollectionId) {
		return _layoutPersistence.findByCTCollectionId(ctCollectionId);
	}

	@Override
	public Class<Layout> getModelClass() {
		return Layout.class;
	}

	@Override
	public long getModelCTCollectionId(Layout model) {
		return model.getCtCollectionId();
	}

	@Override
	public long getModelPrimaryKey(LayoutCT ctContextModel) {
		return ctContextModel.getPlid();
	}

	@Override
	public long getPrimaryKey(Layout model) {
		return model.getPlid();
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return "plid";
	}

	@Override
	public void populateContextModel(Layout model, LayoutCT ctContextModel) {
		ctContextModel.setTypeSettings(model.getTypeSettings());
	}

	@Override
	public void populateModel(Layout model, LayoutCT ctContextModel) {
		model.setTypeSettings(ctContextModel.getTypeSettings());
	}

	@Override
	public void removeContext(long primaryKey, long ctCollectionId) {
		_layoutLocalService.deleteLayoutCT(primaryKey, ctCollectionId);
	}

	@Override
	public void removeContexts(Layout model) {
		_layoutCTPersistence.removeByPlid(model.getPlid());
	}

	@Override
	public void setContextModelCTCollectionId(
		LayoutCT ctContextModel, long ctCollectionId) {

		ctContextModel.setCtCollectionId(ctCollectionId);
	}

	@Override
	public void setModelCTCollectionId(Layout model, long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	@Override
	public void updateContextModel(LayoutCT ctContextModel) {
		_layoutCTPersistence.update(ctContextModel);
	}

	@Reference
	private LayoutCTPersistence _layoutCTPersistence;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPersistence _layoutPersistence;

}