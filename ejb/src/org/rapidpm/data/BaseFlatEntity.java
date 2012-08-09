package org.rapidpm.data;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 18:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;

import javax.inject.Inject;

public class BaseFlatEntity {
    //private static final Logger logger = Logger.getLogger(BaseFlatEntity.class);

    @Inject
    private transient Logger logger;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


}