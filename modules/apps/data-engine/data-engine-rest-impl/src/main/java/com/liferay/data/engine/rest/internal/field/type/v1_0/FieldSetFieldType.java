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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.field.type.BaseFieldType;
import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.icon=icon-font",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Fieldset/Fieldset.es",
		"data.engine.field.type.system=true"
	},
	service = FieldType.class
)
public class FieldSetFieldType extends BaseFieldType {

	@Override
	public String getName() {
		return "fieldset";
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		List<Object> nestedFieldContexts = CustomPropertiesUtil.getValue(
			spiDataDefinitionField.getCustomProperties(), "nestedFields");

		if (nestedFieldContexts != null) {
			context.put(
				"columnSize",
				_getColumnSize(
					nestedFieldContexts.size(),
					CustomPropertiesUtil.getString(
						spiDataDefinitionField.getCustomProperties(),
						"orientation", "horizontal")));
			context.put("nestedFields", nestedFieldContexts);
		}

		if (context.containsKey("label")) {
			context.put("showLabel", true);
		}
	}

	private int _getColumnSize(int nestedFieldsSize, String orientation) {
		if (Objects.equals(orientation, "vertical")) {
			return _COLUMN_SIZE_FULL;
		}

		if (nestedFieldsSize == 0) {
			return 0;
		}

		return _COLUMN_SIZE_FULL / nestedFieldsSize;
	}

	private static final int _COLUMN_SIZE_FULL = 12;

}