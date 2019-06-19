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

package com.liferay.change.tracking.internal.persistence;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTAdapter;
import com.liferay.portal.kernel.change.tracking.persistence.CTPersistenceHelper;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTPersistenceHelper.class)
public class CTPersistenceHelperImpl implements CTPersistenceHelper {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext, CTAdapter.class,
			new CTAdapterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public Object[] appendContextFinderArgs(
		Object[] finderArgs, Class<? extends BaseModel<?>> modelClass) {

		return finderArgs;
	}

	@Override
	public void appendContextSQL(StringBundler sb) {
	}

	@Deactivate
	public void deactivate() {
		_serviceTracker.close();
	}

	@Override
	public boolean isValidFinderResult(BaseModel<?> baseModel) {
		return true;
	}

	@Override
	public void setContext(
		Session session, BaseModel<?> baseModel,
		Class<? extends BaseModel<?>> modelClass) {
	}

	@Override
	public void setContexts(
		Session session, List<BaseModel<?>> baseModels,
		Class<? extends BaseModel<?>> modelClass) {
	}

	private static final Map<Class<? extends BaseModel<?>>, CTAdapter<?, ?>>
		_ctAdapters = new ConcurrentHashMap<>();

	private BundleContext _bundleContext;
	private ServiceTracker<?, ?> _serviceTracker;

	private static class CTAdapterHolder {

		private CTAdapterHolder(
			CTAdapter<?, ?> ctAdapter,
			ServiceRegistration<ModelListener>
				modelListenerServiceRegistration) {

			_ctAdapter = ctAdapter;
			_modelListenerServiceRegistration =
				modelListenerServiceRegistration;
		}

		private final CTAdapter<?, ?> _ctAdapter;
		private final ServiceRegistration<ModelListener>
			_modelListenerServiceRegistration;

	}

	private static class CTModelListener<T extends BaseModel<T>>
		extends BaseModelListener<T> {

		@Override
		public void onAfterRemove(T model) throws ModelListenerException {

			// delete all the contexts

		}

		@Override
		public void onAfterUpdate(T model) throws ModelListenerException {

			// set the context

		}

		@Override
		public void onBeforeUpdate(T model) throws ModelListenerException {

			// maybe create a context
			// fetch old value
			// unset the context fields

		}

		private CTModelListener(CTAdapter<?, ?> ctAdapter) {
			_ctAdapter = ctAdapter;
		}

		private final CTAdapter<?, ?> _ctAdapter;

	}

	private class CTAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<CTAdapter, CTAdapterHolder> {

		@Override
		public CTAdapterHolder addingService(
			ServiceReference<CTAdapter> serviceReference) {

			CTAdapter<?, ?> ctAdapter = _bundleContext.getService(
				serviceReference);

			Class<? extends BaseModel<?>> modelClass =
				ctAdapter.getModelClass();

			ServiceRegistration<ModelListener> serviceRegistration =
				_bundleContext.registerService(
					ModelListener.class, new CTModelListener<>(ctAdapter),
					MapUtil.singletonDictionary(
						"model.class.name", modelClass.getName()));

			_ctAdapters.put(modelClass, ctAdapter);

			return new CTAdapterHolder(ctAdapter, serviceRegistration);
		}

		@Override
		public void modifiedService(
			ServiceReference<CTAdapter> serviceReference,
			CTAdapterHolder ctAdapterHolder) {
		}

		@Override
		public void removedService(
			ServiceReference<CTAdapter> serviceReference,
			CTAdapterHolder ctAdapterHolder) {

			ctAdapterHolder._modelListenerServiceRegistration.unregister();

			_ctAdapters.remove(ctAdapterHolder._ctAdapter.getModelClass());

			_bundleContext.ungetService(serviceReference);
		}

	}

}