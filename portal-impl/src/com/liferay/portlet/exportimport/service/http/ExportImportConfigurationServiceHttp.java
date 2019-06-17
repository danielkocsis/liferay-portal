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

package com.liferay.portlet.exportimport.service.http;

import com.liferay.exportimport.kernel.service.ExportImportConfigurationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.net.ConnectException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the HTTP utility for the
 * <code>ExportImportConfigurationServiceUtil</code> service
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
 * @see ExportImportConfigurationServiceSoap
 * @generated
 */
@ProviderType
public class ExportImportConfigurationServiceHttp {

	public static void deleteExportImportConfiguration(
			HttpPrincipal httpPrincipal, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportConfigurationServiceUtil.class,
				"deleteExportImportConfiguration",
				_deleteExportImportConfigurationParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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

	public static
		com.liferay.exportimport.kernel.model.ExportImportConfiguration
				moveExportImportConfigurationToTrash(
					HttpPrincipal httpPrincipal,
					long exportImportConfigurationId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportConfigurationServiceUtil.class,
				"moveExportImportConfigurationToTrash",
				_moveExportImportConfigurationToTrashParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

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

			return (com.liferay.exportimport.kernel.model.
				ExportImportConfiguration)returnObj;
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

	public static
		com.liferay.exportimport.kernel.model.ExportImportConfiguration
				restoreExportImportConfigurationFromTrash(
					HttpPrincipal httpPrincipal,
					long exportImportConfigurationId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportConfigurationServiceUtil.class,
				"restoreExportImportConfigurationFromTrash",
				_restoreExportImportConfigurationFromTrashParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

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

			return (com.liferay.exportimport.kernel.model.
				ExportImportConfiguration)returnObj;
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

	private static Log _log = LogFactoryUtil.getLog(
		ExportImportConfigurationServiceHttp.class);

	private static final Class<?>[]
		_deleteExportImportConfigurationParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_moveExportImportConfigurationToTrashParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_restoreExportImportConfigurationFromTrashParameterTypes2 =
			new Class[] {long.class};

}