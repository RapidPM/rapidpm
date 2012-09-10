package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.components;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Button;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 05.09.12
 * Time: 08:47
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class UndoButton extends Button implements Button.ClickListener {

    private MyTreeTable treeTable;
    private HierarchicalContainer dataSource;
    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private ResourceBundle messages;

    public UndoButton(ResourceBundle bundle, MyTreeTable treeTable, HierarchicalContainer dataSource,
                      ProjektBean projektBean, RessourceGroupsBean ressourceGroupsBean){
        this.setCaption("remove sortorder");
        this.setStyleName("link");
        this.messages = messages;
        this.treeTable = treeTable;
        this.dataSource = dataSource;
        this.projektBean = projektBean;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.addListener(this);
    }

    @Override
    public void buttonClick(ClickEvent event) {

        final TreeTableFiller treeTableFiller = new TreeTableFiller(messages,projektBean,ressourceGroupsBean,
            treeTable, dataSource);
        treeTableFiller.fill();
        treeTable.requestRepaint();

        this.setVisible(false);
    }
}
