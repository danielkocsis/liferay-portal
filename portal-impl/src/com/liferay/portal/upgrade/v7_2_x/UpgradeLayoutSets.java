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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Daniel Kocsis
 */
public class UpgradeLayoutSets extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_createLayoutSetVersions();

		_incrementCounters();
	}

	private void _createLayoutSetVersions() throws SQLException {
		String sql = "select * from LayoutSet";

		StringBundler sb = new StringBundler(6);

		sb.append("insert into LayoutSetVersion (layoutSetVersionId, ");
		sb.append("version, layoutSetId, groupId, companyId, createDate, ");
		sb.append("modifiedDate, privateLayout, logoId, themeId, ");
		sb.append("colorSchemeId, css, pageCount, settings_, ");
		sb.append("layoutSetPrototypeUuid, layoutSetPrototypeLinkEnabled) ");
		sb.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sql);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				boolean privateLayout = rs.getBoolean("privateLayout");
				long logoId = rs.getLong("logoId");
				String themeId = rs.getString("themeId");
				String colorSchemeId = rs.getString("colorSchemeId");
				String css = rs.getString("css");
				int pageCount = rs.getInt("pageCount");
				String settings_ = rs.getString("settings_");
				String layoutSetPrototypeUuid = rs.getString(
					"layoutSetPrototypeUuid");
				boolean layoutSetPrototypeLinkEnabled = rs.getBoolean(
					"layoutSetPrototypeLinkEnabled");

				ps2.setLong(1, layoutSetId);
				ps2.setInt(2, 1);
				ps2.setLong(3, layoutSetId);
				ps2.setLong(4, groupId);
				ps2.setLong(5, companyId);
				ps2.setTimestamp(6, createDate);
				ps2.setTimestamp(7, modifiedDate);
				ps2.setBoolean(8, privateLayout);
				ps2.setLong(9, logoId);
				ps2.setString(10, themeId);
				ps2.setString(11, colorSchemeId);
				ps2.setString(12, css);
				ps2.setInt(13, pageCount);
				ps2.setString(14, settings_);
				ps2.setString(15, layoutSetPrototypeUuid);
				ps2.setBoolean(16, layoutSetPrototypeLinkEnabled);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private void _incrementCounter(String name, int currentId)
		throws SQLException {

		String sql = "insert into Counter (name, currentId) values (?,?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setInt(2, currentId);

			ps.executeUpdate();
		}
	}

	private void _incrementCounters() throws SQLException {
		String sql = "select max(layoutSetId) from LayoutSet";

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int latestPrimaryKeyValue = rs.getInt(1);

				_incrementCounter(
					LayoutSet.class.getName(), latestPrimaryKeyValue);
			}
		}

		sql = "select max(layoutSetVersionId) from LayoutSetVersion";

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int latestPrimaryKeyValue = rs.getInt(1);

				_incrementCounter(
					LayoutSetVersion.class.getName(), latestPrimaryKeyValue);
			}
		}
	}

}