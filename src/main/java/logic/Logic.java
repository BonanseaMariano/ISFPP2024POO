package logic;

import controller.Coordinator;
import exceptions.CicleException;
import exceptions.InvalidConexionException;
import exceptions.InvalidEquipoException;
import exceptions.LoopException;
import models.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Logic {
    private Coordinator coordinator;
    /**
     * A graph representing the network of equipos (devices) and conexiones (connections).
     * The vertices are Equipo objects and the edges are Conexion objects.
     */
    private Graph<Equipo, Conexion> graph;
    /**
     * A map that stores the equipos (devices) in the network.
     * The keys are strings representing the device identifiers,
     * and the values are Equipo objects representing the devices.
     */
    private Map<String, Equipo> equipos;
    /**
     * A map that stores the connections (conexiones) in the network.
     * The keys are strings representing the connection identifiers,
     * and the values are Conexion objects representing the connections.
     */
    private Map<String, Conexion> conexiones;


    public Logic() {
        graph = new SimpleWeightedGraph<>(Conexion.class);
        equipos = new ConcurrentHashMap<>();
        conexiones = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves the graph representing the network of equipos (devices) and conexiones (connections).
     *
     * @return the graph representing the network
     */
    public Graph<Equipo, Conexion> getGraph() {
        return graph;
    }

    /**
     * Updates the graph data with the provided lists of equipos (vertices) and conexiones (edges).
     * This method clears the existing graph and repopulates it with the new data.
     *
     * @param equipos    a list of Equipo objects representing the vertices to be added to the graph
     * @param conexiones a list of Conexion objects representing the edges to be added to the graph
     */
    public void updateData(List<Equipo> equipos, List<Conexion> conexiones) {
        // Clear the graph
        graph = new SimpleWeightedGraph<>(Conexion.class);

        // Add vertices
        for (Equipo equipo : equipos) {
            addVertex(equipo);
        }

        // Add edges
        for (Conexion conexion : conexiones) {
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
        equipos.put(equipo.getCodigo(), equipo);
    }

    /**
     * Adds an edge (conexion) to the graph and sets its weight based on the cable type's speed.
     *
     * @param conexion the edge (conexion) to be added to the graph
     */
    public void addEdge(Conexion conexion) throws InvalidEquipoException, LoopException, CicleException {
        if (graph.containsEdge(conexion)) {
            throw new InvalidEquipoException("No se puede agregar la conexión porque ya existe.");
        }
        if (!this.graph.containsVertex(conexion.getEquipo1()) || !this.graph.containsVertex(conexion.getEquipo2())) {
            throw new InvalidEquipoException("No se puede agregar la conexión porque " + conexion.getEquipo1().getCodigo() + " y/o " + conexion.getEquipo2().getCodigo() + " no existen en la red.");
        }
        if (conexion.getEquipo1().equals(conexion.getEquipo2())) {
            throw new LoopException("No se puede agregar " + conexion + " porque los equipos son iguales");
        }
        if (!availablePorts(conexion.getEquipo1()) || !availablePorts(conexion.getEquipo2())) {
            throw new InvalidConexionException("No se puede agregar " + conexion + " porque no hay puerto disponible");
        }
        graph.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
        graph.setEdgeWeight(conexion, conexion.getTipoCable().getVelocidad());
        if (ciclesValidation()) {
            deleteEdge(conexion);
            throw new CicleException("No se puede agregar " + conexion + " porque se forma un ciclo");
        }
        conexiones.put(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo(), conexion);
    }

    /**
     * Removes a vertex (equipo) from the graph.
     *
     * @param equipo the vertex (equipo) to be removed from the graph
     */
    public void deleteVertex(Equipo equipo) {
        graph.removeVertex(equipo);
        equipos.remove(equipo.getCodigo());
    }

    /**
     * Removes an edge (conexion) from the graph.
     *
     * @param conexion the edge (conexion) to be removed from the graph
     */
    public void deleteEdge(Conexion conexion) {
        graph.removeEdge(conexion);
        conexiones.remove(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo());
    }

    /**
     * Verifies if there is any cycle in the undirected graph.
     *
     * @return true if there is a cycle, false otherwise
     */
    private boolean ciclesValidation() {
        Set<Equipo> visited = new HashSet<>();
        for (Equipo equipo : graph.vertexSet()) {
            if (!visited.contains(equipo)) {
                if (validationDfs(equipo, null, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to perform DFS and check for cycles.
     *
     * @param current the current node being visited
     * @param parent  the parent node of the current node
     * @param visited the set of visited nodes
     * @return true if a cycle is detected, false otherwise
     */
    private boolean validationDfs(Equipo current, Equipo parent, Set<Equipo> visited) {
        visited.add(current);
        for (Conexion conexion : graph.edgesOf(current)) {
            Equipo neighbor = Graphs.getOppositeVertex(graph, conexion, current);
            if (!visited.contains(neighbor)) {
                if (validationDfs(neighbor, current, visited)) {
                    return true;
                }
            } else if (!neighbor.equals(parent)) {
                return true;
            }
        }
        return false;
    }

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

    /**
     * Retrieves the connected part of the network starting from a given vertex.
     * This method creates a new graph containing only the vertices and edges
     * that are reachable from the specified vertex using a Depth-First Search (DFS) approach.
     *
     * @param vertex the starting vertex from which the connected part is determined
     * @return a new graph containing the connected part of the network
     */
    public Graph<Equipo, Conexion> getConnectedPart(Equipo vertex) {
        // Create a new graph to store the connected part
        Graph<Equipo, Conexion> newGraph = new SimpleWeightedGraph<>(Conexion.class);

        // Use a DFS algorithm to traverse the graph
        validationDfs(vertex, newGraph);

        // Add edges to the new graph where both vertices are present in the new graph
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

    /**
     * Performs a Depth-First Search (DFS) to find and add all reachable vertices
     * from the given starting vertex to the connected subgraph.
     *
     * @param vertex         the starting vertex for the DFS traversal
     * @param parteConectada the graph that will store the connected subgraph
     */
    private void validationDfs(Equipo vertex, Graph<Equipo, Conexion> parteConectada) {
        // Add the vertex to the connected subgraph
        parteConectada.addVertex(vertex);

        // Iterate through adjacent vertices
        for (Equipo adjacentVertex : Graphs.neighborListOf(graph, vertex)) {
            // If the adjacent vertex has not been visited, add it to the connected subgraph and continue DFS
            if (!parteConectada.containsVertex(adjacentVertex) && adjacentVertex.isEstado()) {
                parteConectada.addEdge(vertex, adjacentVertex);
                validationDfs(adjacentVertex, parteConectada);
            }
        }
    }

    /**
     * Sets the coordinator for the logic.
     *
     * @param coordinator the Coordinator object to be set
     */
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    /**
     * Checks if the given equipo (device) has available ports.
     * This method compares the number of edges connected to the equipo
     * with the total number of ports available on the equipo.
     *
     * @param equipo the equipo (device) to check for available ports
     * @return true if the number of connected edges is less than or equal to the number of available ports, false otherwise
     */
    public boolean availablePorts(Equipo equipo) {
        return edgeLength(equipo) <= equipo.getCantidadPuertos();
    }

    /**
     * Calculates the number of edges connected to a given vertex (equipo).
     * This method iterates through all edges in the graph and counts how many
     * are connected to the specified vertex.
     *
     * @param vertice the vertex (equipo) for which the edge count is calculated
     * @return the number of edges connected to the specified vertex
     */
    private int edgeLength(Equipo vertice) {
        int edgeLength = 0;
        for (Conexion c : graph.edgeSet()) {
            if (c.getEquipo1().equals(vertice) || c.getEquipo2().equals(vertice)) {
                edgeLength++;
            }
        }
        return edgeLength;
    }

}
