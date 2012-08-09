package org.rapidpm.webapp.vaadin.ui;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 17.03.12
 * Time: 22:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Window;
import org.apache.log4j.Logger;

public class DisclaimerWindow extends Window {
    private static final Logger logger = Logger.getLogger(DisclaimerWindow.class);

    public DisclaimerWindow() {
        final Embedded e = new Embedded("Disclaimer", new ThemeResource("data/static/disclaimer.html"));
        e.setType(Embedded.TYPE_BROWSER);
        e.setWidth("400px");
        e.setHeight("600px");
        super.addComponent(e);
    }
}
