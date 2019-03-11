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

package com.liferay.digital.signature.internal.model.builder;

import com.liferay.digital.signature.internal.model.DSSealInfoImpl;
import com.liferay.digital.signature.internal.model.DSSealProviderOptionsImpl;
import com.liferay.digital.signature.internal.model.SealDSParticipantImpl;
import com.liferay.digital.signature.model.DSSealInfo;
import com.liferay.digital.signature.model.SealDSParticipant;
import com.liferay.digital.signature.model.builder.SealDSParticipantBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SealDSParticipantBuilderImpl
	extends BaseDSParticipantBuilder<SealDSParticipant>
	implements SealDSParticipantBuilder {

	public SealDSParticipantBuilderImpl(
		String participantKey, String name, String emailAddress,
		int routingOrder) {

		super(name, emailAddress, routingOrder);

		setParticipantKey(participantKey);
	}

	@Override
	public SealDSParticipantBuilder addDSSealInfo(
		Boolean sealDocumentsWithFieldsOnly, String sealName,
		String sealProviderName) {

		DSSealInfoImpl dsSealInfoImpl = new DSSealInfoImpl(
			sealDocumentsWithFieldsOnly, sealName, sealProviderName);

		_dsSealInfos.put(sealName, dsSealInfoImpl);

		return this;
	}

	@Override
	public SealDSParticipantBuilder addDSSealInfo(
		String sealName, String sealProviderName) {

		DSSealInfoImpl dsSealInfoImpl = new DSSealInfoImpl(
			sealName, sealProviderName);

		_dsSealInfos.put(sealName, dsSealInfoImpl);

		return this;
	}

	@Override
	public SealDSParticipantBuilder addMobilePhoneNumber(
		String mobilePhoneNumber, String sealName, String signerRole) {

		DSSealProviderOptionsImpl dsSealProviderOptionsImpl =
			addDSSealProviderOptions(sealName, signerRole);

		dsSealProviderOptionsImpl.setMobilePhoneNumber(mobilePhoneNumber);

		return this;
	}

	@Override
	public SealDSParticipantBuilder addOneTimePassword(
		String oneTimePasssword, String sealName, String signerRole) {

		DSSealProviderOptionsImpl dsSealProviderOptionsImpl =
			addDSSealProviderOptions(sealName, signerRole);

		dsSealProviderOptionsImpl.setOneTimePassword(oneTimePasssword);

		return this;
	}

	protected DSSealProviderOptionsImpl addDSSealProviderOptions(
		String sealName, String signerRole) {

		DSSealInfoImpl dsSealInfoImpl = (DSSealInfoImpl)_dsSealInfos.get(
			sealName);

		if (dsSealInfoImpl == null) {
			throw new IllegalArgumentException(
				"No DSSealInfo found for: " + sealName);
		}

		DSSealProviderOptionsImpl dsSealProviderOptionsImpl =
			new DSSealProviderOptionsImpl();

		dsSealProviderOptionsImpl.setRoleName(signerRole);

		dsSealInfoImpl.setDSSealProviderOptions(dsSealProviderOptionsImpl);

		return dsSealProviderOptionsImpl;
	}

	@Override
	protected SealDSParticipant createDSParticipant() {
		SealDSParticipantImpl sealDSParticipantImpl = new SealDSParticipantImpl(
			getEmailAddress(), getName(), getParticipantKey(),
			getRoutingOrder());

		sealDSParticipantImpl.addDSSealInfos(_dsSealInfos.values());

		return sealDSParticipantImpl;
	}

	private Map<String, DSSealInfo> _dsSealInfos = new HashMap<>();

}