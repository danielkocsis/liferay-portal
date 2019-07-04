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

package com.liferay.change.tracking.internal.display;

import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistry;
import com.liferay.change.tracking.display.CTDisplayHelper;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.display.CTDisplayAdapter;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTDisplayHelper.class)
public class CTDisplayHelperImpl implements CTDisplayHelper {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CTDisplayAdapter.class, null,
			(serviceReference, emitter) -> {
				CTDisplayAdapter<?> ctDisplayAdapter = bundleContext.getService(
					serviceReference);

				try {
					emitter.emit(
						_classNameLocalService.getClassNameId(
							ctDisplayAdapter.getModelClass()));
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	public String getDisplayName(long classNameId, Locale locale) {
		CTDisplayAdapter<?> ctDisplayAdapter = _serviceTrackerMap.getService(
			classNameId);

		if (ctDisplayAdapter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No CTDisplayAdapter is registered for classNameId " +
						classNameId);
			}

			ClassName className = _classNameLocalService.fetchClassName(
				classNameId);

			if (className == null) {
				return String.valueOf(classNameId);
			}

			return className.getValue();
		}

		return ctDisplayAdapter.getModelDisplayName(locale);
	}

	@Override
	public <T extends BaseModel<T>> String getDisplayName(
		T model, Locale locale) {

		CTDisplayAdapter<T> ctDisplayAdapter = _serviceTrackerMap.getService(
			_classNameLocalService.getClassNameId(model.getModelClass()));

		if (ctDisplayAdapter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No CTDisplayAdapter is registered for " +
						model.getModelClass());
			}

			return String.valueOf(model.getPrimaryKeyObj());
		}

		return ctDisplayAdapter.getModelDisplayName(model, locale);
	}

	@Override
	public String getDisplayName(CTEntry ctEntry, Locale locale) {
		CTDisplayAdapter<?> ctDisplayAdapter = _serviceTrackerMap.getService(
			ctEntry.getModelClassNameId());

		if (ctDisplayAdapter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No CTDisplayAdapter is registered for " +
						ctEntry.getModelClassNameId());
			}

			return String.valueOf(ctEntry.getModelClassPK());
		}

		//return ctDisplayAdapter.getModelDisplayName(model, locale);

		return StringPool.BLANK;
	}

	@Override
	public Map<Long, String> getDisplayNames(Locale locale) {
		Collection<CTDisplayAdapter> values = _serviceTrackerMap.values();

		Map<Long, String> displayNames = new HashMap<>(values.size());

		for (CTDisplayAdapter<?> ctDisplayAdapter : values) {
			displayNames.put(
				_classNameLocalService.getClassNameId(
					ctDisplayAdapter.getModelClass()),
				ctDisplayAdapter.getModelDisplayName(locale));
		}

		List<CTDefinition<?, ?>> ctDefinitions =
			_ctDefinitionRegistry.getAllCTDefinitions();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, CTDisplayHelperImpl.class);

		for (CTDefinition<?, ?> ctDefinition : ctDefinitions) {
			String translation = ResourceBundleUtil.getString(
				resourceBundle, ctDefinition.getContentTypeLanguageKey());

			displayNames.put(
				_classNameLocalService.getClassNameId(
					ctDefinition.getVersionEntityClass()),
				translation);
		}

		return displayNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTDisplayHelperImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTDefinitionRegistry _ctDefinitionRegistry;

	@Reference
	private Language _language;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private ServiceTrackerMap<Long, CTDisplayAdapter> _serviceTrackerMap;

}