package controller;

import config.Config;
import exceptions.*;
import gui.Gui;
import logic.Logic;
import logic.Red;
import models.*;
import org.jgrapht.Graph;
import utils.LoggerUtil;

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
     * The configuration (Config) instance.
     */
    private Config config;

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

    /**
     * Gets the configuration (Config) instance.
     *
     * @return the configuration instance
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Sets the configuration (Config) instance.
     *
     * @param config the configuration instance to set
     */
    public void setConfig(Config config) {
        this.config = config;
    }



    /* ----------------------------- Synchronized methods  ----------------------------- */


    /**
     * Adds a connection (Conexion) to the network.
     * <p>
     * This method attempts to add the connection to both the logic layer and the network (Red).
     * If the connection is invalid, an InvalidConexionException is thrown.
     * The graphical user interface (Gui) is also updated to reflect the new connection.
     *
     * @param conexion the connection to be added
     * @throws InvalidConexionException if the connection is invalid
     */
    public void addConnection(Conexion conexion) throws InvalidConexionException {
        try {
            logic.addEdge(conexion);
        } catch (InvalidConexionException e) {
            throw new InvalidConexionException(e.getMessage());
        }

        try {
            red.addConexion(conexion);
            gui.addVisualEdge(conexion);
        } catch (InvalidEquipoException | InvalidConexionException |
                 InvalidTipoCableException | InvalidTipoPuertoException e) {
            logic.deleteEdge(conexion);
            throw new InvalidConexionException(e.getMessage());
        }
        LoggerUtil.logInfo("Connection added: " + conexion);
        LoggerUtil.logDebug("logic edge: " + logic.getGraph().getEdge(conexion.getEquipo1(), conexion.getEquipo2()));
        LoggerUtil.logDebug("red conexion: " + red.getConexiones().get(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo()));
    }


    /**
     * Deletes a connection (Conexion) from the network.
     * <p>
     * This method removes the connection from the network (Red), logic layer, and graphical user interface (Gui).
     *
     * @param conexion the connection to be deleted
     * @throws InvalidConexionException if the connection is invalid
     */
    public void deleteConnection(Conexion conexion) throws InvalidConexionException {
        red.deleteConexion(conexion);
        logic.deleteEdge(conexion);
        gui.removeVisualEdge(conexion);
        LoggerUtil.logInfo("Connection deleted: " + conexion);
        LoggerUtil.logDebug("logic edge: " + logic.getGraph().getEdge(conexion.getEquipo1(), conexion.getEquipo2()));
        LoggerUtil.logDebug("red conexion: " + red.getConexiones().get(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo()));
    }


    /**
     * Modifies an existing connection (Conexion) in the network.
     * <p>
     * This method attempts to modify the connection in the network (Red), logic layer, and graphical user interface (Gui).
     * If the connection is invalid and cannot be modified, an InvalidConexionException is thrown.
     *
     * @param conexion the connection to be modified
     * @throws InvalidConexionException if the connection is invalid
     */
    public void modifyConnection(Conexion conexion) throws InvalidConexionException {
        try {
            red.modifyConnection(conexion);
            logic.modifyEdge(conexion);
            gui.modifyVisualEdge(conexion, conexion);
        } catch (InvalidConexionException | InvalidEquipoException |
                 InvalidTipoCableException | InvalidTipoPuertoException e) {
            throw new InvalidConexionException(e.getMessage());
        }
        LoggerUtil.logInfo("Connection modified: " + conexion);
        LoggerUtil.logDebug("logic edge: " + logic.getGraph().getEdge(conexion.getEquipo1(), conexion.getEquipo2()));
        LoggerUtil.logDebug("red conexion: " + red.getConexiones().get(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo()));
    }


    /**
     * Adds a device (Equipo) to the network.
     * <p>
     * This method attempts to add the device to the network (Red), logic layer, and graphical user interface (Gui).
     * If the device is invalid and cannot be added, an InvalidEquipoException is thrown.
     *
     * @param equipo the device to be added
     * @throws InvalidEquipoException if the device is invalid
     */
    public void addEquipo(Equipo equipo) throws InvalidEquipoException {
        try {
            red.addEquipo(equipo);
            logic.addVertex(equipo);
            gui.addVisualVertex(equipo);
        } catch (InvalidEquipoException | InvalidUbicacionException | InvalidTipoEquipoException |
                 InvalidTipoPuertoException | InvalidDireccionIPException | IllegalArgumentException e) {
            throw new InvalidEquipoException(e.getMessage());
        }
        LoggerUtil.logInfo("Device added: " + equipo);
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
    }


    /**
     * Deletes a device (Equipo) from the network.
     * <p>
     * This method removes the device from the network (Red), logic layer, and graphical user interface (Gui).
     *
     * @param equipo the device to be deleted
     * @throws InvalidEquipoException if the device is invalid
     */
    public void deleteEquipo(Equipo equipo) throws InvalidEquipoException {
        red.deleteEquipo(equipo);
        logic.deleteVertex(equipo);
        gui.removeVisualVertex(equipo);
        LoggerUtil.logInfo("Device deleted: " + equipo);
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
    }

    /**
     * Modifies an existing device (Equipo) in the network.
     * <p>
     * This method attempts to modify the device in the network (Red), logic layer, and graphical user interface (Gui).
     * If the device is invalid and cannot be modified, an InvalidEquipoException is thrown.
     *
     * @param equipo the device to be modified
     * @throws InvalidEquipoException if the device is invalid
     */
    public void modifyEquipo(Equipo equipo) throws InvalidEquipoException {
        try {
            red.modifyEquipo(equipo);
            logic.modifyVertex(equipo);
            gui.modifyVisualVertex(equipo, equipo);
        } catch (InvalidEquipoException | InvalidUbicacionException | InvalidTipoEquipoException |
                 InvalidTipoPuertoException | InvalidDireccionIPException e) {
            throw new InvalidEquipoException(e.getMessage());
        }
        LoggerUtil.logInfo("Device modified: " + equipo);
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
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

    /**
     * Retrieves the keys of the equipment in the network.
     * <p>
     * This method returns an array of strings that contains the keys
     * of all the equipment in the network. The keys are obtained from
     * the key set of the map of equipment maintained by the {@code red}
     * instance. This method is useful for obtaining a list of identifiers
     * for the available equipment in the network.
     *
     * @return an array of strings representing the keys of the equipment in the network.
     */
    public String[] getEquiposKeys() {
        return red.getEquipos().keySet().toArray(new String[0]);
    }


    /**
     * Retrieves the IP addresses of all devices (equipos) in the network.
     * <p>
     * This method returns an array of strings containing the IP addresses of all devices in the network.
     * The IP addresses are obtained from the network (Red) instance.
     *
     * @return an array of strings representing the IP addresses of all devices in the network
     */
    public String[] getEquiposIps() {
        return red.getEquiposIps().toArray(new String[0]);
    }

    /**
     * Retrieves the equipment associated with the specified code.
     * <p>
     * This method looks up and returns the equipment object that corresponds
     * to the given code from the network's equipment map. If no equipment
     * is found with the specified code, it returns {@code null}.
     *
     * @param codigo the code of the equipment to retrieve.
     * @return the {@code Equipo} object associated with the specified code, or {@code null} if not found.
     */
    public Equipo getEquipo(String codigo) {
        return red.getEquipos().get(codigo);
    }

    /**
     * Gets the map of devices (equipos) in the network.
     * <p>
     * This method retrieves the map of devices from the network (Red).
     *
     * @return a map where the key is the device code and the value is the device
     */
    public Map<String, Equipo> getEquiposMap() {
        return red.getEquipos();
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
     * Gets the map of connections (Conexiones) in the network.
     * <p>
     * This method retrieves the map of connections from the network (Red).
     *
     * @return a map where the key is the connection code and the value is the connection
     */
    public Map<String, Conexion> getConexionesMap() {
        return red.getConexiones();
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
     * Adds a port (Puerto) to a device (Equipo).
     * <p>
     * This method attempts to add the port to the network (Red).
     * If the port is invalid and cannot be added, an InvalidPuertoEquipoException is thrown.
     *
     * @param equipo the device (Equipo) to which the port is to be added
     * @param puerto the port to be added
     * @throws InvalidPuertoEquipoException if the port is invalid
     */
    public void addPuertoEquipo(Equipo equipo, Puerto puerto) throws InvalidPuertoEquipoException {
        try {
            red.addPuertoEquipo(equipo, puerto);
        } catch (InvalidPuertoEquipoException e) {
            throw new InvalidPuertoEquipoException(e.getMessage());
        }
        LoggerUtil.logInfo("Port added: " + puerto + " to device " + equipo.getCodigo());
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
    }

    /**
     * Removes a port (Puerto) from a device (Equipo).
     * <p>
     * This method attempts to remove the port from the network (Red).
     * If the port is invalid and cannot be removed, an InvalidPuertoEquipoException is thrown.
     *
     * @param equipo the device (Equipo) from which the port is to be removed
     * @param puerto the port to be removed
     * @throws InvalidPuertoEquipoException if the port is invalid
     */
    public void removePuertoEquipo(Equipo equipo, Puerto puerto) throws InvalidPuertoEquipoException {
        try {
            red.deletePuertoEquipo(equipo, puerto);
        } catch (InvalidPuertoEquipoException e) {
            throw new InvalidPuertoEquipoException(e.getMessage());
        }
        LoggerUtil.logInfo("Port removed: " + puerto + " from device " + equipo.getCodigo());
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
    }

    /**
     * Modifies an existing port (Puerto) of a device (Equipo).
     * <p>
     * This method attempts to modify the port of the device in the network (Red).
     * If the port is invalid and cannot be modified, an InvalidPuertoEquipoException is thrown.
     *
     * @param equipo    the device (Equipo) whose port is to be modified
     * @param oldPuerto the old port to be replaced
     * @param newPuerto the new port to replace the old one
     * @throws InvalidPuertoEquipoException if the port is invalid
     */
    public void modifyPuertoEquipo(Equipo equipo, Puerto oldPuerto, Puerto newPuerto) throws InvalidPuertoEquipoException {
        try {
            red.modifyPuertoEquipo(equipo, oldPuerto, newPuerto);
        } catch (InvalidPuertoEquipoException e) {
            throw new InvalidPuertoEquipoException(e.getMessage());
        }
        LoggerUtil.logInfo("Port modified: " + oldPuerto + " -> " + newPuerto + " in device " + equipo.getCodigo());
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
    }

    /**
     * Adds an IP address to a device (Equipo).
     * <p>
     * This method attempts to add the IP address to the network (Red).
     * If the IP address is invalid and cannot be added, an InvalidDireccionIPException is thrown.
     *
     * @param equipo      the device (Equipo) to which the IP address is to be added
     * @param direccionIP the IP address to be added
     * @throws InvalidDireccionIPException if the IP address is invalid
     */
    public void addIPEquipo(Equipo equipo, String direccionIP) throws InvalidDireccionIPException {
        try {
            red.addIpEquipo(equipo, direccionIP);
        } catch (InvalidDireccionIPException e) {
            throw new InvalidDireccionIPException(e.getMessage());
        }
        gui.modifyVisualVertex(equipo, equipo); // Update the visual representation of the device
        LoggerUtil.logInfo("IP address added: " + direccionIP + " to device " + equipo.getCodigo());
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
    }

    /**
     * Removes an IP address from a device (Equipo).
     * <p>
     * This method attempts to remove the IP address from the network (Red).
     * If the IP address is invalid and cannot be removed, an InvalidDireccionIPException is thrown.
     *
     * @param equipo      the device (Equipo) from which the IP address is to be removed
     * @param direccionIP the IP address to be removed
     * @throws InvalidDireccionIPException if the IP address is invalid
     */
    public void removeIPEquipo(Equipo equipo, String direccionIP) throws InvalidDireccionIPException {
        try {
            red.removeIpEquipo(equipo, direccionIP);
        } catch (InvalidDireccionIPException e) {
            throw new InvalidDireccionIPException(e.getMessage());
        }
        gui.modifyVisualVertex(equipo, equipo); // Update the visual representation of the device
        LoggerUtil.logInfo("IP address removed: " + direccionIP + " from device " + equipo.getCodigo());
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
    }

    /**
     * Modifies an IP address of a device (Equipo).
     * <p>
     * This method attempts to modify the IP address of the device in the network (Red).
     * If the IP address is invalid and cannot be modified, an InvalidDireccionIPException is thrown.
     *
     * @param equipo         the device (Equipo) whose IP address is to be modified
     * @param oldDireccionIP the old IP address to be replaced
     * @param newDireccionIP the new IP address to replace the old one
     * @throws InvalidDireccionIPException if the IP address is invalid
     */
    public void modifyIPEquipo(Equipo equipo, String oldDireccionIP, String newDireccionIP) throws InvalidDireccionIPException {
        try {
            red.modifyIpEquipo(equipo, oldDireccionIP, newDireccionIP);
        } catch (InvalidDireccionIPException e) {
            throw new InvalidDireccionIPException(e.getMessage());
        }
        gui.modifyVisualVertex(equipo, equipo); // Update the visual representation of the device
        LoggerUtil.logInfo("IP address modified: " + oldDireccionIP + " -> " + newDireccionIP + " in device " + equipo.getCodigo());
        LoggerUtil.logDebug("red equipo: " + red.getEquipos().get(equipo.getCodigo()));
        LoggerUtil.logDebug("logic vertex: " + logic.getVertexMap().get(equipo.getCodigo()));
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
        LoggerUtil.logInfo("Location added: " + ubicacion);
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
        LoggerUtil.logInfo("Location deleted: " + ubicacion);
    }

    /**
     * Modifies an existing location (Ubicacion) in the network.
     * <p>
     * This method attempts to modify the location in the network (Red).
     * If the location is invalid and cannot be modified, an InvalidUbicacionException is thrown.
     *
     * @param ubicacion the location to be modified
     * @throws InvalidUbicacionException if the location is invalid
     */
    public void modifyUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        red.modifyUbicacion(ubicacion);
        LoggerUtil.logInfo("Location modified: " + ubicacion);
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
        LoggerUtil.logInfo("Cable type added: " + tipoCable);
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
        LoggerUtil.logInfo("Cable type deleted: " + tipoCable);
    }

    /**
     * Modifies an existing cable type (TipoCable) in the network.
     * <p>
     * This method attempts to modify the cable type in the network (Red).
     * If the cable type is invalid and cannot be modified, an InvalidTipoCableException is thrown.
     *
     * @param tipoCable the cable type to be modified
     * @throws InvalidTipoCableException if the cable type is invalid
     */
    public void modifyTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        red.modifyTipoCable(tipoCable);
        LoggerUtil.logInfo("Cable type modified: " + tipoCable);
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
        LoggerUtil.logInfo("Port type added: " + TipoPuerto);
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
        LoggerUtil.logInfo("Port type deleted: " + TipoPuerto);
    }

    /**
     * Modifies an existing port type (TipoPuerto) in the network.
     * <p>
     * This method attempts to modify the port type in the network (Red).
     * If the port type is invalid and cannot be modified, an InvalidTipoPuertoException is thrown.
     *
     * @param tipoPuerto the port type to be modified
     * @throws InvalidTipoPuertoException if the port type is invalid
     */
    public void modifyTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        red.modifyTipoPuerto(tipoPuerto);
        LoggerUtil.logInfo("Port type modified: " + tipoPuerto);
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
        LoggerUtil.logInfo("Device type added: " + tipoEquipo);
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
        LoggerUtil.logInfo("Device type deleted: " + tipoEquipo);
    }

    /**
     * Modifies an existing device type (TipoEquipo) in the network.
     * <p>
     * This method attempts to modify the device type in the network (Red).
     * If the device type is invalid and cannot be modified, an InvalidTipoEquipoException is thrown.
     *
     * @param tipoEquipo the device type to be modified
     * @throws InvalidTipoEquipoException if the device type is invalid
     */
    public void modifyTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        red.modifyTipoEquipo(tipoEquipo);
        LoggerUtil.logInfo("Device type modified: " + tipoEquipo);
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
        return logic.getVertexMap();
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
    public Graph<Equipo, Conexion> getConnectedPart(Equipo equipo) {
        return logic.getConnectedPart(equipo);
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

    /* ----------------------------- config  ----------------------------- */

    /**
     * Gets the resource bundle for internationalization.
     *
     * @return the resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return config.getResourceBundle();
    }
}
