package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.IssueBase;

import java.util.List;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 *
 * Verbindung der PlanungsEinheit mit den Aufwänden der PlanungsRessourcen.
 *
 */

public class PlanningUnit {

    private Long id;
    private int orderNumber;
    private String planningUnitElementName;
    private List<PlanningUnit> kindPlanningUnits;
    private List<PlanningUnitElement> planningUnitElementList;
    private IssueBase issueBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPlanningUnitElementName() {
        return planningUnitElementName;
    }

    public void setPlanningUnitElementName(String planningUnitElementName) {
        this.planningUnitElementName = planningUnitElementName;
    }

    public List<PlanningUnitElement> getPlanningUnitElementList() {
        return planningUnitElementList;
    }

    public void setPlanningUnitElementList(List<PlanningUnitElement> planningUnitElementList) {
        this.planningUnitElementList = planningUnitElementList;
    }

    public List<PlanningUnit> getKindPlanningUnits() {
        return kindPlanningUnits;
    }

    public void setKindPlanningUnits(List<PlanningUnit> kindPlanningUnits) {
        this.kindPlanningUnits = kindPlanningUnits;
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

        PlanningUnit that = (PlanningUnit) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "PlanningUnit{" +
                "id=" + id +
                ", planningUnitElementName='" + planningUnitElementName + '\'' +
                '}';
    }
}
