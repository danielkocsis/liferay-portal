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

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletPreferences;

/**
 * A <code>PortletDataHandler</code> is a special class capable of exporting and
 * importing portlet specific data to a Liferay Archive file (LAR) when a site's
 * layouts are exported or imported. <code>PortletDataHandler</code>s are
 * defined by placing a <code>portlet-data-handler-class</code> element in the
 * <code>portlet</code> section of the <b>liferay-portlet.xml</b> file.
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Bruno Farache
 */
public interface PortletDataHandler {

	/**
	 * Returns the portlet's preferences with default data added.
	 *
	 * @param  portletDataContext the context of the data addition
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @return a modified version of the portlet preferences that should be
	 *         saved, or <code>null</code> if the data handler made no changes
	 *         to the portlet preferences
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletPreferences addDefaultData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	/**
	 * Deletes the data created by the portlet. It can optionally return a
	 * modified version of the portlet preferences if it contains references to
	 * data that no longer exists.
	 *
	 * @param  portletDataContext the context of the data deletion
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @return a modified version of the portlet preferences that should be
	 *         saved, or <code>null</code> if the data handler made no changes
	 *         to the portlet preferences
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	/**
	 * Returns a string of data to be placed in the &lt;portlet-data&gt; section
	 * of the LAR file. This data will be passed as the <code>data</code>
	 * parameter of <code>importData()</code>.
	 *
	 * @param  portletDataContext the context of the data export
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @return a string of data to be placed in the LAR, which can be, but not
	 *         limited to XML, or <code>null</code> if no portlet data is to be
	 *         written out
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public DataLevel getDataLevel();

	/**
	 * Returns an array of the portlet preferences that reference data. These
	 * preferences should only be updated if the referenced data is imported.
	 *
	 * @return an array of the portlet preferences that reference data
	 */
	public String[] getDataPortletPreferences();

	public StagedModelType[] getDeletionSystemEventStagedModelTypes();

	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet,
			boolean privateLayout)
		throws Exception;

	public default PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet, long plid,
			boolean privateLayout)
		throws Exception {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<>();

		// Setup

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portlet, false) >
					0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
				portlet.getRootPortletId(), false) > 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				portlet.getRootPortletId(), false) > 0)) {

			PortletDataHandlerControl[] portletDataHandlerControls = null;

			if (isDisplayPortlet()) {
				portletDataHandlerControls = getExportControls();
			}

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, portletDataHandlerControls, null, null));
		}

		// Archived setups

		if (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				-1, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
				portlet.getRootPortletId(), false) > 0) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"configuration-templates", true, false, null, null, null));
		}

		// User preferences

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				-1, PortletKeys.PREFS_OWNER_TYPE_USER, plid, portlet, false) >
					0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_USER,
				PortletKeys.PREFS_PLID_SHARED, portlet, false) > 0)) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	/**
	 * Returns an array of the controls defined for this data handler. These
	 * controls enable the developer to create fine grained controls over export
	 * behavior. The controls are rendered in the export UI.
	 *
	 * @return an array of the controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException;

	/**
	 * Returns an array of the metadata controls defined for this data handler.
	 * These controls enable the developer to create fine grained controls over
	 * export behavior of metadata such as tags, categories, ratings or
	 * comments. The controls are rendered in the export UI.
	 *
	 * @return an array of the metadata controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getExportMetadataControls()
		throws PortletDataException;

	/**
	 * Returns the number of entities defined for this data handler that are
	 * available for export according to the provided manifest summary, or
	 * <code>-1</code> if no entities are included in the manifest summary.
	 *
	 * @param  manifestSummary the manifest summary listing the number of
	 *         exportable entities
	 * @return the number of entities that are available for export according to
	 *         the manifest summary, or <code>-1</code> if no entities are
	 *         included in the manifest summary
	 */
	public long getExportModelCount(ManifestSummary manifestSummary);

	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary);

	public default PortletDataHandlerControl[] getImportConfigurationControls(
		String[] configurationPortletOptions) {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<>();

		// Setup

		if (ArrayUtil.contains(configurationPortletOptions, "setup")) {
			PortletDataHandlerControl[] portletDataHandlerControls = null;

			if (isDisplayPortlet()) {
				try {
					portletDataHandlerControls = getExportControls();
				}
				catch (PortletDataException pde) {
					portletDataHandlerControls =
						new PortletDataHandlerControl[0];
				}
			}

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, portletDataHandlerControls, null, null));
		}

		// Archived setups

		if (ArrayUtil.contains(
				configurationPortletOptions, "archived-setups")) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"configuration-templates", true, false, null, null, null));
		}

		// User preferences

		if (ArrayUtil.contains(
				configurationPortletOptions, "user-preferences")) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	/**
	 * Returns an array of the controls defined for this data handler. These
	 * controls enable the developer to create fine grained controls over import
	 * behavior. The controls are rendered in the import UI.
	 *
	 * @return an array of the controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException;

	/**
	 * Returns an array of the metadata controls defined for this data handler.
	 * These controls enable the developer to create fine grained controls over
	 * import behavior of metadata such as tags, categories, ratings or
	 * comments. The controls are rendered in the export UI.
	 *
	 * @return an array of the metadata controls defined for this data handler
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletDataHandlerControl[] getImportMetadataControls()
		throws PortletDataException;

	public String getPortletId();

	public int getRank();

	public String getSchemaVersion();

	public String getServiceName();

	/**
	 * Handles any special processing of the data when the portlet is imported
	 * into a new layout. Can optionally return a modified version of
	 * <code>preferences</code> to be saved in the new portlet.
	 *
	 * @param  portletDataContext the context of the data import
	 * @param  portletId the portlet ID of the portlet
	 * @param  portletPreferences the portlet preferences of the portlet
	 * @param  data the string data that was returned by
	 *         <code>exportData()</code>
	 * @return a modified version of the portlet preferences that should be
	 *         saved, or <code>null</code> if the data handler made no changes
	 *         to the portlet preferences
	 * @throws PortletDataException if a portlet data exception occurred
	 */
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException;

	public boolean isDataAlwaysStaged();

	public boolean isDataLocalized();

	public boolean isDataPortalLevel();

	public boolean isDataPortletInstanceLevel();

	public boolean isDataSiteLevel();

	public default boolean isDisplayPortlet() {
		if (isDataPortletInstanceLevel() &&
			!ArrayUtil.isEmpty(getDataPortletPreferences())) {

			return true;
		}

		return false;
	}

	/**
	 * Returns whether the data exported by this handler should be included by
	 * default when publishing to live. This should only be <code>true</code>
	 * for data that is meant to be managed in an staging environment such as
	 * CMS content, but not for data meant to be input by users such as wiki
	 * pages or message board posts.
	 *
	 * @return <code>true</code> if the data exported by this handler should be
	 *         included by default when publishing to live; <code>false</code>
	 *         otherwise
	 */
	public boolean isPublishToLiveByDefault();

	/**
	 * Returns <code>true</code> if the data handler stops operations and rolls
	 * back their transactions on operations throwing exceptions.
	 *
	 * @return <code>true</code> if the data handler stops operations and rolls
	 *         back their transactions on operations throwing exceptions;
	 *         <code>false</code> otherwise
	 */
	public default boolean isRollbackOnException() {

		// For now, we are going to throw an exception if one portlet data
		// handler has an exception to ensure that the transaction is rolled
		// back for data integrity. We may decide that this is not the best
		// behavior in the future because a bad plugin could prevent deletion of
		// groups.

		return true;
	}

	public boolean isSupportsDataStrategyCopyAsNew();

	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws PortletDataException;

	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException;

	public void setPortletId(String portletId);

	public void setRank(int rank);

	public default boolean validateSchemaVersion(String schemaVersion) {

		// Major version has to be identical

		Version currentVersion = Version.getInstance(getSchemaVersion());
		Version importedVersion = Version.getInstance(schemaVersion);

		if (!Objects.equals(
				currentVersion.getMajor(), importedVersion.getMajor())) {

			return false;
		}

		// Imported minor version should be less than or equal to the current
		// minor version

		int currentMinorVersion = GetterUtil.getInteger(
			currentVersion.getMinor(), -1);
		int importedMinorVersion = GetterUtil.getInteger(
			importedVersion.getMinor(), -1);

		if (((currentMinorVersion == -1) && (importedMinorVersion == -1)) ||
			(currentMinorVersion < importedMinorVersion)) {

			return false;
		}

		// Import should be compatible with all minor versions if previous
		// validations pass

		return true;
	}

}