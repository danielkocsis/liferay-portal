package com.liferay.journal.internal.exportimport.staged.model.reference.processor;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.reference.processor.StageModelReferenceProcessor;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import org.osgi.service.component.annotations.Component;

import java.util.Optional;

/**
 * @author Daniel Kocsis
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = {StageModelReferenceProcessor.class}
)
public class JournalFolderStagedModelReferenceProcess implements
	StageModelReferenceProcessor<JournalArticle, JournalFolder> {

	@Override
	public String getReferenceType() {
		return PortletDataContext.REFERENCE_TYPE_DEPENDENCY;
	}

	@Override
	public Optional<StagedModel> process(
		PortletDataContext portletDataContext, JournalArticle article) {

		try {
			return Optional.ofNullable(article.getFolder());
		}
		catch (PortalException e) {
			return Optional.empty();
		}
	}

}
