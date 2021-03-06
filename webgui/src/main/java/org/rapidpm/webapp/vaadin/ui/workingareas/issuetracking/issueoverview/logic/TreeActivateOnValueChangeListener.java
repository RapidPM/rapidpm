package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.10.12
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
public class TreeActivateOnValueChangeListener implements Tree.ValueChangeListener {
    private static Logger logger = Logger.getLogger(TreeActivateOnValueChangeListener.class);

    private final Button[] buttonList;

    public TreeActivateOnValueChangeListener(final Button[] buttonList) {
        if (buttonList == null)
            throw new NullPointerException("DetailsLayout must bot be null");

        for (final Button button : buttonList) {
            if (button == null)
                throw new NullPointerException("A button in the array was null");
        }

        this.buttonList = buttonList;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("Activate Buttons");
        }
        if (event.getProperty() != null && event.getProperty().getValue() != null)
            for (final Button button : buttonList)
                if (button != null) {
                    button.setEnabled(true);
                } else {
                    logger.error("The Button to activate was null.");
                }
        else
            for (final Button button : buttonList)
                if (button != null) {
                    button.setEnabled(false);
                } else {
                    logger.error("The Button to activate was null.");
                }
    }
}
