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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.NotPrivileged;

import java.io.Serializable;

import java.sql.Connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Daniel Kocsis
 */
@DoPrivileged
public class CacheSizeAwareSession implements Session {

	public CacheSizeAwareSession(Session session) {
		_session = session;

		Object wrappedSession = session.getWrappedSession();

		_cacheKey = wrappedSession.hashCode();
	}

	@NotPrivileged
	@Override
	public void clear() throws ORMException {
		_session.clear();
	}

	@NotPrivileged
	@Override
	public Connection close() throws ORMException {
		Connection connection = _session.close();

		_sessionCacheSizeMap.remove(_session);

		return connection;
	}

	@NotPrivileged
	@Override
	public boolean contains(Object object) throws ORMException {
		return _session.contains(object);
	}

	@Override
	public Query createQuery(String queryString) throws ORMException {
		return _session.createQuery(queryString);
	}

	@Override
	public Query createQuery(String queryString, boolean strictName)
		throws ORMException {

		return _session.createQuery(queryString, strictName);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString) throws ORMException {
		return _session.createSQLQuery(queryString);
	}

	@Override
	public SQLQuery createSQLQuery(String queryString, boolean strictName)
		throws ORMException {

		return _session.createSQLQuery(queryString, strictName);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(String queryString)
		throws ORMException {

		return _session.createSynchronizedSQLQuery(queryString);
	}

	@Override
	public SQLQuery createSynchronizedSQLQuery(
			String queryString, boolean strictName)
		throws ORMException {

		return createSynchronizedSQLQuery(queryString, strictName);
	}

	@NotPrivileged
	@Override
	public void delete(Object object) throws ORMException {
		_session.delete(object);
	}

	@NotPrivileged
	@Override
	public void evict(Object object) throws ORMException {
		_session.evict(object);
	}

	@NotPrivileged
	@Override
	public void flush() throws ORMException {
		_session.flush();
	}

	@NotPrivileged
	@Override
	public Object get(Class<?> clazz, Serializable id) throws ORMException {
		return _session.get(clazz, id);
	}

	@Deprecated
	@NotPrivileged
	@Override
	public Object get(Class<?> clazz, Serializable id, LockMode lockMode)
		throws ORMException {

		return _session.get(clazz, id, lockMode);
	}

	@NotPrivileged
	@Override
	public Object getWrappedSession() throws ORMException {
		return _session.getWrappedSession();
	}

	@NotPrivileged
	@Override
	public boolean isDirty() throws ORMException {
		return _session.isDirty();
	}

	@NotPrivileged
	@Override
	public Object load(Class<?> clazz, Serializable id) throws ORMException {
		return _session.load(clazz, id);
	}

	@NotPrivileged
	@Override
	public Object merge(Object object) throws ORMException {
		Object returnValue = _session.merge(object);

		maintainSessionCache();

		return returnValue;
	}

	@NotPrivileged
	@Override
	public Serializable save(Object object) throws ORMException {
		Serializable returnValue = _session.save(object);

		maintainSessionCache();

		return returnValue;
	}

	@NotPrivileged
	@Override
	public void saveOrUpdate(Object object) throws ORMException {
		_session.saveOrUpdate(object);

		maintainSessionCache();
	}

	// todo: add a real and working logic to determine when do we need to clear the cache

	protected void maintainSessionCache() {
		if (!_sessionCacheSizeMap.containsKey(_cacheKey)) {
			_sessionCacheSizeMap.put(_cacheKey, 0);
		}

		int sessionCacheSize = _sessionCacheSizeMap.get(_cacheKey);

		if ((++sessionCacheSize % _JDBC_LVL1_CACHE_MAX_SIZE) == 0) {
			_session.flush();
			_session.clear();

			_sessionCacheSizeMap.put(_cacheKey, 0);

			return;
		}

		_sessionCacheSizeMap.put(_cacheKey, sessionCacheSize);
	}

	private static final int _JDBC_LVL1_CACHE_MAX_SIZE = 1;

	private static final Map<Integer, Integer> _sessionCacheSizeMap =
		new ConcurrentHashMap<Integer, Integer>();

	private int _cacheKey;
	private Session _session;

}