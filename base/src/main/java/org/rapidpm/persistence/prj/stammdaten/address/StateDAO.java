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

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class StateDAO extends DAO<Long, State> {
    private static final Logger logger = Logger.getLogger(StateDAO.class);

    public StateDAO(final OrientGraph orientDB) {
        super(orientDB, State.class);
    }

    public State loadStateForShortname(final String shortname) {
//        final TypedQuery<State> typedQuery = orientDB.createQuery("from State s where s.kurzName=:shortname", State.class)
//                .setParameter("shortname", shortname);
//        return getSingleResultOrNull(typedQuery);
        return null;
    }

    @Override
    public State loadFull(State entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public State createEntityFull(State entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        throw new NotYetImplementedException();
    }
}
