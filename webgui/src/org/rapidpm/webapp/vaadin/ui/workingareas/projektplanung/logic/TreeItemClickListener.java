package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.Projekt;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Marco
 * Date: 24.08.12
 * Time: 13:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeItemClickListener implements ItemClickEvent.ItemClickListener {

    private ProjektplanungScreen screen;
    private Projekt projekt;

    public TreeItemClickListener(ProjektplanungScreen screen, Projekt projekt){
        this.screen = screen;
        this.projekt = projekt;
    }

    @Override
    public void itemClick(final ItemClickEvent itemClickEvent) {
        final Object selectedId = itemClickEvent.getItemId();
        VerticalLayout detailsPanelComponentsLayout = null;
        PlanningUnit selectedPlanningUnit = new PlanningUnit();
        PlanningUnitGroup selectedPlanningUnitGroup = new PlanningUnitGroup();
        boolean isGroup = false;
        if (selectedId != null) {
            final Tree treePanelTree = screen.getTreePanelTree();
            final Object itemName = treePanelTree.getItem(selectedId).getItemProperty("name").getValue();
            final Panel detailPanel = screen.getDetailPanel();
            detailPanel.removeAllComponents();
            detailPanel.addComponent(new Label(itemName.toString()));
            final ArrayList<PlanningUnit> planningUnitList = new ArrayList<>();
            for(final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()){
                if(planningUnitGroup.getPlanningUnitName().equals(itemName.toString())){
                    isGroup = true;
                    selectedPlanningUnitGroup = planningUnitGroup;
                    detailsPanelComponentsLayout = new PlanningDetailsFormLayout(selectedPlanningUnitGroup.getIssueBase(), screen);
                }
            }
            if(!isGroup){
                for(final PlanningUnitGroup planningUnitGroup2 : projekt.getPlanningUnitGroups()){
                    projekt.findPlanningUnitAndWriteReferenceInList(planningUnitGroup2.getPlanningUnitList(), itemName.toString(), planningUnitList);
                }
                selectedPlanningUnit = planningUnitList.get(0);
                detailsPanelComponentsLayout = new PlanningDetailsFormLayout(selectedPlanningUnit.getIssueBase(), screen);
            }
            screen.getDetailPanel().addComponent(detailsPanelComponentsLayout);
        }
    }
}
