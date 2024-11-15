package logic;

import controller.Coordinator;
import exceptions.*;
import models.*;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Logic class is responsible for managing the network of equipos (devices) and conexiones (connections).
 * It provides methods to manipulate the graph, such as adding, modifying, and deleting vertices and edges,
 * as well as performing various network operations like finding the shortest path and checking for cycles.
 */
public class Logic {
    /**
     * The coordinator responsible for managing the logic operations.
     */
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
    private Map<String, Equipo> vertexMap;

    /**
     * Constructs a new Logic instance.
     * Initializes the graph, vertexMap, and edgesMap.
     */
    public Logic() {
        graph = new SimpleWeightedGraph<>(Conexion.class);
        vertexMap = new HashMap<>();
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
     * Sets the coordinator for the logic.
     *
     * @param coordinator the Coordinator object to be set
     */
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    /**
     * Retrieves the map of vertexes (equipos) in the graph.
     *
     * @return a map where the keys are strings representing the device identifiers,
     * and the values are Equipo objects representing the devices
     */
    public Map<String, Equipo> getVertexMap() {
        return vertexMap;
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
    public void addVertex(Equipo equipo) throws IllegalArgumentException {
        if (vertexMap.containsKey(equipo.getCodigo())) {
            throw new IllegalArgumentException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        graph.addVertex(equipo);
        vertexMap.put(equipo.getCodigo(), equipo);
    }

    /**
     * Modifies a vertex (equipo) in the graph.
     * <p>
     * This method first saves all the connections of the equipo to be modified.
     * It then removes the equipo from the graph, updates the vertex map, and adds the equipo back to the graph.
     * Finally, it re-adds all the saved connections to the equipo.
     *
     * @param equipo the vertex (equipo) to be modified in the graph
     * @throws InvalidEquipoException if the equipo does not exist in the graph
     */
    public void modifyVertex(Equipo equipo) throws InvalidEquipoException {
        //Save all the connections of the equipo
        List<Conexion> conexiones = new ArrayList<>();
        for (Conexion conexion : graph.edgesOf(equipo)) {
            conexiones.add(conexion);
        }
        if (!graph.removeVertex(equipo)) { // if the equipo does not exist in the graph
            throw new InvalidEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownM"));
        } else { // if the equipo exists in the graph it removes it with all its connections and adds it again with the new data
            vertexMap.put(equipo.getCodigo(), equipo);
            graph.addVertex(equipo);
            //Add all the connections of the equipo
            for (Conexion conexion : conexiones) {
                graph.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
                graph.setEdgeWeight(conexion, conexion.getTipoCable().getVelocidad());
            }
        }
    }

    /**
     * Deletes a vertex (equipo) from the graph.
     * <p>
     * This method removes the specified equipo from the graph and also removes all
     * associated edges (conexiones) connected to the equipo. Additionally, it removes
     * the equipo from the equipos map.
     *
     * @param equipo the vertex (equipo) to be removed from the graph
     * @throws InvalidEquipoException if the equipo does not exist in the graph
     */
    public void deleteVertex(Equipo equipo) throws InvalidEquipoException {
        if (graph.removeVertex(equipo)) {
            vertexMap.remove(equipo.getCodigo());
        } else {
            throw new InvalidEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownD"));
        }
    }

    /**
     * Validates a connection (conexion) to ensure it can be added to the graph.
     *
     * @param conexion the connection (conexion) to be validated
     * @throws InvalidConexionException if the connection is invalid
     */
    private void edgeValidation(Conexion conexion) throws InvalidConexionException {
        // Check if the connection forms a loop (both equipos are the same)
        if (conexion.getEquipo1().equals(conexion.getEquipo2())) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("InvalidConnection_sameDevice"));
        }
        // Check if there are available ports on both equipos
        if (!availablePorts(conexion.getEquipo1()) || !availablePorts(conexion.getEquipo2())) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("InvalidConnection_noAvailablePorts"));
        }
    }

    /**
     * Adds an edge (conexion) to the graph.
     * <p>
     * This method first checks if the edge already exists in the graph. If it does, an InvalidConexionException is thrown.
     * It then validates the edge, adds it to the graph, and sets the edge weight based on the connection's cable speed.
     * If adding the edge forms a cycle, the edge is removed and an InvalidConexionException is thrown.
     * Finally, the edge is added to the edgesMap.
     *
     * @param conexion the edge (conexion) to be added to the graph
     * @throws InvalidConexionException if the edge already exists or if adding the edge forms a cycle
     */
    public void addEdge(Conexion conexion) throws InvalidConexionException {
        if (graph.containsEdge(graph.getEdge(conexion.getEquipo1(), conexion.getEquipo2()))) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("InvalidConnection_existingConnection"));
        }
        edgeValidation(conexion);
        graph.addEdge(conexion.getEquipo1(), conexion.getEquipo2(), conexion);
        graph.setEdgeWeight(conexion, conexion.getTipoCable().getVelocidad());
        if (ciclesValidation()) {
            deleteEdge(conexion);
            throw new InvalidConexionException(conexion + " " + coordinator.getResourceBundle().getString("InvalidConnection_cycle"));
        }
    }

    /**
     * Modifies an edge (conexion) in the graph.
     * <p>
     * This method modifies the edge weight of the specified conexion based on the connection's cable speed.
     *
     * @param conexion the edge (conexion) to be modified in the graph
     */
    public void modifyEdge(Conexion conexion) throws InvalidConexionException {
        try {
            graph.setEdgeWeight(graph.getEdge(conexion.getEquipo1(), conexion.getEquipo2()), conexion.getTipoCable().getVelocidad());
        } catch (IllegalArgumentException e) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("Invalid_unknownM") + " logic");
        }
    }

    /**
     * Deletes an edge (conexion) from the graph.
     * <p>
     * This method removes the specified conexion from the graph and also removes
     * the conexion from the conexiones map. If the conexion does not exist in the graph,
     * an InvalidConexionException is thrown.
     *
     * @param conexion the edge (conexion) to be removed from the graph
     * @throws InvalidConexionException if the conexion does not exist in the graph
     */
    public void deleteEdge(Conexion conexion) throws InvalidConexionException {
        if (!graph.removeEdge(graph.getEdge(conexion.getEquipo1(), conexion.getEquipo2()))) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("Invalid_unknownD"));
        }
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
     * Finds the shortest path between two devices (equipos) in the network.
     * <p>
     * This method uses Dijkstra's algorithm to find the shortest path between the origin and end devices.
     * If no path exists, it returns null.
     *
     * @param origin the starting device (equipo) for the path
     * @param end    the ending device (equipo) for the path
     * @return a list of Conexion objects representing the shortest path, or null if no path exists
     */
    public List<Conexion> shortestPath(Equipo origin, Equipo end) {
        DijkstraShortestPath<Equipo, Conexion> dijkstraAlg = new DijkstraShortestPath<>(graph);
        if (dijkstraAlg.getPath(origin, end) == null) {
            return null;
        }
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
        Map<String, Boolean> results = new LinkedHashMap<>();
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
        if (vertex.isEstado()) {
            // Iterate through adjacent vertices
            for (Equipo adjacentVertex : Graphs.neighborListOf(graph, vertex)) {
                // If the adjacent vertex has not been visited, add it to the connected subgraph and continue DFS
                if (!parteConectada.containsVertex(adjacentVertex) && adjacentVertex.isEstado()) {
                    validationDfs(adjacentVertex, parteConectada);
                }
            }
        }
    }

    /**
     * Checks if the given equipo (device) has available ports.
     * This method compares the number of edges connected to the equipo
     * with the total number of ports available on the equipo.
     *
     * @param equipo the equipo (device) to check for available ports
     * @return true if the number of connected edges is less than or equal to the number of available ports, false otherwise
     */
    private boolean availablePorts(Equipo equipo) {
        return edgeLength(equipo) < equipo.getCantidadPuertos();
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
