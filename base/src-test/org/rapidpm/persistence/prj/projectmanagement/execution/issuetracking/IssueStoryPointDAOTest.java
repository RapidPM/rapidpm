package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.11.12
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class IssueStoryPointDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueStoryPointDAOTest.class);

    private final IssueStoryPointDAO dao = daoFactory.getIssueStoryPointDAO();
    private final IssueStoryPoint assignTo = dao.loadAllEntities().get(0);

    @Test
    public void addChangeDelete() {
        IssueStoryPoint storyPoint = new IssueStoryPoint(1000);
        storyPoint = dao.persist(storyPoint);
        assertEquals(storyPoint, dao.findByID(storyPoint.getId()));

        storyPoint.setStorypoint(1001);
        storyPoint = dao.persist(storyPoint);
        assertEquals(storyPoint, dao.findByID(storyPoint.getId()));

        dao.delete(storyPoint, assignTo);
        assertFalse(dao.loadAllEntities().contains(storyPoint));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueStoryPoint storyPoint = dao.loadAllEntities().get(0);
        IssueStoryPoint stpTest = new IssueStoryPoint();
        stpTest.setStorypoint(storyPoint.getStorypoint());
        dao.persist(stpTest);
    }

    @Test
    public void getConnectedIssus() {
        for (IssueStoryPoint storyPoint : dao.loadAllEntities()) {
            List<IssueBase> issueList = storyPoint.getConnectedIssuesFromProject(1L);

            for (IssueBase issue : daoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getStoryPoints().equals(storyPoint))
                    assertTrue(issueList.contains(issue));
            }
        }
    }
}
