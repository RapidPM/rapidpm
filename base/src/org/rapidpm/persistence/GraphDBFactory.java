package org.rapidpm.persistence;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class GraphDBFactory {

    private final GraphDatabaseService graphDb;
    private static GraphDBFactory instance;

    public static GraphDBFactory getInstance() {
        if (instance == null)
            instance = new GraphDBFactory();
        return instance;
    }

    private GraphDBFactory() {
        graphDb = new EmbeddedGraphDatabase("out/graphDB");
        registerShutdownHook(graphDb);
    }

    public GraphDatabaseService getGraphDBService() {
        return graphDb;
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
}
