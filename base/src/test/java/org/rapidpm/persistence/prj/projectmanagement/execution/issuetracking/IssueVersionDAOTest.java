package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseIssueTrackingDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.11.12
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class IssueVersionDAOTest extends BaseIssueTrackingDAOTest {
    private static Logger logger = Logger.getLogger(IssueVersionDAOTest.class);

    private final IssueVersionDAO dao = daoFactory.getIssueVersionDAO();
    private final IssueVersion assignTo = dao.loadAllEntities(PROJECTID).get(0);

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueVersion> versionList = dao.loadAllEntities(PROJECTID);
        assertTrue(versionList.get(0).equals(versionList.get(0)));
        assertEquals(versionList.get(0).hashCode(), versionList.get(0).hashCode());

        assertFalse(versionList.get(0).equals(new IssueVersion()));
        assertNotSame(new IssueVersion(PROJECTID).hashCode(), versionList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        IssueVersion version = new IssueVersion("test");
        version.setProjectId(PROJECTID);
        version = dao.persist(version);
        assertEquals(version, dao.findByID(version.getId()));

        version.setVersionName("second_test");
        version = dao.persist(version);
        assertEquals(version, dao.findByID(version.getId()));

        dao.delete(version, assignTo);
        assertFalse(dao.loadAllEntities(PROJECTID).contains(version));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueVersion version = dao.loadAllEntities(PROJECTID).get(0);
        final IssueVersion verTest = new IssueVersion();
        verTest.setProjectId(PROJECTID);
        verTest.setVersionName(version.getVersionName());
        dao.persist(verTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueVersion version : dao.loadAllEntities(PROJECTID)) {
            final List<IssueBase> verConnIssueList = version.getConnectedIssues();
            final List<IssueBase> issueList = new ArrayList<>();

            for (final IssueBase issue : daoFactory.getIssueBaseDAO().loadAllEntities(PROJECTID)) {
                if (issue.getVersion().equals(version))
                    issueList.add(issue);
            }

            assertEquals(verConnIssueList.size(), issueList.size());
            for (final IssueBase match : verConnIssueList) {
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
        dao.delete(new IssueVersion(), null);
    }

    @Test(expected = NullPointerException.class)
    public void delete_SecondParameterNull() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SecondParameterNoId() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), new IssueVersion());
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssues_FirstParameterNull() {
        dao.getConnectedIssues(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_firstParameterNoId() {
        dao.getConnectedIssues(new IssueVersion());
    }
}
