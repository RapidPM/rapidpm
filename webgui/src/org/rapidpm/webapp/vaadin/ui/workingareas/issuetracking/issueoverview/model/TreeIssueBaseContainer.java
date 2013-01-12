package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import com.vaadin.data.util.HierarchicalContainer;
import org.apache.log4j.Logger;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.text.Collator;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.10.12
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TreeIssueBaseContainer extends HierarchicalContainer {
    private static Logger logger = Logger.getLogger(TreeIssueBaseContainer.class);

    public final static String PROPERTY_CAPTION = "caption";
    public final static String PROPERTY_ISSUEBASE = "issueBase";
    private final PlannedProject currentProject;
    private DaoFactory daoFactory = DaoFactorySingelton.getInstance();
    private ComparatorIssues comparator;

    public TreeIssueBaseContainer(final PlannedProject currentProject) {
        if (currentProject == null)
            throw new NullPointerException("Project must not be null");

        this.currentProject = currentProject;
        this.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        this.addContainerProperty(PROPERTY_ISSUEBASE, IssueBase.class, null);
        comparator = new ComparatorIssues();
        filltree();
    }

    private void filltree() {
        Object itemId;
        List<IssueBase> subIssueList;
        List<IssueBase> topLevelIssues = daoFactory.getIssueBaseDAO(currentProject.getId()).loadTopLevelEntities();
        Collections.sort(topLevelIssues, comparator);
        for (IssueBase issue : topLevelIssues) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(issue.name());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(issue);
            this.setParent(itemId, null);
            subIssueList = issue.getSubIssues();
            Collections.sort(subIssueList, comparator);
            if (subIssueList == null || subIssueList.isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(subIssueList, itemId);
            }
        }
    }

    private void iterateSubIssues(List<IssueBase> parentSubIssueList, Object parentItemId) {
        Object itemId;
        List<IssueBase> subIssueList;
        for (IssueBase subIssue : parentSubIssueList) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(subIssue.name());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(subIssue);
            this.setParent(itemId, parentItemId);
            subIssueList = subIssue.getSubIssues();
            Collections.sort(subIssueList, comparator);
            if (subIssueList == null || subIssueList.isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(subIssueList, itemId);
            }
        }
    }


    @Override
    public boolean removeItem(Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId must not be null");

        boolean success = false;
        Object parentItem = this.getParent(itemId);
        IssueBase issue = (IssueBase)this.getContainerProperty(itemId, PROPERTY_ISSUEBASE)
                .getValue();

        if (this.hasChildren(itemId)) {
            List<Object> children = new ArrayList<>(this.getChildren(itemId));
            for (Object childItem : children) {
                setParent(childItem, parentItem);
            }
        }

        if (daoFactory.getIssueBaseDAO(currentProject.getId()).delete(issue))
            if (super.removeItem(itemId)) {
                success = true;
                if (!this.hasChildren(parentItem))
                    this.setChildrenAllowed(parentItem, false);
            }

        return success;
    }



    @Override
    public boolean removeItemRecursively(Object itemId) {
        boolean success;
        //TODO Bei Fehler ausgangszustand wiederherstellen.
        success = removeRecusively(itemId);
        return success;
    }


    private boolean removeRecusively(Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId must not be null");

        if (this.hasChildren(itemId)) {
            Object[] children = this.getChildren(itemId).toArray();
            for (Object child : children) {
                boolean success = removeRecusively(child);
                if (!success)
                    return false;
            }
        }
        return removeItem(itemId);
    }

    public void refresh() {
        removeAllItems();
        filltree();
    }

//     public boolean containsIssue(IssueBase issue) {
//        for (Object itemId : this.getItemIds()) {
//            if (issue.equals(this.getContainerProperty(itemId, PROPERTY_ISSUEBASE)))
//                return true;
//        }
//        return false;
//     }

    private class ComparatorIssues implements Comparator<IssueBase> {

        @Override
        public int compare(IssueBase o1, IssueBase o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    }

}
