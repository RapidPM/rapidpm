package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.AbstractIssueDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.CommentsDataContainer;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 08.11.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class AddCommentWindow extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(AddRelationWindow.class);

    private final IssueOverviewScreen screen;
    private final CommentsDataContainer commentContainer;
    private final AddCommentWindow self;
    private final ResourceBundle messageBundle;

    private TextArea commentText;
    private Button saveButton;
    private Button cancelButton;




    public AddCommentWindow(final IssueOverviewScreen screen, final AbstractIssueDataContainer commentContainer){
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (commentContainer == null)
            throw new NullPointerException("Container must not be null");

        self = this;
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.commentContainer = (CommentsDataContainer) commentContainer;
        this.setModal(true);
        this.setResizable(false);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        final VerticalLayout baseLayout = new VerticalLayout();
        baseLayout.setSizeFull();
        baseLayout.setSpacing(true);

        commentText = new TextArea();
        commentText.setWidth("100%");
        baseLayout.addComponent(commentText);

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
        this.setCaption(messageBundle.getString("issuetracking_issue_addcommentwindow"));
        commentText.setCaption(messageBundle.getString("issuetracking_issue_comments"));
        saveButton.setCaption(messageBundle.getString("add"));
        cancelButton.setCaption(messageBundle.getString("cancel"));
    }


    private class SaveButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            commentText.setRequired(false);

            final String commentTextValue = commentText.getValue();

            if (commentTextValue != null && commentTextValue != "") {
                final IssueComment newComment = new IssueComment();
                newComment.setText(commentTextValue);
                newComment.setCreator(screen.getUi().getCurrentUser());
                newComment.setCreated(new Date());
                commentContainer.addComment(newComment);
                self.close();
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Text for comment is needed");
                commentText.setRequired(true);
                commentText.setRequiredError(messageBundle.getString("issuetracking_issue_details_commenttext"));
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
