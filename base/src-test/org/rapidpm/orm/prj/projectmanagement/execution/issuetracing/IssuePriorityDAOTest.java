package org.rapidpm.orm.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 3:57:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssuePriorityDAOTest extends BaseDAOTest {

    @Test
    public void testDringendUWichtig() throws Exception {
        final org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.IssuePriority issuePriority = daoFactory.getIssuePriorityDAO().loadPriorityDringend_u_Wichtig();
        assertNotNull(issuePriority);
        assertEquals("dringend und wichtig", issuePriority.getPriorityName());

    }
}
