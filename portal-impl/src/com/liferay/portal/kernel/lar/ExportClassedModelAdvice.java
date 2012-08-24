/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.lar.digest.LarDigestEntry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lar.DataHandlersUtil;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.persistence.lar.LockDataHandler;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.lar.AssetCategoryDataHandler;
import com.liferay.portlet.asset.lar.AssetLinkDataHandler;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.AfterReturningAdvice;

/**
 * @author Mate Thurzo
 */
public class ExportClassedModelAdvice extends ExportImportAdvice
	implements AfterReturningAdvice {

	public void afterReturning(
			Object returnValue, Method method, Object[] args, Object target)
		throws Throwable {

		if (returnValue == null) {
			return;
		}

		Object entity = args[0];
		DataHandlerContext context = (DataHandlerContext)args[1];

		LarDigestEntry entry = (LarDigestEntry)returnValue;

		StagedDataHandler dataHandler = DataHandlersUtil.getDataHandlerInstance(
			entry.getClassName());

		if (entity == null) {
			return;
		}

		boolean portletMetadataAll = context.getBooleanParameter(
			dataHandler.getNamespace(),
			PortletDataHandlerKeys.PORTLET_METADATA_ALL);

		boolean categoriesParam = context.getBooleanParameter(
			dataHandler.getNamespace(), "categories");
		boolean commentsParam = context.getBooleanParameter(
			dataHandler.getNamespace(), "comments");
		boolean ratingsParam = context.getBooleanParameter(
			dataHandler.getNamespace(), "ratings");
		boolean tagsParam = context.getBooleanParameter(
			dataHandler.getNamespace(), "tags");

		if (!isResourceMain(entity)) {
			return;
		}

		digestAssetLinks(entry, entity, context);

		digestLocks(entry, (ClassedModel)entity, context);

		if (portletMetadataAll || categoriesParam) {
			digestAssetCategories(entry, entity, context);
		}

		if (portletMetadataAll || commentsParam) {
			digestComments(entry, entity);
		}

		if (portletMetadataAll || ratingsParam) {
			digestRatingsEntries(entry, entity, context);
		}

		if (portletMetadataAll || tagsParam) {
			digestAssetTags(entry, entity, context);
		}
	}

	private void digestAssetCategories(
			LarDigestEntry entry, Object entity, DataHandlerContext context)
		throws Exception {

		long classPK = getClassPK(entry);

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getCategories(
				entity.getClass().getName(), classPK);

		AssetCategoryDataHandler categoryDataHandler =
			(AssetCategoryDataHandler)DataHandlersUtil.getDataHandlerInstance(
				AssetCategory.class.getName());

		for (AssetCategory assetCategory : assetCategories) {
			categoryDataHandler.export(assetCategory, context, null);
		}
	}

	private void digestAssetLinks(
			LarDigestEntry entry, Object entity, DataHandlerContext context)
		throws Exception {

		AssetEntry assetEntry = null;

		try {
			long classPK = getClassPK(entry);

			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				entity.getClass().getName(), classPK);
		}
		catch (Exception e) {
			return;
		}

		List<AssetLink> directAssetLinks =
			AssetLinkLocalServiceUtil.getDirectLinks(assetEntry.getEntryId());

		if (directAssetLinks.isEmpty()) {
			return;
		}

		Map<Integer, List<AssetLink>> assetLinksMap =
			new HashMap<Integer, List<AssetLink>>();

		for (AssetLink assetLink : directAssetLinks) {
			List<AssetLink> assetLinks = assetLinksMap.get(assetLink.getType());

			if (assetLinks == null) {
				assetLinks = new ArrayList<AssetLink>();

				assetLinksMap.put(assetLink.getType(), assetLinks);
			}

			assetLinks.add(assetLink);
		}

		for (Map.Entry<Integer, List<AssetLink>> assetLinkEntry :
				assetLinksMap.entrySet()) {

			int assetLinkType = assetLinkEntry.getKey();
			List<AssetLink> assetLinks = assetLinkEntry.getValue();

			List<String> assetLinkUuids = new ArrayList<String>(
				directAssetLinks.size());

			AssetLinkDataHandler linkDataHandler =
				(AssetLinkDataHandler)DataHandlersUtil.getDataHandlerInstance(
					AssetLink.class.getName());

			for (AssetLink assetLink : assetLinks) {
				linkDataHandler.export(assetLink, context, null);
			}
		}
	}

	private void digestAssetTags(
			LarDigestEntry entry, Object entity, DataHandlerContext context)
		throws Exception {

		long classPK = getClassPK(entry);

		String[] tagNames = AssetTagLocalServiceUtil.getTagNames(
			entity.getClass().getName(), classPK);

		for (String tagName : tagNames) {
			// TODO create AssetTagDataHandler
		}
	}

	private void digestComments(LarDigestEntry entry, Object entity)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			entity.getClass().getName());

		long classPK = getClassPK(entry);

		MBDiscussion discussion = MBDiscussionUtil.fetchByC_C(
			classNameId, classPK);

		if (discussion == null) {
			return;
		}

		List<MBMessage> messages = MBMessageLocalServiceUtil.getThreadMessages(
			discussion.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		if (messages.size() == 0) {
			return;
		}

		for (MBMessage message : messages) {
			// TODO create MBMessageDataHandler
		}
	}

	private void digestLocks(
			LarDigestEntry entry, ClassedModel object,
			DataHandlerContext context)
		throws Exception {

		String key = String.valueOf(object.getPrimaryKeyObj());

		if (LockLocalServiceUtil.isLocked(object.getClass().getName(), key)) {

			Lock lock = LockLocalServiceUtil.getLock(
				object.getClass().getName(), key);

			LockDataHandler lockDataHandler =
				(LockDataHandler)DataHandlersUtil.getDataHandlerInstance(
					Lock.class.getName());

			lockDataHandler.export(lock, context, null);
		}
	}

	private void digestRatingsEntries(
			LarDigestEntry entry, Object entity, DataHandlerContext context)
		throws Exception {

		long classPK = getClassPK(entry);

		List<RatingsEntry> ratingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(
				entity.getClass().getName(), classPK);

		if (ratingsEntries.size() == 0) {
			return;
		}

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			// TODO create RatingsEntryDataHandler
		}
	}

}