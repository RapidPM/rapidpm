package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseIssueTrackingDAOTest;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 15.11.12
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public class IssueCommentDAOTest extends BaseIssueTrackingDAOTest {

    private final IssueCommentDAO dao = daoFactory.getIssueCommentDAO();

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueComment> commentList = dao.loadAllEntities();
        assertTrue(commentList.get(0).equals(commentList.get(0)));
        assertEquals(commentList.get(0).hashCode(), commentList.get(0).hashCode());

        assertFalse(commentList.get(0).equals(new IssueComment()));
        assertNotSame(new IssueComment().hashCode(), commentList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        final IssueComment comment = new IssueComment();
        comment.setText("test");
        comment.setCreated(new Date());
        comment.setCreator(daoFactory.getBenutzerDAO().loadAllEntities().get(0));
        daoFactory.saveOrUpdateTX(comment);
        assertTrue(comment.getId() != null);
        assertEquals(comment, dao.findByID(comment.getId()));

        comment.setText("second_test");
        comment.setCreated(new Date());
        comment.setCreator(daoFactory.getBenutzerDAO().loadAllEntities().get(1));
        daoFactory.saveOrUpdateTX(comment);
        assertTrue(comment.getId() != null);
        assertEquals(comment, dao.findByID(comment.getId()));


        daoFactory.removeTX(comment);
        assertFalse(dao.loadAllEntities().contains(comment));
    }

    @Test//(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueComment comment = dao.loadAllEntities().get(0);
        final IssueComment comTest = new IssueComment();
        comTest.setCreated(comment.getCreated());
        comTest.setCreator(comment.getCreator());
        comTest.setText(comment.getText());
        daoFactory.saveOrUpdateTX(comTest);
    }
}
