package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.AbstractIssueDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.RelationsDataContainer;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 07.11.12
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class AddRelationWindow extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(AddRelationWindow.class);
    private DaoFactory daoFactory = DaoFactorySingelton.getInstance();

    private final static String PROPERTY_NAME = "name";
    private final IssueOverviewScreen screen;
    private final RelationsDataContainer relationContainer;
    private final AddRelationWindow self;
    private final ResourceBundle messageBundle;

    private ComboBox relationsSelect;
    private ComboBox issueSelect;
    private Button saveButton;
    private Button cancelButton;




    public AddRelationWindow(final IssueOverviewScreen screen, final AbstractIssueDataContainer relationContainer){
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (relationContainer == null)
            throw new NullPointerException("Container must not be null");

        self = this;
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.relationContainer = (RelationsDataContainer)relationContainer;
        this.setModal(true);
        this.setResizable(false);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        final VerticalLayout baseLayout = new VerticalLayout();
        baseLayout.setSizeFull();
        baseLayout.setSpacing(true);

        final Long projectId = screen.getUi().getCurrentProject().getId();
        final List<IssueRelation> relationList = daoFactory.getIssueRelationDAO().loadAllEntities(projectId);
        relationsSelect = new ComboBox();
        relationsSelect.setWidth("100%");
        relationsSelect.addContainerProperty(PROPERTY_NAME, String.class, null);
        relationsSelect.setItemCaptionPropertyId(PROPERTY_NAME);
        for (final IssueRelation relation : relationList) {
            final Item item = relationsSelect.addItem(relation);
            item.getItemProperty(PROPERTY_NAME).setValue(relation.getRelationName());
        }
        relationsSelect.setNullSelectionAllowed(false);
        relationsSelect.setScrollToSelectedItem(true);
        relationsSelect.setFilteringMode(FilteringMode.CONTAINS);
        baseLayout.addComponent(relationsSelect);

        final List<IssueBase> issueList = daoFactory.getIssueBaseDAO().loadAllEntities(projectId);
        issueSelect = new ComboBox();
        issueSelect.setWidth("100%");
        issueSelect.addContainerProperty(PROPERTY_NAME, String.class, null);
        issueSelect.setItemCaptionPropertyId(PROPERTY_NAME);
        final IssueBase selectedIssue = relationContainer.getCurrentIssue();
        for (final IssueBase connIssue : issueList) {
            if (selectedIssue != null && !connIssue.equals(selectedIssue)) {
                final Item item = issueSelect.addItem(connIssue);
                item.getItemProperty(PROPERTY_NAME).setValue(connIssue.name());
            }
        }
        issueSelect.setNullSelectionAllowed(false);
        issueSelect.setScrollToSelectedItem(true);
        issueSelect.setFilteringMode(FilteringMode.CONTAINS);
        baseLayout.addComponent(issueSelect);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");

        saveButton = new Button();
        saveButton.addClickListener(new SaveButtonClickListener());
        cancelButton = new Button();
        cancelButton.addClickListener(new CancelButtonClickListener());

        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);

        baseLayout.addComponent(buttonLayout);
        addComponent(baseLayout);
    }

    @Override
    public void doInternationalization() {
        this.setCaption(messageBundle.getString("issuetracking_issue_addrelationswindow"));
        relationsSelect.setCaption(messageBundle.getString("issuetracking_issue_relations"));
        issueSelect.setCaption(messageBundle.getString("issuetracking_issue_table"));
        saveButton.setCaption(messageBundle.getString("add"));
        cancelButton.setCaption(messageBundle.getString("cancel"));
    }


    private class SaveButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            relationsSelect.setRequired(false);
            issueSelect.setRequired(false);

            final Object relation = relationsSelect.getValue();
            final Object connIssue = issueSelect.getValue();

            if (relation != null && relation != "") {
                if (connIssue != null && connIssue != "")  {
                    relationContainer.addRelation((IssueBase) connIssue, (IssueRelation)relation);
                    self.close();
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Issue to connect to is needed");
                    issueSelect.setRequired(true);
                    issueSelect.setRequiredError(messageBundle.getString("issuetracking_issue_details_relationissue"));
                }
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Relations to connect issues is needed");
                relationsSelect.setRequired(true);
                relationsSelect.setRequiredError(messageBundle.getString("issuetracking_issue_details_relation"));
            }

        }
    }

    private class CancelButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
