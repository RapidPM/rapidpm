package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.uicomponents;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.system.security.*;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.BenutzerScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.AlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.EmailAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.UsernameAlreadyExistsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.benutzer.exceptions.WrongLoginNameException;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 12:01
 */
public class BenutzerEditor extends FormLayout implements Internationalizationable {

    private BeanItem<Benutzer> benutzerBean;

//    @Inject
//    @LoggerQualifier
//    private Logger logger;

    @Inject
    private UserTransaction userTransaction;


    //REFAC per CDI ?
//    @Inject
//    private StammdatenScreensBean stammdatenScreenBean;
//    private final BenutzerEditorBean bean = EJBFactory.getEjbInstance(BenutzerEditorBean.class);

    private Collection<Mandantengruppe> mandantengruppen;
    private Collection<BenutzerGruppe> benutzerGruppen;
    private Collection<BenutzerWebapplikation> benutzerWebapplikationen;
    private Collection<Rolle> rollen;

    private TextField idTextField;
    private TextField loginTextField;
    private PasswordField passwdTextField;
    private TextField emailTextField;
    private DateField validFromDateField;
    private DateField validUntilDateFiled;
    private DateField lastLoginDateField;
    private ComboBox mandantengruppenSelect;
    private ComboBox benutzerGruppenSelect;
    private ComboBox benutzerWebapplikationenSelect;
    private ListSelect rollenSelect;
    private CheckBox isActiveCheckbox;
    private CheckBox isHiddenCheckBox;
    private Button saveButton;

    private BenutzerScreen screen;
    private ResourceBundle messages;

    public BenutzerEditor(final BenutzerScreen theScreen) {
        this.screen = theScreen;
        this.messages = screen.getMessagesBundle();
        setVisible(false);
        idTextField = new TextField();
        idTextField.setRequired(false);
//        idTextField.setReadOnly(true);
        idTextField.setEnabled(false);
        idTextField.setConverter(Long.class); // vaadin 7
//REFAC        idTextField.addValidator(new IntegerRangeValidator("ungültige ID", 0, Integer.MAX_VALUE));
        addComponent(idTextField);

        loginTextField = new TextField();
        loginTextField.setRequired(true);
        loginTextField.setNullRepresentation("");
        addComponent(loginTextField);

        passwdTextField = new PasswordField();
        passwdTextField.setRequired(true);
        passwdTextField.setNullRepresentation("");
        addComponent(passwdTextField);

        emailTextField = new TextField();
        emailTextField.addValidator(new EmailValidator("Ungültige E-Mail-Adresse"));
        emailTextField.setNullRepresentation("@rapidpm.org");
        addComponent(emailTextField);

        validFromDateField = new DateField();
        validFromDateField.setConverter(Date.class);
        addComponent(validFromDateField);

        validUntilDateFiled = new DateField();
        validUntilDateFiled.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(validUntilDateFiled);

        lastLoginDateField = new DateField();
        lastLoginDateField.setDateFormat(BenutzerScreen.DATE_FORMAT.toPattern());
        addComponent(lastLoginDateField);

        mandantengruppenSelect = new ComboBox();
//        mandantengruppenSelect.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
        mandantengruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        mandantengruppenSelect.setItemCaptionPropertyId("mandantengruppe");
        mandantengruppenSelect.setNullSelectionAllowed(false);
        mandantengruppenSelect.setRequired(true);
        mandantengruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(mandantengruppenSelect);

        benutzerGruppenSelect = new ComboBox();
        benutzerGruppenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        benutzerGruppenSelect.setItemCaptionPropertyId("gruppenname");
        benutzerGruppenSelect.setNullSelectionAllowed(false);
        benutzerGruppenSelect.setRequired(true);
        benutzerGruppenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(benutzerGruppenSelect);

        benutzerWebapplikationenSelect = new ComboBox();
        benutzerWebapplikationenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        benutzerWebapplikationenSelect.setItemCaptionPropertyId("webappName");
        benutzerWebapplikationenSelect.setNullSelectionAllowed(false);
        benutzerWebapplikationenSelect.setRequired(true);
        benutzerWebapplikationenSelect.setFilteringMode(FilteringMode.CONTAINS);
        addComponent(benutzerWebapplikationenSelect);

        rollenSelect = new ListSelect();
        rollenSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        rollenSelect.setItemCaptionPropertyId("name");
        rollenSelect.setMultiSelect(true);
        addComponent(rollenSelect);

        isActiveCheckbox = new CheckBox();
        addComponent(isActiveCheckbox);

        isHiddenCheckBox = new CheckBox();
        addComponent(isHiddenCheckBox);

        saveButton = new Button();
        saveButton.addClickListener(new Button.ClickListener() {
            private final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                if (benutzerBean == null) {
                    benutzerBean = new BeanItem<>(new Benutzer());
                } else {
                    final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
                    final Benutzer benutzer = benutzerBean.getBean();
                }
                boolean valid = true;
                for (final Component component : BenutzerEditor.this.components) {
                    if (component instanceof Validatable) {
                        final Validatable validatable = (Validatable) component;
                        try {
                            validatable.validate();
                        } catch (Validator.InvalidValueException e) {
                            valid = false;
                        }
                    }
                }
                try {
                    if (valid) {
                        final List<String> userNames = new ArrayList<>();
                        final List<String> userEmails = new ArrayList<>();
                        final List<Benutzer> users = daoFactory.getBenutzerDAO().loadAllEntities();
                        for (final Benutzer user : users) {
                            daoFactory.getEntityManager().refresh(user);
                            userNames.add(user.getLogin());
                            userEmails.add(user.getEmail());
                        }
                        final String enteredLoginName = loginTextField.getValue().toString();
                        if (userNames.contains(enteredLoginName) && idTextField.getValue().isEmpty()) {
                            throw new UsernameAlreadyExistsException();
                        }
                        if (userEmails.contains(emailTextField.getValue().toString()) && idTextField.getValue().isEmpty()) {
                            throw new EmailAlreadyExistsException();
                        }
                        if (enteredLoginName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN) || enteredLoginName
                                .toCharArray().length <= 2) {
                            throw new WrongLoginNameException();
                        }
                        final Set<Rolle> rolleSet = new HashSet<>();
                        final Object rollenSelectValue = rollenSelect.getValue();
                        if (rollenSelectValue instanceof Rolle) {
                            rolleSet.add((Rolle) rollenSelectValue);
                        } else if (rollenSelectValue instanceof Collection) {
                            final Collection<Rolle> rollenCollection = (Collection<Rolle>) rollenSelectValue;
                            rolleSet.addAll(rollenCollection);
                        }

                        // Tabelle aktualisieren
                        //                    benutzerBean.getItemProperty("id").setValue(Long.parseLong(idTextField.getValue().toString())); // ID wird von der DB verwaltet
                        benutzerBean.getItemProperty("validFrom").setValue(validFromDateField.getValue());
                        benutzerBean.getItemProperty("validUntil").setValue(validUntilDateFiled.getValue());
                        benutzerBean.getItemProperty("login").setValue(loginTextField.getValue());
                        benutzerBean.getItemProperty("passwd").setValue(passwdTextField.getValue());
                        benutzerBean.getItemProperty("email").setValue(emailTextField.getValue());
                        benutzerBean.getItemProperty("lastLogin").setValue(lastLoginDateField.getValue());
                        //REFAC beheben von detached persistent Beans
                        benutzerBean.getItemProperty("mandantengruppe").setValue(mandantengruppenSelect.getValue());
                        benutzerBean.getItemProperty("benutzerGruppe").setValue(benutzerGruppenSelect.getValue());
                        benutzerBean.getItemProperty("benutzerWebapplikation").setValue(benutzerWebapplikationenSelect.getValue());
                        benutzerBean.getItemProperty("rollen").setValue(rolleSet);
                        benutzerBean.getItemProperty("active").setValue(isActiveCheckbox.getValue());
                        benutzerBean.getItemProperty("hidden").setValue(isHiddenCheckBox.getValue());

                        // in die DB speichern
                        final Benutzer benutzer = benutzerBean.getBean();
                        daoFactory.saveOrUpdateTX(benutzer);

                        final MainUI ui = screen.getUi();
                        ui.setWorkingArea(new BenutzerScreen(ui));
                        setVisible(false);
                    } else {
                        Notification.show(messages.getString("incompletedata"));
                    }
                } catch (final AlreadyExistsException e) {
                    if (e instanceof EmailAlreadyExistsException) {
                        Notification.show(messages.getString("users_emailexists"));
                    }
                    if (e instanceof UsernameAlreadyExistsException) {
                        Notification.show(messages.getString("users_nameexists"));
                    }
                } catch (final WrongLoginNameException e) {
                    Notification.show(messages.getString("users_namenotaccepted"));
                }
            }
        });
        addComponent(saveButton);

        doInternationalization();

    }

    public Benutzer getBenutzer() {
        return benutzerBean.getBean();
    }

    public void setBenutzerBean(final BeanItem<Benutzer> benutzerBean) {
        if (benutzerBean == null) {
            return;
        }
        this.benutzerBean = benutzerBean;
        final Benutzer benutzer = benutzerBean.getBean();
        if (benutzer.getId() != null) {
            idTextField.setValue(benutzer.getId().toString());
        }
        loginTextField.setValue(benutzer.getLogin());
        passwdTextField.setValue(benutzer.getPasswd());
        emailTextField.setValue(benutzer.getEmail());
        validFromDateField.setValue(benutzer.getValidFrom());
        validUntilDateFiled.setValue((benutzer.getValidUntil()));
        lastLoginDateField.setValue(benutzer.getLastLogin());
        mandantengruppenSelect.select(benutzer.getMandantengruppe());
        benutzerGruppenSelect.select(benutzer.getBenutzerGruppe());
        benutzerWebapplikationenSelect.select(benutzer.getBenutzerWebapplikation());
        rollenSelect.setValue(null); // Selektion aufheben
        if (benutzer.getRollen() != null) {
            for (final Rolle rolle : benutzer.getRollen()) {
                rollenSelect.select(rolle);
            }
        }
        isActiveCheckbox.setValue(benutzer.getActive());
        isHiddenCheckBox.setValue(benutzer.getHidden());
    }


    public void setMandantengruppen(final Collection<Mandantengruppe> mandantengruppen) {
        this.mandantengruppen = mandantengruppen;
        this.mandantengruppenSelect.setContainerDataSource(new BeanItemContainer<>(Mandantengruppe.class, mandantengruppen));
    }

    public void setBenutzerGruppen(final Collection<BenutzerGruppe> benutzerGruppen) {
        this.benutzerGruppen = benutzerGruppen;
        this.benutzerGruppenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerGruppe.class, benutzerGruppen));
    }

    public void setBenutzerWebapplikationen(final Collection<BenutzerWebapplikation> benutzerWebapplikationen) {
        this.benutzerWebapplikationen = benutzerWebapplikationen;
        this.benutzerWebapplikationenSelect.setContainerDataSource(new BeanItemContainer<>(BenutzerWebapplikation.class, benutzerWebapplikationen));
    }

    public void setRollen(final Collection<Rolle> rollen) {
        this.rollen = rollen;
        this.rollenSelect.setContainerDataSource(new BeanItemContainer<>(Rolle.class, rollen));
    }

    @Override
    public void doInternationalization() {
        isActiveCheckbox.setCaption(messages.getString("users_active"));
        isHiddenCheckBox.setCaption(messages.getString("users_hidden"));
        saveButton.setCaption(messages.getString("save"));
        rollenSelect.setCaption(messages.getString("users_roles"));
        benutzerWebapplikationenSelect.setCaption(messages.getString("users_webapp"));
        benutzerGruppenSelect.setCaption(messages.getString("users_usergroups"));
        mandantengruppenSelect.setCaption(messages.getString("users_mandantgroups"));
        lastLoginDateField.setCaption(messages.getString("users_lastlogin"));
        validFromDateField.setCaption(messages.getString("users_validfrom"));
        validUntilDateFiled.setCaption(messages.getString("users_validuntil"));
        emailTextField.setCaption(messages.getString("users_email"));
        passwdTextField.setCaption(messages.getString("users_password"));
        loginTextField.setCaption(messages.getString("users_login"));
        idTextField.setCaption(messages.getString("users_id"));
        setCaption(messages.getString("users_usereditor"));
    }
}
