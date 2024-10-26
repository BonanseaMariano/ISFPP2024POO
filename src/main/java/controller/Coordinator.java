package controller;

import exceptions.*;
import gui.Gui;
import logic.Logic;
import logic.Red;
import models.*;

import java.util.*;

/**
 * The Coordinator class is responsible for managing the network (Red), logic (Logic), and graphical user interface (Gui) instances keeping them in sync.
 * <p>
 * This class provides methods to manipulate and retrieve information about the network, including adding, deleting, and modifying devices (equipos),
 * connections (conexiones), locations (ubicaciones), cable types (tipoCable), and port types (tipoPuerto). It also includes methods for network operations
 * such as finding the shortest path, calculating maximum bandwidth, and pinging devices.
 */
public class Coordinator {
    /**
     * The network (Red) instance.
     */
    private Red red;

    /**
     * The logic (Logic) instance.
     */
    private Logic logic;

    /**
     * The graphical user interface (Gui) instance.
     */
    private Gui gui;

    /**
     * Gets the network (Red) instance.
     *
     * @return the network instance
     */
    public Red getRed() {
        return red;
    }

    /**
     * Sets the network (Red) instance.
     *
     * @param red the network instance to set
     */
    public void setRed(Red red) {
        this.red = red;
    }

    /**
     * Gets the logic (Logic) instance.
     *
     * @return the logic instance
     */
    public Logic getLogic() {
        return logic;
    }

    /**
     * Sets the logic (Logic) instance.
     *
     * @param logic the logic instance to set
     */
    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    /**
     * Gets the graphical user interface (Gui) instance.
     *
     * @return the GUI instance
     */
    public Gui getGui() {
        return gui;
    }

    /**
     * Sets the graphical user interface (Gui) instance.
     *
     * @param gui the GUI instance to set
     */
    public void setGui(Gui gui) {
        this.gui = gui;
    }


    /* ----------------------------- Synchronized methods  ----------------------------- */


    /**
     * Adds a connection (Conexion) to the network.
     * <p>
     * This method attempts to add the connection to the logic layer first. If successful, it then adds the connection
     * to the network (Red) and updates the graphical user interface (Gui) to reflect the new connection.
     * If any exception occurs during the process, the connection is not added and an error message is printed.
     *
     * @param conexion the connection to be added
     */
    public void addConnection(Conexion conexion) {
        try {
            logic.addEdge(conexion);
        } catch (InvalidConexionException | LoopException | CicleException | NoAvailablePortsException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            red.addConexion(conexion);
            gui.addVisualEdge(conexion);
        } catch (InvalidEquipoException | InvalidConexionException | NoAvailablePortsException |
                 InvalidTipoCableException | InvalidTipoPuertoException e) {
            logic.deleteEdge(conexion);
            System.out.println(e.getMessage());
        }
    }


    /**
     * Deletes a connection (Conexion) from the network.
     * <p>
     * This method attempts to delete the connection from the network (Red) and the logic layer.
     * If the connection is invalid and cannot be deleted, an InvalidConexionException is caught and its message is printed.
     * The graphical user interface (Gui) is also updated to reflect the removal of the connection.
     *
     * @param conexion the connection to be deleted
     */
    public void deleteConnection(Conexion conexion) {
        try {
            red.deleteConexion(conexion);
        } catch (InvalidConexionException e) {
            System.out.println(e.getMessage());
        }

        try {
            logic.deleteEdge(conexion);
            gui.removeVisualEdge(conexion);
        } catch (InvalidConexionException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Modifies an existing connection (Conexion) in the network.
     * <p>
     * This method attempts to modify the connection in the logic layer first. If successful, it then modifies the connection
     * in the network (Red) and updates the graphical user interface (Gui) to reflect the new connection.
     * If any exception occurs during the process, the connection is reverted to its original state and an error message is printed.
     *
     * @param oldConexion the existing connection to be replaced
     * @param newConexion the new connection to replace the old one
     */
    public void modifyConnection(Conexion oldConexion, Conexion newConexion) {
        Conexion oldConnection = null;
        try {
            logic.modifyEdge(oldConexion, newConexion);
        } catch (InvalidConexionException | LoopException | CicleException | NoAvailablePortsException e) {
            System.out.println(e.getMessage() + " en logic");
            return;
        }

        try {
            red.modifyConnection(oldConexion, newConexion);
            gui.modifyVisualEdge(oldConexion, newConexion);
        } catch (InvalidConexionException | NoAvailablePortsException e) {
            System.out.println(e.getMessage());
        } catch (InvalidEquipoException |
                 InvalidTipoCableException | InvalidTipoPuertoException e) {
            logic.modifyEdge(newConexion, oldConnection); // Revert changes
            System.out.println(e.getMessage());
        }
    }


    /**
     * Adds a device (Equipo) to the network.
     * <p>
     * This method attempts to add the device to the network (Red), logic layer, and graphical user interface (Gui).
     * If any exception occurs during the process, the device is not added and an error message is printed.
     *
     * @param equipo the device to be added
     */
    public void addEquipo(Equipo equipo) {
        try {
            red.addEquipo(equipo);
            logic.addVertex(equipo);
            gui.addVisualVertex(equipo);
        } catch (InvalidEquipoException | InvalidUbicacionException | InvalidTipoEquipoException |
                 InvalidTipoPuertoException | InvalidDireccionIPException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Deletes a device (Equipo) from the network.
     * <p>
     * This method attempts to delete the device from the network (Red), logic layer, and graphical user interface (Gui).
     * If the device is invalid and cannot be deleted, an InvalidEquipoException is caught and its message is printed.
     *
     * @param equipo the device to be deleted
     */
    public void deleteEquipo(Equipo equipo) {
        try {
            red.deleteEquipo(equipo);
            logic.deleteVertex(equipo);
            gui.removeVisualVertex(equipo);
        } catch (InvalidEquipoException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Modifies an existing device (Equipo) in the network.
     * <p>
     * This method attempts to modify the device in the network (Red), logic layer, and graphical user interface (Gui).
     * If any exception occurs during the process, the device is not modified and an error message is printed.
     *
     * @param oldEquipo the existing device to be replaced
     * @param newEquipo the new device to replace the old one
     */
    public void modifyEquipo(Equipo oldEquipo, Equipo newEquipo) {
        try {
            red.modifyEquipo(oldEquipo, newEquipo);
            logic.modifyVertex(oldEquipo, newEquipo);
            gui.modifyVisualVertex(oldEquipo, newEquipo);
        } catch (InvalidEquipoException | InvalidUbicacionException | InvalidTipoEquipoException |
                 InvalidTipoPuertoException | InvalidDireccionIPException e) {
            System.out.println(e.getMessage());
        }
    }


    /* ----------------------------- Red  ----------------------------- */

    /**
     * Gets the list of devices (equipos) in the network.
     *
     * @return a list of devices in the network
     */
    public List<Equipo> getEquipos() {
        return new ArrayList<>(red.getEquipos().values());
    }

    public String[] getEquiposIps() {
        ArrayList<String> ips = new ArrayList<>();

        for (Equipo equipo : getEquipos()){
            ips.addAll(equipo.getDireccionesIp());
        }

        return ips.toArray(new String[ips.size()]);
    }

    /**
     * Gets the list of connections (conexiones) in the network.
     *
     * @return a list of connections in the network
     */
    public List<Conexion> getConexiones() {
        return new ArrayList<>(red.getConexiones().values());
    }


    /**
     * Gets the map of locations (Ubicaciones) in the network.
     * <p>
     * This method retrieves the map of locations from the network (Red).
     *
     * @return a map where the key is the location code and the value is the location
     */
    public Map<String, Ubicacion> getUbicaciones() {
        return red.getUbicaciones();
    }

    /**
     * Adds a location (Ubicacion) to the network.
     * <p>
     * This method attempts to add the location to the network (Red).
     * If the location is invalid and cannot be added, an InvalidUbicacionException is thrown.
     *
     * @param ubicacion the location to be added
     * @throws InvalidUbicacionException if the location is invalid
     */
    public void addUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        red.addUbicacion(ubicacion);
    }

    /**
     * Deletes a location (Ubicacion) from the network.
     * <p>
     * This method attempts to delete the location from the network (Red).
     * If the location is invalid and cannot be deleted, an InvalidUbicacionException is thrown.
     *
     * @param ubicacion the location to be deleted
     * @throws InvalidUbicacionException if the location is invalid
     */
    public void deleteUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        red.deleteUbicacion(ubicacion);
    }

    /**
     * Modifies an existing location (Ubicacion) in the network.
     * <p>
     * This method attempts to modify the location in the network (Red).
     * If the location is invalid and cannot be modified, an InvalidUbicacionException is thrown.
     *
     * @param oldUbicacion the existing location to be replaced
     * @param newUbicacion the new location to replace the old one
     * @throws InvalidUbicacionException if the location is invalid
     */
    public void modifyUbicacion(Ubicacion oldUbicacion, Ubicacion newUbicacion) throws InvalidUbicacionException {
        red.modifyUbicacion(oldUbicacion, newUbicacion);
    }


    /**
     * Gets the map of cable types (TipoCable) in the network.
     * <p>
     * This method retrieves the map of cable types from the network (Red).
     *
     * @return a map where the key is the cable type code and the value is the cable type
     */
    public Map<String, TipoCable> getTiposCables() {
        return red.getTiposCables();
    }

    /**
     * Adds a cable type (TipoCable) to the network.
     * <p>
     * This method attempts to add the cable type to the network (Red).
     * If the cable type is invalid and cannot be added, an InvalidTipoCableException is thrown.
     *
     * @param tipoCable the cable type to be added
     * @throws InvalidTipoCableException if the cable type is invalid
     */
    public void addTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        red.addTipoCable(tipoCable);
    }

    /**
     * Deletes a cable type (TipoCable) from the network.
     * <p>
     * This method attempts to delete the cable type from the network (Red).
     * If the cable type is invalid and cannot be deleted, an InvalidTipoCableException is thrown.
     *
     * @param tipoCable the cable type to be deleted
     * @throws InvalidTipoCableException if the cable type is invalid
     */
    public void deleteTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        red.deleteTipoCable(tipoCable);
    }

    /**
     * Modifies an existing cable type (TipoCable) in the network.
     * <p>
     * This method attempts to modify the cable type in the network (Red).
     * If the cable type is invalid and cannot be modified, an InvalidTipoCableException is thrown.
     *
     * @param oldTipoCable the existing cable type to be replaced
     * @param newTipoCable the new cable type to replace the old one
     * @throws InvalidTipoCableException if the cable type is invalid
     */
    public void modifyTipoCable(TipoCable oldTipoCable, TipoCable newTipoCable) throws InvalidTipoCableException {
        red.modifyTipoCable(oldTipoCable, newTipoCable);
    }


    /**
     * Gets the map of port types (TipoPuerto) in the network.
     * <p>
     * This method retrieves the map of port types from the network (Red).
     *
     * @return a map where the key is the port type code and the value is the port type
     */
    public Map<String, TipoPuerto> getTiposPuertos() {
        return red.getTiposPuertos();
    }

    /**
     * Adds a port type (TipoPuerto) to the network.
     * <p>
     * This method attempts to add the port type to the network (Red).
     * If the port type is invalid and cannot be added, an InvalidTipoPuertoException is thrown.
     *
     * @param TipoPuerto the port type to be added
     * @throws InvalidTipoPuertoException if the port type is invalid
     */
    public void addTipoPuerto(TipoPuerto TipoPuerto) throws InvalidTipoPuertoException {
        red.addTipoPuerto(TipoPuerto);
    }

    /**
     * Deletes a port type (TipoPuerto) from the network.
     * <p>
     * This method attempts to delete the port type from the network (Red).
     * If the port type is invalid and cannot be deleted, an InvalidTipoPuertoException is thrown.
     *
     * @param TipoPuerto the port type to be deleted
     * @throws InvalidTipoPuertoException if the port type is invalid
     */
    public void deleteTipoPuerto(TipoPuerto TipoPuerto) throws InvalidTipoPuertoException {
        red.deleteTipoPuerto(TipoPuerto);
    }

    /**
     * Modifies an existing port type (TipoPuerto) in the network.
     * <p>
     * This method attempts to modify the port type in the network (Red).
     * If the port type is invalid and cannot be modified, an InvalidTipoPuertoException is thrown.
     *
     * @param oldTipoPuerto the existing port type to be replaced
     * @param newTipoPuerto the new port type to replace the old one
     * @throws InvalidTipoPuertoException if the port type is invalid
     */
    public void modifyTipoPuerto(TipoPuerto oldTipoPuerto, TipoPuerto newTipoPuerto) throws InvalidTipoPuertoException {
        red.modifyTipoPuerto(oldTipoPuerto, newTipoPuerto);
    }


    /**
     * Gets the map of device types (TipoEquipo) in the network.
     * <p>
     * This method retrieves the map of device types from the network (Red).
     *
     * @return a map where the key is the device type code and the value is the device type
     */
    public Map<String, TipoEquipo> getTiposEquipos() {
        return red.getTiposEquipos();
    }

    /**
     * Adds a device type (TipoEquipo) to the network.
     * <p>
     * This method attempts to add the device type to the network (Red).
     * If the device type is invalid and cannot be added, an InvalidTipoEquipoException is thrown.
     *
     * @param tipoEquipo the device type to be added
     * @throws InvalidTipoEquipoException if the device type is invalid
     */
    public void addTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        red.addTipoEquipo(tipoEquipo);
    }

    /**
     * Deletes a device type (TipoEquipo) from the network.
     * <p>
     * This method attempts to delete the device type from the network (Red).
     * If the device type is invalid and cannot be deleted, an InvalidTipoEquipoException is thrown.
     *
     * @param tipoEquipo the device type to be deleted
     * @throws InvalidTipoEquipoException if the device type is invalid
     */
    public void deleteTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        red.deleteTipoEquipo(tipoEquipo);
    }

    /**
     * Modifies an existing device type (TipoEquipo) in the network.
     * <p>
     * This method attempts to modify the device type in the network (Red).
     * If the device type is invalid and cannot be modified, an InvalidTipoEquipoException is thrown.
     *
     * @param oldTipoEquipo the existing device type to be replaced
     * @param newTipoEquipo the new device type to replace the old one
     * @throws InvalidTipoEquipoException if the device type is invalid
     */
    public void modifyTipoEquipo(TipoEquipo oldTipoEquipo, TipoEquipo newTipoEquipo) throws InvalidTipoEquipoException {
        red.modifyTipoEquipo(oldTipoEquipo, newTipoEquipo);
    }


    /* ----------------------------- Logic  ----------------------------- */

    /**
     * Gets the map of devices (equipos) in the network.
     * <p>
     * This method retrieves the map of devices from the logic layer.
     *
     * @return a map where the key is the device code and the value is the device
     */
    public Map<String, Equipo> getVertexMap() {
        return logic.getEquiposMap();
    }

    /**
     * Gets the map of connections (conexiones) in the network.
     * <p>
     * This method retrieves the map of connections from the logic layer.
     *
     * @return a map where the key is the connection code and the value is the connection
     */
    public Map<String, Conexion> getEdgesMap() {
        return logic.getConexionesMap();
    }

    /**
     * Finds the shortest path between two devices (equipos) in the network.
     * <p>
     * This method uses the logic layer to calculate the shortest path between the two specified devices.
     *
     * @param equipo1 the starting device
     * @param equipo2 the destination device
     * @return a list of connections representing the shortest path between the two devices or null if no path is found
     */
    public List<Conexion> shortestPath(Equipo equipo1, Equipo equipo2) {
        return logic.shortestPath(equipo1, equipo2);
    }

    /**
     * Calculates the maximum bandwidth along a given path of connections (sPath).
     * <p>
     * This method uses the logic layer to determine the maximum bandwidth available along the specified path of connections.
     *
     * @param sPath the list of connections representing the path
     * @return the maximum bandwidth along the path
     */
    public Double maxBandwith(List<Conexion> sPath) {
        return logic.maxBandwith(sPath);
    }

    /**
     * Pings a device by its IP address.
     * <p>
     * This method uses the logic layer to ping the specified IP address.
     *
     * @param ip the IP address to ping
     * @return true if the ping is successful, false otherwise
     */
    public boolean ping(String ip) {
        return logic.ping(ip);
    }

    /**
     * Pings a range of IP addresses.
     * <p>
     * This method uses the logic layer to ping a collection of IP addresses.
     *
     * @param ips the collection of IP addresses to ping
     * @return a map where the key is the IP address and the value is true if the ping is successful, false otherwise
     */
    public Map<String, Boolean> pingRange(Collection<String> ips) {
        return logic.pingRange(ips);
    }

    /**
     * Gets the connected part of the network for a given device (equipo).
     * <p>
     * This method uses the logic layer to retrieve the subgraph of the network that is connected to the specified device.
     *
     * @param equipo the device for which to get the connected part of the network
     * @return a set of connections representing the connected part of the network for the specified device
     */
    public Set<Conexion> getConnectedPart(Equipo equipo) {
        return logic.getConnectedPart(equipo).edgeSet();
    }

    /**
     * Maps the status of each device (equipo) in the network.
     * <p>
     * This method uses the logic layer to retrieve the status of each device in the network.
     *
     * @return a map where the key is the device and the value is true if the device is active, false otherwise
     */
    public Map<Equipo, Boolean> mapStatus() {
        return logic.mapStatus();
    }

}
