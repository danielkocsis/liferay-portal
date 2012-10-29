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
import com.liferay.portal.model.User;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Vilmos Papp
 */
public class ShardUserAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		String methodName = method.getName();

		Object[] arguments = methodInvocation.getArguments();

		if ((arguments == null) || (arguments.length == 0)) {
			return methodInvocation.proceed();
		}

		Object argument1 = arguments[0];
		Object argument2 = null;
		if (arguments.length > 1) {
			argument2 = arguments[1];
		}

		long companyId = -1;

		if (argument1 instanceof Long) {
			if (argument2 instanceof Long) {
				if (methodName.equals("addUser") ||
					methodName.equals("addUserWithWorkflow") ||
					methodName.equals("getUserById") ||
					methodName.equals("updateIncompleteUser")) {

					companyId = (Long)argument2;
				}
				else if (methodName.equals("authenticateByUserId")) {
					companyId = (Long)argument1;
				}
			}
			else if (methodName.equals("addDefaultAdminUser") ||
					methodName.equals("authenticateByEmailAddress") ||
					methodName.equals("authenticateByScreenName") ||
					methodName.equals("authenticateForBasic") ||
					methodName.equals("authenticateForDigest") ||
					methodName.equals("checkLoginFailureByEmailAddress") ||
					methodName.equals("checkLoginFailureByScreenName") ||
					methodName.equals("decryptUserId") ||
					methodName.equals("fetchUserByEmailAddress") ||
					methodName.equals("fetchUserByScreenName") ||
					methodName.equals("getCompanyUsers") ||
					methodName.equals("getCompanyUsersCount") ||
					methodName.equals("getDefaultUser") ||
					methodName.equals("getDefaultUserId") ||
					methodName.equals("getUserByEmailAddress") ||
					methodName.equals("getUserByFacebookId") ||
					methodName.equals("getUserByOpenId") ||
					methodName.equals("getUserByScreenName") ||
					methodName.equals("getUserIdByEmailAddress") ||
					methodName.equals("getUserIdByScreenName") ||
					methodName.equals("hasRoleUser") ||
					methodName.equals("loadGetDefaultUser") ||
					methodName.equals("search") ||
					methodName.equals("searchCount") ||
					methodName.equals("sendPassword") ||
					methodName.equals("updateLockoutByEmailAddress") ||
					methodName.equals("updateLockoutByScreenName")) {

					companyId = (Long)argument1;
			}
		}
		else if ((argument1 instanceof String) && (argument2 instanceof Long)) {
			if (methodName.equals("getUserByUuidAndCompanyId")) {
				companyId = (Long)argument2;
			}
		}
		else if (argument1 instanceof User) {
			if (methodName.equals("addUser") ||
				methodName.equals("checkLockout") ||
				methodName.equals("checkLoginFailure") ||
				methodName.equals("checkPasswordExpired") ||
				methodName.equals("completeUserRegistration") ||
				methodName.equals("deleteUser") ||
				methodName.equals("isPasswordExpired") ||
				methodName.equals("isPasswordExpiringSoon") ||
				methodName.equals("sendEmailAddressVerification") ||
				methodName.equals("updateLockout") ||
				methodName.equals("updateUser")) {

				User user = (User)argument1;

				companyId = user.getCompanyId();
			}
		}
		else if (argument2 instanceof User) {
			if (methodName.equals("updateAsset")) {
				User user = (User)argument1;

				companyId = user.getCompanyId();
			}
		}

		if (companyId <= 0) {
			return methodInvocation.proceed();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Setting company service to shard of companyId " + companyId +
					" for " + methodInvocation.toString());
		}

		Object returnValue = null;

		_shardAdvice.pushCompanyService(companyId);

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

	private static Log _log = LogFactoryUtil.getLog(ShardPortletAdvice.class);

	private ShardAdvice _shardAdvice;

}