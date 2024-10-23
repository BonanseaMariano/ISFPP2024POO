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
     * Adds a connection (conexion) to the network.
     * <p>
     * This method first attempts to add the connection to the logic layer. If the connection is invalid due to
     * various exceptions such as InvalidConexionException, LoopException, CicleException, or NoAvailablePortsException,
     * the exception message is printed and the method returns. If the connection is successfully added to the logic layer,
     * it then attempts to add the connection to the network (red). If adding the connection to the network fails due to an
     * InvalidEquipoException, InvalidConexionException, NoAvailablePortsException, InvalidTipoCableException, or InvalidTipoPuertoException,
     * the connection is removed from the logic layer and the exception message is printed.
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
        } catch (InvalidEquipoException | InvalidConexionException | NoAvailablePortsException |
                 InvalidTipoCableException | InvalidTipoPuertoException e) {
            logic.deleteEdge(conexion);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a connection (conexion) from the network.
     * <p>
     * This method attempts to delete the connection from both the network (red) and the logic layer.
     * If the connection is invalid and cannot be deleted, an InvalidConexionException is caught and its message is printed.
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
        } catch (InvalidConexionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifies an existing connection (conexion) in the network.
     * <p>
     * This method attempts to modify the connection in both the logic layer and the network (red). If the modification fails
     * in the logic layer due to various exceptions such as InvalidConexionException, LoopException, CicleException, or NoAvailablePortsException,
     * the exception message is printed and the method returns. If the modification fails in the network due to InvalidConexionException,
     * NoAvailablePortsException, InvalidEquipoException, InvalidTipoCableException, or InvalidTipoPuertoException, the changes in the logic layer
     * are reverted and the exception message is printed.
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
        } catch (InvalidConexionException | NoAvailablePortsException e) {
            System.out.println(e.getMessage());
        } catch (InvalidEquipoException |
                 InvalidTipoCableException | InvalidTipoPuertoException e) {
            logic.modifyEdge(newConexion, oldConnection); // Revert changes
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a device (equipo) to the network.
     * <p>
     * This method attempts to add the device to both the network (red) and the logic layer.
     * If the device is invalid due to various exceptions such as InvalidEquipoException, InvalidUbicacionException,
     * InvalidTipoEquipoException, InvalidTipoPuertoException, or InvalidDireccionIPException, the exception message is printed.
     *
     * @param equipo the device to be added
     */
    public void addEquipo(Equipo equipo) {
        try {
            red.addEquipo(equipo);
            logic.addVertex(equipo);
        } catch (InvalidEquipoException | InvalidUbicacionException | InvalidTipoEquipoException |
                 InvalidTipoPuertoException | InvalidDireccionIPException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a device (equipo) from the network.
     * <p>
     * This method attempts to delete the device from both the network (red) and the logic layer.
     * If the device is invalid and cannot be deleted, an InvalidEquipoException is caught and its message is printed.
     *
     * @param equipo the device to be deleted
     */
    public void deleteEquipo(Equipo equipo) {
        try {
            red.deleteEquipo(equipo);
            logic.deleteVertex(equipo);
        } catch (InvalidEquipoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifies an existing device (equipo) in the network.
     * <p>
     * This method attempts to modify the device in both the network (red) and the logic layer. If the modification fails
     * due to various exceptions such as InvalidEquipoException, InvalidUbicacionException, InvalidTipoEquipoException,
     * InvalidTipoPuertoException, or InvalidDireccionIPException, the exception message is printed.
     *
     * @param oldEquipo the existing device to be replaced
     * @param newEquipo the new device to replace the old one
     */
    public void modifyEquipo(Equipo oldEquipo, Equipo newEquipo) {
        try {
            red.modifyEquipo(oldEquipo, newEquipo);
            logic.modifyVertex(oldEquipo, newEquipo);
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

    /**
     * Gets the list of connections (conexiones) in the network.
     *
     * @return a list of connections in the network
     */
    public List<Conexion> getConexiones() {
        return new ArrayList<>(red.getConexiones().values());
    }

    /**
     * Adds a location (ubicacion) to the network.
     * <p>
     * This method attempts to add the location to the network (red).
     * If the location is invalid and cannot be added, an InvalidUbicacionException is caught and its message is printed.
     *
     * @param ubicacion the location to be added
     */
    public void addUbicacion(Ubicacion ubicacion) {
        try {
            red.addUbicacion(ubicacion);
        } catch (InvalidUbicacionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a location (ubicacion) from the network.
     * <p>
     * This method attempts to delete the location from the network (red).
     * If the location is invalid and cannot be deleted, an InvalidUbicacionException is caught and its message is printed.
     *
     * @param ubicacion the location to be deleted
     */
    public void deleteUbicacion(Ubicacion ubicacion) {
        try {
            red.deleteUbicacion(ubicacion);
        } catch (InvalidUbicacionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifies an existing location (ubicacion) in the network.
     * <p>
     * This method attempts to modify the location in the network (red). If the modification fails due to an
     * InvalidUbicacionException, the exception message is printed.
     *
     * @param oldUbicacion the existing location to be replaced
     * @param newUbicacion the new location to replace the old one
     */
    public void modifyUbicacion(Ubicacion oldUbicacion, Ubicacion newUbicacion) {
        try {
            red.modifyUbicacion(oldUbicacion, newUbicacion);
        } catch (InvalidUbicacionException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a cable type (tipoCable) to the network.
     * <p>
     * This method attempts to add the cable type to the network (red).
     * If the cable type is invalid and cannot be added, an InvalidTipoCableException is caught and its message is printed.
     *
     * @param tipoCable the cable type to be added
     */
    public void addTipoCable(TipoCable tipoCable) {
        try {
            red.addTipoCable(tipoCable);
        } catch (InvalidTipoCableException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a cable type (tipoCable) from the network.
     * <p>
     * This method attempts to delete the cable type from the network (red).
     * If the cable type is invalid and cannot be deleted, an InvalidTipoCableException is caught and its message is printed.
     *
     * @param tipoCable the cable type to be deleted
     */
    public void deleteTipoCable(TipoCable tipoCable) {
        try {
            red.deleteTipoCable(tipoCable);
        } catch (InvalidTipoCableException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifies an existing cable type (tipoCable) in the network.
     * <p>
     * This method attempts to modify the cable type in the network (red). If the modification fails due to an
     * InvalidTipoCableException, the exception message is printed.
     *
     * @param oldTipoCable the existing cable type to be replaced
     * @param newTipoCable the new cable type to replace the old one
     */
    public void modifyTipoCable(TipoCable oldTipoCable, TipoCable newTipoCable) {
        try {
            red.modifyTipoCable(oldTipoCable, newTipoCable);
        } catch (InvalidTipoCableException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a port type (TipoPuerto) to the network.
     * <p>
     * This method attempts to add the port type to the network (red).
     * If the port type is invalid and cannot be added, an InvalidTipoPuertoException is caught and its message is printed.
     *
     * @param TipoPuerto the port type to be added
     */
    public void addTipoPuerto(TipoPuerto TipoPuerto) {
        try {
            red.addTipoPuerto(TipoPuerto);
        } catch (InvalidTipoPuertoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a port type (TipoPuerto) from the network.
     * <p>
     * This method attempts to delete the port type from the network (red).
     * If the port type is invalid and cannot be deleted, an InvalidTipoPuertoException is caught and its message is printed.
     *
     * @param TipoPuerto the port type to be deleted
     */
    public void deleteTipoPuerto(TipoPuerto TipoPuerto) {
        try {
            red.deleteTipoPuerto(TipoPuerto);
        } catch (InvalidTipoPuertoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifies an existing port type (tipoPuerto) in the network.
     * <p>
     * This method attempts to modify the port type in the network (red). If the modification fails due to an
     * InvalidTipoPuertoException, the exception message is printed.
     *
     * @param oldTipoPuerto the existing port type to be replaced
     * @param newTipoPuerto the new port type to replace the old one
     */
    public void modifyTipoPuerto(TipoPuerto oldTipoPuerto, TipoPuerto newTipoPuerto) {
        try {
            red.modifyTipoPuerto(oldTipoPuerto, newTipoPuerto);
        } catch (InvalidTipoPuertoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a device type (tipoEquipo) to the network.
     * <p>
     * This method attempts to add the device type to the network (red).
     * If the device type is invalid and cannot be added, an InvalidTipoEquipoException is caught and its message is printed.
     *
     * @param tipoEquipo the device type to be added
     */
    public void addTipoEquipo(TipoEquipo tipoEquipo) {
        try {
            red.addTipoEquipo(tipoEquipo);
        } catch (InvalidTipoEquipoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a device type (tipoEquipo) from the network.
     * <p>
     * This method attempts to delete the device type from the network (red).
     * If the device type is invalid and cannot be deleted, an InvalidTipoEquipoException is caught and its message is printed.
     *
     * @param tipoEquipo the device type to be deleted
     */
    public void deleteTipoEquipo(TipoEquipo tipoEquipo) {
        try {
            red.deleteTipoEquipo(tipoEquipo);
        } catch (InvalidTipoEquipoException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifies an existing device type (tipoEquipo) in the network.
     * <p>
     * This method attempts to modify the device type in the network (red). If the modification fails due to an
     * InvalidTipoEquipoException, the exception message is printed.
     *
     * @param oldTipoEquipo the existing device type to be replaced
     * @param newTipoEquipo the new device type to replace the old one
     */
    public void modifyTipoEquipo(TipoEquipo oldTipoEquipo, TipoEquipo newTipoEquipo) {
        try {
            red.modifyTipoEquipo(oldTipoEquipo, newTipoEquipo);
        } catch (InvalidTipoEquipoException e) {
            System.out.println(e.getMessage());
        }
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
