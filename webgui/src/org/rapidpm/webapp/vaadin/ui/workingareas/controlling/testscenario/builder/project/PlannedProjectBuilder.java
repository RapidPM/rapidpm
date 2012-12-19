package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class PlannedProjectBuilder {
    private Benutzer creator;
    private Benutzer responsiblePerson;
    private Set<PlanningUnit> planningUnitList;
    private String projectName;
    private long id;

    public PlannedProjectBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public PlannedProjectBuilder setCreator(final Benutzer creator) {
        this.creator = creator;
        return this;
    }

    public PlannedProjectBuilder setResponsiblePerson(final Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public PlannedProjectBuilder setPlanningUnitList(final Set<PlanningUnit> planningUnitList) {
        this.planningUnitList = planningUnitList;
        return this;
    }

    public PlannedProjectBuilder setProjectName(final String projectName) {
        this.projectName = projectName;
        return this;
    }

    public PlannedProject getPlannedProject(){
        final PlannedProject plannedProject = new PlannedProject();
        plannedProject.setId(id);
        plannedProject.setCreator(creator);
        plannedProject.setResponsiblePerson(responsiblePerson);
        plannedProject.setPlanningUnits(planningUnitList);
        plannedProject.setProjektName(projectName);
        return plannedProject;
    }
}