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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.manifest.ManifestTreeNode;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.sitesadmin.lar.StagedGroup;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Mate Thurzo
 */
public class ExportImportController {

	public static ExportImportController getInstance() {
		return _instance;
	}

	public void export(ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);

		final PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				exportImportConfiguration.getCompanyId(),
				exportImportConfiguration.getGroupId(), parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate(),
				ZipWriterFactoryUtil.getZipWriter());

		// TODO prepare phase

		//portletDataContext.prepareInContext(exportImportConfiguration).map(stagedModel -> stagedModels.add(stagedModel))

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		StagedModel rootStagedModel = ModelAdapterUtil.adapt(
			group, StagedGroup.class);

		ManifestSummary manifestSummary = portletDataContext.prepareInContext(
			rootStagedModel);

		ManifestTreeNode[] sortedTreeNodes = sortManifestTreeNodes(
			manifestSummary);

		for (ManifestTreeNode manifestTreeNode : sortedTreeNodes) {
			System.out.println(manifestTreeNode);
		}

		// TODO export

		/*List<StagedModel> stagedModels = null;

		stagedModels.parallelStream().forEach(stagedModel -> portletDataContext.exportInContext(stagedModel));*/
	}

	protected void buildHeap(ManifestTreeNode[] nodes) {
		int n = nodes.length - 1;

		for (int i = n/2; i >= 0; i--) {
			maxHeap(n, nodes, i);
		}
	}

	protected void exchange(
		ManifestTreeNode[] manifestTreeNodes, int i, int j) {

		ManifestTreeNode node = manifestTreeNodes[i];
		manifestTreeNodes[i] = manifestTreeNodes[j];
		manifestTreeNodes[j] = node;
	}

	protected ManifestTreeNode[] heapSort(
		Collection<ManifestTreeNode> manifestTreeNodes) {

		ManifestTreeNode[] nodesArray = manifestTreeNodes.toArray(
			new ManifestTreeNode[manifestTreeNodes.size()]);

		buildHeap(nodesArray);

		int maxIndex = nodesArray.length - 1;

		for (int i = maxIndex; i > 0; i--) {
			exchange(nodesArray, 0, i);
			maxIndex--;
			maxHeap(maxIndex, nodesArray, 0);
		}

		return nodesArray;
	}

	protected void maxHeap(int n, ManifestTreeNode[] manifestTreeNodes, int i) {
		int left = 2 * i;

		int right = left + 1;
		int largest = -1;

		if ((left <= n) &&
			(manifestTreeNodes[left].getLabel() >
				manifestTreeNodes[i].getLabel())) {

			largest = left;
		}
		else {
			largest = i;
		}

		if ((right <= n) &&
			(manifestTreeNodes[right].getLabel() >
				manifestTreeNodes[largest].getLabel())) {

			largest = right;
		}

		if (largest != i) {
			exchange(manifestTreeNodes, i, largest);
			maxHeap(n, manifestTreeNodes, largest);
		}
	}

	protected ManifestTreeNode[] sortManifestTreeNodes(
		ManifestSummary manifestSummary) {

		ManifestTreeNode rootManifestTreeNode =
			manifestSummary.getRootManifestTreeNode();

		return heapSort(topologicalSort(rootManifestTreeNode));
	}

	protected List<ManifestTreeNode> topologicalSort(
		ManifestTreeNode rootNode) {

		Stack<ManifestTreeNode> sortedNodes = new Stack<>();
		Set<ManifestTreeNode> markedNodes = new HashSet<>();
		Set<ManifestTreeNode> tempMarkedNodes = new HashSet<>();

		visit(tempMarkedNodes, markedNodes, sortedNodes, rootNode, 0);

		return sortedNodes;
	}

	protected void visit(
		Set<ManifestTreeNode> tempMarkedNodes,
		Set<ManifestTreeNode> markedNodes, Stack<ManifestTreeNode> sortedNodes,
		ManifestTreeNode manifestTreeNode, int depth) {

		if (tempMarkedNodes.contains(manifestTreeNode)) {
			throw new SystemException("Not a DAG");
		}

		tempMarkedNodes.add(manifestTreeNode);

		if (markedNodes.contains(manifestTreeNode)) {
			return;
		}

		manifestTreeNode.setLabel(depth);

		for (ManifestTreeNode dependentNode : manifestTreeNode.getChildren()) {
			visit(
				tempMarkedNodes, markedNodes, sortedNodes, dependentNode,
				depth++);
		}

		markedNodes.add(manifestTreeNode);
		tempMarkedNodes.remove(manifestTreeNode);
		sortedNodes.push(manifestTreeNode);
	}

	private ExportImportController() {
	}

	private static final ExportImportController _instance =
		new ExportImportController();

}