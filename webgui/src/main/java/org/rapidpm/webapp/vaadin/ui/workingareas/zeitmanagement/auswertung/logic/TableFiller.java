package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic;

import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.UserWorkLog;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.UserWorkLogContainer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 26.10.12
 * Time: 10:11
 */
public class TableFiller {

    private UserWorkLogContainer userWorkLogContainer;

    public TableFiller(List<UserWorkLog> userWorkLogs, Table table){
        userWorkLogContainer = new UserWorkLogContainer();
        userWorkLogContainer.addAll(userWorkLogs);
        table.setContainerDataSource(userWorkLogContainer);

    }

}
