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

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Zoltan Csaszi
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateLanguageSettings();
	}

	private String _checkLocales(UnicodeProperties typeSettingsProperties) {
		String[] locales = ArrayUtil.filter(
			StringUtil.split(
				typeSettingsProperties.getProperty(PropsKeys.LOCALES)),
			inheritLocale -> LanguageUtil.isAvailableLocale(
				LocaleUtil.fromLanguageId(inheritLocale)));

		return StringUtil.merge(locales);
	}

	private void _updateLanguageSettings() throws Exception {
		try (PreparedStatement selectStatement = connection.prepareStatement(
				"select groupId, typeSettings from Group_ where typeSettings " +
					"like '%inheritLocales=true%'")) {

			try (ResultSet rs = selectStatement.executeQuery()) {
				while (rs.next()) {
					String typeSettings = rs.getString("typeSettings");

					UnicodeProperties typeSettingsProperties =
						new UnicodeProperties(true);

					typeSettingsProperties.load(typeSettings);

					_updateTypeSettings(
						rs.getLong("groupId"), typeSettingsProperties);
				}
			}
		}
	}

	private void _updateTypeSettings(
			long groupId, UnicodeProperties typeSettingsProperties)
		throws SQLException {

		typeSettingsProperties.put(
			PropsKeys.LOCALES, _checkLocales(typeSettingsProperties));

		try (PreparedStatement updateStatement = connection.prepareStatement(
				"update Group_ set typeSettings = ? where groupId = ?")) {

			updateStatement.setString(1, typeSettingsProperties.toString());
			updateStatement.setLong(2, groupId);

			updateStatement.executeUpdate();
		}
	}

}