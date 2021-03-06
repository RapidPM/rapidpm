package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components;

import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.CheckBox;

import java.util.Collection;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 07.09.12
 * Time: 14:46
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ExpandTableCheckBox extends CheckBox implements Property.ValueChangeListener{

    private MyTreeTable treeTable;
    private HierarchicalContainer dataSource;

    public ExpandTableCheckBox(final MyTreeTable treeTable, final HierarchicalContainer dataSource){
        this.treeTable = treeTable;
        this.dataSource = dataSource;
        addValueChangeListener(this);
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent valueClickEvent) {
        if((Boolean)valueClickEvent.getProperty().getValue()){
           for(final Object itemId : dataSource.getItemIds()){
               final Collection<?> children = dataSource.getChildren(itemId);
               if(children == null || children.isEmpty()){
                   treeTable.setCollapsed(itemId, true);
               } else {
                   treeTable.setCollapsed(itemId, false);
               }
           }
        } else {
            for(final Object itemId : treeTable.getItemIds()){
                treeTable.setCollapsed(itemId, true);
            }
        }
        treeTable.markAsDirty();
    }



}
