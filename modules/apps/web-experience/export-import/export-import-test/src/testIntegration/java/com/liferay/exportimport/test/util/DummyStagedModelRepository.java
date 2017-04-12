package com.liferay.exportimport.test.util;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.exportimport.test.util.Dummy"},
	service = StagedModelRepository.class
)
public class DummyStagedModelRepository
	implements StagedModelRepository<Dummy> {

	private List<Dummy> _dummies;


	@Override
	public Dummy addStagedModel(
			PortletDataContext portletDataContext, Dummy dummy)
		throws PortalException {

		_dummies.add(dummy);

		return dummy;
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_dummies.removeIf(
			dummy ->
				dummy.getUuid().equals(uuid) && dummy.getGroupId() == groupId);
	}

	@Override
	public void deleteStagedModel(Dummy dummy) throws PortalException {
		_dummies.remove(dummy);
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_dummies.clear();
	}

	@Override
	public Dummy fetchMissingReference(String uuid, long groupId) {
		return fetchStagedModelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public Dummy fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		List<Dummy> dummies = _dummies.stream().filter(
			dummy ->
				dummy.getUuid().equals(uuid) &&
					dummy.getGroupId() == groupId).
			collect(Collectors.toList());

		if (dummies.size() == 0) {
			return null;
		}

		return dummies.get(0);
	}

	@Override
	public List<Dummy> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _dummies.stream().filter(
			dummy ->
				dummy.getUuid().equals(uuid) &&
				dummy.getCompanyId() == companyId).
			collect(Collectors.toList());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
			@Override
			public long performCount() throws PortalException {
				ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

				StagedModelType stagedModelType = getStagedModelType();

				long modelAdditionCount = _dummies.size();

				manifestSummary.addModelAdditionCount(stagedModelType,
					modelAdditionCount);

				manifestSummary.addModelDeletionCount(stagedModelType, 0);

				return modelAdditionCount;
			}

			@Override
			protected Projection getCountProjection() {
				return ProjectionFactoryUtil.countDistinct(
					"resourcePrimKey");
			}
		};

		//exportActionableDynamicQuery.setBaseLocalService(journalArticleLocalService);
		exportActionableDynamicQuery.setClassLoader(getClass().getClassLoader());
		exportActionableDynamicQuery.setModelClass(Dummy.class);

		exportActionableDynamicQuery.setPrimaryKeyPropertyName("id");

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
			@Override
			public void addCriteria(DynamicQuery dynamicQuery) {
				Criterion modifiedDateCriterion = portletDataContext.getDateRangeCriteria(
					"modifiedDate");

				if (modifiedDateCriterion != null) {
					Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

					conjunction.add(modifiedDateCriterion);

					Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

					disjunction.add(RestrictionsFactoryUtil.gtProperty(
						"modifiedDate", "lastPublishDate"));

					Property lastPublishDateProperty = PropertyFactoryUtil.forName(
						"lastPublishDate");

					disjunction.add(lastPublishDateProperty.isNull());

					conjunction.add(disjunction);

					modifiedDateCriterion = conjunction;
				}

				Criterion statusDateCriterion = portletDataContext.getDateRangeCriteria(
					"statusDate");

				if ((modifiedDateCriterion != null) &&
					(statusDateCriterion != null)) {
					Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

					disjunction.add(modifiedDateCriterion);
					disjunction.add(statusDateCriterion);

					dynamicQuery.add(disjunction);
				}

				StagedModelType stagedModelType = exportActionableDynamicQuery.getStagedModelType();

				long referrerClassNameId = stagedModelType.getReferrerClassNameId();

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				if ((referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
					(referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {
					dynamicQuery.add(classNameIdProperty.eq(
						stagedModelType.getReferrerClassNameId()));
				}
				else if (referrerClassNameId == StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {
					dynamicQuery.add(classNameIdProperty.isNotNull());
				}

				Property workflowStatusProperty = PropertyFactoryUtil.forName(
					"status");

				if (portletDataContext.isInitialPublication()) {
					dynamicQuery.add(workflowStatusProperty.ne(
						WorkflowConstants.STATUS_IN_TRASH));
				}
				else {
					StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(Dummy.class.getName());

					dynamicQuery.add(workflowStatusProperty.in(
						stagedModelDataHandler.getExportableStatuses()));
				}
			}
		});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Dummy>() {
			@Override
			public void performAction(Dummy dummy)
				throws PortalException {
				StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
					dummy);
			}
		});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
			PortalUtil.getClassNameId(Dummy.class.getName()),
			StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	@Override
	public void restoreStagedModel(PortletDataContext portletDataContext, Dummy stagedModel) throws PortletDataException {

	}

	@Override
	public Dummy saveStagedModel(Dummy stagedModel) throws PortalException {
		return null;
	}

	@Override
	public Dummy updateStagedModel(PortletDataContext portletDataContext, Dummy stagedModel) throws PortalException {
		return null;
	}
}
