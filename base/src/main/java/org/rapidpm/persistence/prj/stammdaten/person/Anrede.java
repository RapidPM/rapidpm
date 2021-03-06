/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.person;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:37
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import org.apache.log4j.Logger;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Anrede {
    private static final Logger logger = Logger.getLogger(Anrede.class);

    @Id
    @TableGenerator(name = "PKGenAnrede", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Anrede_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenAnrede")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String anrede;


    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(final String anrede) {
        this.anrede = anrede;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Anrede anrede = (Anrede) o;

        if (getId() != null ? !getId().equals(anrede.getId()) : anrede.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
