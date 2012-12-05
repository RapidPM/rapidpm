package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueTypeDAOTest.class);

    private final IssueTypeDAO dao = daoFactory.getIssueTypeDAO();
    private final IssueType assignTo = dao.loadAllEntities().get(0);

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueType> typeList = dao.loadAllEntities();
        assertTrue(typeList.get(0).equals(typeList.get(0)));
        assertEquals(typeList.get(0).hashCode(), typeList.get(0).hashCode());

        assertFalse(typeList.get(0).equals(new IssueComment()));
        assertNotSame(new IssueComment().hashCode(), typeList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        IssueType type = new IssueType("test");
        type.setTypeFileName("test_filename");
        type = dao.persist(type);
        assertEquals(type, dao.findByID(type.getId()));

        type.setTypeName("second_test");
        type.setTypeFileName("second_test_filename");
        type = dao.persist(type);
        assertEquals(type, dao.findByID(type.getId()));

        dao.delete(type, assignTo);
        assertFalse(dao.loadAllEntities().contains(type));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueType priority = dao.loadAllEntities().get(0);
        final IssueType prioTest = new IssueType();
        prioTest.setTypeName(priority.getTypeName());
        prioTest.setTypeFileName(priority.getTypeFileName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueType type : dao.loadAllEntities()) {
            final List<IssueBase> issueList = type.getConnectedIssuesFromProject(1L);
            for (final IssueBase issue : daoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getType().equals(type))
                    assertTrue(issueList.contains(issue));
            }
        }
    }
}
