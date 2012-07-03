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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.LarPersistenceContext;
import com.liferay.portal.kernel.lar.LarPersistenceContextThreadLocal;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.lar.XStreamWrapper;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.persistence.BaseLarPersistence;
import com.liferay.portal.service.persistence.lar.BookmarksEntryLarPersistence;
import com.liferay.portal.service.persistence.lar.BookmarksFolderLarPersistence;
import com.liferay.portal.service.persistence.lar.BookmarksPortletLarPersistence;
import com.liferay.portal.service.persistence.lar.ImageLarPersistence;
import com.liferay.portal.service.persistence.lar.PortletLarPersistence;
import com.liferay.portal.service.persistence.lar.JournalStructureLarPersistence;
import com.liferay.portal.service.persistence.lar.JournalTemplateLarPersistence;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portal.service.persistence.lar.JournalArticleLarPersistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 */
public class BaseLarPersistenceImpl<T extends BaseModel<T>>
	implements BaseLarPersistence<T> {

	public void addZipEntry(String path, T object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, byte[] bytes) throws SystemException {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, InputStream is)
		throws SystemException {

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, Object object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, String s) throws SystemException {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, s);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, StringBuilder sb)
			throws SystemException {

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			ZipWriter zipWriter = getZipWriter();

			zipWriter.addEntry(path, sb);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addExpando(T object) throws SystemException {
		if (!(object instanceof ClassedModel)) {
			return;
		}

		ClassedModel classedModel = (ClassedModel)object;

		String expandoPath = getEntityPath(object);
		expandoPath = StringUtil.replace(expandoPath, ".xml", "-expando.xml");

		ExpandoBridge expandoBridge = classedModel.getExpandoBridge();

		addZipEntry(expandoPath, expandoBridge.getAttributes());
	}

	public void digest(T object) throws Exception {
		try {
			doDigest(object);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public Object fromXML(byte[] bytes) {
		if ((bytes == null) || (bytes.length == 0)) {
			return null;
		}

		return getXStreamWrapper().fromXML(new String(bytes));
	}

	public Object fromXML(String xml) {
		if (Validator.isNull(xml)) {
			return null;
		}

		return getXStreamWrapper().fromXML(xml);
	}

	public String getEntityPath(T object) {
		if (object instanceof BaseModel) {
			BaseModel<T> baseModel = (BaseModel<T>)object;

			Map<String, Object> modelAttributes =
				baseModel.getModelAttributes();

			StringBundler sb = new StringBundler();

			sb.append(modelAttributes.get("groupId"));
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(baseModel.getModelClassName());
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(baseModel.getPrimaryKeyObj() + ".xml");

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	public List<String> getZipEntries() {
		return getZipReader().getEntries();
	}

	public byte[] getZipEntryAsByteArray(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsByteArray(path);
	}

	public File getZipEntryAsFile(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsFile(path);
	}

	public InputStream getZipEntryAsInputStream(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsInputStream(path);
	}

	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	public String getZipEntryAsString(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsString(path);
	}

	public List<String> getZipFolderEntries() {
		return getZipFolderEntries(StringPool.SLASH);
	}

	public List<String> getZipFolderEntries(String path) {
		return getZipReader().getFolderEntries(path);
	}

	public void importData(LarDigestItem item) {
		try {
			doImport(item);
		}
		catch (Exception e) {
		}
	}

	public void serialize(String classPK) {

		T object = getEntity(classPK);

		if (object == null) {
			return;
		}

		String path = getEntityPath(object);

		if (isPathProcessed(path)) {
			return;
		}

		try {
			doSerialize(object);

			addExpando(object);

			addProcessedPath(path);
		}
		catch (Exception e) {
		}

	}

	protected T getEntity(String classPK) {
		return null;
	}

	public String toXML(Object object) {
		return getXStreamWrapper().toXML(object);
	}

	public void setXstreamWrapper(XStreamWrapper xstreamWrapper) {
		_xStreamWrapper = xstreamWrapper;
	}

	public XStreamWrapper getXStreamWrapper() {
		if (_xStreamWrapper == null) {
			Object o = PortalBeanLocatorUtil.locate("xStreamWrapper");

			if (o != null) {
				_xStreamWrapper = ((XStreamWrapper)o);
			}
		}

		return _xStreamWrapper;
	}

	public ZipReader getZipReader() {
		LarPersistenceContext context =
			LarPersistenceContextThreadLocal.getLarPersistenceContext();

		if (context != null) {
			return context.getZipReader();
		}

		return null;
	}

	public ZipWriter getZipWriter() {
		LarPersistenceContext context =
			LarPersistenceContextThreadLocal.getLarPersistenceContext();

		if (context != null) {
			return context.getZipWriter();
		}

		return null;
	}

	public void setZipWriter(ZipWriter zipWriter) {
		_zipWriter = zipWriter;
	}

	protected void doDigest(T object) throws Exception {
		return;
	}

	protected void doImport(LarDigestItem item) throws Exception{
		return;
	}

	protected void doSerialize(T object) throws Exception {
		String path = getEntityPath(object);

		addZipEntry(path, object);
	}

	protected LarPersistenceContext getLarPersistenceContext() {
		return LarPersistenceContextThreadLocal.getLarPersistenceContext();
	}

	protected String getLayoutPath(long layoutId) {
		return getRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	protected String getPortletPath(String portletId) {
		return getRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	protected String getRootPath() {
		return ROOT_PATH_GROUPS + getScopeGroupId();
	}

	protected long getScopeGroupId() {
		LarPersistenceContext larPersistenceContext =
			getLarPersistenceContext();

		if (larPersistenceContext != null) {
			return larPersistenceContext.getGroupId();
		}

		return 0;
	}

	protected boolean isPathProcessed(String path) {
		if (_storedPaths.contains(path)) {
			return true;
		}

		return false;
	}

	protected boolean isPathNotProcessed(String path) {
		return !isPathProcessed(path);
	}

	private void addProcessedPath(String path) {
		_storedPaths.add(path);
	}

	private Log _log = LogFactoryUtil.getLog(BaseLarPersistenceImpl.class);
	private PortletDataContextListener _portletDataContextListener;
	private static Set<String> _storedPaths = new HashSet<String>();
	private static ZipWriter _zipWriter;
	private XStreamWrapper _xStreamWrapper;

	@BeanReference(type = JournalArticleLarPersistence.class)
	protected JournalArticleLarPersistence journalArticleLarPersistence;
	@BeanReference(type = JournalStructureLarPersistence.class)
	protected JournalStructureLarPersistence journalStructureLarPersistence;
	@BeanReference(type = JournalTemplateLarPersistence.class)
	protected JournalTemplateLarPersistence journalTemplateLarPersistence;
	@BeanReference(type = ImageLarPersistence.class)
	protected ImageLarPersistence imageLarPersistence;
	@BeanReference(type = BookmarksEntryLarPersistence.class)
	protected BookmarksEntryLarPersistence bookmarksEntryLarPersistence;
	@BeanReference(type = BookmarksFolderLarPersistence.class)
	protected BookmarksFolderLarPersistence bookmarksFolderLarPersistence;
	@BeanReference(type = BookmarksPortletLarPersistence.class)
	protected BookmarksPortletLarPersistence bookmarksPortletLarPersistence;
	@BeanReference(type = PortletLarPersistence.class)
	protected PortletLarPersistence portletLarPersistence;

}
