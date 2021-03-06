package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.apache.log4j.Logger;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.datenmodell.KnotenBlattEnum;

import javax.persistence.EntityManager;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;

public class SaveButtonClickListener implements ClickListener {
    private static final Pattern SPLITT_PATTERN = Pattern.compile(":");
    private static final Logger logger = Logger.getLogger(SaveButtonClickListener.class);
    private FieldGroup fieldGroup;
    private AufwandProjInitScreen screen;
    private KnotenBlattEnum knotenBlattEnum;
    private Object itemId;
    private PlanningUnit foundPlanningUnit = null;
    private ResourceBundle messages;
//    private SaveButtonClickListenerBean bean;
//    private DaoFactoryBean baseDaoFactoryBean;

    public SaveButtonClickListener(final ResourceBundle bundle, final FieldGroup fieldGroup,
                                   final AufwandProjInitScreen screen, final KnotenBlattEnum knotenBlattEnum, final Object itemId) {
        this.messages = bundle;
        this.fieldGroup = fieldGroup;
        this.screen = screen;
        this.knotenBlattEnum = knotenBlattEnum;
        this.itemId = itemId;

//        bean = EJBFactory.getEjbInstance(SaveButtonClickListenerBean.class);
//        baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        try {
            final Item item = screen.getDataSource().getItem(itemId);
            final String planningUnitNameBeforeCommit = item.getItemProperty(messages.getString("aufgabe")).getValue().toString();
            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
            final PlanningUnitDAO planningUnitDAO = daoFactory.getPlanningUnitDAO();
            foundPlanningUnit = planningUnitDAO.loadPlanningUnitByName(planningUnitNameBeforeCommit);
            daoFactory.getEntityManager().refresh(foundPlanningUnit);

            fieldGroup.commit();
            final String planningUnitNameAfterCommit = item.getItemProperty(messages.getString("aufgabe")).getValue().toString();
            if (knotenBlattEnum.equals(KnotenBlattEnum.KNOTEN)) {
                foundPlanningUnit.setPlanningUnitName(planningUnitNameAfterCommit);
            } else {
                foundPlanningUnit.setPlanningUnitName(planningUnitNameAfterCommit);
                for (final PlanningUnitElement planningUnitElement : foundPlanningUnit.getPlanningUnitElementList()) {
                    final String planningUnitElementRessourceGroupName = planningUnitElement.getRessourceGroup().getName();
                    final Property<?> planningUnitElementCellContent = item.getItemProperty(planningUnitElementRessourceGroupName);
                    final String daysHoursMinutesString = planningUnitElementCellContent.getValue().toString();
                    final String[] daysHoursMinutes = SPLITT_PATTERN.split(daysHoursMinutesString);
                    final int plannedDays = Integer.parseInt(daysHoursMinutes[0]);
                    final int plannedHours = Integer.parseInt(daysHoursMinutes[1]);
                    final int plannedMinutes = Integer.parseInt(daysHoursMinutes[2]);
                    final VaadinSession session = screen.getUi().getSession();
                    final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
                    planningUnitElement.setPlannedMinutes((plannedDays * currentProject.getHoursPerWorkingDay() *
                            Constants.MINS_HOUR) + (plannedHours * Constants.MINS_HOUR) + (plannedMinutes));
                    daoFactory.saveOrUpdateTX(planningUnitElement);
                }
            }
            daoFactory.saveOrUpdateTX(foundPlanningUnit);
            final MainUI ui = screen.getUi();
            ui.setWorkingArea(new AufwandProjInitScreen(ui));
        }catch (CommitException e){
            logger.info(COMMIT_EXCEPTION_MESSAGE);
        }catch(Exception e){
            logger.warn("Exception", e);
        }
    }

}
