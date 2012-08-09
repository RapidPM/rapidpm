package org.rapidpm.orm;

//import com.avaje.ebean.EbeanServerFactory;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: Nov 16, 2010
 * Time: 5:36:49 PM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class DaoFactorySingelton {
    private static DaoFactorySingelton ourInstance = new DaoFactorySingelton();

    private DaoFactory daoFactory = null;

    public static DaoFactorySingelton getInstance() {
        System.out.println("ourInstance = " + ourInstance);
        return ourInstance;
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    private DaoFactorySingelton() {
        //        final EbeanServer em = EbeanServerFactory.create(new ConfigDevelop());
        //        daoFactory = new BaseDaoFactory(em);

    }
}