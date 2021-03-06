package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 09:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class CurrentProjectEditableRapidPanel extends EditableRapidPanel {


    private ComboBox currentProjectBox;
    private ProjectAdministrationScreen screen;
//    private CurrentProjectPanelBean bean;


    public CurrentProjectEditableRapidPanel(final ResourceBundle messagesBundle, final ProjectAdministrationScreen theScreen){
        super(messagesBundle);
        this.screen = theScreen;
//        bean = EJBFactory.getEjbInstance(CurrentProjectPanelBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        setCaption(messagesBundle.getString("project_currentproject"));
        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        if(projects.isEmpty()){
            addComponent(new Label(messagesBundle.getString("project_noprojects")));
        } else {
            currentProjectBox = new ComboBox("", new BeanItemContainer<>(PlannedProject.class, projects));
            currentProjectBox.setNullSelectionAllowed(false);
            currentProjectBox.setTextInputAllowed(false);
            currentProjectBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            currentProjectBox.setItemCaptionPropertyId(PlannedProject.NAME);
            final VaadinSession session = screen.getUi().getSession();
            final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
            if(currentProject != null){
                currentProjectBox.select(currentProject);
            }
            buttonsLayout.addComponent(saveButton);
            buttonsLayout.addComponent(cancelButton);
            activate(false);

            cancelButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    activate(false);
                }
            });
            saveButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    session.setAttribute(PlannedProject.class, (PlannedProject)currentProjectBox.getValue());
                    activate(false);
                }
            });
            setComponents();
        }

    }

    @Override
    public void activate(boolean b) {
        buttonsLayout.setVisible(b);
        currentProjectBox.setReadOnly(!b);
    }

    @Override
    public void doInternationalization() {
        cancelButton.setCaption(messagesBundle.getString("cancel"));
        saveButton.setCaption(messagesBundle.getString("save"));
    }

    @Override
    public void setComponents() {
        addComponent(currentProjectBox);
        addComponent(buttonsLayout);
    }
}
