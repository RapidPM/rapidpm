package org.rapidpm.persistence.prj.stammdaten.person;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * RapidPM - www.rapidpm.org
 * User: MLI
 * Date: 22.04.2010
 * Time: 15:27:39
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class TitelDAO extends DAO<Long, Titel> {

    public TitelDAO(final EntityManager entityManager) {
        super(entityManager, Titel.class);
    }

    //   public List<Titel> loadTitelFor
}
