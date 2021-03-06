package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 3:57:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseIssueTrackingDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class IssuePriorityDAOTest extends BaseIssueTrackingDAOTest {
    private static Logger logger = Logger.getLogger(IssuePriorityDAOTest.class);

    private final IssuePriorityDAO dao = daoFactory.getIssuePriorityDAO();
    private final IssuePriority assignTo = dao.loadAllEntities(PROJECTID).get(0);


    @Test
    public void equalsAndHashCodeTest() {
        List<IssuePriority> priorityList = dao.loadAllEntities(PROJECTID);
        assertTrue(priorityList.get(0).equals(priorityList.get(0)));
        assertEquals(priorityList.get(0).hashCode(), priorityList.get(0).hashCode());

        assertFalse(priorityList.get(0).equals(new IssuePriority()));
        assertNotSame(new IssuePriority(PROJECTID).hashCode(), priorityList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        IssuePriority priority = new IssuePriority(1, "test");
        priority.setProjectId(PROJECTID);
        priority.setPriorityFileName("test_filename");
        priority = dao.persist(priority);
        assertEquals(priority, dao.findByID(priority.getId()));

        priority.setPrio(2);
        priority.setPriorityName("second_test");
        priority.setPriorityFileName("second_test_filename");
        priority = dao.persist(priority);
        assertEquals(priority, dao.findByID(priority.getId()));

        dao.delete(priority, assignTo);
        assertFalse(dao.loadAllEntities(PROJECTID).contains(priority));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssuePriority priority = dao.loadAllEntities(PROJECTID).get(0);
        final IssuePriority prioTest = new IssuePriority();
        prioTest.setProjectId(PROJECTID);
        prioTest.setPriorityName(priority.getPriorityName());
        prioTest.setPriorityFileName(priority.getPriorityFileName());
        prioTest.setPrio(priority.getPrio());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssuePriority priority : dao.loadAllEntities(PROJECTID)) {
            final List<IssueBase> prioConnIssueList = priority.getConnectedIssues();
            final List<IssueBase> issueList = new ArrayList<>();

            for (final IssueBase issue : daoFactory.getIssueBaseDAO().loadAllEntities(PROJECTID)) {
                if (issue.getPriority().equals(priority))
                    issueList.add(issue);
            }

            assertEquals(prioConnIssueList.size(), issueList.size());
            for (final IssueBase match : prioConnIssueList) {
                assertTrue(issueList.contains(match));
            }
        }
    }


    @Test(expected = NullPointerException.class)
    public void delete_FirstParameterNull() {
        dao.delete(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_FirstParameterNoId() {
        dao.delete(new IssuePriority(), null);
    }

    @Test(expected = NullPointerException.class)
    public void delete_SecondParameterNull() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SecondParameterNoId() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), new IssuePriority());
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssues_FirstParameterNull() {
        dao.getConnectedIssues(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_firstParameterNoId() {
        dao.getConnectedIssues(new IssuePriority());
    }
}
