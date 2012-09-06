package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends HorizontalSplitPanel {


    private static final String NAME = "name";
    private static final String ICON = "icon";

    private final VerticalLayout menuLayout;
    private Panel mainPanel;
    private Panel ressourcesPanel;
    private final Panel planningUnitGroupPanel;
    private final Panel treePanel;
    private final Panel detailPanel;
    private final ListSelect projektSelect;
    private final VerticalLayout mainLayout;
    private final ProjektBean projektBean;
    private final RessourceGroupsBean ressourceGroupsBean;
    private Tree treePanelTree;


    public ProjektplanungScreen(final ProjektBean cont, final RessourceGroupsBean ressourceGroupsBean) {
        this.projektBean = cont;
        this.ressourceGroupsBean = ressourceGroupsBean;
        final PlanningCalculator calculator = new PlanningCalculator(this.projektBean, this.ressourceGroupsBean);
        calculator.compute();
        setSizeFull();
        setSplitPosition(40, Unit.PERCENTAGE);

        menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        addComponent(menuLayout);

        planningUnitGroupPanel = new Panel("Projekt");
        menuLayout.addComponent(planningUnitGroupPanel);
        treePanel = new Panel();
        menuLayout.addComponent(treePanel);
        detailPanel = new Panel("Details");
        menuLayout.addComponent(detailPanel);

        mainPanel = new Panel();
        ressourcesPanel = new Panel();
        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.addComponent(ressourcesPanel);
        mainLayout.addComponent(mainPanel);
        addComponent(mainLayout);

        final List<String> listenWerteArrayList = projektBean.getProjekt().getPlanningUnitGroupsNames();
        projektSelect = new ListSelect(null, listenWerteArrayList);

        projektSelect.setNullSelectionAllowed(false);
        projektSelect.setImmediate(true);
        planningUnitGroupPanel.getContent().addComponent(projektSelect);
        projektSelect.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final String value = (String) valueChangeEvent.getProperty().getValue();
                treePanel.getContent().removeAllComponents();
                detailPanel.getContent().removeAllComponents();
                treePanel.setCaption(value);
                fillTreePanel(value);
                treePanelTree.select(value);
                detailPanel.setCaption("Details");
            }

        });

    }

    public void fillTreePanel(String planningGroupName) {

        treePanel.removeAllComponents();
        treePanelTree = new Tree();
        PlanningUnitGroup planningUnitGroup = null;
        final Projekt projekt = projektBean.getProjekt();
        for (final PlanningUnitGroup pug : projekt.getPlanningUnitGroups()) {
            if (pug.getPlanningUnitGroupName().equals(planningGroupName)) {
                planningUnitGroup = pug;
            }
        }

        if (planningUnitGroup != null) {
            treePanelTree.addContainerProperty(NAME, String.class, "");
            treePanelTree.addContainerProperty(ICON, Resource.class, null);
            treePanelTree.setItemCaptionPropertyId(NAME);
            treePanelTree.setItemIconPropertyId(ICON);
            treePanelTree.setImmediate(true);
            final String itemId = planningUnitGroup.getPlanningUnitGroupName();
            final Item planningUnitGroupItem = treePanelTree.addItem(itemId);

            planningUnitGroupItem.getItemProperty(NAME).setValue(itemId);
            final IssueBase issueBase = planningUnitGroup.getIssueBase();
            final String issueStatusName = issueBase.getIssueStatus().getStatusName();
            planningUnitGroupItem.getItemProperty(ICON).setValue(IssueStatusEnum.valueOf(issueStatusName).getIcon());
            if (planningUnitGroup.getPlanningUnitList() != null && !planningUnitGroup.getPlanningUnitList().isEmpty()) {
                treePanelTree.setChildrenAllowed(itemId, true);
            } else {
                treePanelTree.setChildrenAllowed(itemId, false);
            }

            buildTree(planningUnitGroup.getPlanningUnitList(), itemId);
            treePanelTree.expandItemsRecursively(itemId);
            treePanelTree.addListener(new TreeValueChangeListener(this, projekt));
            treePanel.addComponent(treePanelTree);
        } //else if (planningGroupName.equals("Technische Planung")) {
        //  showTechnischePlanung();
        //}
    }

    private void buildTree(List<PlanningUnit> planningUnits, Object parentId) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final Object itemId = treePanelTree.addItem();
            treePanelTree.getItem(itemId).getItemProperty(NAME).setValue(planningUnit.getPlanningUnitName());
            final String issueStatusName = planningUnit.getIssueBase().getIssueStatus().getStatusName();
            treePanelTree.getItem(itemId).getItemProperty(ICON).setValue(IssueStatusEnum.valueOf(issueStatusName).getIcon());
            treePanelTree.setParent(itemId, parentId);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                treePanelTree.setChildrenAllowed(itemId, false);
            } else {
                buildTree(planningUnit.getKindPlanningUnits(), itemId);
            }
        }
    }

    public ListSelect getProjektSelect() {
        return projektSelect;
    }

    public Tree getTreePanelTree() {
        return treePanelTree;
    }

    public Panel getDetailPanel() {
        return detailPanel;
    }

    public Panel getPlanningUnitGroupPanel() {
        return planningUnitGroupPanel;
    }

    public Panel getTreePanel() {
        return treePanel;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }

    public VerticalLayout getMainLayout() {
        return mainLayout;
    }

    public Panel getMainPanel() {
        return mainPanel;
    }

    public Panel getRessourcesPanel() {
        return ressourcesPanel;
    }

    public RessourceGroupsBean getRessourceGroupsBean() {
        return ressourceGroupsBean;
    }
}
