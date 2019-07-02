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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PortletPreferencesCT service. Represents a row in the &quot;PortletPreferencesCT&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesCTModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.model.impl.PortletPreferencesCTImpl"
)
@ProviderType
public interface PortletPreferencesCT extends PortletPreferencesCTModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PortletPreferencesCTImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PortletPreferencesCT, Long> CLASS_PK_ACCESSOR =
		new Accessor<PortletPreferencesCT, Long>() {

			@Override
			public Long get(PortletPreferencesCT portletPreferencesCT) {
				return portletPreferencesCT.getClassPK();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PortletPreferencesCT> getTypeClass() {
				return PortletPreferencesCT.class;
			}

		};
	public static final Accessor<PortletPreferencesCT, Long>
		CT_COLLECTION_ID_ACCESSOR = new Accessor<PortletPreferencesCT, Long>() {

			@Override
			public Long get(PortletPreferencesCT portletPreferencesCT) {
				return portletPreferencesCT.getCtCollectionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PortletPreferencesCT> getTypeClass() {
				return PortletPreferencesCT.class;
			}

		};

}