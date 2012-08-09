package org.rapidpm.orm.prj.projectmanagement.execution.issuetracing.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class IssueBaseDAOTest extends BaseDAOTest {

    @Test
    public void testLoadAllIssuesFor001() throws Exception {
        final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
        final Date version = Date.valueOf("2010-11-26");
        final List<IssueBase> issueBases = issueBaseDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", version, null, null);
        assertNotNull(issueBases);
    }

    @Test
    public void testLoadAllIssuesFor002() throws Exception {
        final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
        final Date version = Date.valueOf("2010-11-26");
        final List<IssueBase> issueBases = issueBaseDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", null, null, null);
        assertNotNull(issueBases);
    }

    @Test
    public void testLoadAllIssuesFor003() throws Exception {
        final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
        final DateTime dateTime = new DateTime("2010-11-26");
        final List<IssueBase> issueBases = issueBaseDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", dateTime.toDate(), null, null);
        assertNotNull(issueBases);
    }

    @Test
    public void testLoadAllIssuesFor004() throws Exception {
        final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
        final List<IssueBase> issueBases = issueBaseDAO.loadAllIssuesFromVersionToVersion(null, null, null, null, null, null, null);
        assertNotNull(issueBases);
    }

    @Test
    public void testLoadAllIssuesFromVersionToVersion() throws Exception {
        final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
        List<IssueBase> issueBases = issueBaseDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", null, null, null);
        assertNotNull(issueBases);
        issueBases = issueBaseDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", new DateTime("2010-11-23").toDate(), new DateTime("2010-12-03").toDate(), null);
        assertNotNull(issueBases);
    }

    @Test
    public void testAddIssue() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Test
    public void testLoadAllIssuesForBenutzer() throws Exception {
        final IssueBaseDAO issueBaseDAO = daoFactory.getIssueBaseDAO();
        assertNotNull(issueBaseDAO.loadAllIssuesForBenutzer("sven.ruppert"));
        assertNotNull(issueBaseDAO.loadAllIssuesForBenutzer(6L));
    }
}
