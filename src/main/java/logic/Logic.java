package logic;

import exceptions.InvalidDireccionIPException;
import models.*;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.spanning.PrimMinimumSpanningTree;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import utils.Utils;

import java.util.*;

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
            addVertex(equipo);
        }

        // Add edges
        for (Conexion conexion : red.getConexiones()) {
            addEdge(conexion);
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
     * Updates the weights of all edges in the graph.
     * The weight of each edge is set to the difference between the provided top weight and the speed of the cable type associated with the edge.
     *
     * @param topWeight the maximum weight to be used as a reference for updating the edge weights
     */
    public static void updateEdgesWeight(int topWeight) {
        for (Conexion conexion : graph.edgeSet()) {
            graph.setEdgeWeight(conexion, topWeight - conexion.getTipoCable().getVelocidad());
        }
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

    // Dado dos equipos mostrar todos los equipos intermedios y sus conexiones.
    // Calcular la velocidad máxima de acuerdo al tipo de puerto y cables por donde se transmiten los datos.

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
        //Teniendo en cuenta el peso de la conexion y el tipo de cable y puerto (Complejidad O(n^2))
        double maxBW = 0;
        for (Conexion conexion : path) {
            if (conexion.getTipoCable().getVelocidad() < maxBW) {
                maxBW = conexion.getTipoCable().getVelocidad();
            }
            for (Puerto puerto : conexion.getEquipo1().getPuertos()) {
                if (puerto.getTipoPuerto().getVelocidad() < maxBW) {
                    maxBW = puerto.getTipoPuerto().getVelocidad();
                }
            }

            //Si es el ultimo equipo de la conexion
            if (path.indexOf(conexion) == path.size() - 1) {
                for (Puerto puerto : conexion.getEquipo2().getPuertos()) {
                    if (puerto.getTipoPuerto().getVelocidad() < maxBW) {
                        maxBW = puerto.getTipoPuerto().getVelocidad();
                    }
                }
            }

        }

        return maxBW;
    }


    // Realizar un ping a un equipo.

    /**
     * Pings a given IP address to check if it is active.
     *
     * @param ip the IP address to ping
     * @return true if the IP address is active, false otherwise
     */
    public static boolean ping(String ip) {
        for (Equipo equipo : graph.vertexSet()) {
            for (String direccionIP : equipo.getDireccionesIp()) {
                if (direccionIP.equals(ip)) {
                    return equipo.isEstado();
                }
            }
        }
        return false;
    }

    // Realizar un ping a un rango de IP.

    /**
     * Pings a range of IP addresses to check if they are active.
     *
     * @param ips a collection of IP addresses to ping
     * @return a map where the keys are IP addresses and the values are booleans indicating if the IP address is active
     */
    public static Map<String, Boolean> pingRange(Collection<String> ips) {
        Map<String, Boolean> results = new TreeMap<>();
        for (String ip : ips) {
            results.put(ip, ping(ip));
        }
        return results;
    }

    // Realizar un mapa del estado actual de los equipos conectados a la red.

    /**
     * Creates a map of the current status of all equipos (devices) connected to the network.
     *
     * @return a map where the keys are Equipo objects and the values are booleans indicating if the equipo is active
     */
    public static Map<Equipo, Boolean> mapStatus() {
        Map<Equipo, Boolean> status = new TreeMap<>();
        for (Equipo equipo : graph.vertexSet()) {
            status.put(equipo, equipo.isEstado());
        }
        return status;
    }

    //TODO: 3.3 Detectar problemas de conectividad, por ejemplo un usuario de una computadora no puede navegar en Internet. Detectar hasta que parte de la red puede acceder y donde pierde la conectividad.

    public static Set<Equipo> conectionProblems(Equipo equipo) {
        Set<Equipo> equipos = new HashSet<>();
        Set<Equipo> visited = new HashSet<>();
        DepthFirstIterator<Equipo, Conexion> iterator = new DepthFirstIterator<>(graph, equipo);

        while (iterator.hasNext()) {
            Equipo current = iterator.next();
            if (!visited.contains(current)) {
                visited.add(current);
                if (!current.isEstado()) {
                    equipos.add(current);
                }
            }
        }

        return equipos;
    }

}
