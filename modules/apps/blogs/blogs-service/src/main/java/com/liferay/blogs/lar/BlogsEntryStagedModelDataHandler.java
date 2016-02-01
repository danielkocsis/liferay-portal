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

package com.liferay.blogs.lar;

import com.liferay.blogs.exportimport.content.processor.BlogsEntryExportImportContentProcessor;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class BlogsEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BlogsEntry> {

	public static final String[] CLASS_NAMES = {BlogsEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(BlogsEntry entry) {
		return entry.getTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(entry);

		if (entry.isSmallImage()) {
			Image smallImage = _imageLocalService.fetchImage(
				entry.getSmallImageId());

			if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					entry,
					smallImage.getImageId() + StringPool.PERIOD +
						smallImage.getType());

				entryElement.addAttribute("small-image-path", smallImagePath);

				entry.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		if (entry.getSmallImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getSmallImageFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, entry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		if (entry.getCoverImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getCoverImageFileEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, entry, fileEntry,
				PortletDataContext.REFERENCE_TYPE_WEAK);
		}

		String content =
			_blogsEntryExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, entry, entry.getContent(),
					portletDataContext.getBooleanParameter(
						"blogs", "referenced-content"),
					true);

		entry.setContent(content);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(entry), entry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long entryId)
		throws Exception {

		BlogsEntry existingEntry = fetchMissingReference(uuid, groupId);

		if (existingEntry == null) {
			return;
		}

		Map<Long, Long> entryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BlogsEntry.class);

		entryIds.put(entryId, existingEntry.getEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BlogsEntry entry)
		throws Exception {

		Element entryElement =
			portletDataContext.getImportDataStagedModelElement(entry);

		String content =
			_blogsEntryExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, entry, entry.getContent());

		entry.setContent(content);

		BlogsEntry importedEntry = (BlogsEntry)entry.clone();

		BlogsEntry existingEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				entry.getUuid(), portletDataContext.getScopeGroupId());

		if (existingEntry == null) {
			importedEntry =_stagedModelRepository.addStagedModel(
				portletDataContext, importedEntry);
		}
		else {
			importedEntry.setEntryId(existingEntry.getEntryId());

			importedEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedEntry);
		}

		if ((entry.getCoverImageFileEntryId() == 0) &&
			Validator.isNull(entry.getCoverImageURL()) &&
			(entry.getSmallImageFileEntryId() == 0) &&
			Validator.isNull(entry.getSmallImageURL()) &&
			!entry.isSmallImage()) {

			portletDataContext.importClassedModel(entry, importedEntry);

			return;
		}

		// Cover image

		ImageSelector coverImageSelector = null;

		List<Element> attachmentElements =
			portletDataContext.getReferenceDataElements(
				entry, DLFileEntry.class,
				PortletDataContext.REFERENCE_TYPE_WEAK);

		if (Validator.isNotNull(entry.getCoverImageURL())) {
			coverImageSelector = new ImageSelector(entry.getCoverImageURL());
		}
		else if (entry.getCoverImageFileEntryId() != 0) {
			coverImageSelector = _getImageSelector(
				portletDataContext, entry.getCoverImageFileEntryId(),
				attachmentElements);
		}

		if (coverImageSelector != null) {
			_blogsEntryLocalService.addCoverImage(
				importedEntry.getEntryId(), coverImageSelector);
		}

		// Small image

		ImageSelector smallImageSelector = null;

		if (entry.isSmallImage()) {
			String smallImagePath = entryElement.attributeValue(
				"small-image-path");

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				smallImageSelector = new ImageSelector(
					entry.getSmallImageURL());
			}
			else if (Validator.isNotNull(smallImagePath)) {
				String smallImageFileName =
					entry.getSmallImageId() + StringPool.PERIOD +
						entry.getSmallImageType();

				InputStream inputStream = null;

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						smallImagePath);

					smallImageSelector = new ImageSelector(
						FileUtil.getBytes(inputStream), smallImageFileName,
						MimeTypesUtil.getContentType(smallImageFileName), null);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
			else if (entry.getSmallImageFileEntryId() != 0) {
				smallImageSelector = _getImageSelector(
					portletDataContext, entry.getSmallImageFileEntryId(),
					attachmentElements);
			}
		}

		if (smallImageSelector != null) {
			_blogsEntryLocalService.addSmallImage(
				importedEntry.getEntryId(), smallImageSelector);
		}

		if ((coverImageSelector != null) || (smallImageSelector != null)) {
			importedEntry =_blogsEntryLocalService.getBlogsEntry(
				importedEntry.getEntryId());
		}

		portletDataContext.importClassedModel(entry, importedEntry);
	}

	protected InputStream getSmallImageInputStream(
		PortletDataContext portletDataContext, Element attachmentElement) {

		InputStream inputStream = null;

		String path = attachmentElement.attributeValue("path");

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		String binPath = attachmentElement.attributeValue("bin-path");

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				inputStream = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception e) {
			}
		}
		else {
			inputStream = portletDataContext.getZipEntryAsInputStream(binPath);
		}

		if (inputStream == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import small image file entry " +
						fileEntry.getFileEntryId());
			}
		}

		return inputStream;
	}

	protected StagedModelRepository<BlogsEntry> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryExportImportContentProcessor(
		BlogsEntryExportImportContentProcessor
			blogsEntryExportImportContentProcessor) {

		_blogsEntryExportImportContentProcessor =
			blogsEntryExportImportContentProcessor;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(
		target =
			"(model.class.name=com.liferay.portlet.blogs.model.BlogsEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<BlogsEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private ImageSelector _getImageSelector(
			PortletDataContext portletDataContext, long fileEntryId,
			List<Element> attachmentElements)
		throws Exception {

		FileEntry oldImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

		for (Element attachmentElement : attachmentElements) {
			Attribute uuidAttribute = attachmentElement.attribute("uuid");

			String uuidAttributeValue = uuidAttribute.getValue();

			if (uuidAttributeValue.equals(oldImageFileEntry.getUuid())) {
				String path = attachmentElement.attributeValue("path");

				FileEntry fileEntry =
					(FileEntry)portletDataContext.getZipEntryAsObject(path);

				return new ImageSelector(
					FileUtil.getBytes(fileEntry.getContentStream()),
					fileEntry.getFileName(), fileEntry.getMimeType(), null);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryStagedModelDataHandler.class);

	private BlogsEntryExportImportContentProcessor
		_blogsEntryExportImportContentProcessor;
	private BlogsEntryLocalService _blogsEntryLocalService;
	private ImageLocalService _imageLocalService;
	private StagedModelRepository<BlogsEntry> _stagedModelRepository;

}