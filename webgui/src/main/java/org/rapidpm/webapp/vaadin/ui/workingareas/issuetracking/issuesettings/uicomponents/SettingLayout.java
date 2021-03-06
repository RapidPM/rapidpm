package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents;

import com.vaadin.data.Property;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.IssueSettingsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.logic.SingleRowEditTableFieldFactory;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.model.SettingsDataContainer;

import java.util.ResourceBundle;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.11.12
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class SettingLayout<T> extends VerticalLayout {
    private static Logger logger = Logger.getLogger(SettingLayout.class);

    private final IssueSettingsScreen screen;
    private final boolean simpleErasing;

    private final Label headerLabel;
    private final Table contentTable;
    private final String headerString;
    private final Class aClass;
    private final SettingsDataContainer<T> container;

    private final Button addButton;
    private final Button editButton;
    private final Button deleteButton;
    private final Button saveButton;
    private final Button cancelButton;

    private final HorizontalLayout buttonHorLayout;
    private final VerticalLayout addButtonLayout;
    private final VerticalLayout saveButtonLayout;

    private final ResourceBundle messageBundle;


    public SettingLayout(final IssueSettingsScreen screen, final String headerName, final Class aClass,
                         final boolean simpleErasing) {
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.aClass = aClass;
        this.simpleErasing = simpleErasing;
        this.setSpacing(true);
        container = new SettingsDataContainer<T>(aClass);

        buttonHorLayout = new HorizontalLayout();
        buttonHorLayout.setSizeFull();

        addButtonLayout = new VerticalLayout();
        addButtonLayout.setSizeFull();
        addButtonLayout.setSpacing(true);
        addButtonLayout.setMargin(true);

        saveButtonLayout = new VerticalLayout();
        saveButtonLayout.setSizeFull();
        saveButtonLayout.setSpacing(true);
        saveButtonLayout.setMargin(true);

        headerString = "<b>" + headerName + "</b>";

        headerLabel = new Label();
        headerLabel.setContentMode(ContentMode.HTML);
        headerLabel.setValue(headerString);
        headerLabel.setWidth("100%");
        addComponent(headerLabel);

//        errorLabel = new Label();
//        errorLabel.setWidth("100%");
//        errorLabel.setContentMode(ContentMode.HTML);
//        addComponent(errorLabel);

        contentTable = new Table();
        contentTable.setContainerDataSource(container);
        contentTable.setVisibleColumns(container.getVisibleColumns().toArray());
        contentTable.setWidth("100%");
        contentTable.setImmediate(true);
        contentTable.setEditable(false);
        contentTable.setSelectable(true);
        contentTable.setPageLength(10);
        contentTable.addValueChangeListener(new ContentTableValueChangeListener());

        addButton = new Button(messageBundle.getString("add"));
        addButton.addClickListener(new AddEditButtonClickListener(true, aClass));
        addButton.setWidth("100%");
        addButton.setImmediate(true);
        addButton.setEnabled(true);

        editButton = new Button(messageBundle.getString("edit"));
        editButton.addClickListener(new AddEditButtonClickListener(false, aClass));
        editButton.setWidth("100%");
        editButton.setImmediate(true);
        editButton.setEnabled(false);

        deleteButton = new Button(messageBundle.getString("delete"));
        deleteButton.addClickListener(new DeleteButtonClickListener());
        deleteButton.setWidth("100%");
        deleteButton.setImmediate(true);
        deleteButton.setEnabled(false);

        addButtonLayout.addComponent(addButton);
        addButtonLayout.addComponent(editButton);
        addButtonLayout.addComponent(deleteButton);


        saveButton = new Button(messageBundle.getString("save"));
        saveButton.addClickListener(new SaveCancelButtonClickListener(true));
        saveButton.setWidth("100%");
        saveButton.setImmediate(true);

        cancelButton = new Button(messageBundle.getString("cancel"));
        cancelButton.addClickListener(new SaveCancelButtonClickListener(false));
        cancelButton.setWidth("100%");
        cancelButton.setImmediate(true);

        saveButtonLayout.addComponent(saveButton);
        saveButtonLayout.addComponent(cancelButton);


        buttonHorLayout.addComponent(contentTable);
        buttonHorLayout.addComponent(addButtonLayout);
        buttonHorLayout.setExpandRatio(contentTable, 0.75F);
        buttonHorLayout.setExpandRatio(addButtonLayout, 0.25F);
        addComponent(buttonHorLayout);
    }


    private class AddEditButtonClickListener implements Button.ClickListener {

        private final Class aClass;
        private final boolean isAddButton;

        public AddEditButtonClickListener(boolean isAddButton, Class aClass) {
            this.isAddButton = isAddButton;
            this.aClass = aClass;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            try {
                headerLabel.setValue(headerString);

                final T entity;
                if (isAddButton) {
                    final Long projectid = screen.getUi().getCurrentProject().getId();
                    entity = (T) aClass.getConstructor(Long.class).newInstance(projectid);

                    container.addEntityToTable(entity);
                    contentTable.select(entity);
                    if (logger.isDebugEnabled())
                        logger.debug("Add Entity: " + entity);
                } else {
                    entity = (T) contentTable.getValue();
                    if (logger.isDebugEnabled())
                        logger.debug("Edit Entity: " + entity);
                }

                contentTable.setSelectable(false);
                contentTable.setTableFieldFactory(new SingleRowEditTableFieldFactory(entity));
                contentTable.setEditable(true);
                buttonHorLayout.replaceComponent(addButtonLayout, saveButtonLayout);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    private class DeleteButtonClickListener implements Button.ClickListener {

        public DeleteButtonClickListener() {
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            headerLabel.setValue(headerString);
            UI.getCurrent().addWindow(new SettingsDeleteWindow<T>(screen, aClass, contentTable, simpleErasing));
            contentTable.select(contentTable.getNullSelectionItemId());
        }
    }


    private class SaveCancelButtonClickListener implements Button.ClickListener {
        private final boolean isSaveButton;

        public SaveCancelButtonClickListener(boolean isSaveButton) {
            this.isSaveButton = isSaveButton;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object entity = contentTable.getValue();
            logger.info("selected: " + entity);
            if (isSaveButton) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SaveItem");
                    logger.debug("selected entity: " + entity);
                }
                entity = container.fillObjectFromItem(entity);
                if (entity != null) {
                    contentTable.setComponentError(null);
                    headerLabel.setValue(headerString);
                    if (logger.isInfoEnabled())
                        logger.info("filled entity: " + entity);
                    try {
                        container.persistItem(entity);
                    } catch (IllegalArgumentException e) {
                        if (logger.isInfoEnabled())
                            logger.info("Name is already in use");
                        headerLabel.setValue(headerString + " - <b><font color=\"red\">" +
                                messageBundle.getString("issuetracking_settings_error_name") +
                                "</font></b>");
                    }
                } else {
                    if (logger.isInfoEnabled())
                        logger.info("No null values allowed in Item.");
                    headerLabel.setValue(headerString + " - <b><font color=\"red\">" +
                            messageBundle.getString("issuetracking_settings_error_null") +
                            "</font></b>");
                }
                container.fillTableWithDaoEntities();
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("CancelEditing");
                }

                container.cancelAddingEditing(entity);
            }
            contentTable.setEditable(false);
            contentTable.setSelectable(true);
            contentTable.select(contentTable.getNullSelectionItemId());
            buttonHorLayout.replaceComponent(saveButtonLayout, addButtonLayout);

        }
    }

    private class ContentTableValueChangeListener implements Property.ValueChangeListener{

        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if (contentTable.getValue() != null) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }

            if (contentTable.size() < 2) {
                deleteButton.setEnabled(false);
            }
        }
    }

}
