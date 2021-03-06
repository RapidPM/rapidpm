package org.rapidpm.persistence.rohdaten;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 21, 2010
 * Time: 5:37:52 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.DAOTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OntologieConnectionDAOTest extends DAOTest {

    @Test
    public void testLoadOntologieConnection() throws Exception {
        final OntologieConnectionDAO dao = daoFactory.getOntologieConnectionDAO();
        final OntologieConnection oc = dao.loadOntologieConnection("IST EIN");
        assertNotNull(oc);
        assertEquals(oc.getName(), "IST EIN");
    }
}
