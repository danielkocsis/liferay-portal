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

package com.liferay.portal.lar.digest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.xml.StAXReaderUtil;
import com.liferay.portal.xml.StAXWriterUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public class LarDigestImpl implements LarDigest {

	public LarDigestImpl() throws FileNotFoundException, XMLStreamException {

		XMLOutputFactory xmlOutputFactory =
			StAXWriterUtil.getXMLOutputFactory();

		_xmlEventFactory = XMLEventFactory.newInstance();

		initElements();

		OutputStream outputStream = new FileOutputStream(getDigestFile());

		_xmlEventWriter = xmlOutputFactory.createXMLEventWriter(outputStream);

		_xmlEventWriter.add(_xmlEventFactory.createStartDocument());

		_xmlEventWriter.add(createStartElement("root"));
	}

	public void close() throws XMLStreamException {
		_xmlEventWriter.flush();
		_xmlEventWriter.close();

		format(_digestFile);
	}

	public File getDigestFile() {
		if (_digestFile == null) {
			String path =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					StringPool.SLASH + "digest_" + PortalUUIDUtil.generate() +
					".xml";

			_digestFile = new File(path);
		}

		return _digestFile;
	}

	public Iterator<LarDigestItem> iterator() {
		XMLInputFactory xmlInputFactory = StAXReaderUtil.getXMLInputFactory();

		try {
			InputStream inputStream = new FileInputStream(getDigestFile());

			_xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

			return new LarDigestIterator(_xmlEventReader);
		}
		catch (Exception e) {
			return null;
		}
	}

	public void write(LarDigestItem digestItem)
		throws PortalException, XMLStreamException {

		write(
			digestItem.getAction(), digestItem.getPath(), digestItem.getType(),
			digestItem.getClassPK());
	}

	protected void addXmlNode(String name, String value)
		throws XMLStreamException {

		_xmlEventWriter.add(getStartElement(name));
		_xmlEventWriter.add(_xmlEventFactory.createCharacters(value));
		_xmlEventWriter.add(getEndElement(name));
	}

	protected EndElement createEndElement(String name) {
		QName qName = new QName(name);

		EndElement endElement = _xmlEventFactory.createEndElement(qName, null);

		return endElement;
	}

	protected StartElement createStartElement(String name) {
		QName qName = new QName(name);

		StartElement startElement = _xmlEventFactory.createStartElement(
				qName, null, null);

		return startElement;
	}

	protected void format(File larDigest) {

		if (larDigest == null) {
			return;
		}

		File formattedLarDigest = null;

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		try {
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(
				"{http://xml.apache.org/xsl0t}indent-amount", "2");

			formattedLarDigest = FileUtil.createTempFile();

			transformer.transform(
				new StreamSource(larDigest),
				new StreamResult(formattedLarDigest));

			if (formattedLarDigest.length() > 0) {
				FileUtil.copyFile(formattedLarDigest, larDigest);
			}
		}
		catch (Exception ex) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Can't format the " + larDigest.getName() + " file!", ex);
			}
		}
		finally {
			if (formattedLarDigest != null) {
				FileUtil.delete(formattedLarDigest);
			}
		}
	}

	protected EndElement getEndElement(String name) {
		if ((_endElements == null) || _endElements.isEmpty()) {
			return null;
		}

		return _endElements.get(name);
	}

	protected StartElement getStartElement(String name) {
		if ((_startElements == null) || _startElements.isEmpty()) {
			return null;
		}

		return _startElements.get(name);
	}

	protected void initElements() {
		_endElements = new HashMap<String, EndElement>();
		_startElements = new HashMap<String, StartElement>();

		_endElements.put(
			LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL,
			createEndElement(LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL));

		_startElements.put(
			LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL,
			createStartElement(LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL));

		_endElements.put(
			LarDigesterConstants.NODE_ACTION_LABEL,
			createEndElement(LarDigesterConstants.NODE_ACTION_LABEL));

		_startElements.put(
			LarDigesterConstants.NODE_ACTION_LABEL,
			createStartElement(LarDigesterConstants.NODE_ACTION_LABEL));

		_endElements.put(
			LarDigesterConstants.NODE_CLASS_PK_LABEL,
			createEndElement(LarDigesterConstants.NODE_CLASS_PK_LABEL));

		_startElements.put(
			LarDigesterConstants.NODE_CLASS_PK_LABEL,
			createStartElement(LarDigesterConstants.NODE_CLASS_PK_LABEL));

		_endElements.put(
			LarDigesterConstants.NODE_PATH_LABEL,
			createEndElement(LarDigesterConstants.NODE_PATH_LABEL));

		_startElements.put(
			LarDigesterConstants.NODE_PATH_LABEL,
			createStartElement(LarDigesterConstants.NODE_PATH_LABEL));

		_endElements.put(
			LarDigesterConstants.NODE_TYPE_LABEL,
			createEndElement(LarDigesterConstants.NODE_TYPE_LABEL));

		_startElements.put(
			LarDigesterConstants.NODE_TYPE_LABEL,
			createStartElement(LarDigesterConstants.NODE_TYPE_LABEL));
	}

	protected void write(int action, String path, String type, String classPK)
		throws PortalException, XMLStreamException {

		try {
			_xmlEventWriter.add(
				getStartElement(LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL));

			addXmlNode(LarDigesterConstants.NODE_PATH_LABEL, path);

			addXmlNode(
				LarDigesterConstants.NODE_ACTION_LABEL,
				StringUtil.valueOf(action));

			addXmlNode(LarDigesterConstants.NODE_TYPE_LABEL, type);

			addXmlNode(LarDigesterConstants.NODE_CLASS_PK_LABEL, classPK);

			_xmlEventWriter.add(
				getEndElement(LarDigesterConstants.NODE_DIGEST_ENTRY_LABEL));
		}
		finally {
			try {
				_xmlEventWriter.flush();
			}
			catch (Exception ex) {
				throw new PortalException(ex.getMessage());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LarDigest.class);

	private File _digestFile;

	private Map<String, EndElement> _endElements;
	private Map<String, StartElement> _startElements;
	private XMLEventFactory _xmlEventFactory;
	private XMLEventReader _xmlEventReader;
	private XMLEventWriter _xmlEventWriter;

}