package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.OrientDBTransactionExecutor;

import java.security.InvalidKeyException;

import static org.rapidpm.persistence.Edges.CONSISTS_OF;
import static org.rapidpm.persistence.Edges.VALID_FOR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupDAO extends DAO<Long, RessourceGroup> {
    private static final Logger logger = Logger.getLogger(RessourceGroupDAO.class);

    public RessourceGroupDAO(final OrientGraph orientDB) {
        super(orientDB, RessourceGroup.class);
    }

    @Override
    public RessourceGroup createEntityFull(RessourceGroup tempRessourceGroup) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        return createEntityFlat(tempRessourceGroup);
    }

    public RessourceGroup loadRessourceGroupByName(final String name) {
//        final TypedQuery<RessourceGroup> typedQuery = orientDB.createQuery("from RessourceGroup rg "
//                + "where rg.name=:name ", RessourceGroup.class).setParameter("name", name);
//        final RessourceGroup singleResultOrNull = getSingleResultOrNull(typedQuery);
//        return singleResultOrNull;
        return null;
    }

    @Override
    public RessourceGroup loadFull(RessourceGroup entity) throws InvalidKeyException, NotYetImplementedException {
        return findByID(entity.getId(), false);
    }

    @Override
    public void deleteByIDFull(final String id) {
        final Iterable<Vertex> planningUnitElements = orientDB.command(new OCommandSQL("select expand( IN('"+VALID_FOR+"') ) from RessourceGroup where @rid = " + id)).execute();
        for (final Vertex pueVertex : planningUnitElements) {
            DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().deleteByIDFull(pueVertex.getId().toString());
        }
        deleteByIDFlat(id);
    }

//    private void deletePlanningUnitElementsWithRessourceGroup(RessourceGroup entity) {
//        final String deletePUEsQuery = "DELETE VERTEX PlanningUnitElement WHERE @rid IN (Select in('validFor') From RessourceGroup where @rid = "+entity.getId()+")";
//        new OrientDBTransactionExecutor(orientDB) {
//            @Override
//            public void doSpecificDBWork() {
//                orientDB.getRawGraph().command(new OCommandSQL(deletePUEsQuery)).execute();
//            }
//        }.execute();
//    }
}
