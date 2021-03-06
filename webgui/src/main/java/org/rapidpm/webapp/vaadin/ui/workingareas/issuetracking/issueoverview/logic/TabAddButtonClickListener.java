package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.AbstractIssueDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.CommentsDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.RelationsDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TestCasesDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.AddCommentWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.AddRelationWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.AddTestcaseWindow;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 06.11.12
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class TabAddButtonClickListener implements Button.ClickListener {
    private static Logger logger = Logger.getLogger(TabAddButtonClickListener.class);

    private final IssueOverviewScreen screen;
    private final AbstractIssueDataContainer tableContainer;

    public TabAddButtonClickListener(final IssueOverviewScreen screen, final Table table) {
        if (screen == null)
            throw new NullPointerException("Screen is null.");
        if (table == null)
            throw new NullPointerException("Table is null.");
        if (!(table.getContainerDataSource() instanceof AbstractIssueDataContainer))
            throw new IllegalArgumentException("Tabledatacontainer is no instance of AbstractIssueDataContainer");

        this.screen = screen;
        this.tableContainer = (AbstractIssueDataContainer)table.getContainerDataSource();
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (tableContainer instanceof CommentsDataContainer) {
            if (logger.isDebugEnabled())
                logger.debug("AddButton Comment");
            UI.getCurrent().addWindow(new AddCommentWindow(screen, tableContainer));
        } else if (tableContainer instanceof TestCasesDataContainer) {
            if (logger.isDebugEnabled())
                logger.debug("AddButton IssueTestCase");
            UI.getCurrent().addWindow(new AddTestcaseWindow(screen, tableContainer));
        } else if (tableContainer instanceof RelationsDataContainer) {
            if (logger.isDebugEnabled())
                logger.debug("AddButton Relation");
            UI.getCurrent().addWindow(new AddRelationWindow(screen, tableContainer));
        }
    }
}
