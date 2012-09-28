package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssuePrioritiesEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditablePanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.DummyProjectData;

import java.util.Date;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class IssueDetailsPanel extends ComponentEditablePanel implements Internationalizationable{

    final private IssueOverviewScreen screen;
    private final static Integer[] storyPoints = new Integer[] {1,2,3,4,5,6,7,8,9,10};

    private Label headerLabel;
    private ComboBox statusSelect;
    private ComboBox prioritySelect;
    private ComboBox assigneeSelect;
    private Label reporterLabel;
    private DateField plannedDateField;
    private DateField resolvedDateField;
    private DateField closedDateField;
    private ComboBox storyPointSelect;

    private TextArea descriptionTextArea;
    private TabSheet tabSheet;
    private TabSheet.Tab tabComments;
    private TabSheet.Tab tabTestcases;
    private TabSheet.Tab tabStory;

    private VerticalLayout componentsLayout;

    public IssueDetailsPanel(IssueOverviewScreen screen) {
        super(screen);
        this.screen = screen;
        this.setSizeFull();
        doInternationalization();
    }


    @Override
    protected AbstractOrderedLayout buildForm() {
        componentsLayout = new VerticalLayout();

        FormLayout dateLayout = new FormLayout();
        FormLayout detailLayout = new FormLayout();
        HorizontalLayout horLayout = new HorizontalLayout();

        headerLabel = new Label();
        addComponent(headerLabel);

        statusSelect = new ComboBox();
        statusSelect.addContainerProperty(DummyProjectData.PROPERTY_NAME, String.class, null);
        statusSelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_NAME);
        Item item;
        for (IssueStatus status : DummyProjectData.getStatusList()) {
            item = statusSelect.addItem(status);
            item.getItemProperty(DummyProjectData.PROPERTY_NAME).setValue(status.getStatusName());
            statusSelect.setItemIcon(status, IssueStatusEnum.valueOf(status.getStatusName()).getIcon());
        }
        //statusSelect.select();
        statusSelect.setTextInputAllowed(false);
        statusSelect.setNullSelectionAllowed(false);
        statusSelect.setReadOnly(true);
        detailLayout.addComponent(statusSelect);

        prioritySelect = new ComboBox();
        prioritySelect.addContainerProperty(DummyProjectData.PROPERTY_NAME, String.class, null);
        prioritySelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_NAME);
        for (IssuePriority priority : DummyProjectData.getPriorityList()) {
            item = prioritySelect.addItem(priority);
            item.getItemProperty(DummyProjectData.PROPERTY_NAME).setValue(priority.getPriorityName());
            prioritySelect.setItemIcon(priority, IssuePrioritiesEnum.valueOf(priority.getPriorityName()).getIcon());
        }
        //prioritySelect.select(DummyProjectData.getPriorityList().get(0));
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        prioritySelect.setReadOnly(true);
        detailLayout.addComponent(prioritySelect);

        reporterLabel = new Label("sven.ruppert");
        reporterLabel.setValue(" sven.ruppert");
        detailLayout.addComponent(reporterLabel);

        assigneeSelect = new ComboBox();
        assigneeSelect.addItem("sven.ruppert");
        assigneeSelect.select("sven.ruppert");
        assigneeSelect.setTextInputAllowed(false);
        assigneeSelect.setNullSelectionAllowed(false);
        assigneeSelect.setReadOnly(true);
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
        for (Integer storyPoint : storyPoints) {
            storyPointSelect.addItem(storyPoint);
        }
        storyPointSelect.select(storyPoints[0]);
        storyPointSelect.setTextInputAllowed(false);
        storyPointSelect.setNullSelectionAllowed(false);
        storyPointSelect.setReadOnly(true);
        dateLayout.addComponent(storyPointSelect);

        horLayout.addComponent(detailLayout);
        horLayout.addComponent(dateLayout);
        componentsLayout.addComponent(horLayout);

        descriptionTextArea = new TextArea();
        descriptionTextArea.setWidth("100%");
        descriptionTextArea.setReadOnly(true);
        componentsLayout.addComponent(descriptionTextArea);

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        Panel commentsPanel = new Panel();
        commentsPanel.addComponent(new Label("Fill with Comments"));
        commentsPanel.addComponent(new Label("Fill with Comments"));
        commentsPanel.addComponent(new Label("Fill with Comments"));
        commentsPanel.addComponent(new Label("Fill with Comments"));
        tabComments = tabSheet.addTab(commentsPanel);

        Panel testcasePanel = new Panel();
        testcasePanel.addComponent(new Label("Fill with TestCases"));
        tabTestcases = tabSheet.addTab(testcasePanel);

        Panel storyPanel = new Panel();
        storyPanel.addComponent(new Label("Fill with Story"));
        tabStory = tabSheet.addTab(storyPanel);

        componentsLayout.addComponent(tabSheet);

        return componentsLayout;
    }


    @Override
    public void doInternationalization() {
        statusSelect.setCaption(screen.getMessagesBundle().getString("issue_status"));
        prioritySelect.setCaption(screen.getMessagesBundle().getString("issue_priority"));
        assigneeSelect.setCaption(screen.getMessagesBundle().getString("issue_assignee"));
        reporterLabel.setCaption(screen.getMessagesBundle().getString("issue_reporter"));
        plannedDateField.setCaption(screen.getMessagesBundle().getString("issue_planned"));
        resolvedDateField.setCaption(screen.getMessagesBundle().getString("issue_resolved"));
        closedDateField.setCaption(screen.getMessagesBundle().getString("issue_closed"));
        storyPointSelect.setCaption(screen.getMessagesBundle().getString("issue_storypoints"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issue_description"));
        tabComments.setCaption(screen.getMessagesBundle().getString("issue_comments"));
        tabTestcases.setCaption(screen.getMessagesBundle().getString("issue_testcases"));
        tabStory.setCaption(screen.getMessagesBundle().getString("issue_story"));
    }

    public void setPropertiesFromIssue(IssueBase issue) {
        setLayoutReadOnly(false);

        headerLabel.setValue(issue.getSummary());
        statusSelect.select(issue.getIssueStatus());
        prioritySelect.select(issue.getIssuePriority());
        //assigneeSelect.setValue(issue.getAssignee().getLogin());
        //reporterLabel.setValue(issue.getReporter().getLogin());
        plannedDateField.setValue(issue.getDueDate_planned());
        resolvedDateField.setValue(issue.getDueDate_resolved());
        closedDateField.setValue(issue.getDueDate_closed());
        storyPointSelect.select(issue.getStoryPoints());
        descriptionTextArea.setValue(issue.getText());

        setLayoutReadOnly(true);
    }

}
