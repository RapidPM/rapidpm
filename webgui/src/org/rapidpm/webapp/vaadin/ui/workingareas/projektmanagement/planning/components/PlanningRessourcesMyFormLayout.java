package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.ProjektmanagementScreensBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.List;
import java.util.regex.Pattern;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;
import static org.rapidpm.Constants.DAYSHOURSMINUTES_REGEX;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 30.08.12
 * Time: 11:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningRessourcesMyFormLayout extends MyFormLayout {

    private static final Logger logger = Logger.getLogger(PlanningRessourcesMyFormLayout.class);

    private Table tabelle = new Table();
    private List<PlanningUnitElement> planningUnitElements;

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel) {
        super(screen, screenPanel);
        planningUnitElements = planningUnit.getPlanningUnitElementList();
        buildTable();
        buildForm();
        for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
            screenPanel.removeClickListener((MouseEvents.ClickListener) listener);
        }
    }

    public PlanningRessourcesMyFormLayout(final PlanningUnit planningUnit, final ProjektplanungScreen screen,
                                          final Panel screenPanel, boolean hasChildren) {
        super(screen, screenPanel);
        final ProjektBean projektBean = screen.getProjektBean();
        planningUnitElements = planningUnit.getPlanningUnitElementList();
        buildTable();
        buildForm();
        if (hasChildren) {
            for (final Object listener : screenPanel.getListeners(MouseEvents.ClickEvent.class)) {
                screenPanel.removeClickListener((MouseEvents.ClickListener) listener);
            }
        } else {
            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    buildTable();
                    buttonLayout.setVisible(false);
                    tabelle.setEditable(false);
                }
            });

            saveButton.addClickListener(new Button.ClickListener() {

                private final Pattern COMPILE = Pattern.compile(DAYSHOURSMINUTES_REGEX);
                private final Pattern SPLIT = Pattern.compile(":");

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try{
                        tabelle.commit();
                        for (final Object propertyId : tabelle.getContainerPropertyIds()) {
                            for(final Object itemId : tabelle.getItemIds()){
                                final String cellContent = tabelle.getItem(itemId).getItemProperty(propertyId)
                                        .getValue().toString();
                                if(!COMPILE.matcher(cellContent).matches()){
                                    throw new FieldGroup.CommitException();
                                }
                                final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
                                for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                                    if (ressourceGroup.getName().equals(propertyId.toString())) {
                                        final String[] daysHoursMinutes = SPLIT.split(cellContent);
                                        planningUnitElement.setPlannedDays(Integer.parseInt(daysHoursMinutes[0]));
                                        planningUnitElement.setPlannedHours(Integer.parseInt(daysHoursMinutes[1]));
                                        planningUnitElement.setPlannedMinutes(Integer.parseInt(daysHoursMinutes[2]));
                                    }
                                }
                            }
                        }
                        final ProjektmanagementScreensBean projektmanagementScreensBean = screen
                                .getProjektmanagementScreensBean();
                        final PlanningCalculator calculator = new PlanningCalculator(screen.getMessagesBundle(),
                                projektBean, projektmanagementScreensBean);
                        calculator.calculate();

                        tabelle.setEditable(false);
                        buttonLayout.setVisible(false);
                    }catch (CommitException e){
                        logger.info(COMMIT_EXCEPTION_MESSAGE);
                    }catch(Exception e){
                        logger.warn("Exception",e);
                    }
                }
            });
        }
    }

    private void buildTable() {
        tabelle.removeAllItems();
        tabelle.setPageLength(2);
        tabelle.setColumnCollapsingAllowed(true);
        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
        //final String[] cells = new String[planningUnitElements.size()];
        final String[] cells = new String[8];
        Integer counter = 0;
        for (final PlanningUnitElement elementOld : planningUnitElements) {
            if(counter <= 7){
                final String spaltenName = elementOld.getRessourceGroup().getName();
                tabelle.addContainerProperty(spaltenName, String.class, null);
                daysHoursMinutesItem.setDays(elementOld.getPlannedDays());
                daysHoursMinutesItem.setHours(elementOld.getPlannedHours());
                daysHoursMinutesItem.setMinutes(elementOld.getPlannedMinutes());
                cells[counter] = daysHoursMinutesItem.toString();
                counter++;
            } else {
                break;
            }
        }
        try{
            final Object itemId = tabelle.addItem(cells, null);
            if(itemId == null){
                throw new NullPointerException();
            }
        } catch(NullPointerException e){
            logger.warn("tabelle konnte nicht erstellt werden");
        }
    }

    @Override
    protected void buildForm() {
        componentsLayout.addComponent(tabelle);
    }
}
