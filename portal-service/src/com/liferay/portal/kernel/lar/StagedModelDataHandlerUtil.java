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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourcedModel;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class StagedModelDataHandlerUtil {

	public static void deleteStagedModel(
			PortletDataContext portletDataContext, Element deletionElement)
		throws PortalException, SystemException {

		String className = deletionElement.attributeValue("class-name");
		String extraData = deletionElement.attributeValue("extra-data");
		String uuid = deletionElement.attributeValue("uuid");

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				className);

		if (stagedModelDataHandler != null) {
			stagedModelDataHandler.deleteStagedModel(
				uuid, portletDataContext.getScopeGroupId(), className,
				extraData);
		}
	}

	public static <T extends StagedModel> void exportReferenceStagedModel(
			PortletDataContext portletDataContext, String referrerPortletId,
			T stagedModel)
		throws PortletDataException {

		Portlet referrerPortlet = PortletLocalServiceUtil.getPortletById(
			referrerPortletId);

		Map<String, String> referenceParameters = new HashMap<String, String>();

		referenceParameters.put(
			"type", PortletDataContext.REFERENCE_TYPE_DEPENDENCY);

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			if (portletDataContext.isCompanyStagedGroupedModel(
					stagedGroupedModel)) {

				referenceParameters.put(
					"missing", String.valueOf(Boolean.TRUE));

				LarHandlerUtil.writeReference(
					portletDataContext, referrerPortlet, stagedModel,
					referenceParameters);
			}
		}

		exportStagedModel(portletDataContext, stagedModel);

		referenceParameters.put("missing", String.valueOf(Boolean.FALSE));

		LarHandlerUtil.writeReference(
			portletDataContext, referrerPortlet, stagedModel,
			referenceParameters);
	}

	public static <T extends StagedModel, U extends StagedModel> Element
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<?> referrerStagedModelClass, U stagedModel,
			Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		Element referrerStagedModelElement =
			portletDataContext.getExportDataElement(
				referrerStagedModel, referrerStagedModelClass);

		return exportReferenceStagedModel(
			portletDataContext, referrerStagedModel, referrerStagedModelElement,
			stagedModel, stagedModelClass, referenceType);
	}

	public static <T extends StagedModel, U extends StagedModel> Element
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Element referrerStagedModelElement, U stagedModel,
			Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		Element referenceElement = null;

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			if (portletDataContext.isCompanyStagedGroupedModel(
					stagedGroupedModel)) {

				referenceElement = portletDataContext.addReferenceElement(
					referrerStagedModel, referrerStagedModelElement,
					stagedModel, stagedModelClass,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);

				return referenceElement;
			}
		}

		exportStagedModel(portletDataContext, stagedModel);

		referenceElement = portletDataContext.addReferenceElement(
			referrerStagedModel, referrerStagedModelElement, stagedModel,
			stagedModelClass, referenceType, false);

		return referenceElement;
	}

	public static <T extends StagedModel, U extends StagedModel> void
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			U stagedModel, Class<?> stagedModelClass, String referenceType)
		throws PortletDataException {

		Map<String, String> referenceParameters = new HashMap<String, String>();

		referenceParameters.put(
			"staged-model-class", stagedModelClass.getName());

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			if (portletDataContext.isCompanyStagedGroupedModel(
					stagedGroupedModel)) {

				referenceParameters.put(
					"type", PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				referenceParameters.put(
					"missing", String.valueOf(Boolean.TRUE));

				LarHandlerUtil.writeReference(
					portletDataContext, referrerStagedModel, stagedModel,
					referenceParameters);
			}
		}

		exportStagedModel(portletDataContext, stagedModel);

		referenceParameters.put("type", referenceType);
		referenceParameters.put("missing", String.valueOf(Boolean.FALSE));

		LarHandlerUtil.writeReference(
			portletDataContext, referrerStagedModel, stagedModel,
			referenceParameters);
	}

	public static <T extends StagedModel, U extends StagedModel> Element
		exportReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			U stagedModel, String referenceType)
		throws PortletDataException {

		return exportReferenceStagedModel(
			portletDataContext, referrerStagedModel,
			referrerStagedModel.getModelClass(), stagedModel,
			stagedModel.getModelClass(), referenceType);
	}

	public static <T extends StagedModel> void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		stagedModelDataHandler.exportStagedModel(
			portletDataContext, stagedModel);
	}

	public static <T extends StagedModel> String getDisplayName(T stagedModel) {
		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		if (stagedModelDataHandler == null) {
			return StringPool.BLANK;
		}

		return stagedModelDataHandler.getDisplayName(stagedModel);
	}

	public static Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		StagedModelDataHandler<StagedModel> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		return stagedModelDataHandler.getReferenceAttributes(
			portletDataContext, stagedModel);
	}

	public static <T extends StagedModel> void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<? extends ClassedModel> stagedModelClass, long classPK)
		throws PortletDataException {

		StagedModel stagedModel =
			(StagedModel)LarHandlerUtil.readReferencedModel(
				portletDataContext, referrerStagedModel, stagedModelClass,
				classPK);

		importReferenceStagedModel(
			portletDataContext, referrerStagedModel, stagedModel);
	}

	public static <T extends StagedModel, U extends StagedModel>
		void importReferenceStagedModel(
			PortletDataContext portletDataContext, T referrerStagedModel,
			U referredStagedModel)
		throws PortletDataException {

		Map<String, String> attributes =
			LarHandlerUtil.readReferenceAttributes(
				portletDataContext, referrerStagedModel, referredStagedModel);

		long groupId = MapUtil.getLong(attributes, "group-id");

		if ((portletDataContext.getSourceCompanyGroupId() == groupId) &&
			(portletDataContext.getGroupId() !=
				portletDataContext.getCompanyGroupId())) {

			StagedModelType stagedModelType =
				referredStagedModel.getStagedModelType();

			StagedModelDataHandler<U> stagedModelDataHandler =
				(StagedModelDataHandler<U>)
					StagedModelDataHandlerRegistryUtil.
						getStagedModelDataHandler(
							stagedModelType.getClassName());

			stagedModelDataHandler.importCompanyStagedModel(
				portletDataContext, referredStagedModel);

			return;
		}

		importStagedModel(portletDataContext, referredStagedModel);
	}

	public static void importReferenceStagedModels(
			PortletDataContext portletDataContext, Portlet referrerPortlet,
			Class<? extends ClassedModel> stagedModelClass)
		throws PortletDataException {

		List<ClassedModel> referencedModels =
			LarHandlerUtil.readReferencedModels(
				portletDataContext, referrerPortlet, stagedModelClass);

		if (referencedModels == null) {
			return;
		}

		for (ClassedModel referenceModel : referencedModels) {
			Map<String, String> attributes =
				LarHandlerUtil.readReferenceAttributes(
					portletDataContext, referrerPortlet, referenceModel);

			long groupId = MapUtil.getLong(attributes, "group-id");

			if ((portletDataContext.getSourceCompanyGroupId() == groupId) &&
				(portletDataContext.getGroupId() !=
					portletDataContext.getCompanyGroupId())) {

				StagedModelDataHandler<StagedModel> stagedModelDataHandler =
					(StagedModelDataHandler<StagedModel>)
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								stagedModelClass.getName());

				stagedModelDataHandler.importCompanyStagedModel(
					portletDataContext, (StagedModel)referenceModel);

				continue;
			}

			long classPK = MapUtil.getLong(attributes, "class-pk");

			String stagedModelPath = ExportImportPathUtil.getModelPath(
				portletDataContext, stagedModelClass.getName(), classPK);

			StagedModel stagedModel =
				(StagedModel)portletDataContext.getZipEntryAsObject(
					stagedModelPath);

			importStagedModel(portletDataContext, stagedModel);
		}
	}

	public static void importReferenceStagedModels(
			PortletDataContext portletDataContext, String portletId,
			Class<? extends ClassedModel> stagedModelClass)
		throws PortletDataException {

		Portlet portlet = null;

		try {
			PortletLocalServiceUtil.getPortletById(portletId);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}

		importReferenceStagedModels(
			portletDataContext, portlet, stagedModelClass);
	}

	public static <T extends StagedModel> void importReferenceStagedModels(
			PortletDataContext portletDataContext, T referrerStagedModel,
			Class<? extends ClassedModel> stagedModelClass)
		throws PortletDataException {

		List<ClassedModel> referenceModels =
			LarHandlerUtil.readReferencedModels(
				portletDataContext, referrerStagedModel, stagedModelClass);

		for (ClassedModel referencedModel : referenceModels) {
			importReferenceStagedModel(
				portletDataContext, referrerStagedModel,
				(StagedModel)referencedModel);
		}
	}

	public static void importStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException {

		//StagedModel stagedModel =
		// _getStagedModel(portletDataContext, element);

		//importStagedModel(portletDataContext, stagedModel);
	}

	public static <T extends StagedModel> void importStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelDataHandler<T> stagedModelDataHandler =
			_getStagedModelDataHandler(stagedModel);

		stagedModelDataHandler.importStagedModel(
			portletDataContext, stagedModel);
	}

	public static<T extends StagedModel> void serializeStagedModel(
			PortletDataContext portletDataContext, T stagedModel,
			Map<String, String> attributes)
		throws SystemException {

		String path = ExportImportPathUtil.getModelPath(stagedModel);

		attributes.put("path", path);

		if (stagedModel instanceof AttachedModel) {
			AttachedModel attachedModel = (AttachedModel)stagedModel;

			attributes.put("class-name", attachedModel.getClassName());
		}

		// todo: move this to portal-impl now

		/*
		else if (BeanUtil.hasProperty(stagedModel, "className")) {
			String className = BeanPropertiesUtil.getStringSilent(
				stagedModel, "className");

			if (className != null) {
				attributes.put("class-name", className);
			}
		}
		 */

		if (portletDataContext.isPathProcessed(path)) {
			return;
		}

		if (stagedModel instanceof AuditedModel) {
			AuditedModel auditedModel = (AuditedModel)stagedModel;

			auditedModel.setUserUuid(auditedModel.getUserUuid());
		}

		if (!isResourceMain(stagedModel)) {
			portletDataContext.addZipEntry(path, stagedModel);

			return;
		}

		/*
		long classPK = getClassPK(stagedModel);

		portletDataContext.addAssetCategories(clazz, classPK);
		//portletDataContext.addAssetLinks(clazz, classPK);
		portletDataContext.addAssetTags(clazz, classPK);
		portletDataContext.addExpando(element, path, stagedModel, clazz);
		portletDataContext.addLocks(clazz, String.valueOf(classPK));
		portletDataContext.addPermissions(clazz, classPK);

		boolean portletDataAll = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PORTLET_DATA_ALL);

		if (portletDataAll ||
			MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.COMMENTS)) {

			portletDataContext.addComments(clazz, classPK);
		}

		if (portletDataAll ||
			MapUtil.getBoolean(
				portletDataContext.getParameterMap(),
				PortletDataHandlerKeys.RATINGS)) {

			portletDataContext.addRatingsEntries(clazz, classPK);
		}
		 */

		portletDataContext.addZipEntry(path, stagedModel);
	}

	protected static long getClassPK(ClassedModel classedModel) {
		if (classedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)classedModel;

			return resourcedModel.getResourcePrimKey();
		}
		else {
			return (Long)classedModel.getPrimaryKeyObj();
		}
	}

	protected static boolean isResourceMain(StagedModel stagedModel) {
		if (stagedModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)stagedModel;

			return resourcedModel.isResourceMain();
		}

		return true;
	}

	private static <T extends StagedModel> StagedModelDataHandler<T>
		_getStagedModelDataHandler(T stagedModel) {

		ClassedModel classedModel = stagedModel;

		StagedModelDataHandler<T> stagedModelDataHandler =
			(StagedModelDataHandler<T>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					classedModel.getModelClassName());

		return stagedModelDataHandler;
	}

}