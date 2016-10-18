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

package com.liferay.exportimport.staged.model.reference.processor;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.osgi.util.StringPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Daniel Kocsis
 */
public class StagedModelReferenceProcessorRegistryUtil {

	public static List<StageModelReferenceProcessor<?, ?>>
		getStagedModelReferenceProcessors() {

		return _instance._getStagedModelReferenceProcessors();
	}

	public static List<StageModelReferenceProcessor<?, ?>>
		getStagedModelReferenceProcessors(String className) {

		return _instance._getStagedModelReferenceProcessors(className);
	}

	private StagedModelReferenceProcessorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			StagedModelReferenceProcessorRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, StageModelReferenceProcessor.class,
			new StagedModelReferenceProcessorServiceTrackerCustomizer());
	}

	private List<StageModelReferenceProcessor<?, ?>>
	_getStagedModelReferenceProcessors() {

		List<StageModelReferenceProcessor<?, ?>>
			allExportImportContentProcessors = new ArrayList<>();

		for (List<StageModelReferenceProcessor<?, ?>>
			exportImportContentProcessors :
				_stagedModelReferenceProcessors.values()) {

			allExportImportContentProcessors.addAll(
				exportImportContentProcessors);
		}

		return allExportImportContentProcessors;
	}

	private List<StageModelReferenceProcessor<?, ?>>
		_getStagedModelReferenceProcessors(String className) {

		return _stagedModelReferenceProcessors.get(className);
	}

	private static final StagedModelReferenceProcessorRegistryUtil _instance =
		new StagedModelReferenceProcessorRegistryUtil();

	private final BundleContext _bundleContext;
	private final Map<String, ArrayList<StageModelReferenceProcessor<?, ?>>>
		_stagedModelReferenceProcessors = new ConcurrentHashMap<>();
	private final ServiceTracker
		<StageModelReferenceProcessor, StageModelReferenceProcessor>
			_serviceTracker;

	private class StagedModelReferenceProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<StageModelReferenceProcessor, StageModelReferenceProcessor> {

		@Override
		public StageModelReferenceProcessor addingService(
			ServiceReference<StageModelReferenceProcessor> serviceReference) {

			StageModelReferenceProcessor stageModelReferenceProcessor =
				_bundleContext.getService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				ArrayList<StageModelReferenceProcessor<?, ?>>
					stageModelReferenceProcessors =
						_stagedModelReferenceProcessors.get(modelClassName);

				if (stageModelReferenceProcessors == null) {
					stageModelReferenceProcessors = new ArrayList<>();
				}

				int capacity = stageModelReferenceProcessors.size() + 1;

				stageModelReferenceProcessors.ensureCapacity(capacity);

				stageModelReferenceProcessors.add(stageModelReferenceProcessor);

				_stagedModelReferenceProcessors.put(
					modelClassName, stageModelReferenceProcessors);
			}

			return stageModelReferenceProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<StageModelReferenceProcessor> serviceReference,
			StageModelReferenceProcessor stageModelReferenceProcessor) {

			removedService(serviceReference, stageModelReferenceProcessor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<StageModelReferenceProcessor> serviceReference,
			StageModelReferenceProcessor stageModelReferenceProcessor) {

			_bundleContext.ungetService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				List<StageModelReferenceProcessor<?, ?>>
					stageModelReferenceProcessors =
						_stagedModelReferenceProcessors.get(modelClassName);

				stageModelReferenceProcessors.remove(
					stageModelReferenceProcessor);

				if (stageModelReferenceProcessors.isEmpty()) {
					_stagedModelReferenceProcessors.remove(modelClassName);
				}
			}
		}

	}

}