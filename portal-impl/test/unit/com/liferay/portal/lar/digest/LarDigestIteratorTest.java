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

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author Mate Thurzo
 */
public class LarDigestIteratorTest {

	@Before
	public void setUp() throws Exception {
		_larDigest = new LarDigestImpl();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testDigest() {
		LarDigestItem item = new LarDigestItemImpl();

		item.setAction(LarDigesterConstants.ACTION_ADD);
		item.setPath("/10190/" + Layout.class.getName() + "/1");
		item.setType(Layout.class.getName());
		item.setClassPK("1000");

		item = new LarDigestItemImpl();

		item.setAction(LarDigesterConstants.ACTION_DELETE);
		item.setPath("/10190/" + Portlet.class.getName() + "/1");
		item.setType(Portlet.class.getName());
		item.setClassPK("2000");

		item = new LarDigestItemImpl();

		item.setAction(LarDigesterConstants.ACTION_UPDATE);
		item.setPath("/10190/" + BookmarksEntry.class.getName() + "/1");
		item.setType(BookmarksEntry.class.getName());
		item.setClassPK("3000");


	}

	@Test
	public void testEmptyDigest() {

	}

	private LarDigest _larDigest;
}
