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

package com.liferay.portal.async.advice.internal;

import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistry;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.async.advice.internal.configuration.AsyncAdviceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.portal.async.advice.internal.configuration.AsyncAdviceConfiguration",
	immediate = true, service = ChainableMethodAdvice.class
)
public class AsyncAdvice extends ChainableMethodAdvice {

	@Activate
	@Modified
	public void activate(Map<String, String> properties) {
		_asyncAdviceConfiguration = ConfigurableUtil.createConfigurable(
			AsyncAdviceConfiguration.class, properties);

		String[] targetClassNamesToDestinationNames =
			_asyncAdviceConfiguration.targetClassNamesToDestinationNames();

		if (targetClassNamesToDestinationNames != null) {
			Map<String, String> destinationNames = new HashMap<>();

			for (String targetClassNameToDestinationName :
					targetClassNamesToDestinationNames) {

				int index = targetClassNameToDestinationName.indexOf(
					CharPool.EQUAL);

				if (index <= 0) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid target class name to destination name \"" +
								targetClassNameToDestinationName + "\"");
					}
				}
				else {
					destinationNames.put(
						targetClassNameToDestinationName.substring(0, index),
						targetClassNameToDestinationName.substring(index + 1));
				}
			}

			if (!destinationNames.isEmpty()) {
				_destinationNames = destinationNames;
			}
		}
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		// map check targetClass

		if (method.getReturnType() != void.class) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Async annotation on method " + method.getName() +
						" does not return void");
			}

			return null;
		}

		String name = method.getName();
		Class<?> returnType = method.getReturnType();
		
		if (name.startsWith("add") &&
			BaseModel.class.isAssignableFrom(returnType) &&
			ShardedModel.class.isAssignableFrom(returnType)) {

			return new AddMethodHandler();
		}

		String destinationName = null;

		if (_destinationNames != null) {
			destinationName = _destinationNames.get(targetClass.getName());
		}

		if (destinationName == null) {
			return _asyncAdviceConfiguration.defaultDestinationName();
		}

		return destinationName;
	}

	@FunctionalInterface
	private interface CTMethodHandler {

		public void handle(
				AopMethodInvocation aopMethodInvocation, BaseModel<?> baseModel)
			throws Throwable;

	}

	private class AddMethodHandler implements CTMethodHandler {

		@Override
		public void handle(
				AopMethodInvocation aopMethodInvocation, BaseModel<?> baseModel)
			throws Throwable {

			String modelClassName = baseModel.getModelClassName();

			ShardedModel shardedModel = (ShardedModel)baseModel;

			if (!_ctEngineManager.isChangeTrackingEnabled(
					shardedModel.getCompanyId()) ||
				!_ctEngineManager.isChangeTrackingSupported(
					shardedModel.getCompanyId(),
					(Class<? extends BaseModel<?>>)baseModel.getModelClass())) {

				return;
			}

			if (_ctManager.isModelUpdateInProgress()) {
				return;
			}

			CTDefinition<?, ?> ctDefinition = _ctDefinitionRegistry.getCTDefinitionOptionalByResourceClassName(baseModel.getModelClassName()).get();

			try {
				_ctManager.registerModelChange(
					journalArticle.getCompanyId(),
					PrincipalThreadLocal.getUserId(),
					_portal.getClassNameId(JournalArticle.class.getName()),
					ctDefinition.getResourceEntityIdFromVersionEntityFunction().apply(baseModel), ctDefinition,
					changeType, force);
	
				_ctManager.registerRelatedChanges(
					journalArticle.getCompanyId(), PrincipalThreadLocal.getUserId(),
					_portal.getClassNameId(JournalArticle.class.getName()),
					journalArticle.getId(), force);
			}
			catch (CTEngineException ctee) {
				if (ctee instanceof CTEntryCTEngineException) {
					if (_log.isWarnEnabled()) {
						_log.warn(ctee.getMessage());
					}
				}
				else {
					throw ctee;
				}
			}
		}

	}

	@Override
	protected void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {

		CTMethodHandler ctMethodHandler =
			aopMethodInvocation.getAdviceMethodContext();

		ctMethodHandler.handle(aopMethodInvocation, (BaseModel<?>) result);
	}

	private static final Log _log = LogFactoryUtil.getLog(AsyncAdvice.class);

	private AsyncAdviceConfiguration _asyncAdviceConfiguration;
	private Map<String, String> _destinationNames;

	@Reference
	private CTManager _ctManager;
	@Reference
	private CTEngineManager _ctEngineManager;
	@Reference
	private CTDefinitionRegistry _ctDefinitionRegistry;
	@Reference
	private MessageBus _messageBus;

}