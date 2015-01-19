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

package com.liferay.portal.adapter;

import com.liferay.portal.kernel.adapter.ModelAdapterBuilder;
import com.liferay.portal.kernel.adapter.ModelAdapterBuilderLocator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerMapModelAdapterBuilderLocator<T, V>
	implements ModelAdapterBuilderLocator<T, V>, Closeable {

	public ServiceTrackerMapModelAdapterBuilderLocator() {
		_modelAdaptorsMap.open();
	}

	@Override
	public void close() throws IOException {
		_modelAdaptorsMap.close();
	}

	@Override
	public ModelAdapterBuilder<T, V> locate(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return _modelAdaptorsMap.getService(
			getKey(adapteeModelClass, adaptedModelClass));
	}

	private String getKey(
		Class<T> adapteeModelClass, Class<V> adaptedModelClass) {

		return adapteeModelClass.getName() + "->" + adaptedModelClass.getName();
	}

	private final ServiceTrackerMap<String, ModelAdapterBuilder>
		_modelAdaptorsMap =
			ServiceTrackerCollections.singleValueMap(
				ModelAdapterBuilder.class, null,
				new ServiceReferenceMapper<String, ModelAdapterBuilder>() {

					@Override
					public void map(
						ServiceReference<ModelAdapterBuilder> serviceReference,
						Emitter<String> emitter) {

						Registry registry = RegistryUtil.getRegistry();

						ModelAdapterBuilder modelAdapterBuilder =
							registry.getService(serviceReference);

						Class<?> modelAdapterBuilderClass =
							modelAdapterBuilder.getClass();

						Type[] genericInterfaces =
							modelAdapterBuilderClass.getGenericInterfaces();

						ParameterizedType type =
							(ParameterizedType)genericInterfaces[0];

						Type[] typeArguments = type.getActualTypeArguments();

						Class adapteeModelClass = (Class)typeArguments[0];
						Class adaptedModelClass = (Class)typeArguments[1];

						emitter.emit(
							getKey(adapteeModelClass, adaptedModelClass));
					}
				});

}