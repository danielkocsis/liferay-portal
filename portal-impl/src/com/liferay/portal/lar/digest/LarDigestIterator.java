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

import com.liferay.portal.xml.StAXReaderUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;

/**
 * @author Mate Thurzo
 */
public class LarDigestIterator implements Iterator<LarDigestItem> {

	public LarDigestIterator(XMLEventReader digestXMLEventReader) {
		_xmlEventReader = digestXMLEventReader;
	}

	public boolean hasNext() {
		return _xmlEventReader.hasNext();
	}

	public LarDigestItem next() {
		try {
			LarDigestItem item = new LarDigestItem();

			while (_xmlEventReader.hasNext()) {
				XMLEvent event = _xmlEventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();

					String startElementName =
						startElement.getName().getLocalPart();

					if (startElementName.equals(
							LarDigesterConstants.NODE_PATH_LABEL)) {

						String path = StAXReaderUtil.read(_xmlEventReader);

						item.setPath(path);
					}
					else if (startElementName.equals(
								LarDigesterConstants.NODE_ACTION_LABEL)) {

						String action = StAXReaderUtil.read(_xmlEventReader);
						int actionCode = Integer.parseInt(action);

						item.setAction(actionCode);
					}
					else if (startElementName.equals(
								LarDigesterConstants.NODE_TYPE_LABEL)) {

						String type = StAXReaderUtil.read(_xmlEventReader);

						item.setType(type);
					}
					else if (startElementName.equals(
								LarDigesterConstants.NODE_CLASS_PK_LABEL)) {

						String classPKString =
							StAXReaderUtil.read(_xmlEventReader);
						long classPK = Long.valueOf(classPKString);

						item.setClassPK(classPK);
					}
				}
				else if (event.isEndElement()) {
					EndElement endElement = event.asEndElement();

					String endElementName = endElement.getName().getLocalPart();

					if (endElementName.equals(
							LarDigesterConstants.NODE_ITEMS_LABEL)) {

						break;
					}
				}
			}

			return item;
		}
		catch (Exception e) {
			return null;
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	private XMLEventReader _xmlEventReader;
}
