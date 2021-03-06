package org.rapidpm.persistence.system.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:43
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class LogginEntityEntryDAO extends DAO<Long, LoggingEntityEntry> {
    private static final Logger logger = Logger.getLogger(LogginEntityEntryDAO.class);

    public LogginEntityEntryDAO(final EntityManager entityManager) {
        super(entityManager, LoggingEntityEntry.class);
    }

}