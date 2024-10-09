package logic;

import models.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Logic {
    private Graph<Equipo, Conexion> graph;

    public Logic() {
        graph = new SimpleWeightedGraph<>(Conexion.class);
    }

    public Graph<Equipo, Conexion> getGraph() {
        return graph;
    }

    /**
     * Updates the graph with the vertices and edges from the given network (Red).
     *
     * @param red the network containing the vertices (Equipos) and edges (Conexiones) to be added to the graph
     */
    public void updateGraph(Red red) {
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
    public void addVertex(Equipo equipo) {
        graph.addVertex(equipo);
    }

    /**
     * Adds an edge (conexion) to the graph and sets its weight based on the cable type's speed.
     *
     * @param conexion the edge (conexion) to be added to the graph
     */
    public void addEdge(Conexion conexion) {
        graph.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
        graph.setEdgeWeight(conexion, conexion.getTipoCable().getVelocidad());
    }

    /**
     * Removes a vertex (equipo) from the graph.
     *
     * @param equipo the vertex (equipo) to be removed from the graph
     */
    public void deleteVertex(Equipo equipo) {
        graph.removeVertex(equipo);
    }

    /**
     * Removes an edge (conexion) from the graph.
     *
     * @param conexion the edge (conexion) to be removed from the graph
     */
    public void deleteEdge(Conexion conexion) {
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
    public List<Conexion> shortestPath(Equipo origin, Equipo end) {
        DijkstraShortestPath<Equipo, Conexion> dijkstraAlg = new DijkstraShortestPath<>(graph);
        return dijkstraAlg.getPath(origin, end).getEdgeList();
    }

    /**
     * Calculates the maximum bandwidth along a given path.
     *
     * @param path a list of Conexion objects representing the path
     * @return the maximum bandwidth (minimum weight) along the path
     */
    public double maxBandwith(List<Conexion> path) {
        //Teniendo en cuenta el peso de la conexion y el tipo de cable y puerto (Complejidad O(n^2))
        double maxBW = path.getFirst().getTipoCable().getVelocidad();
        for (Conexion conexion : path) {
            if (conexion.getTipoCable().getVelocidad() < maxBW) {
                maxBW = conexion.getTipoCable().getVelocidad();
            }
            if (conexion.getPuerto1().getVelocidad() < maxBW) {
                maxBW = conexion.getPuerto1().getVelocidad();
            }

            if (conexion.getPuerto2().getVelocidad() < maxBW) {
                maxBW = conexion.getPuerto2().getVelocidad();
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
    public boolean ping(String ip) {
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
    public Map<String, Boolean> pingRange(Collection<String> ips) {
        Map<String, Boolean> results = new ConcurrentHashMap<>();
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
    public Map<Equipo, Boolean> mapStatus() {
        Map<Equipo, Boolean> status = new ConcurrentHashMap<>();
        for (Equipo equipo : graph.vertexSet()) {
            status.put(equipo, equipo.isEstado());
        }
        return status;
    }

    //TODO: 3.3 Detectar problemas de conectividad, por ejemplo un usuario de una computadora no puede navegar en Internet. Detectar hasta que parte de la red puede acceder y donde pierde la conectividad.

    /**
     * Creates a copy of the graph, removing vertices and edges where isEstado() == false.
     *
     * @return a new graph with only active vertices and edges
     */
    public Graph<Equipo, Conexion> copyGraph() {
        Graph<Equipo, Conexion> newGraph = new SimpleWeightedGraph<>(Conexion.class);

        // Add vertices with isEstado() == true
        for (Equipo equipo : graph.vertexSet()) {
            if (equipo.isEstado()) {
                newGraph.addVertex(equipo);
            }
        }

        // Add edges where both vertices are in the new graph
        for (Conexion conexion : graph.edgeSet()) {
            Equipo source = graph.getEdgeSource(conexion);
            Equipo target = graph.getEdgeTarget(conexion);
            if (newGraph.containsVertex(source) && newGraph.containsVertex(target)) {
                newGraph.addEdge(source, target, conexion);
                newGraph.setEdgeWeight(conexion, graph.getEdgeWeight(conexion));
            }
        }

        return newGraph;
    }

    public Graph<Equipo, Conexion> getConnectedPart(Equipo vertex) {
        // Crea un nuevo grafo para almacenar la parte conectada
        Graph<Equipo, Conexion> newGraph = new SimpleWeightedGraph<>(Conexion.class);

        // Utiliza un algoritmo DFS para recorrer el grafo
        dfs(vertex, newGraph);

        for (Conexion conexion : graph.edgeSet()) {
            Equipo source = graph.getEdgeSource(conexion);
            Equipo target = graph.getEdgeTarget(conexion);
            if (newGraph.containsVertex(source) && newGraph.containsVertex(target)) {
                newGraph.addEdge(source, target, conexion);
                newGraph.setEdgeWeight(conexion, graph.getEdgeWeight(conexion));
            }
        }

        return newGraph;
    }

    private void dfs(Equipo vertex, Graph<Equipo, Conexion> parteConectada) {
        // Agrega el vértice al grafo conectado
        parteConectada.addVertex(vertex);

        // Recorre los vértices adyacentes
        for (Equipo adjacentVertex : Graphs.neighborListOf(graph, vertex)) {
            // Si el vértice adyacente no ha sido visitado, agrega al grafo conectado y recorre
            if (!parteConectada.containsVertex(adjacentVertex) && adjacentVertex.isEstado()) {
                parteConectada.addEdge(vertex, adjacentVertex);
                dfs(adjacentVertex, parteConectada);
            }
        }
    }
}
