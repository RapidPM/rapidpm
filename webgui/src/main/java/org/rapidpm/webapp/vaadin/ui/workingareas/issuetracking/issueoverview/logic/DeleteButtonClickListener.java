package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.DeleteIssueWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.10.12
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
public class DeleteButtonClickListener implements Button.ClickListener {
    private static Logger logger = Logger.getLogger(DeleteButtonClickListener.class);

    private final Tree issueTree;
    private final IssueOverviewScreen screen;

    public DeleteButtonClickListener(final IssueOverviewScreen screen, final Tree issueTree) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (issueTree == null)
            throw new NullPointerException("Tree must not be null");

        this.screen = screen;
        this.issueTree = issueTree;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        UI.getCurrent().addWindow(new DeleteIssueWindow(screen, issueTree));
    }
}
