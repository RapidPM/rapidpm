package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTreePanelLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents.PlanningUnitSelect;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends Screen {

    private HorizontalSplitPanel splitPanel;
    private final VerticalLayout menuLayout;
    private Panel mainPanel;
    private Panel ressourcesPanel;
    private final Panel planningUnitPanel;
    private final Panel treePanel;
    private final Panel detailPanel;
    private PlanningUnitSelect planningUnitSelect;
    private final VerticalLayout mainLayout;
    private ProjektPlanungScreenBean projektplanungScreenBean;
    private DaoFactoryBean baseDaoFactoryBean;
    private PlanningUnitsTreePanelLayout planningUnitsTreePanelLayout;
    private PlanningDetailsEditableLayout planningDetailsEditableLayout;
    private PlanningUnitsTree planningUnitsTree;
    private PlanningUnit tempPlanningUnit = new PlanningUnit();


    public ProjektplanungScreen(final MainUI ui) {
        super(ui);

        projektplanungScreenBean = EJBFactory.getEjbInstance(ProjektPlanungScreenBean.class);
        baseDaoFactoryBean = projektplanungScreenBean.getDaoFactoryBean();


        final PlanningCalculator calculator = new PlanningCalculator(messagesBundle);
        calculator.calculate();
        splitPanel = new HorizontalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setSplitPosition(40, Unit.PERCENTAGE);

        planningUnitPanel = new Panel();
        treePanel = new Panel();
        detailPanel = new Panel();

        menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        menuLayout.addComponent(planningUnitPanel);
        menuLayout.addComponent(treePanel);
        menuLayout.addComponent(detailPanel);

        mainPanel = new Panel();
        ressourcesPanel = new Panel();
        ressourcesPanel.setSizeFull();

        mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(ressourcesPanel);
        mainLayout.addComponent(mainPanel);

        splitPanel.addComponent(menuLayout);
        splitPanel.addComponent(mainLayout);

        buildPlanningUnitPanel();
        doInternationalization();
        setComponents();
    }

    private void buildPlanningUnitPanel() {
        planningUnitSelect = new PlanningUnitSelect(ui);
        final PlannedProject projectFromDB = planningUnitSelect.getProjectFromDB();
        final List<?> ids = (List<?>) planningUnitSelect.getItemIds();
        planningUnitSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final PlanningUnit planningUnitFromSelect = (PlanningUnit) valueChangeEvent.getProperty().getValue();
                PlanningUnit planningUnitFromDB = baseDaoFactoryBean.getPlanningUnitDAO().findByID
                        (planningUnitFromSelect.getId());
                if(planningUnitFromDB != null){
                    baseDaoFactoryBean.getEntityManager().refresh(planningUnitFromDB);
                }else{
                    planningUnitFromDB = planningUnitFromSelect;
                }
                treePanel.getContent().removeAllComponents();
                detailPanel.getContent().removeAllComponents();
                treePanel.setCaption(planningUnitFromSelect.getPlanningUnitName());
                fillTreePanel(planningUnitFromDB, projectFromDB);
            }
        });
        if (ids != null && !ids.isEmpty()) {
            planningUnitSelect.setValue(ids.get(0));
        } else {
            //tempPlanningUnit.setId(666l);
            tempPlanningUnit.setPlanningUnitName("Platzhalter");
            tempPlanningUnit.setTestcases(new ArrayList<String>());
            tempPlanningUnit.setDescription("Bitte dem Projekt über den \"+\"-Button neue Planungseinheiten " +
                    "hinzufügen.");
            tempPlanningUnit.setKindPlanningUnits(new ArrayList<PlanningUnit>());
            planningUnitSelect.addItem(tempPlanningUnit);
            planningUnitSelect.setValue(tempPlanningUnit);
        }
        planningUnitPanel.setCaption(projectFromDB.getProjektName());
        planningUnitPanel.addComponent(planningUnitSelect);
    }

    public void fillTreePanel(final PlanningUnit selectedPlanningUnit, final PlannedProject projekt) {
        planningUnitsTree = new PlanningUnitsTree(this, selectedPlanningUnit, projekt);
        planningUnitsTree.select(selectedPlanningUnit);
        planningUnitsTreePanelLayout = new PlanningUnitsTreePanelLayout(projekt, ProjektplanungScreen.this);
//        planningUnitsTree.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(Property.ValueChangeEvent event) {
//                planningDetailsEditableLayout = new PlanningDetailsEditableLayout((PlanningUnit)planningUnitsTree.getValue
//                        (),ProjektplanungScreen.this,detailPanel);
//                detailPanel.addComponent(planningDetailsEditableLayout);
//            }
//        });
        treePanel.removeAllComponents();
        treePanel.addComponent(planningUnitsTreePanelLayout);
    }

    @Override
    public void doInternationalization() {
        detailPanel.setCaption(messagesBundle.getString("details"));
    }

    @Override
    public void setComponents() {
        addComponent(splitPanel);
    }

    public PlanningUnitsTree getPlanningUnitsTree() {
        return planningUnitsTree;
    }

    public Panel getDetailPanel() {
        return detailPanel;
    }

    public Panel getMainPanel() {
        return mainPanel;
    }

    public Panel getRessourcesPanel() {
        return ressourcesPanel;
    }

    public PlanningUnit getTempPlanningUnit() {
        return tempPlanningUnit;
    }

    public PlanningUnitsTreePanelLayout getPlanningUnitsTreePanelLayout() {
        return planningUnitsTreePanelLayout;
    }

    public PlanningUnitSelect getPlanningUnitSelect() {
        return planningUnitSelect;
    }
}
