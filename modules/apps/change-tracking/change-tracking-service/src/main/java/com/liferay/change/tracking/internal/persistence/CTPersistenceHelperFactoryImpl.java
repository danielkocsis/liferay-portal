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
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.change.tracking.CTAdapter;
import com.liferay.portal.change.tracking.persistence.CTPersistenceHelper;
import com.liferay.portal.change.tracking.persistence.CTPersistenceHelperFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = CTPersistenceHelperFactory.class)
public class CTPersistenceHelperFactoryImpl
	implements CTPersistenceHelperFactory {

	@Override
	public <T extends BaseModel<T>> CTPersistenceHelper<T> create(
		Class<T> modelClass) {

		Optional<CTCollection> ctCollectionOptional =
			_activeCTCollectionOptionalThreadLocal.get();

		if (!ctCollectionOptional.isPresent()) {
			return new EmptyCTPersistenceHelperImpl<>();
		}

		CTAdapterHolder ctAdapterHolder = _ctAdapterHolders.get(modelClass);

		if (ctAdapterHolder == null) {
			return new EmptyCTPersistenceHelperImpl<>();
		}

		return new CTPersistenceHelperImpl<>(
			ctCollectionOptional.get(), ctAdapterHolder);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_activeCTCollectionOptionalThreadLocal = new CentralizedThreadLocal<>(
			CTPersistenceHelperFactoryImpl.class.getName() +
				"_activeCTCollectionOptionalThreadLocal",
			() -> _ctManager.getActiveCTCollectionOptional(
				CompanyThreadLocal.getCompanyId(),
				PrincipalThreadLocal.getUserId()));

		_serviceTracker = new ServiceTracker<>(
			bundleContext, CTAdapter.class,
			new CTAdapterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPersistenceHelperFactoryImpl.class);

	private static final Map<Class<?>, CTAdapterHolder> _ctAdapterHolders =
		new ConcurrentHashMap<>();

	private ThreadLocal<Optional<CTCollection>>
		_activeCTCollectionOptionalThreadLocal;
	private BundleContext _bundleContext;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTManager _ctManager;

	private ServiceTracker<?, ?> _serviceTracker;

	private static class CTAdapterHolder {

		private CTAdapterHolder(
			CTAdapter<?, ?> ctAdapter,
			ServiceRegistration<ModelListener> modelListenerServiceRegistration,
			long classNameId) {

			_ctAdapter = ctAdapter;
			_modelListenerServiceRegistration =
				modelListenerServiceRegistration;
			_classNameId = classNameId;
		}

		private final long _classNameId;
		private final CTAdapter<?, ?> _ctAdapter;
		private final ServiceRegistration<ModelListener>
			_modelListenerServiceRegistration;

	}

	private static class EmptyCTPersistenceHelperImpl<T extends BaseModel<T>>
		implements CTPersistenceHelper<T> {

		@Override
		public Object[] appendContextFinderArgs(Object[] finderArgs) {
			return finderArgs;
		}

		@Override
		public void appendContextSQL(StringBundler sb) {
		}

		@Override
		public boolean isValidFinderResult(T baseModel) {
			return true;
		}

		@Override
		public void setContext(T baseModel) {
		}

		@Override
		public void setContexts(List<T> baseModels) {
		}

	}

	private class CTAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<CTAdapter, CTAdapterHolder> {

		@Override
		public CTAdapterHolder addingService(
			ServiceReference<CTAdapter> serviceReference) {

			CTAdapter<?, ?> ctAdapter = _bundleContext.getService(
				serviceReference);

			Class<? extends BaseModel<?>> modelClass =
				ctAdapter.getModelClass();

			long classNameId = _classNameLocalService.getClassNameId(
				modelClass);

			ServiceRegistration<ModelListener> serviceRegistration =
				_bundleContext.registerService(
					ModelListener.class,
					new CTModelListener<>(ctAdapter, classNameId),
					MapUtil.singletonDictionary(
						"model.class.name", modelClass.getName()));

			CTAdapterHolder ctAdapterHolder = new CTAdapterHolder(
				ctAdapter, serviceRegistration, classNameId);

			_ctAdapterHolders.put(modelClass, ctAdapterHolder);

			return ctAdapterHolder;
		}

		@Override
		public void modifiedService(
			ServiceReference<CTAdapter> serviceReference,
			CTAdapterHolder ctAdapterHolder) {
		}

		@Override
		public void removedService(
			ServiceReference<CTAdapter> serviceReference,
			CTAdapterHolder ctAdapterHolder) {

			ctAdapterHolder._modelListenerServiceRegistration.unregister();

			_ctAdapterHolders.remove(
				ctAdapterHolder._ctAdapter.getModelClass());

			_bundleContext.ungetService(serviceReference);
		}

	}

	private class CTModelListener
		<T extends BaseModel<T>, C extends BaseModel<C>>
			extends BaseModelListener<T> {

		@Override
		public void onAfterCreate(T model) {
			Optional<CTCollection> ctCollectionOptional =
				_activeCTCollectionOptionalThreadLocal.get();

			if (ctCollectionOptional.isPresent()) {
				CTCollection ctCollection = ctCollectionOptional.get();

				try {
					_ctManager.registerModelChange(
						ctCollection.getCompanyId(), ctCollection.getUserId(),
						_classNameId, _ctAdapter.getPrimaryKey(model), 0,
						CTConstants.CT_CHANGE_TYPE_ADDITION);
				}
				catch (CTEngineException ctee) {
					if (_log.isWarnEnabled()) {
						_log.warn(ctee, ctee);
					}
				}
			}
		}

		@Override
		public void onAfterRemove(T model) {
			_ctAdapter.removeContexts(model);

			Optional<CTCollection> ctCollectionOptional =
				_activeCTCollectionOptionalThreadLocal.get();

			if (ctCollectionOptional.isPresent()) {
				CTCollection ctCollection = ctCollectionOptional.get();

				try {
					_ctManager.registerModelChange(
						ctCollection.getCompanyId(), ctCollection.getUserId(),
						_classNameId, _ctAdapter.getPrimaryKey(model), 0,
						CTConstants.CT_CHANGE_TYPE_DELETION);
				}
				catch (CTEngineException ctee) {
					if (_log.isWarnEnabled()) {
						_log.warn(ctee, ctee);
					}
				}
			}
		}

		@Override
		public void onAfterUpdate(T model) {
			Optional<CTCollection> ctCollectionOptional =
				_activeCTCollectionOptionalThreadLocal.get();

			if (ctCollectionOptional.isPresent()) {
				CTCollection ctCollection = ctCollectionOptional.get();

				C contextModel = _ctAdapter.fetchContextModel(
					_ctAdapter.getPrimaryKey(model),
					ctCollection.getCtCollectionId());

				_ctAdapter.populateModel(model, contextModel);

				try {
					_ctManager.registerModelChange(
						ctCollection.getCompanyId(), ctCollection.getUserId(),
						_classNameId, _ctAdapter.getPrimaryKey(model), 0,
						CTConstants.CT_CHANGE_TYPE_MODIFICATION);
				}
				catch (CTEngineException ctee) {
					if (_log.isWarnEnabled()) {
						_log.warn(ctee, ctee);
					}
				}
			}
		}

		@Override
		public void onBeforeCreate(T model) {
			Optional<CTCollection> ctCollectionOptional =
				_activeCTCollectionOptionalThreadLocal.get();

			if (ctCollectionOptional.isPresent()) {
				CTCollection ctCollection = ctCollectionOptional.get();

				_ctAdapter.setModelCTCollectionId(
					model, ctCollection.getCtCollectionId());
			}
		}

		@Override
		public void onBeforeUpdate(T model) {
			Optional<CTCollection> ctCollectionOptional =
				_activeCTCollectionOptionalThreadLocal.get();

			if (ctCollectionOptional.isPresent()) {
				CTCollection ctCollection = ctCollectionOptional.get();

				C contextModel = _ctAdapter.fetchContextModel(
					_ctAdapter.getPrimaryKey(model),
					ctCollection.getCtCollectionId());

				if (contextModel == null) {
					contextModel = _ctAdapter.createContextModel(model);

					_ctAdapter.populateContextModel(model, contextModel);

					_ctAdapter.setModelContextCTCollectionId(
						contextModel, ctCollection.getCtCollectionId());
				}
				else {
					_ctAdapter.populateContextModel(model, contextModel);
				}

				_ctAdapter.updateContextModel(contextModel);

				T oldModel = _ctAdapter.fetchByPrimaryKey(
					_ctAdapter.getPrimaryKey(model));

				if (oldModel != null) {
					_ctAdapter.populateContextModel(oldModel, contextModel);

					_ctAdapter.populateModel(model, contextModel);
				}
			}
		}

		@SuppressWarnings("unchecked")
		private CTModelListener(CTAdapter<?, ?> ctAdapter, long classNameId) {
			_ctAdapter = (CTAdapter<T, C>)ctAdapter;
			_classNameId = classNameId;
		}

		private final long _classNameId;
		private final CTAdapter<T, C> _ctAdapter;

	}

	private class CTPersistenceHelperImpl
		<T extends BaseModel<T>, C extends BaseModel<C>>
			implements CTPersistenceHelper<T> {

		@Override
		public Object[] appendContextFinderArgs(Object[] finderArgs) {
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
		public void appendContextSQL(StringBundler sb) {
			sb.append(" AND (");
			sb.append(_ctAdapter.getTableName());
			sb.append(".ctCollectionId = 0 OR ");
			sb.append(_ctAdapter.getTableName());
			sb.append(".ctCollectionId = ");
			sb.append(_ctCollection.getCtCollectionId());

			List<CTEntry> ctEntries = _getCTEntries();

			if (ctEntries.isEmpty()) {
				sb.append(") AND ");
				sb.append(_ctAdapter.getTableName());
				sb.append(".");
				sb.append(_ctAdapter.getPrimaryKeyColumnName());
				sb.append(" NOT IN (");

				for (CTEntry ctEntry : ctEntries) {
					sb.append(ctEntry.getModelClassPK());
					sb.append(",");
				}

				sb.setIndex(sb.index() - 1);
			}

			sb.append(") ");
		}

		@Override
		public boolean isValidFinderResult(T baseModel) {
			long ctCollectionId = _ctAdapter.getModelCTCollectionId(baseModel);

			if (ctCollectionId == _ctCollection.getCtCollectionId()) {
				return true;
			}

			if (ctCollectionId != 0) {
				return false;
			}

			List<CTEntry> ctEntries = _getCTEntries();

			if (!ctEntries.isEmpty()) {
				long primaryKey = _ctAdapter.getPrimaryKey(baseModel);

				for (CTEntry ctEntry : ctEntries) {
					if (primaryKey == ctEntry.getModelClassPK()) {
						return false;
					}
				}
			}

			return true;
		}

		@Override
		public void setContext(T baseModel) {
			C contextModel = _ctAdapter.fetchContextModel(
				_ctAdapter.getPrimaryKey(baseModel),
				_ctCollection.getCtCollectionId());

			_ctAdapter.populateModel(baseModel, contextModel);
		}

		@Override
		public void setContexts(List<T> baseModels) {
			long[] primaryKeys = ListUtil.toLongArray(
				baseModels, _ctAdapter::getPrimaryKey);

			List<C> contextModels = _ctAdapter.fetchContextModels(
				primaryKeys, _ctCollection.getCtCollectionId());

			Map<Long, C> contextModelMap = new HashMap<>();

			for (C contextModel : contextModels) {
				contextModelMap.put(
					_ctAdapter.getModelPrimaryKey(contextModel), contextModel);
			}

			for (T baseModel : baseModels) {
				C contextModel = contextModelMap.get(
					_ctAdapter.getPrimaryKey(baseModel));

				if (contextModel != null) {
					_ctAdapter.populateModel(baseModel, contextModel);
				}
			}
		}

		@SuppressWarnings("unchecked")
		private CTPersistenceHelperImpl(
			CTCollection ctCollection, CTAdapterHolder ctAdapterHolder) {

			_ctCollection = ctCollection;
			_ctAdapter = (CTAdapter<T, C>)ctAdapterHolder._ctAdapter;
			_classNameId = ctAdapterHolder._classNameId;
		}

		private List<CTEntry> _getCTEntries() {
			if (_ctEntries == null) {
				_ctEntries = _ctManager.getCTCollectionCTEntries(
					_ctCollection.getCompanyId(),
					_ctCollection.getCtCollectionId(), _classNameId);
			}

			return _ctEntries;
		}

		private final long _classNameId;
		private final CTAdapter<T, C> _ctAdapter;
		private final CTCollection _ctCollection;
		private List<CTEntry> _ctEntries;

	}

}