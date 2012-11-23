package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TabAddButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TabDeleteButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class IssueDetailsLayout extends ComponentEditableVLayout implements Internationalizationable{
    private static Logger logger = Logger.getLogger(IssueDetailsLayout.class);

    public final static String PROPERTY_CAPTION = "caption";

    private TextField headerSummaryField;
    private Label headerTextField;

    private ComboBox typeSelect;
    private ComboBox statusSelect;
    private ComboBox prioritySelect;
    private ComboBox assigneeSelect;
    private Label reporterLabel;
    private DateField plannedDateField;
    private DateField resolvedDateField;
    private DateField closedDateField;
    private ComboBox storyPointSelect;
    private ComboBox versionSelect;
    private ComboBox riskSelect;
    private ListSelect componentListSelect;

    private TextArea descriptionTextArea;
    private TabSheet tabSheet;
    private Table tabComments;
    private Table tabTestcases;
    private Table tabRelations;

    private Button tabAddButton;
    private Button tabDeleteButton;

    private IssueBase issue;


    public IssueDetailsLayout(final IssueOverviewScreen screen, final boolean componentsReadOnlyInit) {
        super(screen, componentsReadOnlyInit);
        doInternationalization();
    }


    @Override
    protected AbstractOrderedLayout buildSaveableForm() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final List<IssueType> typeList = daoFactory.getIssueTypeDAO().loadAllEntities();
        final List<IssueStatus> statusList = daoFactory.getIssueStatusDAO().loadAllEntities();
        final List<IssuePriority> priorityList =  daoFactory.getIssuePriorityDAO().loadAllEntities();
        final List<IssueVersion> versionList =  daoFactory.getIssueVersionDAO().loadAllEntities();
        final List<IssueStoryPoint> storyPointList =  daoFactory.getIssueStoryPointDAO().loadAllEntities();
        final List<IssueComponent> componentsList = daoFactory.getIssueComponentDAO().loadAllEntities();
        final List<Benutzer> userList =  daoFactory.getBenutzerDAO().loadAllEntities();

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(true);

        FormLayout dateLayout = new FormLayout();
        FormLayout detailLayout = new FormLayout();
        FormLayout componentLayout = new FormLayout();
        HorizontalLayout horLayout = new HorizontalLayout();

        dateLayout.setWidth("100%");
        detailLayout.setWidth("100%");
        componentLayout.setWidth("100%");
        horLayout.setWidth("100%");

        headerTextField = new Label();
        headerTextField.setWidth("100%");

        headerSummaryField = new TextField();
        headerSummaryField.setInputPrompt("Enter name");
        headerSummaryField.setWidth("100%");
        headerSummaryField.setReadOnly(true);

        VerticalLayout headerLayout = new VerticalLayout();
        headerLayout.addComponent(headerTextField);
        headerLayout.addComponent(headerSummaryField);
        headerLayout.setExpandRatio(headerSummaryField, 1.0F);

        formLayout.addComponent(headerLayout);

        typeSelect = new ComboBox();
        typeSelect.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        typeSelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        Item item;
        for (IssueType type : typeList) {
            item = typeSelect.addItem(type);
            item.getItemProperty(PROPERTY_CAPTION).setValue(type.getTypeName());
            typeSelect.setItemIcon(type, new ThemeResource("images/" + type.getTypeFileName()));
        }
        typeSelect.select(typeList.get(0));
        typeSelect.setTextInputAllowed(false);
        typeSelect.setNullSelectionAllowed(false);
        typeSelect.setReadOnly(true);
        typeSelect.setScrollToSelectedItem(true);
        detailLayout.addComponent(typeSelect);

        statusSelect = new ComboBox();
        statusSelect.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        statusSelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        for (IssueStatus status : statusList) {
            item = statusSelect.addItem(status);
            item.getItemProperty(PROPERTY_CAPTION).setValue(status.getStatusName());
            statusSelect.setItemIcon(status, new ThemeResource("images/" + status.getStatusFileName()));
        }
        statusSelect.select(statusList.get(0));
        statusSelect.setTextInputAllowed(false);
        statusSelect.setNullSelectionAllowed(false);
        statusSelect.setReadOnly(true);
        statusSelect.setScrollToSelectedItem(true);
        detailLayout.addComponent(statusSelect);

        prioritySelect = new ComboBox();
        prioritySelect.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        prioritySelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        for (IssuePriority priority : priorityList) {
            item = prioritySelect.addItem(priority);
            item.getItemProperty(PROPERTY_CAPTION).setValue(priority.getPriorityName());
            prioritySelect.setItemIcon(priority, new ThemeResource("images/" + priority.getPriorityFileName()));
        }
        prioritySelect.select(priorityList.get(0));
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        prioritySelect.setReadOnly(true);
        prioritySelect.setScrollToSelectedItem(true);
        detailLayout.addComponent(prioritySelect);

        reporterLabel = new Label();
        reporterLabel.setValue(screen.getUi().getCurrentUser().getLogin());
        detailLayout.addComponent(reporterLabel);

        assigneeSelect = new ComboBox();
        assigneeSelect.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        assigneeSelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        int i= 0;
        for (Benutzer user : userList) {
            //TODO entfernen sobald bug behoben
            if (i == 0) {
                i++;
                continue;
            }
            item = assigneeSelect.addItem(user);
            item.getItemProperty(PROPERTY_CAPTION).setValue(user.getLogin());
        }
        assigneeSelect.select(assigneeSelect.getItemIds().toArray()[0]);
        assigneeSelect.setTextInputAllowed(false);
        assigneeSelect.setNullSelectionAllowed(false);
        assigneeSelect.setReadOnly(true);
        assigneeSelect.setScrollToSelectedItem(true);
        detailLayout.addComponent(assigneeSelect);


        plannedDateField = new DateField();
        plannedDateField.setResolution(Resolution.DAY);
        plannedDateField.setValue(new Date());
        plannedDateField.setReadOnly(true);
        dateLayout.addComponent(plannedDateField);

        resolvedDateField = new DateField();
        resolvedDateField.setResolution(Resolution.DAY);
        resolvedDateField.setValue(new Date());
        resolvedDateField.setReadOnly(true);
        dateLayout.addComponent(resolvedDateField);

        closedDateField = new DateField();
        closedDateField.setResolution(Resolution.DAY);
        closedDateField.setValue(new Date());
        closedDateField.setReadOnly(true);
        dateLayout.addComponent(closedDateField);


        storyPointSelect = new ComboBox();
        storyPointSelect.addContainerProperty(PROPERTY_CAPTION, Integer.class, null);
        storyPointSelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        for (IssueStoryPoint storypoint : storyPointList) {
            item = storyPointSelect.addItem(storypoint);
            item.getItemProperty(PROPERTY_CAPTION).setValue(storypoint.getStorypoint());
        }
        storyPointSelect.select(storyPointList.get(0));
        storyPointSelect.setTextInputAllowed(false);
        storyPointSelect.setNullSelectionAllowed(false);
        storyPointSelect.setReadOnly(true);
        storyPointSelect.setScrollToSelectedItem(true);
        dateLayout.addComponent(storyPointSelect);


        versionSelect = new ComboBox();
        versionSelect.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        versionSelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        for (IssueVersion version : versionList) {
            item = versionSelect.addItem(version);
            item.getItemProperty(PROPERTY_CAPTION).setValue(version.getVersionName());
        }
        versionSelect.select(versionList.get(0));
        versionSelect.setTextInputAllowed(false);
        versionSelect.setNullSelectionAllowed(false);
        versionSelect.setReadOnly(true);
        versionSelect.setScrollToSelectedItem(true);
        dateLayout.addComponent(versionSelect);


        riskSelect = new ComboBox();
        //TODO Persistente Daten verwenden
        List<Integer> riskarray = new ArrayList<>(Arrays.asList(0, 25, 50, 75, 100));
        for (Integer version : riskarray) {
            riskSelect.addItem(version);
        }
        riskSelect.select(riskarray.get(0));
        riskSelect.setTextInputAllowed(false);
        riskSelect.setNullSelectionAllowed(true);
        riskSelect.setReadOnly(true);
        riskSelect.setScrollToSelectedItem(true);
        componentLayout.addComponent(riskSelect);


        componentListSelect = new ListSelect();
        componentListSelect.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        componentListSelect.setItemCaptionPropertyId(PROPERTY_CAPTION);
        for (IssueComponent component : componentsList) {
            item = componentListSelect.addItem(component);
            item.getItemProperty(PROPERTY_CAPTION).setValue(component.getComponentName());
        }
        componentListSelect.setNullSelectionAllowed(true);
        componentListSelect.setMultiSelect(true);
        componentListSelect.setRows(8);
        componentListSelect.setReadOnly(true);
        componentLayout.addComponent(componentListSelect);


        horLayout.addComponent(detailLayout);
        horLayout.addComponent(dateLayout);
        horLayout.addComponent(componentLayout);
        formLayout.addComponent(horLayout);

        descriptionTextArea = new TextArea();
        descriptionTextArea.setWidth("100%");
        descriptionTextArea.setReadOnly(true);
        formLayout.addComponent(descriptionTextArea);

        VerticalLayout bottomLayout = new VerticalLayout();
        bottomLayout.setSpacing(true);
        bottomLayout.setWidth("100%");

        tabAddButton = new Button();
        tabAddButton.setImmediate(true);

        tabDeleteButton = new Button();
        tabDeleteButton.setImmediate(true);
        tabDeleteButton.setEnabled(false);

        HorizontalLayout tabButtonLayout = new HorizontalLayout();
        tabButtonLayout.setSpacing(true);
        tabButtonLayout.addComponent(tabAddButton);
        tabButtonLayout.addComponent(tabDeleteButton);
        bottomLayout.addComponent(tabButtonLayout);

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.setImmediate(true);

        TableValueChangeListener tabValueListener = new TableValueChangeListener();

        CommentsDataContainer comContainer = new CommentsDataContainer(screen);
        tabComments = new Table();
        tabComments.setWidth("100%");
        tabComments.setImmediate(true);
        tabComments.setContainerDataSource(comContainer);
        tabComments.setVisibleColumns(comContainer.getVisibleColumns().toArray());
        tabComments.setNullSelectionAllowed(false);
        tabComments.setSelectable(true);
        tabComments.setEditable(false);
        tabComments.setPageLength(10);
        tabComments.addValueChangeListener(tabValueListener);
        tabSheet.addTab(tabComments);


        TestCasesDataContainer tesContainer = new TestCasesDataContainer(screen);
        tabTestcases = new Table();
        tabTestcases.setWidth("100%");
        tabTestcases.setImmediate(true);
        tabTestcases.setContainerDataSource(tesContainer);
        tabTestcases.setVisibleColumns(tesContainer.getVisibleColumns().toArray());
        tabTestcases.setNullSelectionAllowed(false);
        tabTestcases.setSelectable(true);
        tabTestcases.setEditable(false);
        tabTestcases.setPageLength(10);
        tabTestcases.addValueChangeListener(tabValueListener);
        tabSheet.addTab(tabTestcases);


        RelationsDataContainer relContainer = new RelationsDataContainer(screen);
        tabRelations = new Table();
        tabRelations.setWidth("100%");
        tabRelations.setImmediate(true);
        tabRelations.setContainerDataSource(relContainer);
        tabRelations.setVisibleColumns(relContainer.getVisibleColumns().toArray());
        tabRelations.setNullSelectionAllowed(false);
        tabRelations.setSelectable(true);
        tabRelations.setEditable(false);
        tabRelations.setPageLength(10);
        tabRelations.addItemClickListener(new RelationTableItemClickListener());
        tabRelations.addValueChangeListener(tabValueListener);
        tabSheet.addTab(tabRelations);

        tabSheet.addSelectedTabChangeListener(new TabSheetSelectionChangeListener());

        tabAddButton.addClickListener(new TabAddButtonClickListener(screen, tabComments));
        tabDeleteButton.addClickListener(new TabDeleteButtonClickListener(screen, tabComments));

        bottomLayout.addComponent(tabSheet);
        formLayout.addComponent(bottomLayout);

        return formLayout;
    }

    @Override
    protected AbstractOrderedLayout buildUnsaveableForm() {
        return null;  //No unsavable Components
    }


    @Override
    public void doInternationalization() {
        typeSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_type"));
        statusSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_status"));
        prioritySelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_priority"));
        assigneeSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_assignee"));
        reporterLabel.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_reporter"));
        componentListSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_components"));
        plannedDateField.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_planned"));
        resolvedDateField.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_resolved"));
        closedDateField.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_closed"));
        storyPointSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_storypoints"));
        versionSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_version"));
        riskSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_risk"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_description"));
        tabAddButton.setCaption(screen.getMessagesBundle().getString("add"));
        tabDeleteButton.setCaption(screen.getMessagesBundle().getString("delete"));
        tabSheet.getTab(tabComments).setCaption(screen.getMessagesBundle().getString("issuetracking_issue_comments"));
        tabSheet.getTab(tabTestcases).setCaption(screen.getMessagesBundle().getString("issuetracking_issue_testcases"));
        tabSheet.getTab(tabRelations).setCaption(screen.getMessagesBundle().getString
                ("issuetracking_issue_relations"));

    }

    public void setDetailsFromIssue(IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Details can't be set from null.");
        this.issue = issue;
        setLayoutReadOnly(false);

        headerTextField.setValue(issue.getText());
        headerSummaryField.setValue(issue.getSummary());
        typeSelect.select(issue.getType());
        statusSelect.select(issue.getStatus());
        prioritySelect.select(issue.getPriority());
        assigneeSelect.setValue(issue.getAssignee());
        reporterLabel.setValue(issue.getReporter().getLogin());
        plannedDateField.setValue(issue.getDueDate_planned());
        resolvedDateField.setValue(issue.getDueDate_resolved());
        closedDateField.setValue(issue.getDueDate_closed());
        storyPointSelect.select(issue.getStoryPoints());
        versionSelect.select(issue.getVersion());
        riskSelect.select(issue.getRisk());
        descriptionTextArea.setValue(issue.getStory());

        for (Object item : componentListSelect.getItemIds())
            componentListSelect.unselect(item);

        for (IssueComponent component : issue.getComponents()) {
            componentListSelect.select(component);
        }

        ((CommentsDataContainer)tabComments.getContainerDataSource()).fillContainer(issue);
        tabComments.select(tabComments.getNullSelectionItemId());

        ((TestCasesDataContainer)tabTestcases.getContainerDataSource()).fillContainer(issue);
        tabTestcases.select(tabComments.getNullSelectionItemId());

        ((RelationsDataContainer)tabRelations.getContainerDataSource()).fillContainer(issue);
        tabRelations.select(tabComments.getNullSelectionItemId());

        setLayoutReadOnly(true);
    }

    public IssueBase setIssueProperties(boolean newIssue) {
        if (newIssue) {
            issue = new IssueBase(screen.getCurrentProject().getId());
            issue.setReporter(screen.getUi().getCurrentUser());
            if (logger.isInfoEnabled())
                    logger.info("Adding new issue");
        } else
            if (logger.isInfoEnabled())
                logger.info("Updating issue");

        if (issue == null) {
            logger.error("Issue to save was null");
            return null;
        }


        if (headerSummaryField.getValue() == "") {
            headerSummaryField.setRequired(true);
            headerSummaryField.setRequiredError("Issue must have a name!");
            logger.warn("Issue must have a name");
            return null;
        }

        issue.setSummary(headerSummaryField.getValue());
        issue.setStatus((IssueStatus) statusSelect.getValue());
        issue.setPriority((IssuePriority) prioritySelect.getValue());
        issue.setType((IssueType) typeSelect.getValue());
        issue.setAssignee((Benutzer) assigneeSelect.getValue());
        issue.setDueDate_planned(plannedDateField.getValue());
        issue.setDueDate_resolved(resolvedDateField.getValue());
        issue.setDueDate_closed(closedDateField.getValue());
        issue.setStoryPoints((IssueStoryPoint) storyPointSelect.getValue());
        issue.setVersion((IssueVersion) versionSelect.getValue());
        issue.setRisk((Integer) riskSelect.getValue());
        issue.setStory(descriptionTextArea.getValue());

        Set<IssueComponent> comps = (Set<IssueComponent>) componentListSelect.getValue();
        if (!newIssue) {
            for (final IssueComponent issueComponent : issue.getComponents()) {
                if (!comps.contains(issueComponent)) {
                    issue.removeComponent(issueComponent);
                }
            }
        }
        for (final IssueComponent component : comps) {
            issue.addComponent(component);
        }

        final List<IssueComment> comments = new ArrayList<>((Collection<IssueComment>)tabComments.getItemIds());
        issue.setComments(comments);

        final List<IssueTestCase> testCases = new ArrayList<>((Collection<IssueTestCase>)tabTestcases.getItemIds());
        issue.setTestcases(testCases);

        final RelationsDataContainer relContainer = (RelationsDataContainer)tabRelations.getContainerDataSource();
        for (RelationItem delItem : relContainer.getDeleteList()) {
            issue.removeConnectionToIssue(delItem.getConnIssue(), delItem.getRelation(), delItem.getDirection());
        }

        for (RelationItem addItem : relContainer.getCreateList()) {
            issue.connectToIssueAs(addItem.getConnIssue(), addItem.getRelation());
        }

        relContainer.resetTransactions();

        issue = DaoFactorySingelton.getInstance().getIssueBaseDAO(screen.getCurrentProject().getId()).persist(issue);
        return issue;
    }


    public IssueBase getCurrentIssue() {
        return issue;
    }


    private class TabSheetSelectionChangeListener implements TabSheet.SelectedTabChangeListener {

        @Override
        public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
            final Component comp = event.getTabSheet().getSelectedTab();
            for (final Object clickListener : tabAddButton.getListeners(Button.ClickEvent.class))
                tabAddButton.removeClickListener((Button.ClickListener)clickListener);
            for (final Object clickListener : tabDeleteButton.getListeners(Button.ClickEvent.class))
                tabDeleteButton.removeClickListener((Button.ClickListener)clickListener);

            if (comp instanceof Table) {
                final Table table = (Table) comp;
                if (table.getValue() != null) {
                    tabDeleteButton.setEnabled(true);
                    if (logger.isDebugEnabled())
                        logger.debug("DeleteButton Enabled TRUE");
                } else {
                    tabDeleteButton.setEnabled(false);
                    if (logger.isDebugEnabled())
                        logger.debug("DeleteButton Enabled FALSE");
                }
                tabAddButton.addClickListener(new TabAddButtonClickListener(screen, table));
                tabDeleteButton.addClickListener(new TabDeleteButtonClickListener(screen, table));
            }
        }
    }

    private class TableValueChangeListener implements Table.ValueChangeListener{

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if (event.getProperty().getValue() != null) {
                tabDeleteButton.setEnabled(true);
                if (logger.isDebugEnabled())
                    logger.debug("DeleteButton Enabled TRUE");
            } else {
                tabDeleteButton.setEnabled(false);
                if (logger.isDebugEnabled())
                    logger.debug("DeleteButton Enabled FALSE");
            }
        }
    }

    private class RelationTableItemClickListener implements ItemClickEvent.ItemClickListener {

        @Override
        public void itemClick(ItemClickEvent event) {
            Container container = ((Table)event.getComponent()).getContainerDataSource();
            if (container instanceof AbstractIssueDataContainer)
                if (event.isDoubleClick()) {
                    IssueBase issue = ((RelationsDataContainer)container).getConnIssueFromItemId(event.getItemId());
                    screen.getIssueTreeLayout().setSelectedItemByIssue(issue);
                }
        }
    }

}