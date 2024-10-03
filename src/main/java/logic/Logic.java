package logic;

import models.*;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

public class Logic {
    private static Logic instance;
    private static Graph<Equipo, Conexion> graph;

    private Logic() {
        graph = new SimpleWeightedGraph<>(Conexion.class);
    }

    public static Logic getInstance() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }

    public static Graph<Equipo, Conexion> getGraph() {
        return graph;
    }

    /**
     * Updates the graph with the vertices and edges from the given network (Red).
     *
     * @param red the network containing the vertices (Equipos) and edges (Conexiones) to be added to the graph
     */
    public static void updateGraph(Red red) {
        // Clear the graph
        graph = new SimpleWeightedGraph<>(Conexion.class);

        // Add vertices
        for (Equipo equipo : red.getEquipos().values()) {
            graph.addVertex(equipo);
        }

        // Add edges
        for (Conexion conexion : red.getConexiones()) {
            graph.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
            graph.setEdgeWeight(conexion, conexion.getTipoCable().getVelocidad());
        }
    }

    /**
     * Adds a vertex (equipo) to the graph.
     *
     * @param equipo the vertex (equipo) to be added to the graph
     */
    public static void addVertex(Equipo equipo) {
        graph.addVertex(equipo);
    }

    /**
     * Adds an edge (conexion) to the graph and sets its weight based on the cable type's speed.
     *
     * @param conexion the edge (conexion) to be added to the graph
     */
    public static void addEdge(Conexion conexion) {
        graph.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
        graph.setEdgeWeight(conexion, conexion.getTipoCable().getVelocidad());
    }

    /**
     * Removes a vertex (equipo) from the graph.
     *
     * @param equipo the vertex (equipo) to be removed from the graph
     */
    public static void deleteVertex(Equipo equipo) {
        graph.removeVertex(equipo);
    }

    /**
     * Removes an edge (conexion) from the graph.
     *
     * @param conexion the edge (conexion) to be removed from the graph
     */
    public static void deleteEdge(Conexion conexion) {
        graph.removeEdge(conexion);
    }

    //TODO: 3.1 Dado dos equipos mostrar todos los equipos intermedios y sus conexiones. Calcular la velocidad máxima de acuerdo al tipo de puerto y cables por donde se transmiten los datos.

    /**
     * Finds the shortest path between two given nodes (equipos) in the graph.
     *
     * @param origin the starting node (equipo) of the path
     * @param end    the ending node (equipo) of the path
     * @return a list of Conexion objects representing the edges in the shortest path
     */
    public static List<Conexion> shortestPath(Equipo origin, Equipo end) {
        DijkstraShortestPath<Equipo, Conexion> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPath(origin, end).getEdgeList();
    }


    /**
     * Calculates the maximum bandwidth along a given path.
     *
     * @param path a list of Conexion objects representing the path
     * @return the maximum bandwidth (minimum weight) along the path
     */
    public static double maxBandwith(List<Conexion> path) {
//        Teniendo en cuenta unicamente el peso de la conexion (Complejodad O(n))
        return path.stream()
                .mapToDouble(conexion -> conexion.getTipoCable().getVelocidad())
                .min()
                .orElse(0);

        //Teniendo en cuenta el peso de la conexion y el tipo de cable y puerto (Complejidad O(n^2))
//        double maxBW = 0;
//        for (Conexion conexion : path) {
//            if (conexion.getTipoCable().getVelocidad() < maxBW) {
//                maxBW = conexion.getTipoCable().getVelocidad();
//            }
//            for (Puerto puerto : conexion.getEquipo1().getPuertos()) {
//                if (puerto.getTipoPuerto().getVelocidad() < maxBW) {
//                    maxBW = puerto.getTipoPuerto().getVelocidad();
//                }
//            }
//        }
//
//        return maxBW;
    }

    //TODO: 3.2 Realizar un ping a un equipo. Realizar un ping a un rango de IP. Realizar un mapa del estado actual de los equipos conectados a la red.
    //TODO: 3.3 Detectar problemas de conectividad, por ejemplo un usuario de una computadora no puede navegar en Internet. Detectar hasta que parte de la red puede acceder y donde pierde la conectividad.

}
