/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.service.base.AssetLinkLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class implements the methods needed to handle AssetLinks, the entity
 * that relates different assets in the portal.
 *
 * The basic information stored for every link includes both assets entry IDs,
 * the userId, the link type and the link's weight.
 *
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetLinkLocalServiceImpl extends AssetLinkLocalServiceBaseImpl {

	/**
	 * Adds a new asset link.
	 *
	 * @param  userId the primary key of the link's creator
	 * @param  entryId1 the primary key of the first asset entry
	 * @param  entryId2 the primary key of the second asset entry
	 * @param  type the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @param  weight the weight of the relationship, allowing precedence
	 *         ordering of links
	 * @return the asset link
	 * @throws PortalException if the user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink addLink(
			long userId, long entryId1, long entryId2, int type, int weight)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long linkId = counterLocalService.increment();

		AssetLink link = assetLinkPersistence.create(linkId);

		link.setCompanyId(user.getCompanyId());
		link.setUserId(user.getUserId());
		link.setUserName(user.getFullName());
		link.setCreateDate(now);
		link.setEntryId1(entryId1);
		link.setEntryId2(entryId2);
		link.setType(type);
		link.setWeight(weight);

		assetLinkPersistence.update(link);

		if (AssetLinkConstants.isTypeBi(type)) {

			AssetLink inverseAssetLink = assetLinkPersistence.fetchByE_E_T(
				entryId2, entryId1, type);

			if (inverseAssetLink == null) {
				long inverseLinkId = counterLocalService.increment();

				inverseAssetLink = assetLinkPersistence.create(inverseLinkId);
			}

			inverseAssetLink.setCompanyId(user.getCompanyId());
			inverseAssetLink.setUserId(user.getUserId());
			inverseAssetLink.setUserName(user.getFullName());
			inverseAssetLink.setCreateDate(now);
			inverseAssetLink.setEntryId1(entryId2);
			inverseAssetLink.setEntryId2(entryId1);
			inverseAssetLink.setType(type);

			assetLinkPersistence.update(inverseAssetLink);
		}

		return link;
	}

	/**
	 * Deletes the asset link.
	 *
	 * @param  link the asset link
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLink(AssetLink link) throws SystemException {
		if (AssetLinkConstants.isTypeBi(link.getType())) {
			try {
				assetLinkPersistence.removeByE_E_T(
					link.getEntryId2(), link.getEntryId1(), link.getType());
			}
			catch (NoSuchLinkException nsle) {
			}
		}

		assetLinkPersistence.remove(link);
	}

	/**
	 * Deletes the asset link.
	 *
	 * @param  linkId the primary key of the asset link
	 * @throws PortalException if the asset link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLink(long linkId)
		throws PortalException, SystemException {

		AssetLink link = assetLinkPersistence.findByPrimaryKey(linkId);

		deleteLink(link);
	}

	/**
	 * Deletes all links associated with the asset entry.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLinks(long entryId) throws SystemException {
		for (AssetLink link : assetLinkPersistence.findByE1(entryId)) {
			deleteLink(link);
		}

		for (AssetLink link : assetLinkPersistence.findByE2(entryId)) {
			deleteLink(link);
		}
	}

	/**
	 * Delete all links that associate the two asset entries.
	 *
	 * @param  entryId1 the primary key of the first asset entry
	 * @param  entryId2 the primary key of the second asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteLinks(long entryId1, long entryId2)
		throws SystemException {

		List<AssetLink> links = assetLinkPersistence.findByE_E(
			entryId1, entryId2);

		for (AssetLink link : links) {
			deleteLink(link);
		}
	}

	/**
	 * Returns all the asset links whose first entry ID is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @return the asset links whose first entry ID is the given entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> getDirectLinks(long entryId) throws SystemException {
		return assetLinkFinder.findByE1_V(entryId, true);
	}

	/**
	 * Returns all the asset links of the given link type whose first entry ID
	 * is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @return the asset links of the given link type whose first entry ID is
	 *         the given entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> getDirectLinks(long entryId, int typeId)
		throws SystemException {

		return assetLinkFinder.findByE1_T_V(entryId, typeId, true);
	}

	/**
	 * Get AssetLink from groupId,Entry1 class UUID and entry 2 class UUID
	 *
	 * @param  groupId the primary key of the group
	 * @param  entry1Uuid the class UUID of the first asset entry
	 * @param  entry2Uuid the class UUID of the second asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @return the asset link
	 * @throws NoSuchLinkException if the link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetLink getLink(
			long groupId, String entry1Uuid, String entry2Uuid, int typeId)
		throws NoSuchLinkException, SystemException {

		return assetLinkFinder.findByG_E1_E2_T(
			groupId, entry1Uuid, entry2Uuid, typeId);
	}

	/**
	 * Returns all the asset links whose first or second entry ID is the given
	 * entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @return the asset links whose first or second entry ID is the given entry
	 *         ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> getLinks(long entryId) throws SystemException {
		List<AssetLink> e1Links = assetLinkPersistence.findByE1(entryId);
		List<AssetLink> e2Links = assetLinkPersistence.findByE2(entryId);

		List<AssetLink> links = new ArrayList<AssetLink>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	/**
	 * Returns all the asset links of the given link type whose first or second
	 * entry ID is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @return the asset links of the given link type whose first or second
	 *         entry ID is the given entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> getLinks(long entryId, int typeId)
		throws SystemException {

		List<AssetLink> e1Links = assetLinkPersistence.findByE1_T(
			entryId, typeId);
		List<AssetLink> e2Links = assetLinkPersistence.findByE2_T(
			entryId, typeId);

		List<AssetLink> links = new ArrayList<AssetLink>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	/**
	 * Returns all the asset links of the given link type whose second entry ID
	 * is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @return the asset links of the given link type whose second entry ID is
	 *         the given entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetLink> getReverseLinks(long entryId, int typeId)
		throws SystemException {

		return assetLinkPersistence.findByE2_T(entryId, typeId);
	}

	/**
	 * Updates link of the asset entry given by entryId
	 * @param userId the primary key of the user updating the link
	 * @param entryId1 the primary key of the source asset entry
	 * @param entryId2 the primary key of the target asset entry
	 * @param typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @param weight the weight of the link
	 * @return the updated link
	 * @throws PortalException  if a portal exception occurred
	 * @throws SystemException  if a system exception occurred
	 */
	public AssetLink updateLink(
			long userId, long entryId1, long entryId2, int typeId, int weight)
		throws PortalException, SystemException {

		AssetLink assetLink = assetLinkPersistence.fetchByE_E_T(
				entryId1, entryId2, typeId);

		if (assetLink == null) {
			assetLink = addLink(userId, entryId1, entryId2, typeId, weight);
		}
		else {
			assetLink.setWeight(weight);
			assetLinkPersistence.update(assetLink);
		}

		return assetLink;
	}

	/**
	 * Updates all links of the asset entry, replacing them with links
	 * associating the asset entry with the asset entries of the given link
	 * entry IDs.
	 *
	 * <p>
	 * If no link exists with a given link entry ID, a new link is created
	 * associating the current asset entry with the asset entry of that link
	 * entry ID. An existing link is deleted if either of its entry IDs is not
	 * contained in the given link entry IDs.
	 * </p>
	 *
	 * @param  userId the primary key of the user updating the links
	 * @param  entryId the primary key of the asset entry to be managed
	 * @param  linkEntryIds the primary keys of the asset entries to be linked
	 *         with the asset entry to be managed
	 * @param  typeId the type of the asset links to be created. Acceptable
	 *         values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @throws PortalException if the user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void updateLinks(
			long userId, long entryId, long[] linkEntryIds, int typeId)
		throws PortalException, SystemException {

		if (linkEntryIds == null) {
			return;
		}

		List<AssetLink> links = getLinks(entryId, typeId);

		for (AssetLink link : links) {
			if (((link.getEntryId1() == entryId) &&
				 !ArrayUtil.contains(linkEntryIds, link.getEntryId2())) ||
				((link.getEntryId2() == entryId) &&
				 !ArrayUtil.contains(linkEntryIds, link.getEntryId1()))) {

				deleteLink(link);
			}
		}

		for (long assetLinkEntryId : linkEntryIds) {
			if (assetLinkEntryId != entryId) {
				AssetLink link = assetLinkPersistence.fetchByE_E_T(
					entryId, assetLinkEntryId, typeId);

				if (link == null) {
					addLink(userId, entryId, assetLinkEntryId, typeId, 0);
				}
			}
		}
	}

}