package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.ServerSideCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Tree;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.01.13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class TreeSubissueSortDropHandler implements DropHandler {
    private final Tree tree;
    private boolean activated = false;

    private final Map<IssueBase, List<IssueBase>> changedIssues;

    private final AcceptCriterion declineAll = new ServerSideCriterion() {
        @Override
        public boolean accept(DragAndDropEvent dragEvent) {
            return false;
        }
    };

    private final AcceptCriterion acceptAll = new ServerSideCriterion() {
        @Override
        public boolean accept(DragAndDropEvent dragEvent) {
            return true;
        }
    };

    /**
     * Tree must use {@link com.vaadin.data.util.HierarchicalContainer}.
     *
     * @param tree
     */

    public TreeSubissueSortDropHandler(final Tree tree) {
        if (tree == null)
            throw new NullPointerException("Tree is null");

        this.tree = tree;
        changedIssues = new HashMap<>();
    }


    private void addIssueToMap(final IssueBase key, final IssueBase value) {
        if (key == null)
            throw new NullPointerException("Key to add cant be NULL");

        if (changedIssues.containsKey(key)) {
            changedIssues.get(key).add(value);
        } else {
            final List<IssueBase> valueList = new ArrayList<>();
            valueList.add(value);
            changedIssues.put(key, valueList);
        }
    }

    public Map<IssueBase, List<IssueBase>> getChangedIssues() {
        return changedIssues;
    }



    public void setActivated(final boolean value) {
        activated = value;
    }

    public AcceptCriterion getAcceptCriterion() {
        // Alternatively, could use the following criteria to eliminate some
        // checks in drop():
        // new And(IsDataBound.get(), new DragSourceIs(tree));
        if (activated) {
            return acceptAll;
        } else {
            return declineAll;
        }
    }

    public void drop(final DragAndDropEvent dropEvent) {
        // Called whenever a drop occurs on the component

        // Make sure the drag source is the same tree
        final Transferable t = dropEvent.getTransferable();

        // see the comment in getAcceptCriterion()
        if (t.getSourceComponent() != tree
                || !(t instanceof DataBoundTransferable)) {
            return;
        }

        final Tree.TreeTargetDetails dropData = ((Tree.TreeTargetDetails) dropEvent.getTargetDetails());
        final Object sourceItemId = ((DataBoundTransferable) t).getItemId();
        final Object targetItemId = dropData.getItemIdOver();

        // Location describes on which part of the node the drop took
        // place
        final VerticalDropLocation location = dropData.getDropLocation();

        moveNode(sourceItemId, targetItemId, location);

    }

    /**
     * Move a node within a tree onto, above or below another node depending
     * on the drop location.
     *
     * @param sourceItemId
     *            id of the item to move
     * @param targetItemId
     *            id of the item onto which the source node should be moved
     * @param location
     *            VerticalDropLocation indicating where the source node was
     *            dropped relative to the target node
     */
    private void moveNode(final Object sourceItemId, final Object targetItemId, final VerticalDropLocation location) {
        final HierarchicalContainer container = (HierarchicalContainer) tree
                .getContainerDataSource();

        // Sorting goes as
        // - If dropped ON a node, we append it as a child
        // - If dropped on the TOP part of a node, we move/add it before
        // the node
        // - If dropped on the BOTTOM part of a node, we move/add it
        // after the node

        final Object parentIdSource = container.getParent(sourceItemId);

        if (location == VerticalDropLocation.MIDDLE) {
            container.setChildrenAllowed(targetItemId, true);
            if (container.setParent(sourceItemId, targetItemId)
                    && container.hasChildren(targetItemId)) {
                // move first in the container
                container.moveAfterSibling(sourceItemId, null);
                tree.expandItem(targetItemId);
            }
        } else if (location == VerticalDropLocation.TOP) {
            final Object parentId = container.getParent(targetItemId);
            if (container.setParent(sourceItemId, parentId)) {
                // reorder only the two items, moving source above target
                container.moveAfterSibling(sourceItemId, targetItemId);
                container.moveAfterSibling(targetItemId, sourceItemId);
            }
        } else if (location == VerticalDropLocation.BOTTOM) {
            final Object parentId = container.getParent(targetItemId);
            if (container.setParent(sourceItemId, parentId)) {
                container.moveAfterSibling(sourceItemId, targetItemId);
            }
        }

        if (parentIdSource != null && !container.hasChildren(parentIdSource)){
            container.setChildrenAllowed(parentIdSource, false);
        }


        if (location == VerticalDropLocation.MIDDLE) {
            final IssueBase issueMiddle = (IssueBase)container.getContainerProperty(targetItemId,
                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
            final IssueBase subIssue = (IssueBase) container.getContainerProperty(sourceItemId,
                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();

            addIssueToMap(issueMiddle, subIssue);

        } else {
            final Object parentIdTarget = container.getParent(targetItemId);
            if (parentIdTarget != null) {
                final IssueBase issueChild = (IssueBase)container.getContainerProperty(parentIdTarget,
                        TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
                final IssueBase subIssue = (IssueBase) container.getContainerProperty(sourceItemId,
                        TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();

                addIssueToMap(issueChild, subIssue);

            } else {
                final IssueBase issueRoot = (IssueBase)container.getContainerProperty(sourceItemId,
                        TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();

                addIssueToMap(issueRoot, null);
            }
        }
    }
}
