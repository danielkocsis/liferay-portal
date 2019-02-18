import ClayManagementToolbar from 'clay-management-toolbar';

import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './ChangeListsHistory.soy';


/**
 * Handles the tags of the selected
 * fileEntries inside a modal.
 */
class ChangeListsHistory extends PortletBase {

	created() {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		let init = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		fetch(this.urlProcesses, init)
			.then(r => r.json())
			.then(response => this._populateProcessEntries(response))
			.then(() => this.loaded = true)
			.catch(
				error => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Util.sub(Liferay.Language.get('an-error-occured-while-getting-data-from-x'), this.urlProcesses);

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);
				}
			);
	}

	_populateProcessEntries(processEntries) {
		this.processEntries = [];

		processEntries.forEach(
			processEntry => {
				this.processEntries.push(
					{
						name: processEntry.CTCollection.name,
						description: processEntry.CTCollection.description,
						timestamp: new Intl.DateTimeFormat(
							Liferay.ThemeDisplay.getBCP47LanguageId(),
							{
								day: 'numeric',
								hour: 'numeric',
								minute: 'numeric',
								month: 'numeric',
								year: 'numeric'
							}).format(processEntry.date),
						state: 'published',
						userName: processEntry.userName
					}
				);
			}
		);
	}

}

/**
 * State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
ChangeListsHistory.STATE = {

	urlProcesses: Config.string(),

	processEntries: Config.arrayOf(
		Config.shapeOf(
			{
				name: Config.string(),
				description: Config.string(),
				timestamp: Config.string(),
				userName: Config.string(),
				state: Config.string()
			}
		)
	),

	loaded: Config.bool().value(false),

	/**
	 * Path to images.
	 *
	 * @instance
	 * @memberOf ChangeListsHistory
	 * @review
	 * @type {String}
	 */
	spritemap: Config.string().required()

};

// Register component

Soy.register(ChangeListsHistory, templates);

export {ChangeListsHistory};
export default ChangeListsHistory;