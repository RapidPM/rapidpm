package org.rapidpm.persistence.prj.stammdaten.address;
/**
 * NeoScio
 * User: svenruppert
 * Date: 06.03.2010
 * Time: 18:45:14
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class StateDAO extends DAO<Long, State> {
    private static final Logger logger = Logger.getLogger(StateDAO.class);

    public StateDAO(final EntityManager entityManager) {
        super(entityManager, State.class);
    }

    public State loadStateForShortname(final String shortname) {
        final TypedQuery<State> typedQuery = entityManager.createQuery("from State s where s.kurzName=:shortname", State.class)
                .setParameter("shortname", shortname);
        return getSingleResultOrNull(typedQuery);
    }
}
