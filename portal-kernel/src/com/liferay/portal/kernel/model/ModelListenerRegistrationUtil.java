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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Peter Fellwock
 */
public class ModelListenerRegistrationUtil {

	public static <T> ModelListener<T>[] getModelListeners(Class<T> clazz) {
		List<ModelListener<?>> modelListeners = _modelListeners.get(
			clazz.getName());

		if (modelListeners == null) {
			return new ModelListener[0];
		}

		return modelListeners.toArray(new ModelListener[0]);
	}

	public static void register(ModelListener<?> modelListener) {
		Class<?> clazz = modelListener.getClass();

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			ModelListener.class.getName(), modelListener);

		_serviceRegistrations.put(clazz.getName(), serviceRegistration);
	}

	public static void unregister(ModelListener<?> modelListener) {
		Class<?> clazz = modelListener.getClass();

		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(clazz.getName());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private ModelListenerRegistrationUtil() {
	}

	private static final ConcurrentMap<String, List<ModelListener<?>>>
		_modelListeners = new ConcurrentHashMap<>();
	private static final Map<String, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private static final ServiceTracker<ModelListener<?>, ModelListener<?>>
		_serviceTracker;

	private static class ModelListenerTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ModelListener<?>, ModelListener<?>> {

		@Override
		public ModelListener<?> addingService(
			ServiceReference<ModelListener<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ModelListener<?> modelListener = registry.getService(
				serviceReference);

			String modelClassName = (String)serviceReference.getProperty(
				"model.class.name");

			if (modelClassName == null) {
				Class<?> modelClass = _getModelClass(modelListener);

				if (modelClass == null) {
					throw new IllegalStateException(
						"Cannot find model class name for model listener " +
							modelListener.getClass());
				}

				modelClassName = modelClass.getName();
			}

			List<ModelListener<?>> modelListeners = _modelListeners.get(
				modelClassName);

			if (modelListeners == null) {
				modelListeners = new ArrayList<>();

				List<ModelListener<?>> previousModelListeners =
					_modelListeners.putIfAbsent(modelClassName, modelListeners);

				if (previousModelListeners != null) {
					modelListeners = previousModelListeners;
				}
			}

			modelListeners.add(modelListener);

			return modelListener;
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelListener<?>> serviceReference,
			ModelListener<?> modelListener) {
		}

		@Override
		public void removedService(
			ServiceReference<ModelListener<?>> serviceReference,
			ModelListener<?> modelListener) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String modelClassName = (String)serviceReference.getProperty(
				"model.class.name");

			if (modelClassName == null) {
				Class<?> modelClass = _getModelClass(modelListener);

				modelClassName = modelClass.getName();
			}

			List<ModelListener<?>> modelListeners = _modelListeners.get(
				modelClassName);

			if (modelListeners != null) {
				modelListeners.remove(modelListener);

				if (modelListeners.isEmpty()) {
					_modelListeners.remove(modelClassName);
				}
			}
		}

		private Class<?> _getGenericSuperType(Class<?> clazz) {
			try {
				ParameterizedType parameterizedType =
					(ParameterizedType)clazz.getGenericSuperclass();

				Type[] types = parameterizedType.getActualTypeArguments();

				if (types.length > 0) {
					return (Class<?>)types[0];
				}
			}
			catch (Throwable t) {
			}

			return null;
		}

		private Class<?> _getModelClass(ModelListener<?> modelListener) {
			Class<?> clazz = modelListener.getClass();

			if (ProxyUtil.isProxyClass(clazz)) {
				InvocationHandler invocationHandler =
					ProxyUtil.getInvocationHandler(modelListener);

				if (invocationHandler instanceof ClassLoaderBeanHandler) {
					ClassLoaderBeanHandler classLoaderBeanHandler =
						(ClassLoaderBeanHandler)invocationHandler;

					Object bean = classLoaderBeanHandler.getBean();

					clazz = bean.getClass();
				}
			}

			return _getGenericSuperType(clazz);
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + ModelListener.class.getName() + ")");

		_serviceTracker = registry.trackServices(
			filter, new ModelListenerTrackerCustomizer());

		_serviceTracker.open();
	}

}