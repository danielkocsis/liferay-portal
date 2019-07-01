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

package com.liferay.change.tracking.internal.persistence;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.internal.adapter.CTAdapterBag;
import com.liferay.change.tracking.internal.adapter.CTAdapterHelper;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.change.tracking.CTCollectionIdProvider;
import com.liferay.portal.kernel.change.tracking.model.CTModelAdapter;
import com.liferay.portal.kernel.change.tracking.persistence.CTPersistenceHelper;
import com.liferay.portal.kernel.change.tracking.persistence.CTPersistenceHelperFactory;
import com.liferay.portal.kernel.change.tracking.service.CTServiceAdapter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true,
	service = {
		CTAdapterHelper.class, CTCollectionIdProvider.class,
		CTPersistenceHelperFactory.class
	}
)
public class CTPersistenceHelperFactoryImpl
	implements CTAdapterHelper, CTCollectionIdProvider,
			   CTPersistenceHelperFactory {

	@Override
	public <T extends BaseModel<T>> CTPersistenceHelper<T> create(
		Class<T> modelClass) {

		ActiveCTCollectionHolder activeCTCollectionHolder =
			_activeCTCollectionHolderThreadLocal.get();

		Optional<CTCollection> ctCollectionOptional =
			activeCTCollectionHolder.getActiveCTCollectionOptional();

		if (!ctCollectionOptional.isPresent()) {
			return _getDefaultCTPersistenceHelper();
		}

		CTCollection ctCollection = ctCollectionOptional.get();

		if (ctCollection.isProduction()) {
			return _getDefaultCTPersistenceHelper();
		}

		CTAdapterBag<?, ?> ctAdapterHolder = _ctAdapterBags.get(
			_classNameLocalService.getClassNameId(modelClass));

		if (ctAdapterHolder == null) {
			return _getDefaultCTPersistenceHelper();
		}

		return new CTPersistenceHelperImpl<>(
			ctCollectionOptional.get(), ctAdapterHolder);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends BaseModel<T>, C extends BaseModel<C>> CTAdapterBag<T, C>
		getCTAdapterBag(long classNameId) {

		return (CTAdapterBag<T, C>)_ctAdapterBags.get(classNameId);
	}

	@Override
	public long getCTCollectionId() {
		ActiveCTCollectionHolder activeCTCollectionHolder =
			_activeCTCollectionHolderThreadLocal.get();

		Optional<CTCollection> ctCollectionOptional =
			activeCTCollectionHolder.getActiveCTCollectionOptional();

		if (!ctCollectionOptional.isPresent()) {
			return 0;
		}

		CTCollection ctCollection = ctCollectionOptional.get();

		return ctCollection.getCtCollectionId();
	}

	@Override
	public <T, E extends Throwable> T getInProduction(
			UnsafeSupplier<T, E> unsafeSupplier)
		throws E {

		ActiveCTCollectionHolder activeCTCollectionHolder =
			_activeCTCollectionHolderThreadLocal.get();

		try {
			_activeCTCollectionHolderThreadLocal.set(
				_emptyActiveCTCollectionHolder);

			return unsafeSupplier.get();
		}
		finally {
			_activeCTCollectionHolderThreadLocal.set(activeCTCollectionHolder);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_activeCTCollectionHolderThreadLocal = new CentralizedThreadLocal<>(
			CTPersistenceHelperFactoryImpl.class.getName() +
				"_activeCTCollectionHolderThreadLocal",
			ValidatingActiveCTCollectionHolder::new);

		_serviceTracker = new ServiceTracker<>(
			bundleContext, CTServiceAdapter.class,
			new CTAdapterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@SuppressWarnings("unchecked")
	private <T extends BaseModel<T>> CTPersistenceHelper<T>
		_getDefaultCTPersistenceHelper() {

		return (CTPersistenceHelper<T>)_defaultCTPersistenceHelper;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPersistenceHelperFactoryImpl.class);

	private static final Map<Long, CTAdapterBag> _ctAdapterBags =
		new ConcurrentHashMap<>();

	@SuppressWarnings("rawtypes")
	private static final CTPersistenceHelper _defaultCTPersistenceHelper =
		new DefaultCTPersistenceHelperImpl<>();

	private static final EmptyActiveCTCollectionHolder
		_emptyActiveCTCollectionHolder = new EmptyActiveCTCollectionHolder();

	private ThreadLocal<ActiveCTCollectionHolder>
		_activeCTCollectionHolderThreadLocal;
	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTManager _ctManager;

	private ServiceTracker<?, ?> _serviceTracker;

	private static class DefaultCTPersistenceHelperImpl<T extends BaseModel<T>>
		implements CTPersistenceHelper<T> {

		@Override
		public Object[] appendFinderArgs(Object[] finderArgs) {
			return finderArgs;
		}

		@Override
		public String appendSQL(String tableName, String sql) {
			return StringBundler.concat(
				sql, " AND ", tableName, ".ctCollectionId = 0 ");
		}

		@Override
		public StringBundler appendSQL(String tableName, StringBundler sb) {
			sb.append(" AND ");
			sb.append(tableName);
			sb.append(".ctCollectionId = 0 ");

			return sb;
		}

		@Override
		public boolean isValidFinderResult(T baseModel) {
			return true;
		}

		@Override
		public List<T> populate(List<T> baseModels) {
			return baseModels;
		}

		@Override
		public T populate(T baseModel) {
			return baseModel;
		}

		@Override
		public void remove(T baseModel) {
		}

		@Override
		public void update(T baseModel) {
		}

	}

	private static class EmptyActiveCTCollectionHolder
		implements ActiveCTCollectionHolder {

		@Override
		public Optional<CTCollection> getActiveCTCollectionOptional() {
			return Optional.empty();
		}

	}

	private interface ActiveCTCollectionHolder {

		public Optional<CTCollection> getActiveCTCollectionOptional();

	}

	private class CTAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<CTServiceAdapter, CTAdapterBag> {

		@Override
		public CTAdapterBag addingService(
			ServiceReference<CTServiceAdapter> serviceReference) {

			CTServiceAdapter<?, ?> ctServiceAdapter = _bundleContext.getService(
				serviceReference);

			return _addingService(ctServiceAdapter);
		}

		@Override
		public void modifiedService(
			ServiceReference<CTServiceAdapter> serviceReference,
			CTAdapterBag ctAdapterBag) {
		}

		@Override
		public void removedService(
			ServiceReference<CTServiceAdapter> serviceReference,
			CTAdapterBag ctAdapterBag) {

			_ctAdapterBags.remove(ctAdapterBag.getClassNameId());

			_bundleContext.ungetService(serviceReference);
		}

		private <T extends BaseModel<T>, C extends BaseModel<C>>
			CTAdapterBag<T, C> _addingService(
				CTServiceAdapter<T, C> ctServiceAdapter) {

			CTModelAdapter<T, C> ctModelAdapter =
				ctServiceAdapter.getCTModelAdapter();

			long classNameId = _classNameLocalService.getClassNameId(
				ctModelAdapter.getModelClass());

			CTAdapterBag<T, C> ctAdapterBag = new CTAdapterBag<>(
				ctServiceAdapter, ctModelAdapter, classNameId);

			_ctAdapterBags.put(classNameId, ctAdapterBag);

			return ctAdapterBag;
		}

	}

	private class CTPersistenceHelperImpl
		<T extends BaseModel<T>, C extends BaseModel<C>>
			implements CTPersistenceHelper<T> {

		@Override
		public Object[] appendFinderArgs(Object[] finderArgs) {
			List<CTEntry> ctEntries = _getCTEntries();

			if (ctEntries.isEmpty()) {
				Object[] newFinderArgs = new Object[finderArgs.length + 1];

				System.arraycopy(
					finderArgs, 0, newFinderArgs, 0, finderArgs.length);

				newFinderArgs[finderArgs.length] =
					_ctCollection.getCtCollectionId();

				return newFinderArgs;
			}

			Object[] newFinderArgs = new Object[finderArgs.length + 2];

			System.arraycopy(
				finderArgs, 0, newFinderArgs, 0, finderArgs.length);

			newFinderArgs[finderArgs.length] =
				_ctCollection.getCtCollectionId();

			newFinderArgs[finderArgs.length + 1] = StringUtil.merge(
				ctEntries, ctEntry -> String.valueOf(ctEntry.getModelClassPK()),
				StringPool.COMMA);

			return newFinderArgs;
		}

		@Override
		public String appendSQL(String tableName, String sql) {
			List<CTEntry> ctEntries = _getCTEntries();

			StringBundler sb = new StringBundler(2 * ctEntries.size() + 12);

			sb.append(sql);

			sb = appendSQL(tableName, sb);

			return sb.toString();
		}

		@Override
		public StringBundler appendSQL(String tableName, StringBundler sb) {
			List<CTEntry> ctEntries = _getCTEntries();

			sb.append(" AND (");
			sb.append(tableName);
			sb.append(".ctCollectionId = 0 OR ");
			sb.append(tableName);
			sb.append(".ctCollectionId = ");
			sb.append(_ctCollection.getCtCollectionId());

			if (!ctEntries.isEmpty()) {
				CTModelAdapter<T, C> ctModelAdapter =
					_ctAdapterBag.getCTModelAdapter();

				sb.append(") AND ");
				sb.append(tableName);
				sb.append(".");
				sb.append(ctModelAdapter.getPrimaryKeyColumnDBName());
				sb.append(" NOT IN (");

				for (CTEntry ctEntry : ctEntries) {
					sb.append(ctEntry.getModelClassPK());
					sb.append(",");
				}

				sb.setIndex(sb.index() - 1);
			}

			sb.append(") ");

			return sb;
		}

		@Override
		public boolean isValidFinderResult(T baseModel) {
			CTModelAdapter<T, C> ctModelAdapter =
				_ctAdapterBag.getCTModelAdapter();

			long ctCollectionId = ctModelAdapter.getCTCollectionId(baseModel);

			if (ctCollectionId == _ctCollection.getCtCollectionId()) {
				return true;
			}

			if (ctCollectionId != 0) {
				return false;
			}

			List<CTEntry> ctEntries = _getCTEntries();

			if (!ctEntries.isEmpty()) {
				long primaryKey = ctModelAdapter.getPrimaryKey(baseModel);

				for (CTEntry ctEntry : ctEntries) {
					if (primaryKey == ctEntry.getModelClassPK()) {
						return false;
					}
				}
			}

			return true;
		}

		@Override
		public List<T> populate(List<T> baseModels) {
			if (baseModels.isEmpty()) {
				return baseModels;
			}

			CTModelAdapter<T, C> ctModelAdapter =
				_ctAdapterBag.getCTModelAdapter();

			long[] primaryKeys = new long[baseModels.size()];

			int size = 0;

			for (T baseModel : baseModels) {
				if (ctModelAdapter.getCTCollectionId(baseModel) == 0) {
					primaryKeys[size++] = ctModelAdapter.getPrimaryKey(
						baseModel);
				}
			}

			if (size == 0) {
				return baseModels;
			}

			if (size < baseModels.size()) {
				primaryKeys = Arrays.copyOfRange(primaryKeys, 0, size);
			}

			CTServiceAdapter<T, C> ctServiceAdapter =
				_ctAdapterBag.getCTServiceAdapter();

			List<C> contextModels = ctServiceAdapter.fetchModelCTs(
				primaryKeys, _ctCollection.getCtCollectionId());

			if (contextModels.isEmpty()) {
				return baseModels;
			}

			Map<Long, C> contextModelMap = new HashMap<>();

			for (C contextModel : contextModels) {
				contextModelMap.put(
					ctModelAdapter.getModelPrimaryKey(contextModel),
					contextModel);
			}

			for (T baseModel : baseModels) {
				C contextModel = contextModelMap.get(
					ctModelAdapter.getPrimaryKey(baseModel));

				if (contextModel != null) {
					ctModelAdapter.populateModel(baseModel, contextModel);
				}
			}

			return baseModels;
		}

		@Override
		public T populate(T baseModel) {
			if (baseModel == null) {
				return null;
			}

			CTModelAdapter<T, C> ctModelAdapter =
				_ctAdapterBag.getCTModelAdapter();

			if (ctModelAdapter.getCTCollectionId(baseModel) != 0) {
				return baseModel;
			}

			CTServiceAdapter<T, C> ctServiceAdapter =
				_ctAdapterBag.getCTServiceAdapter();

			C contextModel = ctServiceAdapter.fetchModelCT(
				ctModelAdapter.getPrimaryKey(baseModel),
				_ctCollection.getCtCollectionId());

			if (contextModel != null) {
				ctModelAdapter.populateModel(baseModel, contextModel);
			}

			return baseModel;
		}

		@Override
		public void remove(T baseModel) {
			CTModelAdapter<T, C> ctModelAdapter =
				_ctAdapterBag.getCTModelAdapter();
			CTServiceAdapter<T, C> ctServiceAdapter =
				_ctAdapterBag.getCTServiceAdapter();

			ctServiceAdapter.removeModelCTs(
				ctModelAdapter.getPrimaryKey(baseModel));
		}

		@Override
		public void update(T baseModel) {
			CTModelAdapter<T, C> ctModelAdapter =
				_ctAdapterBag.getCTModelAdapter();
			CTServiceAdapter<T, C> ctServiceAdapter =
				_ctAdapterBag.getCTServiceAdapter();

			int changeType = CTConstants.CT_CHANGE_TYPE_MODIFICATION;

			if (baseModel.isNew()) {
				changeType = CTConstants.CT_CHANGE_TYPE_ADDITION;

				ctModelAdapter.setModelCTCollectionId(
					baseModel, _ctCollection.getCtCollectionId());
			}
			else {
				if (ctModelAdapter.getCTCollectionId(baseModel) != 0) {
					return;
				}

				long primaryKey = ctModelAdapter.getPrimaryKey(baseModel);

				C modelCT = ctServiceAdapter.fetchModelCT(
					primaryKey, _ctCollection.getCtCollectionId());

				if (modelCT == null) {
					modelCT = ctServiceAdapter.createModelCT(
						primaryKey, _ctCollection.getCtCollectionId());
				}

				ctModelAdapter.populateModelCT(baseModel, modelCT);

				ctServiceAdapter.updateModelCT(modelCT);

				T oldModel = getInProduction(
					() -> ctServiceAdapter.fetchByPrimaryKey(primaryKey));

				if (oldModel != null) {
					C oldModelCT = ctServiceAdapter.createModelCT(
						primaryKey, _ctCollection.getCtCollectionId());

					ctModelAdapter.populateModelCT(oldModel, oldModelCT);

					ctModelAdapter.populateModel(baseModel, oldModelCT);
				}
			}

			try {
				_ctManager.registerModelChange(
					_ctCollection, _ctCollection.getUserId(),
					_ctAdapterBag.getClassNameId(),
					ctModelAdapter.getPrimaryKey(baseModel), 0, changeType,
					false);
			}
			catch (CTEngineException ctee) {
				if (_log.isWarnEnabled()) {
					_log.warn(ctee, ctee);
				}
			}
		}

		@SuppressWarnings("unchecked")
		private CTPersistenceHelperImpl(
			CTCollection ctCollection, CTAdapterBag<?, ?> ctAdapterBag) {

			_ctCollection = ctCollection;
			_ctAdapterBag = (CTAdapterBag<T, C>)ctAdapterBag;
		}

		private List<CTEntry> _getCTEntries() {
			if (_ctEntries == null) {
				_ctEntries = _ctManager.getCTCollectionCTEntries(
					_ctCollection.getCompanyId(),
					_ctCollection.getCtCollectionId(),
					_ctAdapterBag.getClassNameId());
			}

			return _ctEntries;
		}

		private final CTAdapterBag<T, C> _ctAdapterBag;
		private final CTCollection _ctCollection;
		private List<CTEntry> _ctEntries;

	}

	private class ValidatingActiveCTCollectionHolder
		implements ActiveCTCollectionHolder {

		@Override
		public Optional<CTCollection> getActiveCTCollectionOptional() {
			long companyId = CompanyThreadLocal.getCompanyId();
			long userId = PrincipalThreadLocal.getUserId();

			if ((_ctCollectionOptional == null) || (_companyId != companyId) ||
				(_userId != userId)) {

				_ctCollectionOptional =
					_ctManager.getActiveCTCollectionOptional(companyId, userId);

				_companyId = companyId;
				_userId = userId;
			}

			return _ctCollectionOptional;
		}

		private long _companyId;
		private Optional<CTCollection> _ctCollectionOptional;
		private long _userId;

	}

}