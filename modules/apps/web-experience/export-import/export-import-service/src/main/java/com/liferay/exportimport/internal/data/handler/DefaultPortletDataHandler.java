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

package com.liferay.exportimport.internal.data.handler;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public abstract class DefaultPortletDataHandler implements PortletDataHandler {

	public DefaultPortletDataHandler(PortletDataHandler portletDataHandler) {
		_portletDataHandler = portletDataHandler;
	}

	@Override
	public PortletPreferences addDefaultData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Adding default data to portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		try {
			return _portletDataHandler.addDefaultData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info(
					"Added default data to portlet in " +
					Time.getDuration(duration));
			}
		}
	}

	@Override
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Deleting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		try {
			return _portletDataHandler.deleteData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Deleted portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		Element rootElement = null;

		try {
			rootElement = portletDataContext.getExportDataRootElement();

			portletDataContext.addDeletionSystemEventStagedModelTypes(
				getDeletionSystemEventStagedModelTypes());

			for (PortletDataHandlerControl portletDataHandlerControl :
				getExportControls()) {

				addUncheckedModelAdditionCount(
					portletDataContext, portletDataHandlerControl);
			}

			return _portletDataHandler.exportData(
				portletDataContext, portletId, portletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			portletDataContext.setExportDataRootElement(rootElement);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Exported portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public DataLevel getDataLevel() {
		return _portletDataHandler.getDataLevel();
	}

	@Override
	public String[] getDataPortletPreferences() {
		return _portletDataHandler.getDataPortletPreferences();
	}

	@Override
	public StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		return _portletDataHandler.getDeletionSystemEventStagedModelTypes();
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet,
			boolean privateLayout)
		throws Exception {

		return getExportConfigurationControls(
			companyId, groupId, portlet, -1, privateLayout);
	}

	@Override
	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet, long plid,
			boolean privateLayout)
		throws Exception {

		return _portletDataHandler.getExportConfigurationControls(
			companyId, groupId, portlet, plid, privateLayout);
	}

	@Override
	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return _portletDataHandler.getExportControls();
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls()
		throws PortletDataException {

		return _portletDataHandler.getExportMetadataControls();
	}

	@Override
	public long getExportModelCount(ManifestSummary manifestSummary) {
		PortletDataHandlerControl[] portletDataHandlerControls = null;

		try {
			portletDataHandlerControls = getExportControls();
		}
		catch (Exception e) {
			portletDataHandlerControls = new PortletDataHandlerControl[0];
		}

		return getExportModelCount(manifestSummary, portletDataHandlerControls);
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary) {

		String[] configurationPortletOptions =
			manifestSummary.getConfigurationPortletOptions(
				portlet.getRootPortletId());

		return getImportConfigurationControls(configurationPortletOptions);
	}

	@Override
	public PortletDataHandlerControl[] getImportConfigurationControls(
		String[] configurationPortletOptions) {

		return _portletDataHandler.getImportConfigurationControls(
			configurationPortletOptions);
	}

	@Override
	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		return _portletDataHandler.getImportControls();
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls()
		throws PortletDataException {

		return _portletDataHandler.getImportMetadataControls();
	}

	@Override
	public String getPortletId() {
		return _portletDataHandler.getPortletId();
	}

	@Override
	public int getRank() {
		return _portletDataHandler.getRank();
	}

	@Override
	public String getSchemaVersion() {
		String schemaVersion = _portletDataHandler.getSchemaVersion();

		if (Validator.isNull(schemaVersion)) {
			return "1.0.0";
		}

		return schemaVersion;
	}

	@Override
	public String getServiceName() {
		return _portletDataHandler.getServiceName();
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Importing portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		long sourceGroupId = portletDataContext.getSourceGroupId();

		Element rootElement = null;

		try {
			if (Validator.isXml(data)) {
				rootElement = portletDataContext.getImportDataRootElement();

				addImportDataRootElement(portletDataContext, data);
			}

			return _portletDataHandler.importData(
				portletDataContext, portletId, portletPreferences, data);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
		finally {
			portletDataContext.setImportDataRootElement(rootElement);
			portletDataContext.setSourceGroupId(sourceGroupId);

			if (_log.isInfoEnabled()) {
				long duration = System.currentTimeMillis() - startTime;

				_log.info("Imported portlet in " + Time.getDuration(duration));
			}
		}
	}

	@Override
	public boolean isDataAlwaysStaged() {
		return _portletDataHandler.isDataAlwaysStaged();
	}

	@Override
	public boolean isDataLocalized() {
		return _portletDataHandler.isDataLocalized();
	}

	@Override
	public boolean isDataPortalLevel() {
		DataLevel dataLevel = _portletDataHandler.getDataLevel();

		return dataLevel.equals(DataLevel.PORTAL);
	}

	@Override
	public boolean isDataPortletInstanceLevel() {
		DataLevel dataLevel = _portletDataHandler.getDataLevel();

		return dataLevel.equals(DataLevel.PORTLET_INSTANCE);
	}

	@Override
	public boolean isDataSiteLevel() {
		DataLevel dataLevel = _portletDataHandler.getDataLevel();

		return dataLevel.equals(DataLevel.SITE);
	}

	@Override
	public boolean isDisplayPortlet() {
		return _portletDataHandler.isDisplayPortlet();
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _portletDataHandler.isPublishToLiveByDefault();
	}

	@Override
	public boolean isRollbackOnException() {
		return _portletDataHandler.isRollbackOnException();
	}

	@Override
	public boolean isSupportsDataStrategyCopyAsNew() {
		return _portletDataHandler.isSupportsDataStrategyCopyAsNew();
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException {

		prepareManifestSummary(portletDataContext, null);
	}

	@Override
	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			_portletDataHandler.prepareManifestSummary(
				portletDataContext, portletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return _portletDataHandler.processExportPortletPreferences(
				portletDataContext, portletId, portletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			return _portletDataHandler.processImportPortletPreferences(
				portletDataContext, portletId, portletPreferences);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	public void setPortletId(String portletId) {
		_portletDataHandler.setPortletId(portletId);
	}

	@Override
	public void setRank(int rank) {
		_portletDataHandler.setRank(rank);
	}

	@Override
	public boolean validateSchemaVersion(String schemaVersion) {
		try {
			return _portletDataHandler.validateSchemaVersion(schemaVersion);
		}
		catch (Exception e) {
			return false;
		}
	}

	protected Element addImportDataRootElement(
			PortletDataContext portletDataContext, String data)
		throws DocumentException {

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		portletDataContext.setImportDataRootElement(rootElement);

		long groupId = GetterUtil.getLong(
			rootElement.attributeValue("group-id"));

		if (groupId != 0) {
			portletDataContext.setSourceGroupId(groupId);
		}

		return rootElement;
	}

	protected void addUncheckedModelAdditionCount(
		PortletDataContext portletDataContext,
		PortletDataHandlerControl portletDataHandlerControl) {

		if (!(portletDataHandlerControl instanceof PortletDataHandlerBoolean)) {
			return;
		}

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			(PortletDataHandlerBoolean)portletDataHandlerControl;

		PortletDataHandlerControl[] childPortletDataHandlerControls =
			portletDataHandlerBoolean.getChildren();

		if (childPortletDataHandlerControls != null) {
			for (PortletDataHandlerControl childPortletDataHandlerControl :
				childPortletDataHandlerControls) {

				addUncheckedModelAdditionCount(
					portletDataContext, childPortletDataHandlerControl);
			}
		}

		if (Validator.isNull(portletDataHandlerControl.getClassName())) {
			return;
		}

		boolean checkedControl = GetterUtil.getBoolean(
			portletDataContext.getBooleanParameter(
				portletDataHandlerControl.getNamespace(),
				portletDataHandlerControl.getControlName(), false));

		if (!checkedControl) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			StagedModelType stagedModelType = new StagedModelType(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerBoolean.getReferrerClassName());

			String manifestSummaryKey = ManifestSummary.getManifestSummaryKey(
				stagedModelType);

			manifestSummary.addModelAdditionCount(manifestSummaryKey, 0);
		}
	}

	protected long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] portletDataHandlerControls) {

		long totalModelCount = -1;

		for (PortletDataHandlerControl portletDataHandlerControl :
			portletDataHandlerControls) {

			StagedModelType stagedModelType = new StagedModelType(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerControl.getReferrerClassName());

			long modelAdditionCount = manifestSummary.getModelAdditionCount(
				stagedModelType);

			if (portletDataHandlerControl instanceof
					PortletDataHandlerBoolean) {

				PortletDataHandlerBoolean portletDataHandlerBoolean =
					(PortletDataHandlerBoolean)portletDataHandlerControl;

				PortletDataHandlerControl[] childPortletDataHandlerControls =
					portletDataHandlerBoolean.getChildren();

				if (childPortletDataHandlerControls != null) {
					long childModelCount = getExportModelCount(
						manifestSummary, childPortletDataHandlerControls);

					if (childModelCount != -1) {
						if (modelAdditionCount == -1) {
							modelAdditionCount = childModelCount;
						}
						else {
							modelAdditionCount += childModelCount;
						}
					}
				}
			}

			if (modelAdditionCount == -1) {
				continue;
			}

			if (totalModelCount == -1) {
				totalModelCount = modelAdditionCount;
			}
			else {
				totalModelCount += modelAdditionCount;
			}
		}

		return totalModelCount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPortletDataHandler.class);

	private final PortletDataHandler _portletDataHandler;

}