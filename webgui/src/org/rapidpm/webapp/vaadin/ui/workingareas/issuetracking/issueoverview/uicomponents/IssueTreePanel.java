package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.DummyProjectData;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class IssueTreePanel extends Panel{

    private static final String CAPTION_PROPERTY = "caption";
    private Tree issueTree;


    public IssueTreePanel(IssueTabSheet issueTabSheet) {
        this.setSizeFull();
        issueTree = new Tree("IssueTree");
        issueTree.setImmediate(true);
        if (issueTabSheet != null)
            issueTree.addItemClickListener(new TreeItemClickListener(issueTabSheet));
        fillTree();
        addComponent(issueTree);
    }

    private void fillTree() {
        issueTree.addContainerProperty(CAPTION_PROPERTY, String.class, null);
        issueTree.setItemCaptionPropertyId(CAPTION_PROPERTY);

        Item itemParent = null;
        Item item = null;
        for (PlanningUnitGroup planningUnitGroup : DummyProjectData.getPlannungUnitGroups()) {
            itemParent = issueTree.addItem(planningUnitGroup);
            itemParent.getItemProperty(CAPTION_PROPERTY).setValue(planningUnitGroup.getPlanningUnitGroupName());
            if (planningUnitGroup.getPlanningUnitList().isEmpty()) {
                issueTree.setChildrenAllowed(planningUnitGroup, false);
            } else {
                issueTree.setChildrenAllowed(planningUnitGroup, true);
                for (PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
                    item = issueTree.addItem(planningUnit);
                    item.getItemProperty(CAPTION_PROPERTY).setValue(planningUnit.getPlanningUnitName());
                    issueTree.setParent(planningUnit, planningUnitGroup);
                    if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                        issueTree.setChildrenAllowed(planningUnit, false);
                    } else {
                        issueTree.setChildrenAllowed(planningUnit, true);
                        iteratePlanningUnits(planningUnit);
                    }
                }
            }
            issueTree.expandItemsRecursively(planningUnitGroup);
        }


    }

    private void iteratePlanningUnits(PlanningUnit parentPlanningUnit) {

        Item item;
        for (PlanningUnit planningUnit : parentPlanningUnit.getKindPlanningUnits()) {
            item = issueTree.addItem(planningUnit);
            item.getItemProperty(CAPTION_PROPERTY).setValue(planningUnit.getPlanningUnitName());
            issueTree.setParent(planningUnit, parentPlanningUnit);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                issueTree.setChildrenAllowed(planningUnit, false);
            } else {
                issueTree.setChildrenAllowed(planningUnit, true);
                iteratePlanningUnits(planningUnit);
            }
        }
    }

//    private void fillTree() {
//        final Object[][] planets = new Object[][]{
//                new Object[]{"Mercury"},
//                new Object[]{"Venus"},
//                new Object[]{"Earth", "The Moon"},
//                new Object[]{"Mars", "Phobos", "Deimos"},
//                new Object[]{"Jupiter", "Io", "Europa", "Ganymedes",
//                        "Callisto"},
//                new Object[]{"Saturn",  "Titan", "Tethys", "Dione",
//                        "Rhea", "Iapetus"},
//                new Object[]{"Uranus",  "Miranda", "Ariel", "Umbriel",
//                        "Titania", "Oberon"},
//                new Object[]{"Neptune", "Triton", "Proteus", "Nereid",
//                        "Larissa"}};
//
///* Add planets as root items in the tree. */
//        for (int i=0; i<planets.length; i++) {
//            String planet = (String) (planets[i][0]);
//            issueTree.addItem(planet);
//
//            if (planets[i].length == 1) {
//                // The planet has no moons so make it a leaf.
//                issueTree.setChildrenAllowed(planet, false);
//            } else {
//                // Add children (moons) under the planets.
//                for (int j=1; j<planets[i].length; j++) {
//                    String moon = (String) planets[i][j];
//
//                    // Add the item as a regular item.
//                    issueTree.addItem(moon);
//
//                    // Set it to be a child.
//                    issueTree.setParent(moon, planet);
//
//                    // Make the moons look like leaves.
//                    issueTree.setChildrenAllowed(moon, false);
//                }
//
//                // Expand the subtree.
//                issueTree.expandItemsRecursively(planet);
//            }
//        }
//    }
}
