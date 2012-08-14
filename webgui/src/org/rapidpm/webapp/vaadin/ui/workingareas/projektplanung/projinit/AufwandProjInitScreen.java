package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic.ProjInitComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic.TableItemClickListener;

import java.util.ArrayList;

public class AufwandProjInitScreen extends Screen {

    private ArrayList<Integer> ersteEbeneIds = new ArrayList<Integer>();

    private Button saveButton = new Button("Speichern");
    private TextField kundeField;
    private TextField projektField;
    private TextField datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeInMinField;
    private TextField summeKundentermineInStdField;

    private TreeTable treeTable = new TreeTable();
    private Table uebersichtTable = new Table();

    private HorizontalLayout felderLayout = new HorizontalLayout();
    private VerticalLayout unterschriftLayout = new VerticalLayout();
    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public AufwandProjInitScreen() {
        final HierarchicalContainer container = new HierarchicalContainer();
        final ProjInitComputer computer = new ProjInitComputer(this);

        erstelleUnterschriftLayout();
        erstelleFelderLayout();
        fillTable();

        uebersichtTable.setPageLength(4);
        addData(container);

        treeTable.setNullSelectionAllowed(false);
        treeTable.setContainerDataSource(container);
        treeTable.setSelectable(true);
        treeTable.setSizeFull();
        uebersichtTable.setSizeFull();
        treeTable.addListener(new TableItemClickListener(this));

        for (final Object id : container.getItemIds()) {
            if (!ersteEbeneIds.contains((Integer) id))
                treeTable.setChildrenAllowed(id, false);
        }
        table1layout.addComponent(uebersichtTable);
        table2layout.setWidth("900px");
        table1layout.setWidth("900px");
        table2layout.addComponent(treeTable);
        table1layout.setMargin(true, false, true, false);
        table2layout.setMargin(true, false, true, false);

        computer.compute();
        computer.setValuesInScreen();

        lowerFormLayout.addComponent(saveButton);

        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);
        formLayout.setVisible(false);
        setComponents();

    }

    private void fillTable() {
        uebersichtTable.addContainerProperty("Angabe", String.class, null);
        uebersichtTable.addContainerProperty("Aushilfe", Double.class, null);
        uebersichtTable.addContainerProperty("Multiprojektmanager",
                Double.class, null);
        uebersichtTable.addContainerProperty("Projektmitarbeiter",
                Double.class, null);
        uebersichtTable.addContainerProperty("Projektleiter", Double.class,
                null);

        uebersichtTable.addItem();
        uebersichtTable.addItem();
    }

    private void addData(HierarchicalContainer container) {

        container.addContainerProperty("Aufgabe", String.class, null);
        container.addContainerProperty("Aushilfe (min)", Integer.class, null);
        container.addContainerProperty("Multiprojektmanager (min)",
                Integer.class, null);
        container.addContainerProperty("Projektmitarbeiter (min)",
                Integer.class, null);
        container.addContainerProperty("Projektleiter (min)", Integer.class,
                null);

        // oberste ebene
        Object itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Vorbereitungen");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        System.out.println(container.getItem(itemId));
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(195);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Projekt-Workshop");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(270);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Angebotserstellung");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(310);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Realisierung Mandantengruppe");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(300);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(130);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(300);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Realisierung / Daten kollektieren");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(60);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Vorbereiten des Reporting");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Projektmanagement");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(120);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Kommunikation");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(60);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(60);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Abschlussarbeiten");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(60);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(240);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        itemId = container.addItem();
        ersteEbeneIds.add((Integer) itemId);
        container.getItem(itemId).getItemProperty("Aufgabe")
                .setValue("Schulung");
        container.getItem(itemId).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Multiprojektmanager (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektmitarbeiter (min)")
                .setValue(null);
        container.getItem(itemId).getItemProperty("Projektleiter (min)")
                .setValue(null);

        // --------------------

        final Object vorbereitungenId1 = container.addItem();
        container.getItem(vorbereitungenId1).getItemProperty("Aufgabe")
                .setValue("Erstkontakt vor Ort");
        container.getItem(vorbereitungenId1).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(vorbereitungenId1)
                .getItemProperty("Multiprojektmanager (min)").setValue(120);
        container.getItem(vorbereitungenId1)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        container.getItem(vorbereitungenId1)
                .getItemProperty("Projektleiter (min)").setValue(null);

        final Object vorbereitungenId2 = container.addItem();
        container.getItem(vorbereitungenId2).getItemProperty("Aufgabe")
                .setValue("Gespr\u00E4chsvorbereitung");
        container.getItem(vorbereitungenId2).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(vorbereitungenId2)
                .getItemProperty("Multiprojektmanager (min)").setValue(60);
        container.getItem(vorbereitungenId2)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        container.getItem(vorbereitungenId2)
                .getItemProperty("Projektleiter (min)").setValue(null);

        final Object vorbereitungenId3 = container.addItem();
        container.getItem(vorbereitungenId3).getItemProperty("Aufgabe")
                .setValue("Pr\u00E4sentation");
        container.getItem(vorbereitungenId3).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(vorbereitungenId3)
                .getItemProperty("Multiprojektmanager (min)").setValue(null);
        container.getItem(vorbereitungenId3)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        container.getItem(vorbereitungenId3)
                .getItemProperty("Projektleiter (min)").setValue(null);

        final Object vorbereitungenId4 = container.addItem();
        container.getItem(vorbereitungenId4).getItemProperty("Aufgabe")
                .setValue("Gespr\u00E4chsbest\u00E4tigung");
        container.getItem(vorbereitungenId4).getItemProperty("Aushilfe (min)")
                .setValue(null);
        container.getItem(vorbereitungenId4)
                .getItemProperty("Multiprojektmanager (min)").setValue(15);
        container.getItem(vorbereitungenId4)
                .getItemProperty("Projektmitarbeiter (min)").setValue(null);
        container.getItem(vorbereitungenId4)
                .getItemProperty("Projektleiter (min)").setValue(null);
        container.setParent(vorbereitungenId1, ersteEbeneIds.get(0));
        container.setParent(vorbereitungenId2, ersteEbeneIds.get(0));
        container.setParent(vorbereitungenId3, ersteEbeneIds.get(0));
        container.setParent(vorbereitungenId4, ersteEbeneIds.get(0));
    }

    private void erstelleFelderLayout() {
        final Label kundeLabel = new Label("Kunde:");
        final Label projektLabel = new Label("Projekt:");
        final Label datumLabel = new Label("Datum:");
        final Label manntageLabel = new Label("MT:");
        final Label summeInMinLabel = new Label("Summe in min:");
        final Label kundentermineLabel = new Label("Summe in min (Kundentermine):");
        final VerticalLayout linkeZeilen = new VerticalLayout();
        final VerticalLayout rechteZeilen = new VerticalLayout();
        final HorizontalLayout linkeZeile1 = new HorizontalLayout();
        final HorizontalLayout linkeZeile2 = new HorizontalLayout();
        final HorizontalLayout linkeZeile3 = new HorizontalLayout();
        final HorizontalLayout rechteZeile1 = new HorizontalLayout();
        final HorizontalLayout rechteZeile2 = new HorizontalLayout();
        final HorizontalLayout rechteZeile3 = new HorizontalLayout();

        kundeField = new TextField();
        projektField = new TextField();
        datumField = new TextField();
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        manntageField = new TextField();
        manntageField.setEnabled(false);
        summeInMinField = new TextField();
        summeInMinField.setEnabled(false);
        summeKundentermineInStdField = new TextField();
        summeKundentermineInStdField.setEnabled(false);
        // Horizontallayout (700px) beinhaltet 2 VerticalLayouts(jew. 350px)
        // beinhalten jeweils x horizontallayouts (sizefull)
        felderLayout.setWidth("700px");
        linkeZeilen.setWidth("350px");
        rechteZeilen.setWidth("350px");
        linkeZeile1.setSizeFull();
        linkeZeile2.setSizeFull();
        linkeZeile3.setSizeFull();
        rechteZeile1.setSizeFull();
        rechteZeile2.setSizeFull();
        rechteZeile3.setSizeFull();

        linkeZeile1.addComponent(kundeLabel);
        linkeZeile1.addComponent(kundeField);
        linkeZeile1.setComponentAlignment(kundeLabel, Alignment.MIDDLE_LEFT);
        linkeZeile1.setComponentAlignment(kundeField, Alignment.MIDDLE_LEFT);
        linkeZeile2.addComponent(projektLabel);
        linkeZeile2.addComponent(projektField);
        linkeZeile3.addComponent(datumLabel);
        linkeZeile3.addComponent(datumField);

        rechteZeile1.addComponent(manntageLabel);
        rechteZeile1.addComponent(manntageField);
        rechteZeile1.setComponentAlignment(manntageLabel, Alignment.MIDDLE_LEFT);
        rechteZeile1
                .setComponentAlignment(manntageField, Alignment.MIDDLE_LEFT);
        rechteZeile2.addComponent(summeInMinLabel);
        rechteZeile2.addComponent(summeInMinField);
        rechteZeile3.addComponent(kundentermineLabel);
        rechteZeile3.addComponent(summeKundentermineInStdField);

        linkeZeilen.addComponent(linkeZeile1);
        linkeZeilen.addComponent(linkeZeile2);
        linkeZeilen.addComponent(linkeZeile3);

        rechteZeilen.addComponent(rechteZeile1);
        rechteZeilen.addComponent(rechteZeile2);
        rechteZeilen.addComponent(rechteZeile3);

        felderLayout.addComponent(linkeZeilen);
        felderLayout.addComponent(rechteZeilen);
        felderLayout.setMargin(true, false, true, false);

    }

    private void erstelleUnterschriftLayout() {
        final Label projleiterLabel = new Label("Projektleiter:");
        final Label unterschriftLabel = new Label("Unterschrift PM:");
        final HorizontalLayout zeile1 = new HorizontalLayout();
        final HorizontalLayout zeile2 = new HorizontalLayout();
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        unterschriftLayout.setWidth("350px");

        zeile1.setSizeFull();
        zeile2.setSizeFull();

        zeile1.addComponent(projleiterLabel);
        zeile1.addComponent(projektLeiterField);
        zeile2.addComponent(unterschriftLabel);
        zeile2.addComponent(unterschriftField);

        unterschriftLayout.addComponent(zeile1);
        unterschriftLayout.addComponent(zeile2);
        unterschriftLayout.setMargin(true, false, true, false);
    }

    public void setComponents() {
        addComponent(felderLayout);
        addComponent(unterschriftLayout);
        addComponent(table1layout);
        addComponent(table2layout);
        addComponent(formLayout);
    }


    public TextField getKundeField() {
        return kundeField;
    }

    public void setKundeField(TextField kundeField) {
        this.kundeField = kundeField;
    }

    public TextField getProjektField() {
        return projektField;
    }

    public void setProjektField(TextField projektField) {
        this.projektField = projektField;
    }

    public TextField getDatumField() {
        return datumField;
    }

    public void setDatumField(TextField datumField) {
        this.datumField = datumField;
    }

    public TextField getProjektLeiterField() {
        return projektLeiterField;
    }

    public void setProjektLeiterField(TextField projektLeiterField) {
        this.projektLeiterField = projektLeiterField;
    }

    public TextField getUnterschriftField() {
        return unterschriftField;
    }

    public void setUnterschriftField(TextField unterschriftField) {
        this.unterschriftField = unterschriftField;
    }

    public TextField getManntageField() {
        return manntageField;
    }

    public void setManntageField(TextField manntageField) {
        this.manntageField = manntageField;
    }

    public TextField getSummeInMinField() {
        return summeInMinField;
    }

    public void setSummeInMinField(TextField summeInMinField) {
        this.summeInMinField = summeInMinField;
    }

    public TextField getSummeKundentermineInStdField() {
        return summeKundentermineInStdField;
    }

    public void setSummeKundentermineInStdField(
            TextField summeKundentermineInStdField) {
        this.summeKundentermineInStdField = summeKundentermineInStdField;
    }

    public ArrayList<Integer> getErsteEbeneIds() {
        return ersteEbeneIds;
    }

    public void setErsteEbeneIds(ArrayList<Integer> ersteEbeneIds) {
        this.ersteEbeneIds = ersteEbeneIds;
    }

    public VerticalLayout getFormLayout() {
        return formLayout;
    }

    public void setFormLayout(VerticalLayout formLayout) {
        this.formLayout = formLayout;
    }

    public GridLayout getUpperFormLayout() {
        return upperFormLayout;
    }

    public void setUpperFormLayout(GridLayout upperFormLayout) {
        this.upperFormLayout = upperFormLayout;
    }

    public VerticalLayout getLowerFormLayout() {
        return lowerFormLayout;
    }

    public void setLowerFormLayout(VerticalLayout lowerFormLayout) {
        this.lowerFormLayout = lowerFormLayout;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public TreeTable getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(TreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public Table getUebersichtTable() {
        return uebersichtTable;
    }

    public void setUebersichtTable(Table uebersichtTable) {
        this.uebersichtTable = uebersichtTable;
    }


}
