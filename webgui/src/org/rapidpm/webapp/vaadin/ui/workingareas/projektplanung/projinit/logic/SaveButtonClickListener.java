package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.*;

import java.util.ArrayList;
import java.util.List;

public class SaveButtonClickListener implements ClickListener {
    private FieldGroup fieldGroup;
    private AufwandProjInitScreen screen;
    private KnotenBlatt knotenBlatt;
    private Object itemId;
    private PlanningUnit foundPlanningUnit = null;

    public SaveButtonClickListener(FieldGroup fieldGroup, AufwandProjInitScreen screen, KnotenBlatt knotenBlatt, Object itemId) {
        this.fieldGroup = fieldGroup;
        this.screen = screen;
        this.knotenBlatt = knotenBlatt;
        this.itemId = itemId;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        try {
            fieldGroup.commit();
            //screen.getTreeTable().setValue(null);    //null selection
            final Projekt projekt = screen.getProjektBean().getProjekt();
            final String planningUnitName = screen.getDataSource().getItem(itemId).getItemProperty("Aufgabe").getValue().toString();
            if (knotenBlatt.equals(KnotenBlatt.PLANNING_UNIT_GROUP)) {
                for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
                    if (planningUnitGroup.getPlanningUnitName().equals(itemId)) {
                        planningUnitGroup.setPlanningUnitName(planningUnitName);
                    }
                }
            } else {
                final ArrayList<PlanningUnitGroup> planningUnitGroups = screen.getProjektBean().getProjekt().getPlanningUnitGroups();

                for (final PlanningUnitGroup planningUnitGroup : planningUnitGroups) {
                    if (foundPlanningUnit == null) {
                        getPlanningUnit(planningUnitGroup.getPlanningUnitList(), itemId.toString());
                    }
                }
                if (knotenBlatt.equals(KnotenBlatt.PLANNING_UNIT_KNOTEN)) {
                    foundPlanningUnit.setPlanningUnitElementName(planningUnitName);
                } else {
                    foundPlanningUnit.setPlanningUnitElementName(planningUnitName);
                    for (final PlanningUnitElement planningUnitElement : foundPlanningUnit.getPlanningUnitElementList()) {
                        final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                        final Property<?> planningUnitElementCellContent = screen.getDataSource().getItem(itemId).getItemProperty(planningUnitElementRessourceGroupName);
                        final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
                        final String[] daysHoursMinutes = daysHoursMinutesString.split(":");
                        planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                        planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                        planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                    }
                }
            }
            TreeTableFiller filler = new TreeTableFiller(screen,screen.getProjektBean(),screen.getRessourceGroupsBean(),screen.getTreeTable(),screen.getDataSource());
            filler.fill();

            OverviewTableFiller overviewTableFiller = new OverviewTableFiller(screen.getUebersichtTable(),screen.getProjektBean(),screen.getRessourceGroupsBean());
            overviewTableFiller.fill();
            screen.getFormLayout().setVisible(false);
        } catch (CommitException e) {
            //tue nichts falls commit nicht erfolgreich war
        }
    }

    private void getPlanningUnit(List<PlanningUnit> planningUnits, String itemId) {
        for (PlanningUnit planningUnit : planningUnits) {
            if (planningUnit.getPlanningUnitElementName().equals(itemId)) {
                foundPlanningUnit = planningUnit;
            } else {
                if (planningUnit.getKindPlanningUnits() != null && !planningUnit.getKindPlanningUnits().isEmpty()) {
                    getPlanningUnit(planningUnit.getKindPlanningUnits(), itemId);
                }
            }
        }
    }

}
