package logic;

import controller.Coordinator;
import exceptions.*;
import models.*;
import services.*;
import utils.LoggerUtil;
import utils.Utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a network (Red) with various components such as devices (equipos), connections (conexiones), locations (ubicaciones),
 * types of cables (tiposCables), types of devices (tiposEquipos), and types of ports (tiposPuertos).
 * Provides methods to manage these components and ensure their validity within the network.
 */
public class Red {

    /**
     * Singleton instance of the network (Red).
     */
    private static Red red;
    /**
     * The coordinator for the network (Red).
     */
    private Coordinator coordinator;
    /**
     * Name of the network (Red).
     */
    private String nombre;
    /**
     * Map of connections (conexiones) in the network.
     * Key: Device1 identifier + "-" + Device2 identifier
     * Value: Connection object
     */
    private Map<String, Conexion> conexiones;
    /**
     * Map of devices (equipos) in the network.
     * Key: Device identifier
     * Value: Device object
     */
    private Map<String, Equipo> equipos;
    /**
     * Map of locations (ubicaciones) in the network.
     * Key: Location identifier
     * Value: Location object
     */
    private Map<String, Ubicacion> ubicaciones;
    /**
     * Map of types of cables (tiposCables) in the network.
     * Key: Cable type identifier
     * Value: Cable type object
     */
    private Map<String, TipoCable> tiposCables;
    /**
     * Map of types of devices (tiposEquipos) in the network.
     * Key: Device type identifier
     * Value: Device type object
     */
    private Map<String, TipoEquipo> tiposEquipos;
    /**
     * Map of types of ports (tiposPuertos) in the network.
     * Key: Port type identifier
     * Value: Port type object
     */
    private Map<String, TipoPuerto> tiposPuertos;

    /**
     * Service for managing connections (conexiones) in the network.
     */
    private final ConexionService conexionService;
    /**
     * Service for managing devices (equipos) in the network.
     */
    private final EquipoService equipoService;
    /**
     * Service for managing locations (ubicaciones) in the network.
     */
    private final UbicacionService ubicacionService;
    /**
     * Service for managing types of cables (tiposCables) in the network.
     */
    private final TipoCableService tipoCableService;
    /**
     * Service for managing types of devices (tiposEquipos) in the network.
     */
    private final TipoEquipoService tipoEquipoService;
    /**
     * Service for managing types of ports (tiposPuertos) in the network.
     */
    private final TipoPuertoService tipoPuertoService;


    /**
     * Private constructor for the Red class.
     * Initializes the maps for conexiones, equipos, ubicaciones, tiposCables, tiposEquipos, and tiposPuertos.
     * Also initializes the services for conexion, equipo, ubicacion, tipoCable, tipoEquipo, and tipoPuerto.
     * Finally, it populates the maps with data from the respective services.
     */
    private Red() {
        this.conexiones = new ConcurrentHashMap<>();
        this.equipos = new ConcurrentHashMap<>();
        this.ubicaciones = new ConcurrentHashMap<>();
        this.tiposCables = new ConcurrentHashMap<>();
        this.tiposEquipos = new ConcurrentHashMap<>();
        this.tiposPuertos = new ConcurrentHashMap<>();

        /*----- Servicios -----*/
        this.conexionService = new ConexionServiceImpl();
        this.equipoService = new EquipoServiceImpl();
        this.ubicacionService = new UbicacionServiceImpl();
        this.tipoCableService = new TipoCableServiceImpl();
        this.tipoEquipoService = new TipoEquipoServiceImpl();
        this.tipoPuertoService = new TipoPuertoServiceImpl();

        tipoCableService.getAll().forEach(tipoCable -> this.tiposCables.put(tipoCable.getCodigo(), tipoCable));
        tipoEquipoService.getAll().forEach(tipoEquipo -> this.tiposEquipos.put(tipoEquipo.getCodigo(), tipoEquipo));
        tipoPuertoService.getAll().forEach(tipoPuerto -> this.tiposPuertos.put(tipoPuerto.getCodigo(), tipoPuerto));
        ubicacionService.getAll().forEach(ubicacion -> this.ubicaciones.put(ubicacion.getCodigo(), ubicacion));
        equipoService.getAll().forEach(equipo -> this.equipos.put(equipo.getCodigo(), equipo));
        conexionService.getAll().forEach(conexion -> this.conexiones.put(conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo(), conexion));
    }

    /**
     * Returns the singleton instance of the network (Red).
     * If the instance does not exist, it creates a new one.
     *
     * @return the singleton instance of the network (Red)
     */
    public static Red getRed() {
        if (red == null) {
            red = new Red();
        }
        return red;
    }

    /**
     * Sets the coordinator for the network (Red).
     *
     * @param coordinator the coordinator to be set
     */
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    /* ----------------------------- Conexion ----------------------------- */


    /**
     * Validates a connection (conexion) in the network (red).
     *
     * @param conexion the connection (conexion) to be validated
     * @throws InvalidEquipoException     if one of the devices (equipos) does not exist in the network
     * @throws InvalidTipoCableException  if the type of cable does not exist in the network
     * @throws InvalidTipoPuertoException if one of the types of ports does not exist in the network
     */
    private void connectionValidation(Conexion conexion) throws InvalidEquipoException, InvalidTipoCableException, InvalidTipoPuertoException {
        if (!this.equipos.containsKey(conexion.getEquipo1().getCodigo()) || !this.equipos.containsKey(conexion.getEquipo2().getCodigo())) {
            throw new InvalidEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + conexion.getEquipo1().getCodigo() + "/" + conexion.getEquipo2().getCodigo());
        }
        if (!this.tiposCables.containsKey(conexion.getTipoCable().getCodigo())) {
            throw new InvalidTipoCableException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + conexion.getTipoCable().getCodigo());
        }
        if (!this.tiposPuertos.containsKey(conexion.getPuerto1().getCodigo()) || !this.tiposPuertos.containsKey(conexion.getPuerto2().getCodigo())) {
            throw new InvalidTipoPuertoException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + conexion.getPuerto1().getCodigo() + "/" + conexion.getPuerto2().getCodigo());
        }
    }

    /**
     * Adds a connection (conexion) to the network (red). Also adds the connection to the database.
     *
     * @param conexion the connection (conexion) to be added
     * @throws InvalidEquipoException   if one of the devices (equipos) does not exist in the network
     * @throws InvalidConexionException if the connection already exists
     */
    public void addConexion(Conexion conexion) throws InvalidEquipoException, InvalidConexionException, InvalidTipoCableException, InvalidTipoPuertoException {
        String conexionkey = conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo();
        connectionValidation(conexion);
        if (availablePorts(conexion.getEquipo1()) == 0 || availablePorts(conexion.getEquipo2()) == 0) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("InvalidConnection_noAvailablePorts"));
        }
        if (this.conexiones.containsKey(conexionkey)) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        this.conexiones.put(conexionkey, conexion);
        this.conexionService.insert(conexion);
    }

    /**
     * Modifies an existing connection (conexion) in the network (red).
     *
     * @param conexion the connection (conexion) to be modified
     * @throws InvalidEquipoException     if one of the devices (equipos) does not exist in the network
     * @throws InvalidConexionException   if the connection does not exist
     * @throws InvalidTipoCableException  if the type of cable does not exist in the network
     * @throws InvalidTipoPuertoException if one of the types of ports does not exist in the network
     */
    public void modifyConnection(Conexion conexion) throws InvalidEquipoException, InvalidConexionException, InvalidTipoCableException, InvalidTipoPuertoException {
        String key = conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo();
        connectionValidation(conexion);
        if (!this.conexiones.containsKey(key)) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("Invalid_unknownM"));
        }
        this.conexiones.put(key, conexion); // Updates the connection in the map
        this.conexionService.update(conexion); // Updates the connection in the database
    }

    /**
     * Deletes a connection (conexion) from the network (red). Also deletes the connection from the database.
     *
     * @param conexion the connection (conexion) to be deleted
     * @throws InvalidConexionException if the connection does not exist
     */
    public void deleteConexion(Conexion conexion) throws InvalidConexionException {
        String conexionkey = conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo();
        if (!this.conexiones.containsKey(conexionkey)) {
            throw new InvalidConexionException(coordinator.getResourceBundle().getString("Invalid_unknownD"));
        }
        this.conexiones.remove(conexionkey);
        this.conexionService.delete(conexion);
    }


    /* ----------------------------- Equipo ----------------------------- */

    /**
     * Validates a device (equipo) in the network (red).
     *
     * @param equipo the device (equipo) to be validated
     * @throws InvalidUbicacionException   if the location (ubicacion) of the device does not exist in the network
     * @throws InvalidTipoEquipoException  if the type of device does not exist in the network
     * @throws InvalidTipoPuertoException  if one of the types of ports does not exist in the network
     * @throws InvalidDireccionIPException if the IP address of the device is invalid
     */
    private void equipoValidation(Equipo equipo) throws InvalidUbicacionException, InvalidTipoEquipoException, InvalidTipoPuertoException, InvalidDireccionIPException {
        if (!this.ubicaciones.containsKey(equipo.getUbicacion().getCodigo())) {
            throw new InvalidUbicacionException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + equipo.getUbicacion().getCodigo());
        }
        if (!equipo.getDireccionesIp().isEmpty() && equipo.getDireccionesIp().stream().noneMatch(Utils::validateIP)) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("InvalidDevice_invalidIp") + equipo.getDireccionesIp());
        }
        if (!this.tiposEquipos.containsKey(equipo.getTipoEquipo().getCodigo())) {
            throw new InvalidTipoEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownA") + " " + equipo.getTipoEquipo().getCodigo());
        }
        for (Puerto puerto : equipo.getPuertos()) {
            if (!this.tiposPuertos.containsKey(puerto.getTipoPuerto().getCodigo())) {
                throw new InvalidTipoPuertoException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + puerto.getTipoPuerto().getCodigo());
            }
        }
    }

    /**
     * Adds a device (equipo) to the network (red). Also adds the device to the database.
     *
     * @param equipo the device (equipo) to be added
     * @throws InvalidEquipoException      if the device already exists
     * @throws InvalidUbicacionException   if the location (ubicacion) of the device does not exist in the network
     * @throws InvalidTipoEquipoException  if the type of device does not exist in the network
     * @throws InvalidTipoPuertoException  if one of the types of ports does not exist in the network
     * @throws InvalidDireccionIPException if the IP address of the device is invalid
     */
    public void addEquipo(Equipo equipo) throws InvalidEquipoException, InvalidUbicacionException, InvalidTipoEquipoException, InvalidTipoPuertoException, InvalidDireccionIPException {
        equipoValidation(equipo);
        if (this.equipos.containsKey(equipo.getCodigo())) {
            throw new IllegalArgumentException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        this.equipos.put(equipo.getCodigo(), equipo);
        this.equipoService.insert(equipo);
    }

    /**
     * Modifies an existing device (equipo) in the network (red).
     * <p>
     * This method first validates the modified device. If the device does not exist, an InvalidEquipoException is thrown.
     * The old device is then removed, and the modified device is added to the map and updated in the database.
     *
     * @param equipo the existing device (equipo) to be replaced
     * @throws InvalidEquipoException      if the device does not exist
     * @throws InvalidUbicacionException   if the location (ubicacion) of the device does not exist in the network
     * @throws InvalidTipoEquipoException  if the type of device does not exist in the network
     * @throws InvalidTipoPuertoException  if one of the types of ports does not exist in the network
     * @throws InvalidDireccionIPException if the IP address of the device is invalid
     */
    public void modifyEquipo(Equipo equipo) throws InvalidEquipoException, InvalidUbicacionException, InvalidTipoEquipoException, InvalidTipoPuertoException, InvalidDireccionIPException {
        equipoValidation(equipo);
        if (!this.equipos.containsKey(equipo.getCodigo())) {
            throw new InvalidEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownM"));
        }
        this.equipos.put(equipo.getCodigo(), equipo); // Updates the device in the map
        this.equipoService.update(equipo); // Updates the device in the database
    }

    /**
     * Deletes a device (equipo) from the network (red). It deletes all connections associated with it. Also deletes the device from the database.
     *
     * @param equipo the device (equipo) to be deleted
     * @throws InvalidEquipoException if the device does not exist in the network
     */
    public void deleteEquipo(Equipo equipo) throws InvalidEquipoException {
        if (!this.equipos.containsKey(equipo.getCodigo())) {
            throw new InvalidEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownD"));
        }
        for (Conexion conexion : this.conexiones.values()) {
            if (conexion.getEquipo1().equals(equipo) || conexion.getEquipo2().equals(equipo)) {
                deleteConexion(conexion);
            }
        }
        this.equipos.remove(equipo.getCodigo());
        this.equipoService.delete(equipo);
    }

    /**
     * Adds a port to a device (equipo) in the network (red). Also adds the port to the database.
     *
     * @param equipo the device (equipo) to which to add the port
     * @param puerto the port to be added
     * @throws InvalidPuertoEquipoException if the device does not exist in the network, if the port already exists in the device, or if there are no available ports
     */
    public void addPuertoEquipo(Equipo equipo, Puerto puerto) throws InvalidPuertoEquipoException {
        if (getEquipos().get(equipo.getCodigo()) == null) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + coordinator.getResourceBundle().getString("TableEquipos_title"));
        }
        if (equipo.getPuertos().contains(puerto)) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("InvalidDevicePort_existingAM"));
        }
        equipo.addPuerto(puerto);
        this.equipoService.insertPort(equipo, puerto); // Inserts the new port into the database
    }

    /**
     * Deletes a port from a device (equipo) in the network (red). Also deletes the port from the database.
     *
     * @param equipo the device (equipo) from which to delete the port
     * @param puerto the port to be deleted
     * @throws InvalidPuertoEquipoException if the device does not exist in the network, if the device has only one port, or if there are no available ports
     */
    public void deletePuertoEquipo(Equipo equipo, Puerto puerto) throws InvalidPuertoEquipoException {
        if (getEquipos().get(equipo.getCodigo()) == null) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + coordinator.getResourceBundle().getString("TableEquipos_title"));
        }
        if (equipo.getPuertos().size() <= 1) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("InvalidDevicePort_onlyOne"));
        }
        if (0 >= equipo.totalPuertos() - puerto.getCantidad()) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("InvalidDevicePort_utilizedDM"));
        }
        equipo.removePuerto(puerto);
        this.equipoService.deletePort(equipo, puerto); // Deletes the port from the database
    }

    /**
     * Modifies a port of a device (equipo) in the network (red). Also updates the port in the database.
     *
     * @param equipo    the device (equipo) whose port is to be modified
     * @param oldPuerto the old port to be replaced
     * @param newPuerto the new port to be set
     * @throws InvalidPuertoEquipoException if the device does not exist in the network, if the new port already exists in the device, or if there are no available ports
     */
    public void modifyPuertoEquipo(Equipo equipo, Puerto oldPuerto, Puerto newPuerto) throws InvalidPuertoEquipoException {
        if (getEquipos().get(equipo.getCodigo()) == null) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + coordinator.getResourceBundle().getString("TableEquipos_title"));
        }

        if (equipo.getPuertos().stream().anyMatch(p -> !p.equals(oldPuerto) && p.equals(newPuerto))) {
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("InvalidDevicePort_existingAM"));
        }

        equipo.addPuerto(newPuerto);
        equipo.removePuerto(oldPuerto);

        if (availablePorts(equipo) < 0) {
            equipo.removePuerto(newPuerto);
            equipo.addPuerto(oldPuerto);
            throw new InvalidPuertoEquipoException(coordinator.getResourceBundle().getString("InvalidDevicePort_utilizedDM"));
        }
        this.equipoService.updatePort(equipo, oldPuerto, newPuerto); // Updates the port in the database
    }

    /**
     * Adds an IP address to a device (equipo) in the network (red). Also adds the IP address to the database.
     *
     * @param equipo the device (equipo) to which to add the IP address
     * @param ip     the IP address to be added
     * @throws InvalidDireccionIPException if the IP address is invalid, if the IP address already exists in the network, or if the device does not exist in the network
     */
    public void addIpEquipo(Equipo equipo, String ip) throws InvalidDireccionIPException {
        if (!Utils.validateIP(ip)) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("InvalidDevice_invalidIp") + ip);
        }
        if (getEquiposIps().contains(ip)) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("InvalidDevice_existingIPAM"));
        }
        if (getEquipos().get(equipo.getCodigo()) == null) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + coordinator.getResourceBundle().getString("TableEquipos_title"));
        }
        equipo.addIP(ip);
        this.equipoService.insertIp(equipo, ip); // Inserts the new IP address into the database
    }

    /**
     * Modifies an IP address of a device (equipo) in the network (red). Also updates the IP address in the database.
     *
     * @param equipo the device (equipo) whose IP address is to be modified
     * @param oldIp  the old IP address to be replaced
     * @param newIp  the new IP address to be set
     * @throws InvalidDireccionIPException if the new IP address is invalid or if the new IP address already exists in the network, or if the device does not exist in the network
     */
    public void modifyIpEquipo(Equipo equipo, String oldIp, String newIp) throws InvalidDireccionIPException {
        if (!Utils.validateIP(newIp)) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("InvalidDevice_invalidIp") + newIp);
        }
        if (getEquiposIps().stream().anyMatch(ip -> !ip.equals(oldIp) && ip.equals(newIp))) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("InvalidDevice_existingIPAM"));
        }
        if (getEquipos().get(equipo.getCodigo()) == null) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + coordinator.getResourceBundle().getString("TableEquipos_title"));
        }
        equipo.removeIP(oldIp);
        equipo.addIP(newIp);
        this.equipoService.updateIp(equipo, oldIp, newIp); // Updates the IP address in the database
    }

    /**
     * Deletes an IP address from a device (equipo) in the network (red). Also deletes the IP address from the database.
     *
     * @param equipo the device (equipo) from which to delete the IP address
     * @param ip     the IP address to be deleted
     * @throws InvalidDireccionIPException if the device does not exist in the network
     */
    public void removeIpEquipo(Equipo equipo, String ip) throws InvalidDireccionIPException {
        if (getEquipos().get(equipo.getCodigo()) == null) {
            throw new InvalidDireccionIPException(coordinator.getResourceBundle().getString("Invalid_unknownAM") + " " + coordinator.getResourceBundle().getString("TableEquipos_title"));
        }
        equipo.removeIP(ip);
        this.equipoService.deleteIp(equipo, ip); // Deletes the IP address from the database
    }

    /**
     * Calculates the number of available ports for a given device (equipo).
     *
     * @param equipo the device (equipo) for which to calculate available ports
     * @return the number of available ports
     */
    private int availablePorts(Equipo equipo) {
        int totalPuertos = equipo.totalPuertos();
        for (Conexion conexion : this.conexiones.values()) {
            if (conexion.getEquipo1().equals(equipo) || conexion.getEquipo2().equals(equipo)) {
                totalPuertos--;
            }
        }
        LoggerUtil.logDebug("Available ports for " + equipo.getCodigo() + ": " + totalPuertos);
        return totalPuertos;
    }


    /**
     * Retrieves the IP addresses of all devices (equipos) in the network.
     * <p>
     * This method iterates through all devices in the network, collects their IP addresses,
     * sorts them using a custom IP comparator, and returns the sorted list of IP addresses.
     *
     * @return a list of strings representing the sorted IP addresses of all devices in the network
     */
    public List<String> getEquiposIps() {
        ArrayList<String> ips = new ArrayList<>();

        for (Equipo equipo : equipos.values()) {
            ips.addAll(equipo.getDireccionesIp());
        }
        Collections.sort(ips, Utils.ipComparator());
        return ips;
    }

    /* ----------------------------- Ubicacion  ----------------------------- */

    /**
     * Validates a location (ubicacion) in the network (red).
     *
     * @param ubicacion the location (ubicacion) to be validated
     * @throws InvalidUbicacionException if the location does not exist in the network
     */
    private void ubicationValidation(Ubicacion ubicacion) throws InvalidUbicacionException {
        if (!this.ubicaciones.containsKey(ubicacion.getCodigo())) {
            throw new InvalidUbicacionException(coordinator.getResourceBundle().getString("Invalid_unknownDM") + " " + ubicacion.getCodigo());
        }
    }

    /**
     * Adds a location (ubicacion) to the network (red). Also adds the location to the database.
     *
     * @param ubicacion the location (ubicacion) to be added
     * @throws InvalidUbicacionException if the location already exists in the network
     */
    public void addUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        if (this.ubicaciones.containsKey(ubicacion.getCodigo())) {
            throw new InvalidUbicacionException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        this.ubicaciones.put(ubicacion.getCodigo(), ubicacion);
        this.ubicacionService.insert(ubicacion);
    }

    /**
     * Modifies an existing location (ubicacion) in the network (red).
     *
     * @param ubicacion the location (ubicacion) to be modified
     * @throws InvalidUbicacionException if the location does not exist in the network
     */
    public void modifyUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        ubicationValidation(ubicacion);
        this.ubicacionService.update(ubicacion);
    }

    /**
     * Deletes a location (ubicacion) from the network (red). Also deletes the location from the database.
     *
     * @param ubicacion the location (ubicacion) to be deleted
     * @throws InvalidUbicacionException if the location does not exist in the network or if there are devices (equipos) in the location
     */
    public void deleteUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        ubicationValidation(ubicacion);
        if (this.equipos.values().stream().anyMatch(equipo -> equipo.getUbicacion().equals(ubicacion))) {
            throw new InvalidUbicacionException(coordinator.getResourceBundle().getString("Invalid_referencedDM"));
        }
        this.ubicaciones.remove(ubicacion.getCodigo());
        this.ubicacionService.delete(ubicacion);
    }


    /* ----------------------------- TipoCable ----------------------------- */

    /**
     * Validates a type of cable (tipoCable) in the network (red).
     *
     * @param tipoCable the type of cable (tipoCable) to be validated
     * @throws InvalidTipoCableException if the type of cable does not exist in the network
     */
    private void tipoCableValidation(TipoCable tipoCable) throws InvalidTipoCableException {
        if (!this.tiposCables.containsKey(tipoCable.getCodigo())) {
            throw new InvalidTipoCableException(coordinator.getResourceBundle().getString("Invalid_unknownDM") + " " + tipoCable.getCodigo());
        }
    }

    /**
     * Adds a type of cable (tipoCable) to the network (red). Also adds the type of cable to the database.
     *
     * @param tipoCable the type of cable (tipoCable) to be added
     * @throws InvalidTipoCableException if the type of cable already exists in the network
     */
    public void addTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        if (tiposCables.containsKey(tipoCable.getCodigo())) {
            throw new InvalidTipoCableException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        this.tiposCables.put(tipoCable.getCodigo(), tipoCable);
        this.tipoCableService.insert(tipoCable);
    }

    /**
     * Modifies an existing type of cable (tipoCable) in the network (red).
     *
     * @param tipoCable the type of cable (tipoCable) to be modified
     * @throws InvalidTipoCableException if the type of cable does not exist in the network or if there are connections using this type of cable
     */
    public void modifyTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        tipoCableValidation(tipoCable);
        this.tipoCableService.update(tipoCable);
    }

    /**
     * Deletes a type of cable (tipoCable) from the network (red). Also deletes the type of cable from the database.
     *
     * @param tipoCable the type of cable (tipoCable) to be deleted
     * @throws InvalidTipoCableException if the type of cable does not exist in the network or if there are connections using this type of cable
     */
    public void deleteTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        tipoCableValidation(tipoCable);
        if (this.conexiones.values().stream().anyMatch(conexion -> (conexion.getTipoCable().equals(tipoCable)))) {
            throw new InvalidTipoCableException(coordinator.getResourceBundle().getString("Invalid_referencedDM"));
        }
        this.tiposCables.remove(tipoCable.getCodigo());
        this.tipoCableService.delete(tipoCable);
    }


    /* ----------------------------- TipoEquipo ----------------------------- */

    /**
     * Validates a type of device (tipoEquipo) in the network (red).
     *
     * @param tipoEquipo the type of device (tipoEquipo) to be validated
     * @throws InvalidTipoEquipoException if the type of device does not exist in the network
     */
    private void tipoEquipoValidation(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        if (!this.tiposEquipos.containsKey(tipoEquipo.getCodigo())) {
            throw new InvalidTipoEquipoException(coordinator.getResourceBundle().getString("Invalid_unknownDM") + " " + tipoEquipo.getCodigo());
        }
    }

    /**
     * Adds a type of device (tipoEquipo) to the network (red). Also adds the type of device to the database.
     *
     * @param tipoEquipo the type of device (tipoEquipo) to be added
     * @throws InvalidTipoEquipoException if the type of device already exists in the network
     */
    public void addTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        if (tiposEquipos.containsKey(tipoEquipo.getCodigo())) {
            throw new InvalidTipoEquipoException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        this.tiposEquipos.put(tipoEquipo.getCodigo(), tipoEquipo);
        this.tipoEquipoService.insert(tipoEquipo);
    }

    /**
     * Modifies an existing type of device (tipoEquipo) in the network (red).
     *
     * @param tipoEquipo the type of device (tipoEquipo) to be modified
     * @throws InvalidTipoEquipoException if the type of device does not exist in the network or if there are devices using this type of device
     */
    public void modifyTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        tipoEquipoValidation(tipoEquipo);
        this.tipoEquipoService.update(tipoEquipo);
    }

    /**
     * Deletes a type of device (tipoEquipo) from the network (red). Also deletes the type of device from the database.
     *
     * @param tipoEquipo the type of device (tipoEquipo) to be deleted
     * @throws InvalidTipoEquipoException if the type of device does not exist in the network or if there are devices using this type of device
     */
    public void deleteTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        tipoEquipoValidation(tipoEquipo);
        if (this.equipos.values().stream().anyMatch(equipo -> (equipo.getTipoEquipo().equals(tipoEquipo)))) {
            throw new InvalidTipoEquipoException(coordinator.getResourceBundle().getString("Invalid_referencedDM"));
        }
        this.tiposEquipos.remove(tipoEquipo.getCodigo());
        this.tipoEquipoService.delete(tipoEquipo);
    }


    /* ----------------------------- TipoPuerto ----------------------------- */

    /**
     * Validates a type of port (tipoPuerto) in the network (red).
     *
     * @param tipoPuerto the type of port (tipoPuerto) to be validated
     * @throws InvalidTipoPuertoException if the type of port does not exist in the network
     */
    private void tipoPuertoValidation(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        if (!this.tiposPuertos.containsKey(tipoPuerto.getCodigo())) {
            throw new InvalidTipoPuertoException(coordinator.getResourceBundle().getString("Invalid_unknownDM") + " " + tipoPuerto.getCodigo());
        }
    }

    /**
     * Adds a type of port (tipoPuerto) to the network (red). Also adds the type of port to the database.
     *
     * @param tipoPuerto the type of port (tipoPuerto) to be added
     * @throws InvalidTipoPuertoException if the type of port already exists in the network
     */
    public void addTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        if (tiposPuertos.containsKey(tipoPuerto.getCodigo())) {
            throw new InvalidTipoPuertoException(coordinator.getResourceBundle().getString("Invalid_existingA"));
        }
        this.tiposPuertos.put(tipoPuerto.getCodigo(), tipoPuerto);
        this.tipoPuertoService.insert(tipoPuerto);
    }

    /**
     * Modifies an existing type of port (tipoPuerto) in the network (red).
     *
     * @param tipoPuerto the type of port (tipoPuerto) to be modified
     * @throws InvalidTipoPuertoException if the type of port does not exist in the network or if there are devices or connections using this type of port
     */
    public void modifyTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        tipoPuertoValidation(tipoPuerto);
        this.tipoPuertoService.update(tipoPuerto);
    }

    /**
     * Deletes a type of port (tipoPuerto) from the network (red). Also deletes the type of port from the database.
     *
     * @param tipoPuerto the type of port (tipoPuerto) to be deleted
     * @throws InvalidTipoPuertoException if the type of port does not exist in the network or if there are devices or connections using this type of port
     */
    public void deleteTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        tipoPuertoValidation(tipoPuerto);
        if (this.equipos.values().stream().anyMatch(equipo -> (equipo.getPuertos().stream().anyMatch(puerto -> (puerto.getTipoPuerto().equals(tipoPuerto)))))) {
            throw new InvalidTipoPuertoException(coordinator.getResourceBundle().getString("Invalid_referencedDM"));
        }
        if (this.conexiones.values().stream().anyMatch(conexion -> (conexion.getPuerto1().equals(tipoPuerto) || conexion.getPuerto2().equals(tipoPuerto)))) {
            throw new InvalidTipoPuertoException(coordinator.getResourceBundle().getString("Invalid_referencedDM"));
        }
        this.tiposPuertos.remove(tipoPuerto.getCodigo());
        this.tipoPuertoService.delete(tipoPuerto);
    }


    /**
     * Gets the name of the network (Red).
     *
     * @return the name of the network
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the name of the network (Red).
     *
     * @param nombre the new name of the network
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the map of connections (conexiones) in the network.
     *
     * @return the map of connections
     */
    public Map<String, Conexion> getConexiones() {
        return conexiones;
    }

    /**
     * Gets the map of devices (equipos) in the network.
     *
     * @return the map of devices
     */
    public Map<String, Equipo> getEquipos() {
        return equipos;
    }

    /**
     * Gets the map of locations (ubicaciones) in the network.
     *
     * @return the map of locations
     */
    public Map<String, Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    /**
     * Gets the map of types of cables (tiposCables) in the network.
     *
     * @return the map of types of cables
     */
    public Map<String, TipoCable> getTiposCables() {
        return tiposCables;
    }

    /**
     * Gets the map of types of devices (tiposEquipos) in the network.
     *
     * @return the map of types of devices
     */
    public Map<String, TipoEquipo> getTiposEquipos() {
        return tiposEquipos;
    }

    /**
     * Gets the map of types of ports (tiposPuertos) in the network.
     *
     * @return the map of types of ports
     */
    public Map<String, TipoPuerto> getTiposPuertos() {
        return tiposPuertos;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }

    @Override
    public String toString() {
        return "Red{" +
                "nombre='" + nombre + '\'' +
                ", conexiones=" + conexiones +
                ", equipos=" + equipos +
                ", ubicaciones=" + ubicaciones +
                '}';
    }

}
