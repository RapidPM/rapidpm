package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainerSingleton;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.IssueSettingsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.model.SettingsDataContainer;

import java.util.Collection;
import java.util.ResourceBundle;

/**
* Created with IntelliJ IDEA.
* User: Alvin Schiller
* Date: 05.11.12
* Time: 09:54
* To change this template use File | Settings | File Templates.
*/
public class SettingsDeleteWindow<T> extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(SettingsDeleteWindow.class);

    private final IssueSettingsScreen screen;
    private final Class aClass;
    private final boolean simpleErasing;
    private Label deleteLabel;
    private Button yesButton;
    private Button noButton;
    private HorizontalLayout buttonLayout;
    private Table table;
    private final Object removeItemId;
    private final Table contentTable;

    private final SettingsDeleteWindow self = this;
    private final ResourceBundle messageBundle;

    public SettingsDeleteWindow(final IssueSettingsScreen screen, final Class aClass, final Table contentTable,
                                final boolean simpleErasing) {
        if (screen == null)
            throw new NullPointerException("Screen must not be NULL!");
        if (aClass == null)
            throw new NullPointerException("Class must not be NULL!");
        if (contentTable == null)
            throw new NullPointerException("contentTable must not be NULL!");
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.aClass = aClass;
        this.simpleErasing = simpleErasing;
        this.contentTable = contentTable;
        this.removeItemId = contentTable.getValue();
        this.setModal(true);
        setComponentens();
        doInternationalization();
    }

    private void setComponentens() {
        final VerticalLayout topLayout = new VerticalLayout();
        topLayout.setSpacing(true);

        deleteLabel = new Label();
        deleteLabel.setWidth("100%");
        topLayout.addComponent(deleteLabel);

        if (!simpleErasing) {
            final SettingsDataContainer<T> container = new SettingsDataContainer(aClass);
            table = new Table();
            table.setContainerDataSource(container);
            table.setVisibleColumns(container.getVisibleColumns().toArray());
            table.removeItem(removeItemId);
            table.setWidth("100%");
            table.setImmediate(true);
            table.setEditable(false);
            table.setSelectable(true);
            table.setNullSelectionAllowed(false);
            table.select(table.firstItemId());
            table.setPageLength(10);
            topLayout.addComponent(table);
        }
        addComponent(topLayout);

        buttonLayout = new HorizontalLayout();
        buttonLayout.setMargin(true);
        buttonLayout.setSpacing(true);

        yesButton = new Button();
        yesButton.setWidth("100%");
        yesButton.addClickListener(new YesButtonClickListener());
        buttonLayout.addComponent(yesButton);
        buttonLayout.setExpandRatio(yesButton, 1.0f);

        noButton = new Button();
        noButton.setWidth("100%");
        noButton.addClickListener(new NoButtonClickListener());
        buttonLayout.addComponent(noButton);
        buttonLayout.setExpandRatio(noButton, 1.0f);


        addComponent(buttonLayout);
    }


    @Override
    public void doInternationalization() {
        setCaption(messageBundle.getString("issuetracking_settings_deletewindow"));

        deleteLabel.setCaption(messageBundle.getString("issuetracking_settings_delete_question"));
        final Collection<?> ids = contentTable.getItem(removeItemId).getItemPropertyIds();
        String labelValue = "-      ";
        Object value;
        for (final Object propId : ids) {
                value = contentTable.getItem(removeItemId).getItemProperty(propId).getValue();
                if (value != null)
                    labelValue += value.toString() + "   -   ";
        }
        deleteLabel.setValue(labelValue);

        if (!simpleErasing) {
            table.setCaption(messageBundle.getString("issuetracking_settings_delete_tablecaption"));
        }
        yesButton.setCaption(messageBundle.getString("yes"));
        noButton.setCaption(messageBundle.getString("no"));
    }



    private class YesButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            final boolean success;
            final SettingsDataContainer<T> container = (SettingsDataContainer<T>)contentTable.getContainerDataSource();
            if (!simpleErasing) {
                final Object assignToItemId = table.getValue();
                logger.info("AssignItemId" + assignToItemId);
                success = container.removeConnectedItem(removeItemId, assignToItemId);
            } else {
                success = container.removeSimpleItem(removeItemId);
            }

            if (!success) {
                Notification.show(messageBundle.getString("issuetracking_error_delete"),
                        Notification.Type.ERROR_MESSAGE);
            }

            TreeIssueBaseContainerSingleton.getInstance(screen.getUi().getCurrentProject()).refresh();
            self.close();
        }
    }

    private class NoButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
