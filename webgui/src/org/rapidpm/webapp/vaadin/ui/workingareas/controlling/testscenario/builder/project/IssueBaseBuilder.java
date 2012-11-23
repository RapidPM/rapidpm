package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.Benutzer;

import java.sql.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseBuilder {
    private Benutzer assignee;
    private Date dueDatePlanned;
    private Date dueDateClosed;
    private Date dueDateResolved;
    private Benutzer reporter;
    private IssueStatus status;
    private String text;


    private List<IssueTimeUnit> timeUnitsUsed;

    public IssueBaseBuilder setAssignee(final Benutzer assignee) {
        this.assignee = assignee;
        return this;
    }

    public IssueBaseBuilder setDueDatePlanned(final Date dueDatePlanned) {
        this.dueDatePlanned = dueDatePlanned;
        return this;
    }

    public IssueBaseBuilder setDueDateClosed(final Date dueDateClosed) {
        this.dueDateClosed = dueDateClosed;
        return this;
    }

    public IssueBaseBuilder setDueDateResolved(final Date dueDateResolved) {
        this.dueDateResolved = dueDateResolved;
        return this;
    }

    public IssueBaseBuilder setReporter(final Benutzer reporter) {
        this.reporter = reporter;
        return this;
    }


    public IssueBaseBuilder setStatus(final IssueStatus status) {
        this.status = status;
        return this;
    }

    public IssueBaseBuilder setText(final String text) {
        this.text = text;
        return this;
    }

    public IssueBaseBuilder setTimeUnitsUsed(final List<IssueTimeUnit> timeUnitsUsed) {
        this.timeUnitsUsed = timeUnitsUsed;
        return this;
    }

    public IssueBase getIssueBase(){
        if(assignee == null
                || reporter == null
                || status == null)

            throw new  IllegalStateException("IssueBaseBuilder: Nicht alle Daten angegegen.");

        final IssueBase issueBase = new IssueBase();
        issueBase.setAssignee(assignee);
//        issueBase.setD(dueDateClosed);
//        issueBase.setDueDate_planned(dueDatePlanned);
//        issueBase.setDueDate_resolved(dueDateResolved);
        issueBase.setReporter(reporter);
        issueBase.setStatus(status);
        issueBase.setText(text);
        for(IssueTimeUnit issueTimeUnit : timeUnitsUsed)
            issueBase.addTimeUnitUsed(issueTimeUnit);
        return issueBase;
    }
}