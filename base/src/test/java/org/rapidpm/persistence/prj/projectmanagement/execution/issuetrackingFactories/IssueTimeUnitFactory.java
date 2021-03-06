package org.rapidpm.persistence.prj.projectmanagement.execution.issuetrackingFactories;

import org.rapidpm.persistence.EntityFactory;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 12.10.11
 * Time: 13:48
 */
public class IssueTimeUnitFactory extends EntityFactory<IssueTimeUnit> {
    public IssueTimeUnitFactory() {
        super(IssueTimeUnit.class);
    }

    @Override
    public IssueTimeUnit createRandomEntity() {
        final IssueTimeUnit issueTimeUnit = new IssueTimeUnit();
        issueTimeUnit.setComment(RND.nextSentence(6, 20, 5, 12));
        issueTimeUnit.setDate(RND.nextDate());
        issueTimeUnit.setMinutes(RND.nextInt(5, 320));
//        issueTimeUnit.setWorker(); //TODO Benutzer
        return issueTimeUnit;
    }

    @Test
    public void testCreate() throws Exception {
        System.out.println("Braucht BENUTZER!!");
        System.out.println(createRandomEntity());
    }
}
