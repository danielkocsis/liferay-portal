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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.lar.XStreamWrapper;
import com.liferay.portal.lar.digest.LarDigest;
import com.liferay.portal.lar.digest.LarDigestItem;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Mate Thurzo
 */
public interface BaseDataHandler<T extends BaseModel<T>> {

	public static final String ROOT_PATH_GROUPS = "/groups/";

	public static final String ROOT_PATH_LAYOUTS = "/layouts/";

	public static final String ROOT_PATH_PORTLETS = "/portlets/";

	public void addExpando(T object) throws SystemException;

	public void addZipEntry(String path, T object) throws SystemException;

	public void addZipEntry(String path, byte[] bytes) throws SystemException;

	public void addZipEntry(String path, InputStream is)
		throws SystemException;

	public ServiceContext createServiceContext(
		Element element, ClassedModel classedModel, String namespace);

	public ServiceContext createServiceContext(
		String path, ClassedModel classedModel, String namespace);

	public void digest(T object) throws Exception;

	public Object fromXML(byte[] bytes);

	public Object fromXML(String xml);

	public String getEntityPath(T object);

	public List<String> getZipEntries();

	public byte[] getZipEntryAsByteArray(String path);

	public File getZipEntryAsFile(String path);

	public InputStream getZipEntryAsInputStream(String path);

	public Object getZipEntryAsObject(String path);

	public String getZipEntryAsString(String path);

	public List<String> getZipFolderEntries();

	public List<String> getZipFolderEntries(String path);

	public void importData(LarDigestItem item);

	public XStreamWrapper getXStreamWrapper();

	public ZipWriter getZipWriter();

	public void serialize(String classPK);

	public void setXstreamWrapper(XStreamWrapper xstreamWrapper);

	public String toXML(Object object);

}
