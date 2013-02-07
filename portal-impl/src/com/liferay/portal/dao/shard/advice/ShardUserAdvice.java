/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.shard.advice;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.User;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author Vilmos Papp
 */
public class ShardUserAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();

		String methodName = method.getName();

		Object[] arguments = methodInvocation.getArguments();

		Class<?>[] parameterTypes = method.getParameterTypes();

		if ((arguments == null) || (arguments.length == 0)) {
			return methodInvocation.proceed();
		}

		Object argument1 = arguments[0];
		Object argument2 = null;

		if (arguments.length > 1) {
			argument2 = arguments[1];
		}

		long companyId = -1;

		try {
			if (ArrayUtil.contains(parameterTypes, User.class)) {
				int pos = ArrayUtils.indexOf(parameterTypes, User.class);

				User user = (User)arguments[pos];

				companyId = user.getCompanyId();
			}
			else if (methodName.equals("addUser") &&
					 !parameterTypes[0].equals(User.class)) {

				companyId = (Long)argument2;
			}
			else if (methodName.equals("getUserById") &&
					 Arrays.equals(parameterTypes, _TYPES_L_L)) {

				companyId = (Long)argument2;
			}
			else if (methodName.equals("hasRoleUser") &&
					 !Arrays.equals(parameterTypes, _TYPES_L_L)) {

				companyId = (Long)argument1;
			}
			else if (_adviceMethodNamesArgument1.contains(methodName)) {
				companyId = (Long)argument1;

			}
			else if (_adviceMethodNamesArgument2.contains(methodName)) {
				companyId = (Long)argument2;
			}
		}
		catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error(e.getMessage());
			}
		}

		if (companyId <= 0) {
			return methodInvocation.proceed();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Setting company service to shard of companyId " + companyId +
					" for " + methodInvocation.toString());
		}

		_shardAdvice.pushCompanyService(companyId);

		Object returnValue = null;

		try {
			returnValue = methodInvocation.proceed();
		}
		finally {
			_shardAdvice.popCompanyService();
		}

		return returnValue;
	}

	public void setShardAdvice(ShardAdvice shardAdvice) {
		_shardAdvice = shardAdvice;
	}

	private static final Class<?>[] _TYPES_L_L = {Long.TYPE, Long.TYPE};

	private static Log _log = LogFactoryUtil.getLog(ShardUserAdvice.class);

	private static Set<String> _adviceMethodNamesArgument1 =
		new HashSet<String>();

	private static Set<String> _adviceMethodNamesArgument2 =
		new HashSet<String>();

	static {

		// Initialize method names for companyId in the 1st arg

		_adviceMethodNamesArgument1.add("addDefaultAdminUser");
		_adviceMethodNamesArgument1.add("authenticateByEmailAddress");
		_adviceMethodNamesArgument1.add("authenticateByScreenName");
		_adviceMethodNamesArgument1.add("authenticateByUserId");
		_adviceMethodNamesArgument1.add("authenticateForBasic");
		_adviceMethodNamesArgument1.add("authenticateForDigest");
		_adviceMethodNamesArgument1.add("checkLoginFailureByEmailAddress");
		_adviceMethodNamesArgument1.add("checkLoginFailureByScreenName");
		_adviceMethodNamesArgument1.add("fetchUserByEmailAddress");
		_adviceMethodNamesArgument1.add("fetchUserByScreenName");
		_adviceMethodNamesArgument1.add("getUserByEmailAddress");
		_adviceMethodNamesArgument1.add("getUserByFacebookId");
		_adviceMethodNamesArgument1.add("getUserByOpenId");
		_adviceMethodNamesArgument1.add("getUserByScreenName");
		_adviceMethodNamesArgument1.add("getUserIdByEmailAddress");
		_adviceMethodNamesArgument1.add("getUserIdByScreenName");
		_adviceMethodNamesArgument1.add("getCompanyUsers");
		_adviceMethodNamesArgument1.add("getCompanyUsersCount");
		_adviceMethodNamesArgument1.add("getDefaultUser");
		_adviceMethodNamesArgument1.add("getDefaultUserId");
		_adviceMethodNamesArgument1.add("decryptUserId");
		_adviceMethodNamesArgument1.add("loadGetDefaultUser");
		_adviceMethodNamesArgument1.add("search");
		_adviceMethodNamesArgument1.add("searchCount");
		_adviceMethodNamesArgument1.add("sendPassword");
		_adviceMethodNamesArgument1.add("updateLockoutByEmailAddress");
		_adviceMethodNamesArgument1.add("updateLockoutByScreenName");

		// Initialize method names for companyId in the 2nd arg

		_adviceMethodNamesArgument2.add("getUserByUuidAndCompanyId");
		_adviceMethodNamesArgument2.add("addUserWithWorkflow");
		_adviceMethodNamesArgument2.add("updateIncompleteUser");
	}

	private ShardAdvice _shardAdvice;

}