package org.rapidpm.persistence.prj.textelement.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class AbsatzKommentarDAO extends DAO<Long, AbsatzKommentar> {
    private static final Logger logger = Logger.getLogger(AbsatzKommentarDAO.class);


    public AbsatzKommentarDAO(final EntityManager entityManager) {
        super(entityManager, AbsatzKommentar.class);
    }
}
