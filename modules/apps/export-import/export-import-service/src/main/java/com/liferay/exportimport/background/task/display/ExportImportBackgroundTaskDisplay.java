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

package com.liferay.exportimport.background.task.display;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.display.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrew Betts
 */
public class ExportImportBackgroundTaskDisplay
	extends BaseBackgroundTaskDisplay {

	public ExportImportBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		super(backgroundTask);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		_exportImportConfiguration = getExportImportConfiguration(
			backgroundTask);
		_processType = _exportImportConfiguration.getType();
		_percentage = PERCENTAGE_NONE;

		if (backgroundTaskStatus == null) {
			_allProgressBarCountersTotal = 0;
			_currentProgressBarCountersTotal = 0;
			_phase = null;
			_stagedModelName = null;
			_stagedModelType = null;

			return;
		}

		long allModelAdditionCountersTotal =
			getBackgroundTaskStatusAttributeLong(
				"allModelAdditionCountersTotal");
		long allPortletAdditionCounter = getBackgroundTaskStatusAttributeLong(
			"allPortletAdditionCounter");

		_allProgressBarCountersTotal =
			allModelAdditionCountersTotal + allPortletAdditionCounter;

		long currentModelAdditionCountersTotal =
			getBackgroundTaskStatusAttributeLong(
				"currentModelAdditionCountersTotal");
		long currentPortletAdditionCounter =
			getBackgroundTaskStatusAttributeLong(
				"currentPortletAdditionCounter");

		_currentProgressBarCountersTotal =
			currentModelAdditionCountersTotal + currentPortletAdditionCounter;

		_phase = getBackgroundTaskStatusAttributeString("phase");
		_stagedModelName = getBackgroundTaskStatusAttributeString(
			"stagedModelName");
		_stagedModelType = getBackgroundTaskStatusAttributeString(
			"stagedModelType");
	}

	@Override
	public int getPercentage() {
		if (_percentage > PERCENTAGE_NONE) {
			return _percentage;
		}

		_percentage = PERCENTAGE_MAX;

		if (_allProgressBarCountersTotal > PERCENTAGE_MIN) {
			int base = PERCENTAGE_MAX;

			if (_phase.equals(Constants.EXPORT) &&
				(_processType ==
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE)) {

				base = _EXPORT_PHASE_MAX_PERCENTAGE;
			}

			_percentage = Math.round(
				(float)_currentProgressBarCountersTotal /
					_allProgressBarCountersTotal * base);
		}

		return _percentage;
	}

	@Override
	public String getStatusMessage(Locale locale) {
		if (!backgroundTask.isInProgress()) {
			return super.getStatusMessage(locale);
		}

		if (hasStagedModelMessage()) {
			return getStagedModelMessage(locale);
		}

		return LanguageUtil.get(locale, getStatusMessageKey());
	}

	@Override
	public boolean hasPercentage() {
		if (!hasBackgroundTaskStatus()) {
			return false;
		}

		if ((_allProgressBarCountersTotal > PERCENTAGE_MIN) &&
			((_processType ==
				ExportImportConfigurationConstants.
					TYPE_PUBLISH_LAYOUT_REMOTE) ||
			 (getPercentage() < PERCENTAGE_MAX))) {

			return true;
		}

		return false;
	}

	protected ExportImportConfiguration getExportImportConfiguration(
		BackgroundTask backgroundTask) {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long exportImportConfigurationId = MapUtil.getLong(
			taskContextMap, "exportImportConfigurationId");

		return ExportImportConfigurationLocalServiceUtil.
			fetchExportImportConfiguration(exportImportConfigurationId);
	}

	@Override
	protected String getMessageTemplate() {
		return _DETIALS_TEMPLATE;
	}

	protected String getStagedModelMessage(Locale locale) {
		StringBundler sb = new StringBundler(8);

		sb.append("<strong>");
		sb.append(LanguageUtil.get(locale, getStatusMessageKey()));
		sb.append(StringPool.TRIPLE_PERIOD);
		sb.append("</strong>");
		sb.append(
			ResourceActionsUtil.getModelResource(locale, _stagedModelType));
		sb.append("<em>");
		sb.append(HtmlUtil.escape(_stagedModelName));
		sb.append("</em>");

		return sb.toString();
	}

	@Override
	protected String getStatusMessageKey() {
		if (Validator.isNotNull(_messageKey)) {
			return _messageKey;
		}

		_messageKey = StringPool.BLANK;

		if (hasRemoteMessage()) {
			_messageKey =
				"please-wait-as-the-publication-processes-on-the-remote-site";
		}
		else if (hasStagedModelMessage()) {
			_messageKey = "exporting";

			if (_processType ==
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT) {

				_messageKey = "importing";
			}
			else if ((_processType ==
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_LAYOUT_REMOTE) ||
					 (_processType ==
						 ExportImportConfigurationConstants.
							 TYPE_PUBLISH_LAYOUT_LOCAL)) {

				_messageKey = "publishing";
			}
		}

		return _messageKey;
	}

	@Override
	protected Map<String, Object> getTemplateVars() {
		Map<String, Object> templateVars = new HashMap<>();

		templateVars.put(
			"exported",
			MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "exported"));
		templateVars.put(
			"validated",
			MapUtil.getBoolean(
				backgroundTask.getTaskContextMap(), "validated"));

		templateVars.put("htmlUtil", HtmlUtil.getHtml());

		return templateVars;
	}

	protected boolean hasRemoteMessage() {
		if ((_processType ==
				ExportImportConfigurationConstants.
					TYPE_PUBLISH_LAYOUT_REMOTE) &&
			(getPercentage() == PERCENTAGE_MAX)) {

			return true;
		}

		return false;
	}

	protected boolean hasStagedModelMessage() {
		if (Validator.isNotNull(_stagedModelName) &&
			Validator.isNotNull(_stagedModelType)) {

			return true;
		}

		return false;
	}

	private static final String _DETIALS_TEMPLATE =
		"export_import_background_task_details.ftl";

	private static final int _EXPORT_PHASE_MAX_PERCENTAGE = 50;

	private final long _allProgressBarCountersTotal;
	private final long _currentProgressBarCountersTotal;
	private final ExportImportConfiguration _exportImportConfiguration;
	private String _messageKey;
	private int _percentage;
	private final String _phase;
	private final int _processType;
	private final String _stagedModelName;
	private final String _stagedModelType;

}