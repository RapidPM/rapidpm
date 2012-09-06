package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.rapidpm.Constants.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.09.12
 * Time: 21:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class OverviewTableFiller {

    private static final String EXTERN = "extern";
    private static final String RELATIV = "relativ";
    private static final String ABSOLUT = "absolut";
    private static final String KOSTEN = "kosten";
    private static final String EXTERN_EUR_PER_H = "Extern € / h";
    private static final String SUM_IN_EUR = "Summe [€]";
    private static final String SUM_IN_PERCENT = "Summe [%]";
    private static final String SUM_IN_DAYSHOURSMINS = "Summe [d:hh:mm]";
    private static final int WIDTH = 250;

    private MyTable table;
    private RessourceGroupsBean ressourceGroupsBean;
    private ProjektBean projektBean;


    public OverviewTableFiller(final MyTable table, final ProjektBean projektBean,
                               final RessourceGroupsBean ressourceGroupsBean) {
        this.table = table;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.projektBean = projektBean;
    }

    public void fill() {
        table.removeAllItems();
        table.addContainerProperty(ANGABE_SPALTE, String.class, null);
        table.setColumnCollapsible(ANGABE_SPALTE, false);
        table.setColumnWidth(ANGABE_SPALTE, WIDTH);
        final List<RessourceGroup> ressourceGroups = ressourceGroupsBean.getRessourceGroups();
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            final String spaltenName = ressourceGroup.getName();
            table.addContainerProperty(spaltenName, String.class, null);
        }

        final TimesCalculator timesCalculator = new TimesCalculator(ressourceGroupsBean, projektBean);
        timesCalculator.calculate();

        final CostsCalculator costsCalculator = new CostsCalculator(projektBean);
        costsCalculator.calculate();

        final Item externItem = table.addItem(EXTERN);
        final Item relativItem = table.addItem(RELATIV);
        final Item absolutItem = table.addItem(ABSOLUT);
        final Item kostenItem = table.addItem(KOSTEN);

        final Item item = table.getItem(EXTERN);
        final Property<?> externItemAngabeProperty = item.getItemProperty(ANGABE_SPALTE);
        externItemAngabeProperty.setValue(EXTERN_EUR_PER_H);
        final Collection<?> itemPropertyIds = externItem.getItemPropertyIds();
        for (final Object spalte : itemPropertyIds) {
            if (!spalte.equals(ANGABE_SPALTE)) {
                for (final RessourceGroup ressourceGroup : ressourceGroups) {
                    final String spaltenName = spalte.toString();
                    final String spaltenNameAusRessourceGroupsBean = ressourceGroup.toString();
                    if (spaltenName.equals(spaltenNameAusRessourceGroupsBean)) {
                        final Property<?> externItemSpalteProperty = externItem.getItemProperty(spalte);
                        final String ressourceGroupsBeanValue = ressourceGroup.getExternalEurosPerHour().toString();
                        externItemSpalteProperty.setValue(ressourceGroupsBeanValue + " " + EUR);
                    }
                }
            }
        }

        final Property<?> absolutItemAngabeProperty = absolutItem.getItemProperty(ANGABE_SPALTE);
        absolutItemAngabeProperty.setValue(SUM_IN_DAYSHOURSMINS);
        for (final Object spalte : absolutItem.getItemPropertyIds()) {
            if (!spalte.equals(ANGABE_SPALTE)) {
                final Map<RessourceGroup, DaysHoursMinutesItem> absoluteWerte = timesCalculator.getAbsoluteWerte();
                for (final Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
                    final String spaltenName = spalte.toString();
                    final RessourceGroup key = absoluteWerteEntry.getKey();
                    final String spaltenNameAusMap = key.getName();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<?> absolutItemSpalteProperty = absolutItem.getItemProperty(spalte);
                        final String mapValue = absoluteWerte.get(key).toString();
                        absolutItemSpalteProperty.setValue(mapValue);
                    }
                }
            }
        }

        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final Property<?> relativItemAngabeProperty = relativItem.getItemProperty(ANGABE_SPALTE);
        relativItemAngabeProperty.setValue(SUM_IN_PERCENT);
        for (final Object spalte : relativItem.getItemPropertyIds()) {
            if (!spalte.equals(ANGABE_SPALTE)) {
                final Map<RessourceGroup, Double> relativeWerte = timesCalculator.getRelativeWerte();
                for (final Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
                    final RessourceGroup key = relativeWerteEntry.getKey();
                    final String spaltenNameAusMap = key.getName();
                    final String spaltenName = spalte.toString();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<?> relativItemSpalteProperty = relativItem.getItemProperty(spalte);
                        final String mapValue = format.format(relativeWerte.get(key));
                        relativItemSpalteProperty.setValue(mapValue + " %");
                    }

                }
            }
        }

        final Property<?> kostenItemAngabeProperty = kostenItem.getItemProperty(ANGABE_SPALTE);
        kostenItemAngabeProperty.setValue(SUM_IN_EUR);
        for (final Object spalte : kostenItem.getItemPropertyIds()) {
            if (!spalte.equals(ANGABE_SPALTE)) {
                final Map<RessourceGroup, Double> kostenMap = costsCalculator.getRessourceGroupsCostsMap();
                for (final Map.Entry<RessourceGroup, Double> kostenEntry : kostenMap.entrySet()) {
                    final String spaltenName = spalte.toString();
                    final RessourceGroup key = kostenEntry.getKey();
                    final String spaltenNameAusMap = key.getName();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<?> kostenItemSpalteProperty = kostenItem.getItemProperty(spalte);
                        final String mapValue = format.format(kostenMap.get(key));
                        kostenItemSpalteProperty.setValue(mapValue + " " + EUR);
                    }
                }
            }
        }
    }
}
