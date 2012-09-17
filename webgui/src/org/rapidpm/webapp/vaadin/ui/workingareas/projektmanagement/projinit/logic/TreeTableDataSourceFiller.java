package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.MINS_HOUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {

    private static final Logger logger = Logger.getLogger(TreeTableDataSourceFiller.class);

    private ProjektmanagementScreensBean projektmanagementScreensBean;
    private ProjektBean projektBean;
    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, DaysHoursMinutesItem> ressourceGroupDaysHoursMinutesItemMap = new HashMap<>();
    private ResourceBundle messages;
    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(final ResourceBundle bundle, final ProjektmanagementScreensBean screensBean,
                                     final ProjektBean pBean, final HierarchicalContainer dSource) {
        this.messages = bundle;
        this.projektmanagementScreensBean = screensBean;
        projektBean = pBean;
        dataSource = dSource;

        final DaoFactoryBean baseDaoFactoryBean = this.projektmanagementScreensBean.getDaoFactoryBean();
        final RessourceGroupDAO ressourceGroupDAO = baseDaoFactoryBean.getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.loadAllEntities();


        dataSource.removeAllItems();
        final String aufgabe = messages.getString("aufgabe");
        dataSource.addContainerProperty(aufgabe, String.class, null);
        for (final RessourceGroup oldRessourceGroup : ressourceGroups) {
            dataSource.addContainerProperty(oldRessourceGroup.getName(), String.class, "");
        }


    }

    public void fill() {
        computePlanningUnitGroupsAndTotalsAbsolut();
    }

    private void computePlanningUnitGroupsAndTotalsAbsolut() {
        final Projekt projekt = projektBean.getProjekt();
        for (final PlanningUnitGroup planningUnitGroup : projekt.getPlanningUnitGroups()) {
            final String planningUnitGroupName = planningUnitGroup.getPlanningUnitGroupName();
            final Item planningUnitGroupItem = dataSource.addItem(planningUnitGroupName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitGroupItem.getItemProperty(aufgabe).setValue(planningUnitGroupName);
            final List<PlanningUnit> planningUnitList = planningUnitGroup.getPlanningUnitList();
            if (planningUnitList == null || planningUnitList.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for (final PlanningUnitElement planningUnitElement : planningUnitGroup.getPlanningUnitElementList()) {
                        if (planningUnitElement.getRessourceGroup().equals(spalte)) {
                            planningUnitElement.setPlannedDays(0);
                            planningUnitElement.setPlannedHours(0);
                            planningUnitElement.setPlannedMinutes(0);
                            final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement);
                            final Property<?> itemProperty = planningUnitGroupItem.getItemProperty(spalte.getName());
                            itemProperty.setValue(daysHoursMinutesItem.toString());
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitList, planningUnitGroupName);
            }
        }
    }


    private void computePlanningUnits(final List<PlanningUnit> planningUnits, final String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitItem.getItemProperty(aufgabe).setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            final List<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement);
                    planningUnitItem.getItemProperty(planningUnitElement.getRessourceGroup().getName()).setValue(item.toString());
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(kindPlanningUnits, planningUnitName);
            }
        }
        for (final RessourceGroup spalte : ressourceGroups) {
            final String mapValue = ressourceGroupDaysHoursMinutesItemMap.get(spalte).toString();
            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(mapValue);
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup oldRessourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messages.getString("aufgabe");
            if (!oldRessourceGroup.getName().equals(aufgabe)) {
//                final RessourceGroup ressourceGroup1 = planningUnitElement.getRessourceGroup();
                final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem();
                final int plannedDays = planningUnitElement.getPlannedDays();
                final int plannedHours = planningUnitElement.getPlannedHours();
                final int plannedMinutes = planningUnitElement.getPlannedMinutes();
                daysHoursMinutesItem.setDays(plannedDays);
                daysHoursMinutesItem.setHours(plannedHours);
                daysHoursMinutesItem.setMinutes(plannedMinutes);
                if (ressourceGroupDaysHoursMinutesItemMap.containsKey(oldRessourceGroup)) {
                    final Integer days = daysHoursMinutesItem.getDays();
                    final Integer hours = daysHoursMinutesItem.getHours();
                    final Integer minutes = daysHoursMinutesItem.getMinutes();
                    final DaysHoursMinutesItem daysHoursMinutesItemFromMap = ressourceGroupDaysHoursMinutesItemMap.get(oldRessourceGroup);
                    daysHoursMinutesItem.setDays(days + daysHoursMinutesItemFromMap.getDays());
                    daysHoursMinutesItem.setHours(hours + daysHoursMinutesItemFromMap
                            .getHours());
                    daysHoursMinutesItem.setMinutes(minutes + daysHoursMinutesItemFromMap.getMinutes());
                }
                correctDaysHoursMinutesItem(daysHoursMinutesItem);
                ressourceGroupDaysHoursMinutesItemMap.put(oldRessourceGroup, daysHoursMinutesItem);
            }
        }
    }

    private void correctDaysHoursMinutesItem(final DaysHoursMinutesItem item) {
        final int hours = item.getMinutes() / MINS_HOUR;
        if (hours > 0) {
            item.setHours(item.getHours() + hours);
            item.setMinutes(item.getMinutes() - (hours * MINS_HOUR));
        }
        final int days = item.getHours() / HOURS_DAY;
        if (days > 0) {
            item.setDays(item.getDays() + days);
            item.setHours(item.getHours() - (days * HOURS_DAY));
        }
    }
}
