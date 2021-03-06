package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components;

import com.vaadin.ui.TreeTable;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 28.08.12
 * Time: 10:46
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class MyTreeTable extends TreeTable implements ColumnCollapseHandable {

    private MyTable connectedTable;

    public MyTreeTable() {
        setColumnReorderingAllowed(true);
        setColumnCollapsingAllowed(true);
        setNullSelectionAllowed(false);
        setSelectable(true);
    }

    public void setConnectedTable(MyTable table) {
        connectedTable = table;
    }

    @Override
    public void setColumnCollapsed(final Object propertyId, final boolean collapsed) {
        super.setColumnCollapsed(propertyId, collapsed);
        try {
            connectedTable.setColumnCollapsedEnd(propertyId, collapsed);
        } catch (Exception e) {
            //do nothing
        }
    }

    public void setColumnCollapsedEnd(final Object propertyId, final boolean collapsed) {
        try {
            super.setColumnCollapsed(propertyId, collapsed);
        } catch (Exception e) {
            //do nothing
        }
    }
}
