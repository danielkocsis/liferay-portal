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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Shuyang Zhou
 * @author Shinn Lok
 */
public class ValidatorTest extends TestCase {

	public void testIsNull() throws Exception {
		String[] nullStrings = {
			null, "", "  ", "null", " null", "null ", "  null  "
		};

		for (String nullString : nullStrings) {
			assertTrue(Validator.isNull(nullString));
		}

		String[] notNullStrings = {
			"a", "anull", "nulla", " anull", " nulla ","  null  a"
		};

		for (String notNullString : notNullStrings) {
			assertFalse(Validator.isNull(notNullString));
		}
	}

	public void testIsValidEmailAddress() throws Exception {
		String[] validEmailAddresses = {
			"test@liferay.com", "test123@liferay.com", "test.user@liferay.com",
			"test@liferay.com.ch", "test!@liferay.com", "test#@liferay.com",
			"test$@liferay.com", "test%@liferay.com", "test&@liferay.com",
			"test'@liferay.com", "test*@liferay.com", "test+@liferay.com",
			"test-@liferay.com", "test/@liferay.com", "test=@liferay.com",
			"test?@liferay.com", "test^@liferay.com", "test_@liferay.com",
			"test`@liferay.com", "test{@liferay.com", "test|@liferay.com",
			"test{@liferay.com", "test~@liferay.com"
		};

		for (String validEmailAddress : validEmailAddresses) {
			if (!Validator.isEmailAddress(validEmailAddress)) {
				fail(validEmailAddress);
			}
		}

		String[] invalidEmailAddresses = {
			"test", "liferay.com", "@liferay.com", "test(@liferay.com",
			"test)@liferay.com", "test,@liferay.com", ".test@liferay.com",
			"test.@liferay.com", "te..st@liferay.com", "test user@liferay.com",
			"test@-liferay.com", "test@liferay"
		};

		for (String invalidEmailAddress : invalidEmailAddresses) {
			if (Validator.isEmailAddress(invalidEmailAddress)) {
				fail(invalidEmailAddress);
			}
		}
	}

	public void testIsValidHostName() throws Exception {
		String[] validHostNames = {
			"localhost", "127.0.0.1", "10.10.10.1", "abc.com", "9to5.net",
			"liferay.com", "www.liferay.com", "www.liferay.co.uk", "[::1]",
			"[abcd:1234:ef01:2345:6789:0123:4567]",
			"[ffff:0000:3333::1222:192.168.2.102]"
		};

		for (String validHostName : validHostNames) {
			if (!Validator.isHostName(validHostName)) {
				fail(validHostName);
			}
		}

		String[] invalidHostNames = {
			"(999.999.999)", "123_456_789_012", "www.$dollar$.com",
			"{abcd:1234:ef01:2345:6789:0123:4567}","[0xee:ff02:4455:2344::1]",
			"[abcd::ef01:2345::6789]"
		};

		for (String invalidHostName : invalidHostNames) {
			if (Validator.isHostName(invalidHostName)) {
				fail(invalidHostName);
			}
		}
	}

	public void testIsValidIPv4Address() throws Exception {
		String[] validIPv4Addresses = {
			"192.168.1.102", "255.0.0.0", "255.255.255.255", "128.000.001.002",
			"10.10.10.1"
		};

		for (String validIPv4Address : validIPv4Addresses) {
			if (!Validator.isIPv4Address(validIPv4Address)) {
				fail(validIPv4Address);
			}
		}

		String[] invalidIPv4Addresses = {
			"392.168.1.102", "255.0.0", "256.257.258.259", "128.0000.001.002",
			"10.10.10.1000", "0192.0168.0001.0001"
		};

		for (String invalidIPv4Address : invalidIPv4Addresses) {
			if (Validator.isIPv4Address(invalidIPv4Address)) {
				fail(invalidIPv4Address);
			}
		}
	}

	public void testIsValidIPv6Address() throws Exception {
		String[] validIPv6Addresses = {
			"::1", "::", "0:0:0:0:0:0:0:1", "0:0:0:0:0:0:0:0",
			"FF02:0:0:0:0:0:0:1", "FF02:0:0:0:0:0:0:2", "FF02:0:0:0:0:0:0:3",
			"FF02:0:0:0:0:0:0:4", "FF02:0:0:0:0:0:0:5", "FF02:0:0:0:0:0:0:6",
			"FF02:0:0:0:0:0:0:7", "FF02:0:0:0:0:0:0:8", "FF02:0:0:0:0:0:0:9",
			"FF02:0:0:0:0:0:0:A", "FF02:0:0:0:0:0:0:B", "FF02:0:0:0:0:0:0:C",
			"FF02:0:0:0:0:0:0:D", "FF02:0:0:0:0:0:0:E", "FF02:0:0:0:0:0:0:F",
			"FF02:0:0:0:0:0:0:12", "FF02:0:0:0:0:0:0:16", "FF02:0:0:0:0:0:0:1A",
			"FF02:0:0:0:0:0:0:6A", "FF02:0:0:0:0:0:0:6B", "FF02:0:0:0:0:0:0:6C",
			"FF02:0:0:0:0:0:0:6D", "FF02:0:0:0:0:0:0:6E", "FF02:0:0:0:0:0:0:6F",
			"FF02:0:0:0:0:0:0:FB", "FF02:0:0:0:0:0:1:1", "FF02:0:0:0:0:0:1:2",
			"FF02:0:0:0:0:0:1:3", "FF02:0:0:0:0:0:1:4", "FF02:0:0:0:0:0:1:5",
			"FF02:0:0:0:0:0:1:6", "FF02:0:0:0:0:0:0:1",
			"FF02:0000:0000:0000:0000:0000:0000:0002",
			"FF02:0000:0000:0000:0000:0000:0000:0003",
			"FF02:0000:0000:0000:0000:0000:0000:0004",
			"FF02:0000:0000:0000:0000:0000:0000:0005",
			"FF02:0000:0000:0000:0000:0000:0000:0006",
			"FF02:0000:0000:0000:0000:0000:0000:0007",
			"FF02:0000:0000:0000:0000:0000:0000:0008",
			"FF02:0000:0000:0000:0000:0000:0000:0009",
			"FF02:0000:0000:0000:0000:0000:0000:000A",
			"FF02:0000:0000:0000:0000:0000:0000:000B",
			"FF02:0000:0000:0000:0000:0000:0000:000C",
			"FF02:0000:0000:0000:0000:0000:0000:000D",
			"FF02:0000:0000:0000:0000:0000:0000:000E",
			"FF02:0000:0000:0000:0000:0000:0000:000F",
			"FF02:0000:0000:0000:0000:0000:0000:0012",
			"FF02:0000:0000:0000:0000:0000:0000:0016",
			"FF02:0000:0000:0000:0000:0000:0000:001A",
			"FF02:0000:0000:0000:0000:0000:0000:006A",
			"FF02:0000:0000:0000:0000:0000:0000:006B",
			"FF02:0000:0000:0000:0000:0000:0000:006C",
			"FF02:0000:0000:0000:0000:0000:0000:006D",
			"FF02:0000:0000:0000:0000:0000:0000:006E",
			"FF02:0000:0000:0000:0000:0000:0000:006F",
			"FF02:0000:0000:0000:0000:0000:0000:00FB",
			"FF02:0000:0000:0000:0000:0000:0001:0001",
			"FF02:0000:0000:0000:0000:0000:0001:0002",
			"FF02:0000:0000:0000:0000:0000:0001:0003",
			"FF02:0000:0000:0000:0000:0000:0001:0004",
			"FF02:0000:0000:0000:0000:0000:0001:0005",
			"FF02:0000:0000:0000:0000:0000:0001:0006",
			"2345:0000:6789:0000:abcd:E1F2:ABCD:9876",
			"1ff2:0ee0:0d00:0000:0001:0000:0000:000f",
			"eE01:0000:0000:0000:0000:0000:0000:0001",
			"0000:0000:0000:0000:0000:0000:0000:0001",
			"0000:0000:0000:0000:0000:0000:0000:0000",
			"2001:0db8:85a3:0042:0000:8a2e:0370:7334", "ff02::1", "fe80::",
			"2002::", "2001:db8::", "2001:0db8:1234::", "::1",
			"1:2:3:4:5:6:7:8", "1:2:3:4:5:6::8", "1:2:3:4:5::8", "1:2:3:4::8",
			"1:2:3::8", "1:2::8", "1::8", "1::2:3:4:5:6:7", "1::2:3:4:5:6",
			"1::2:3:4:5", "1::2:3:4", "1::2:3", "::2:3:4:5:6:7:8",
			"::2:3:4:5:6:7", "::2:3:4:5:6", "::2:3:4:5", "::2:3:4", "::2:3",
			"::8", "1:2:3:4:5:6::", "1:2:3:4:5::", "1:2:3:4::", "1:2:3::",
			"1:2::", "1::", "1:2:3:4:5::7:8", "1:2:3:4::7:8", "1:2:3::7:8",
			"1:2::7:8", "1::7:8", "1:2:3:4:5:6:1.2.3.4", "1:2:3:4:5::1.2.3.4",
			"1:2:3:4::1.2.3.4", "1:2:3::1.2.3.4", "1:2::1.2.3.4", "1::1.2.3.4",
			"1:2:3:4::5:1.2.3.4", "1:2:3::5:1.2.3.4", "1:2::5:1.2.3.4",
			"1::5:1.2.3.4", "1::5:11.22.33.44", "fe80::217:f2ff:254.7.237.98",
			"::ffff:192.168.1.26", "::ffff:192.168.1.1",
			"0:0:0:0:0:0:13.1.68.3", "0:0:0:0:0:FFFF:129.144.52.38",
			"::13.1.68.3", "::FFFF:129.144.52.38",
			"fe80:0:0:0:204:61ff:254.157.241.86",
			"fe80::204:61ff:254.157.241.86", "::ffff:12.34.56.78",
			"::ffff:192.0.2.128", "fe80:0000:0000:0000:0204:61ff:fe9d:f156",
			"fe80:0:0:0:204:61ff:fe9d:f156", "fe80::204:61ff:fe9d:f156", "::1",
			"fe80::", "fe80::1", "::ffff:c000:280"
		};

		for (String validIPv6Address : validIPv6Addresses) {
			if (!Validator.isIPv6Address(validIPv6Address)) {
				fail(validIPv6Address);
			}
		}

		String[] invalidIPv6Addresses = {
				"fe80:0000:0000:0000:0204:61ff:254.157.241.086", ":",
				"2001:0000:1234:0000:0000:C1C0:ABCD:0876  0",
				"2001:0000:1234: 0000:0000:C1C0:ABCD:0876", "",
				"2001:DB8:0:0:8:800:200C:417A:221", "FF01::101::2",
				"02001:0000:1234:0000:0000:C1C0:ABCD:0876",
				"2001:0000:1234:0000:00001:C1C0:ABCD:0876",
				"FF02:0000:0000:0000:0000:0000:0000:0000:0001",
				"3ffe:b00::1::a", "::1111:2222:3333:4444:5555:6666::",
				"1:2:3::4:5::7:8", "12345::6:7:8", "1::5:400.2.3.4",
				"1::5:260.2.3.4", "1::5:256.2.3.4", "1::5:1.256.3.4",
				"1::5:1.2.256.4", "1::5:1.2.3.256", "1::5:300.2.3.4",
				"1::5:1.300.3.4", "1::5:1.2.300.4", "1::5:1.2.3.300",
				"1::5:900.2.3.4", "1::5:1.900.3.4", "1::5:1.2.900.4",
				"1::5:1.2.3.900", "1::5:300.300.300.300", "1::5:3000.30.30.30",
				"1::400.2.3.4", "1::260.2.3.4", "1::256.2.3.4", "1::1.256.3.4",
				"1::1.2.256.4", "1::1.2.3.256", "1::300.2.3.4", "1::1.300.3.4",
				"1::1.2.300.4", "1::1.2.3.300", "1::900.2.3.4", "1::1.900.3.4",
				"1::1.2.900.4", "1::1.2.3.900", "1::300.300.300.300",
				"1::3000.30.30.30", "::400.2.3.4", "::260.2.3.4", "::256.2.3.4",
				"::1.256.3.4", "::1.2.256.4", "::1.2.3.256", "::300.2.3.4",
				"::1.300.3.4", "::1.2.300.4", "::1.2.3.300", "::900.2.3.4",
				"::1.900.3.4", "::1.2.900.4", "::1.2.3.900",
				"::300.300.300.300", "::3000.30.30.30",
				"2001:1:1:1:1:1:255Z255X255Y255", "::ffff:192x168.1.26",
				"::ffff:2.3.4", "::ffff:257.1.2.3", "1.2.3.4",
				"1.2.3.4:1111:2222:3333:4444::5555",
				"1.2.3.4:1111:2222:3333::5555", "1.2.3.4:1111:2222::5555",
				"1.2.3.4:1111::5555", "1.2.3.4::5555", "1.2.3.4::",
				"XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:1.2.3.4",
				"1111:2222:3333:4444:5555:6666:00.00.00.00",
				"1111:2222:3333:4444:5555:6666:000.000.000.000",
				"1111:2222:3333:4444:5555:6666:256.256.256.256"
			};
		for (String invalidIPv6Address : invalidIPv6Addresses) {
			if (Validator.isIPv6Address(invalidIPv6Address)) {
				fail(invalidIPv6Address);
			}
		}
	}

}