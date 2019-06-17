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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.PortletServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.net.ConnectException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the HTTP utility for the
 * <code>PortletServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletServiceSoap
 * @generated
 */
@ProviderType
public class PortletServiceHttp {

	public static com.liferay.portal.kernel.json.JSONArray getWARPortlets(
		HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				PortletServiceUtil.class, "getWARPortlets",
				_getWARPortletsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.json.JSONArray)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			if (se.getCause() instanceof ConnectException) {
				_log.error("Connection error: " + se.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
			else {
				_log.error(se, se);
			}

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.Portlet updatePortlet(
			HttpPrincipal httpPrincipal, long companyId, String portletId,
			String roles, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				PortletServiceUtil.class, "updatePortlet",
				_updatePortletParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, portletId, roles, active);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.Portlet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			if (se.getCause() instanceof ConnectException) {
				_log.error("Connection error: " + se.getMessage());

				if (_log.isDebugEnabled()) {
					_log.debug(se, se);
				}
			}
			else {
				_log.error(se, se);
			}

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletServiceHttp.class);

	private static final Class<?>[] _getWARPortletsParameterTypes0 =
		new Class[] {};
	private static final Class<?>[] _updatePortletParameterTypes1 =
		new Class[] {long.class, String.class, String.class, boolean.class};

}