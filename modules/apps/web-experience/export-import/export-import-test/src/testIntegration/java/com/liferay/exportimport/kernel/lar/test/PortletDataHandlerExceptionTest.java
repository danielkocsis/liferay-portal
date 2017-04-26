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

package com.liferay.exportimport.kernel.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class PortletDataHandlerExceptionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				null, null, null, new TestReaderWriter());

		_portletDataContextExport.setPortletId(RandomTestUtil.randomString());

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				new HashMap<String, String[]>(), new TestUserIdStrategy(),
				testReaderWriter);

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextImport.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContextImport.setMissingReferencesElement(
			missingReferencesElement);
	}

	@Test
	public void testPortletDataHandlerDeleteException() throws Exception {
		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doDeleteData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new Exception();
			}

		};

		try {
			portletDataHandler.deleteData(
				_portletDataContextImport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerDeleteExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doDeleteData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new Exception("test message");
			}

		};

		try {
			portletDataHandler.deleteData(
				_portletDataContextImport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerDeletePortletDataException()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doDeleteData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException();
			}

		};

		try {
			portletDataHandler.deleteData(
				_portletDataContextImport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerDeletePortletDataExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doDeleteData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException("test message");
			}

		};

		try {
			portletDataHandler.deleteData(
				_portletDataContextImport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerDeletePortletDataExceptionMessageType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doDeleteData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				PortletDataException pde = new PortletDataException(
					"test message");

				pde.setType(PortletDataException.INVALID_GROUP);

				throw pde;
			}

		};

		try {
			portletDataHandler.deleteData(
				_portletDataContextImport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerDeletePortletDataExceptionType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doDeleteData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException(
					PortletDataException.INVALID_GROUP);
			}

		};

		try {
			portletDataHandler.deleteData(
				_portletDataContextImport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerExportException() throws Exception {
		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected String doExportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new Exception();
			}

		};

		try {
			portletDataHandler.exportData(
				_portletDataContextExport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerExportExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected String doExportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new Exception("test message");
			}

		};

		try {
			portletDataHandler.exportData(
				_portletDataContextExport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerExportPortletDataException()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected String doExportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException();
			}

		};

		try {
			portletDataHandler.exportData(
				_portletDataContextExport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerExportPortletDataExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected String doExportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException("test message");
			}

		};

		try {
			portletDataHandler.exportData(
				_portletDataContextExport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerExportPortletDataExceptionMessageType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected String doExportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				PortletDataException pde = new PortletDataException(
					"test message");

				pde.setType(PortletDataException.INVALID_GROUP);

				throw pde;
			}

		};

		try {
			portletDataHandler.exportData(
				_portletDataContextExport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerExportPortletDataExceptionType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected String doExportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException(
					PortletDataException.INVALID_GROUP);
			}

		};

		try {
			portletDataHandler.exportData(
				_portletDataContextExport, RandomTestUtil.randomString(), null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerImportException() throws Exception {
		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doImportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences, String data)
				throws Exception {

				throw new Exception();
			}

		};

		try {
			portletDataHandler.importData(
				_portletDataContextImport, RandomTestUtil.randomString(), null,
				null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerImportExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doImportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences, String data)
				throws Exception {

				throw new Exception("test message");
			}

		};

		try {
			portletDataHandler.importData(
				_portletDataContextImport, RandomTestUtil.randomString(), null,
				null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerImportPortletDataException()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doImportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences, String data)
				throws Exception {

				throw new PortletDataException();
			}

		};

		try {
			portletDataHandler.importData(
				_portletDataContextImport, RandomTestUtil.randomString(), null,
				null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerImportPortletDataExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doImportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences, String data)
				throws Exception {

				throw new PortletDataException("test message");
			}

		};

		try {
			portletDataHandler.importData(
				_portletDataContextImport, RandomTestUtil.randomString(), null,
				null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerImportPortletDataExceptionMessageType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doImportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences, String data)
				throws Exception {

				PortletDataException pde = new PortletDataException(
					"test message");

				pde.setType(PortletDataException.INVALID_GROUP);

				throw pde;
			}

		};

		try {
			portletDataHandler.importData(
				_portletDataContextImport, RandomTestUtil.randomString(), null,
				null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerImportPortletDataExceptionType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected PortletPreferences doImportData(
					PortletDataContext portletDataContext, String portletId,
					PortletPreferences portletPreferences, String data)
				throws Exception {

				throw new PortletDataException(
					PortletDataException.INVALID_GROUP);
			}

		};

		try {
			portletDataHandler.importData(
				_portletDataContextImport, RandomTestUtil.randomString(), null,
				null);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerPrepareManifestException()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected void doPrepareManifestSummary(
					PortletDataContext portletDataContext,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new Exception();
			}

		};

		try {
			portletDataHandler.prepareManifestSummary(
				_portletDataContextExport);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerPrepareManifestExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected void doPrepareManifestSummary(
					PortletDataContext portletDataContext,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new Exception("test message");
			}

		};

		try {
			portletDataHandler.prepareManifestSummary(
				_portletDataContextExport);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerPrepareManifestPortletDataException()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected void doPrepareManifestSummary(
					PortletDataContext portletDataContext,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException();
			}

		};

		try {
			portletDataHandler.prepareManifestSummary(
				_portletDataContextExport);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerPrepareManifestPortletDataExceptionMessage()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected void doPrepareManifestSummary(
					PortletDataContext portletDataContext,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException("test message");
			}

		};

		try {
			portletDataHandler.prepareManifestSummary(
				_portletDataContextExport);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerPrepareManifestPortletDataExceptionMessageType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected void doPrepareManifestSummary(
					PortletDataContext portletDataContext,
					PortletPreferences portletPreferences)
				throws Exception {

				PortletDataException pde = new PortletDataException(
					"test message");

				pde.setType(PortletDataException.INVALID_GROUP);

				throw pde;
			}

		};

		try {
			portletDataHandler.prepareManifestSummary(
				_portletDataContextExport);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	@Test
	public void testPortletDataHandlerPrepareManifestPortletDataExceptionType()
		throws Exception {

		PortletDataHandler portletDataHandler = new TestPortletDataHandler() {

			@Override
			protected void doPrepareManifestSummary(
					PortletDataContext portletDataContext,
					PortletPreferences portletPreferences)
				throws Exception {

				throw new PortletDataException(
					PortletDataException.INVALID_GROUP);
			}

		};

		try {
			portletDataHandler.prepareManifestSummary(
				_portletDataContextExport);
		}
		catch (Exception e) {
			_validateException(e);
		}
	}

	private void _validateException(Exception e) {

		// First are foremost it always need to throw a PortletDataException

		if (!(e instanceof PortletDataException)) {
			Assert.fail(
				"Exception thrown always have to be type of " +
					"PortletDataException");
		}

		PortletDataException pde = (PortletDataException)e;

		String message = pde.getMessage();
		String portletId = pde.getPortletId();
		int type = pde.getType();

		// At this point the portlet ID is mandatory

		if (Validator.isNull(portletId)) {
			Assert.fail(
				"Exceptions coming from a PortletDataHandler has to have a " +
					"portletId attribute");
		}

		// It should also have a type by now

		if (type == PortletDataException.DEFAULT) {
			Assert.fail(
				"Exception coming from a PortletDatahandler has to have a " +
					"type attribute");
		}

		// Certain types allow not to have a message

		Set<Integer> allowedTypeSet = SetUtil.fromArray(
			new Integer[] {
				PortletDataException.END_DATE_IS_MISSING_START_DATE,
				PortletDataException.FUTURE_END_DATE,
				PortletDataException.FUTURE_START_DATE,
				PortletDataException.INVALID_GROUP,
				PortletDataException.MISSING_DEPENDENCY,
				PortletDataException.START_DATE_AFTER_END_DATE,
				PortletDataException.START_DATE_IS_MISSING_END_DATE,
				PortletDataException.STATUS_IN_TRASH,
				PortletDataException.STATUS_UNAVAILABLE,
				PortletDataException.UNKNOWN_REASON_DELETE_PORTLET_DATA,
				PortletDataException.UNKNOWN_REASON_EXPORT_PORTLET_DATA,
				PortletDataException.UNKNOWN_REASON_IMPORT_PORTLET_DATA,
				PortletDataException.UNKNOWN_REASON_PREPARE_MANIFEST_SUMMARY
			});

		if (!allowedTypeSet.contains(type) && Validator.isNull(message)) {
			Assert.fail(
				"Exception has a type that requires an exception message too");
		}

		if ((type >= PortletDataException.KNOWN_REASON_DELETE_PORTLET_DATA) &&
			Validator.isNull(message)) {

			Assert.fail("Known types mean that the exception need a message");
		}
	}

	private static PortletDataContext _portletDataContextExport;
	private static PortletDataContext _portletDataContextImport;

	private class TestPortletDataHandler extends BasePortletDataHandler {
	}

}