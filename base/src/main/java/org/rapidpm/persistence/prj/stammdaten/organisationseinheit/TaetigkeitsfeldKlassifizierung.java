/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:30
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import javax.persistence.*;


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class TaetigkeitsfeldKlassifizierung {
    private static final Logger logger = Logger.getLogger(TaetigkeitsfeldKlassifizierung.class);

    @Id
    @TableGenerator(name = "PKGenTaetigkeitsfeldKlassifizierung",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "TaetigkeitsfeldKlassifizierungc_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenTaetigkeitsfeldKlassifizierung")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String klassifizierung;
    @Basic
    private String beschreibung;
    //    @Basic
    //    private String created;
    //    @Basic
    //    private String modified;

    public String getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final String klassifizierung) {
        this.klassifizierung = klassifizierung;
    }


    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    //    public String getCreated(){
    //        return created;
    //    }
    //
    //    public void setCreated(final String created){
    //        this.created = created;
    //    }
    //
    //    public String getModified(){
    //        return modified;
    //    }
    //
    //    public void setModified(final String modified){
    //        this.modified = modified;
    //    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TaetigkeitsfeldKlassifizierung that = (TaetigkeitsfeldKlassifizierung) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TaetigkeitsfeldKlassifizierung");
        sb.append("{id=").append(getId());
        sb.append(", klassifizierung='").append(klassifizierung).append('\'');
        sb.append(", beschreibung='").append(beschreibung).append('\'');
        //        sb.append(", created='").append(created).append('\'');
        //        sb.append(", modified='").append(modified).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
