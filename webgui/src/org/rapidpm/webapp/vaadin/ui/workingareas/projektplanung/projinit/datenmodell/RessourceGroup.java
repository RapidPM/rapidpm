package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import java.util.Date;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:54
 *
 * RessourceGroup, die Gruppierung der internen/externen Personalressourcen.
 * z.B.
 *  - Aushilfe
 *  - Werksstudent
 *  - Junior Developer
 *  - Developer
 *  - Seniordeveloper
 *  - PL
 *  - MPM
 *  - GF
 *
 */

public class RessourceGroup {


    private Long id;

    private String name;
    private String description;

   private Date validFrom;
     private Date validUntil;

    private String currency; // USD / EUR / ..
    private float costPerHour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(float costPerHour) {
        this.costPerHour = costPerHour;
    }
}
