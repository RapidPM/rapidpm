package org.rapidpm.webapp.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.MenuBar;
import org.rapidpm.webapp.vaadin.ui.workingareas.anfragenmanagement.AnfragenmanagementWorkingArea;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.IssueSettingsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.VertriebScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.offer.OfferScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.ZeitauswertungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.erfassung.ZeiterfassungScreen;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:41
 */
//@JavaScript({   "http://localhost:8080/rapidpm/javascript/highcharts/highcharts.js",
//                "http://localhost:8080/rapidpm/javascript/jquery/jquery-1.4.4.min.js"})
@SuppressWarnings("serial")
@Theme("mytheme")
@Widgetset("com.vaadin.DefaultWidgetSet")
public class MainUI extends BaseUI {

    @WebServlet(urlPatterns = "/*", name = "MainUIServlet")
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MainUIServlet extends VaadinServlet {
    }

    @Override
    protected void initMenuBar(final MenuBar menuBar) {

        messages = ResourceBundle.getBundle("MessagesBundle", locale);
        final MenuBar.MenuItem stammdatenMenu = menuBar.addItem(messages.getString("masterdata"), null);
        stammdatenMenu.addItem(messages.getString("users"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new BenutzerScreen(MainUI.this));
            }
        });

        stammdatenMenu.addItem(messages.getString("hourlyrates"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new StundensaetzeScreen(MainUI.this));
            }
        });

        menuBar.addItem(messages.getString("requestmanagement"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AnfragenmanagementWorkingArea());
            }
        });

        final MenuBar.MenuItem projektmanagement = menuBar.addItem(messages.getString("projectmanagement"), null,
                null);


        projektmanagement.addItem(messages.getString("projectplanning"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ProjektplanungScreen(MainUI.this));
            }
        });


        projektmanagement.addItem(messages.getString("projectinit"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new AufwandProjInitScreen(MainUI.this));
            }
        });

        projektmanagement.addItem(messages.getString("costs"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new CostsScreen(MainUI.this));
            }
        });


        projektmanagement.addItem(messages.getString("distribution"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new VertriebScreen(MainUI.this));
            }
        }).setEnabled(false);

        projektmanagement.addItem(messages.getString("offer"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new OfferScreen(MainUI.this));
            }
        }).setEnabled(false);

        projektmanagement.addSeparator();

        projektmanagement.addItem(messages.getString("administrateprojects"), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                setWorkingArea(new ProjectAdministrationScreen(MainUI.this));
            }
        });

        final MenuBar.MenuItem issuetracking = menuBar.addItem(messages.getString("issuetracking"), null,
                null);

        issuetracking.addItem(messages.getString("issuetracking_overview"), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                setWorkingArea(new IssueOverviewScreen(MainUI.this));
            }
        });

        issuetracking.addItem(messages.getString("issuetracking_settings"), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                setWorkingArea(new IssueSettingsScreen(MainUI.this));
            }
        });

        final MenuBar.MenuItem zeitmanagementMenu = menuBar.addItem(messages.getString("time_management"), null);
        zeitmanagementMenu.addItem(messages.getString("time_overview"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ZeitauswertungScreen(MainUI.this));
            }
        });
        zeitmanagementMenu.addItem(messages.getString("time_logging"), new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem menuItem) {
                setWorkingArea(new ZeiterfassungScreen(MainUI.this));
            }
        });

        setWorkingArea(new ProjektplanungScreen(this));

    }

    public Locale getLocale(){
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }
}
