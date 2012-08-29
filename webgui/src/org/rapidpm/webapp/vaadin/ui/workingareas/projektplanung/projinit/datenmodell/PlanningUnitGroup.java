package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.rapidpm.orm.prj.projectmanagement.planning.PlanningStatus;
import org.rapidpm.orm.prj.projectmanagement.planning.management.PlannedMeeting;
import org.rapidpm.orm.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.orm.prj.stammdaten.person.Person;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueBase;

import java.util.List;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 *
 * Planungseinheit, eine Gruppe von Elemeneten
 * die zu einem funktionalen Block zusammengefasst werden.
 *
 * z.B.
 * PlanungsEinheit = Schulung
 * PlanungsEinheitElement
 *  - Vorbereitungen
 *  - Durchführung
 *  - Nachbereitung
 *
 *  verbunden werden müssen noch die Reisen.
 *
 */

public class PlanningUnitGroup {

    private Long id;
    private List<PlannedTravel> plannedTravelList;
    private PlanningStatus planningStatus;
    private Person responsiblePerson;
    private String planningUnitName;
    private List<PlanningUnit> planningUnitList;
    private List<PlannedMeeting> plannedMeetingList;
    private IssueBase issueBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlannedTravel> getPlannedTravelList() {
        return plannedTravelList;
    }

    public void setPlannedTravelList(List<PlannedTravel> plannedTravelList) {
        this.plannedTravelList = plannedTravelList;
    }

    public PlanningStatus getPlanningStatus() {
        return planningStatus;
    }

    public void setPlanningStatus(PlanningStatus planningStatus) {
        this.planningStatus = planningStatus;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getPlanningUnitName() {
        return planningUnitName;
    }

    public void setPlanningUnitName(String planningUnitName) {
        this.planningUnitName = planningUnitName;
    }

    public List<PlanningUnit> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<PlanningUnit> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }

    public List<PlannedMeeting> getPlannedMeetingList() {
        return plannedMeetingList;
    }

    public void setPlannedMeetingList(List<PlannedMeeting> plannedMeetingList) {
        this.plannedMeetingList = plannedMeetingList;
    }

    public IssueBase getIssueBase() {
        return issueBase;
    }

    public void setIssueBase(IssueBase issueBase) {
        this.issueBase = issueBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanningUnitGroup that = (PlanningUnitGroup) o;

        if (!id.equals(that.id)) return false;
        if (!planningUnitName.equals(that.planningUnitName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + planningUnitName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PlanningUnitGroup{" +
                "planningUnitName='" + planningUnitName + '\'' +
                ", id=" + id +
                '}';
    }
}
