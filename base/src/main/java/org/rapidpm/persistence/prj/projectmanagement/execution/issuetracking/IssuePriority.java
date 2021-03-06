package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Identifier;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.NonVisible;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.Simple;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.PersistInGraph;

import java.util.List;


/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.08.2010
 *        Time: 12:19:00
 */

public class IssuePriority  implements PersistInGraph {

    @Identifier
    private Long id;

    @Simple
    @NonVisible
    private Long projectid;

    @Simple
    private Integer prio;

    @Simple
    private String priorityName;

    @Simple
    private String priorityFileName;

    public IssuePriority() {
        //empty on purpose
    }

    public IssuePriority(final int prio, final String priorityName) {
        this.prio = prio;
        this.priorityName = priorityName;
    }

    public IssuePriority(final Long projectid) {
        setProjectId(projectid);
    }


    public List<IssueBase> getConnectedIssues() {
        return DaoFactorySingelton.getInstance().getIssuePriorityDAO().getConnectedIssues(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectid;
    }

    public void setProjectId(final Long projectid) {
        this.projectid = projectid;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(final String name) {
        this.priorityName = name;
    }

    public String getPriorityFileName() {
        return priorityFileName;
    }

    public void setPriorityFileName(final String priorityFileName) {
        this.priorityFileName = priorityFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuePriority that = (IssuePriority) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (prio != null ? !prio.equals(that.prio) : that.prio != null) return false;
        if (priorityFileName != null ? !priorityFileName.equals(that.priorityFileName) : that.priorityFileName != null) return false;
        if (priorityName != null ? !priorityName.equals(that.priorityName) : that.priorityName != null) return false;
        if (projectid != null ? !projectid.equals(that.projectid) : that.projectid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (projectid != null ? projectid.hashCode() : 0);
        result = 31 * result + (prio != null ? prio.hashCode() : 0);
        result = 31 * result + (priorityName != null ? priorityName.hashCode() : 0);
        result = 31 * result + (priorityFileName != null ? priorityFileName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssuePriority{" +
                "id=" + id +
                ", projectid=" + projectid +
                ", prio=" + prio +
                ", priorityName='" + priorityName + '\'' +
                ", priorityFileName='" + priorityFileName + '\'' +
                '}';
    }

    @Override
    public String name() {
        return getPriorityName();
    }
}
