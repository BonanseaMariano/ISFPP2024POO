package logic;

import exceptions.*;
import models.*;
import services.*;
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


    /* ----------------------------- Conexion ----------------------------- */

    /**
     * Validates a connection (conexion) in the network (red).
     *
     * @param conexion the connection (conexion) to be validated
     * @throws InvalidEquipoException     if one of the devices (equipos) does not exist in the network
     * @throws InvalidTipoCableException  if the type of cable does not exist
     * @throws InvalidTipoPuertoException if one of the types of ports does not exist
     * @throws NoAvailablePortsException  if there are no available ports on either device
     */
    private void connectionValidation(Conexion conexion) throws InvalidEquipoException, InvalidConexionException, NoAvailablePortsException, InvalidTipoCableException, InvalidTipoPuertoException {
        if (!this.equipos.containsKey(conexion.getEquipo1().getCodigo()) || !this.equipos.containsKey(conexion.getEquipo2().getCodigo())) {
            throw new InvalidEquipoException("No se puede agregar la conexión porque uno de los equipos no existe");
        }
        if (!this.tiposCables.containsKey(conexion.getTipoCable().getCodigo())) {
            throw new InvalidTipoCableException("No se puede agregar la conexión porque el tipo de cable no existe");
        }
        if (!this.tiposPuertos.containsKey(conexion.getPuerto1().getCodigo()) || !this.tiposPuertos.containsKey(conexion.getPuerto2().getCodigo())) {
            throw new InvalidTipoPuertoException("No se puede agregar la conexión porque uno de los tipos de puertos no existe");
        }
        if (availablePorts(conexion.getEquipo1()) == 0 || availablePorts(conexion.getEquipo2()) == 0) {
            throw new NoAvailablePortsException("No se puede agregar la conexión porque no hay puertos disponibles.");
        }
    }

    /**
     * Adds a connection (conexion) to the network (red). Also adds the connection to the database.
     *
     * @param conexion the connection (conexion) to be added
     * @throws InvalidEquipoException   if one of the devices (equipos) does not exist in the network
     * @throws InvalidConexionException if the connection already exists
     */
    public void addConexion(Conexion conexion) throws InvalidEquipoException, InvalidConexionException, NoAvailablePortsException, InvalidTipoCableException, InvalidTipoPuertoException {
        String conexionkey = conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo();
        connectionValidation(conexion);
        if (this.conexiones.containsKey(conexionkey)) {
            throw new InvalidConexionException("No se puede agregar la conexión porque ya existe.");
        }
        this.conexiones.put(conexionkey, conexion);
        this.conexionService.insert(conexion);
    }


    /**
     * Modifies an existing connection (conexion) in the network (red). Also updates the connection in the database.
     * <p>
     * This method first validates the connection. If the connection is invalid due to various exceptions such as
     * InvalidEquipoException, InvalidConexionException, NoAvailablePortsException, InvalidTipoCableException, or InvalidTipoPuertoException,
     * the exception is thrown. If the connection does not exist in the network, an InvalidConexionException is thrown.
     * If the connection is successfully validated and exists, it is updated in the network and the database.
     *
     * @param conexion the connection (conexion) to be modified
     * @throws InvalidEquipoException     if one of the devices (equipos) does not exist in the network
     * @throws InvalidConexionException   if the connection does not exist or is invalid
     * @throws NoAvailablePortsException  if there are no available ports on either device
     * @throws InvalidTipoCableException  if the type of cable does not exist
     * @throws InvalidTipoPuertoException if one of the types of ports does not exist
     */
    public void modifyConnection(Conexion conexion) throws InvalidEquipoException, InvalidConexionException, NoAvailablePortsException, InvalidTipoCableException, InvalidTipoPuertoException {
        String conexionkey = conexion.getEquipo1().getCodigo() + "-" + conexion.getEquipo2().getCodigo();
        connectionValidation(conexion);
        if (!this.conexiones.containsKey(conexionkey)) {
            throw new InvalidConexionException("No se puede modificar la conexión porque no existe.");
        }
        this.conexiones.put(conexionkey, conexion);
        this.conexionService.update(conexion);
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
            throw new InvalidConexionException("No se puede modificar la conexión porque no existe.");
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
            throw new InvalidUbicacionException("No se puede agregar el equipo porque la ubicación no existe en la red.");
        }
        if (equipo.getDireccionesIp().stream().noneMatch(Utils::validateIP)) {
            throw new InvalidDireccionIPException("No se puede agregar el equipo porque la IP es invalida: " + equipo.getDireccionesIp());
        }
        if (!this.tiposEquipos.containsKey(equipo.getTipoEquipo().getCodigo())) {
            throw new InvalidTipoEquipoException("No se puede agregar el equipo porque el tipo de equipo no existe en la red.");
        }
        for (Puerto puerto : equipo.getPuertos()) {
            if (!this.tiposPuertos.containsKey(puerto.getTipoPuerto().getCodigo())) {
                throw new InvalidTipoPuertoException("No se puede agregar el equipo porque uno de los tipos de puertos no existe en la red.");
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
            throw new InvalidEquipoException("No se puede agregar el equipo porque ya existe.");
        }
        this.equipos.put(equipo.getCodigo(), equipo);
        this.equipoService.insert(equipo);
    }

    /**
     * Modifies an existing device (equipo) in the network (red). It does not alter connections assosiated with it. Also updates the device in the database.
     *
     * @param equipo the device (equipo) to be modified
     * @throws InvalidEquipoException      if the device does not exist in the network
     * @throws InvalidUbicacionException   if the location (ubicacion) of the device does not exist in the network
     * @throws InvalidTipoEquipoException  if the type of device does not exist in the network
     * @throws InvalidTipoPuertoException  if one of the types of ports does not exist in the network
     * @throws InvalidDireccionIPException if the IP address of the device is invalid
     */
    public void modifyEquipo(Equipo equipo) throws InvalidEquipoException, InvalidUbicacionException, InvalidTipoEquipoException, InvalidTipoPuertoException, InvalidDireccionIPException {
        if (!this.equipos.containsKey(equipo.getCodigo())) {
            throw new InvalidEquipoException("No se puede modificar el equipo porque no existe.");
        }
        equipoValidation(equipo);

        this.equipos.put(equipo.getCodigo(), equipo);
        this.equipoService.update(equipo);
    }

    /**
     * Deletes a device (equipo) from the network (red). It deletes all connections associated with it. Also deletes the device from the database.
     *
     * @param equipo the device (equipo) to be deleted
     * @throws InvalidEquipoException if the device does not exist in the network
     */
    public void deleteEquipo(Equipo equipo) throws InvalidEquipoException {
        if (!this.equipos.containsKey(equipo.getCodigo())) {
            throw new InvalidEquipoException("No se puede eliminar el equipo porque no existe.");
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
     * Calculates the number of available ports for a given device (equipo).
     *
     * @param equipo the device (equipo) for which to calculate available ports
     * @return the number of available ports
     */
    private int availablePorts(Equipo equipo) {
        int totalPuertos = 0;
        for (Puerto puerto : equipo.getPuertos()) {
            totalPuertos += puerto.getCantidad();
        }
        for (Conexion conexion : this.conexiones.values()) {
            if (conexion.getEquipo1().equals(equipo) || conexion.getEquipo2().equals(equipo)) {
                totalPuertos--;
            }
        }
        return totalPuertos;
    }


    /* ----------------------------- Ubicacion  ----------------------------- */

    /**
     * Validates a location (ubicacion) in the network (red).
     *
     * @param ubicacion the location (ubicacion) to be validated
     * @throws InvalidUbicacionException if the location does not exist in the network or if there are devices (equipos) in the location
     */
    private void ubicationValidation(Ubicacion ubicacion) throws InvalidUbicacionException {
        if (!this.ubicaciones.containsKey(ubicacion.getCodigo())) {
            throw new InvalidUbicacionException("No se puede modificar o eliminar la ubicación porque no existe");
        }
        if (this.equipos.values().stream().anyMatch(equipo -> equipo.getUbicacion().equals(ubicacion))) {
            throw new InvalidUbicacionException("No se puede modificar o eliminar la ubicación porque hay equipos en ella");
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
            throw new InvalidUbicacionException("No se puede agregar la ubicación porque ya existe");
        }
        this.ubicaciones.put(ubicacion.getCodigo(), ubicacion);
        this.ubicacionService.insert(ubicacion);
    }

    /**
     * Modifies an existing location (ubicacion) in the network (red). Also updates the location in the database.
     *
     * @param ubicacion the location (ubicacion) to be modified
     * @throws InvalidUbicacionException if the location does not exist in the network or if there are devices (equipos) in the location
     */
    public void modifyUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        ubicationValidation(ubicacion);
        this.ubicaciones.put(ubicacion.getCodigo(), ubicacion);
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
        this.ubicaciones.remove(ubicacion.getCodigo());
        this.ubicacionService.delete(ubicacion);
    }


    /* ----------------------------- TipoCable ----------------------------- */

    /**
     * Validates a type of cable (tipoCable) in the network (red).
     *
     * @param tipoCable the type of cable (tipoCable) to be validated
     * @throws InvalidTipoCableException if the type of cable does not exist in the network or if there are connections using this type of cable
     */
    private void tipoCableValidation(TipoCable tipoCable) throws InvalidTipoCableException {
        if (!this.tiposCables.containsKey(tipoCable.getCodigo())) {
            throw new InvalidTipoCableException("No se puede modificar o eliminar el tipo de cable porque no existe en la red");
        }
        if (this.conexiones.values().stream().anyMatch(conexion -> (conexion.getTipoCable().equals(tipoCable)))) {
            throw new InvalidTipoCableException("No se puede modificar o eliminar el tipo de cable porque hay conexiones con ese tipo de cable");
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
            throw new InvalidTipoCableException("No se puede agregar el tipo de cable porque ya existe en la red");
        }
        this.tiposCables.put(tipoCable.getCodigo(), tipoCable);
        this.tipoCableService.insert(tipoCable);
    }

    /**
     * Modifies an existing type of cable (tipoCable) in the network (red). Also updates the type of cable in the database.
     *
     * @param tipoCable the type of cable (tipoCable) to be modified
     * @throws InvalidTipoCableException if the type of cable does not exist in the network or if there are connections using this type of cable
     */
    public void modifyTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        tipoCableValidation(tipoCable);
        this.tiposCables.put(tipoCable.getCodigo(), tipoCable);
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
        this.tiposCables.remove(tipoCable.getCodigo());
        this.tipoCableService.delete(tipoCable);
    }


    /* ----------------------------- TipoEquipo ----------------------------- */

    /**
     * Validates a type of device (tipoEquipo) in the network (red).
     *
     * @param tipoEquipo the type of device (tipoEquipo) to be validated
     * @throws InvalidTipoEquipoException if the type of device does not exist in the network or if there are devices using this type of device
     */
    private void tipoEquipoValidation(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        if (!this.tiposEquipos.containsKey(tipoEquipo.getCodigo())) {
            throw new InvalidTipoEquipoException("No se puede modificar o eliminar el tipo de equipo porque no existe en la red");
        }
        if (this.equipos.values().stream().anyMatch(equipo -> (equipo.getTipoEquipo().equals(tipoEquipo)))) {
            throw new InvalidTipoEquipoException("No se puede modificar o eliminar el tipo de equipo porque hay equipos con ese tipo de equipo");
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
            throw new InvalidTipoEquipoException("No se puede agregar el tipo de equipo porque ya existe en la red");
        }
        this.tiposEquipos.put(tipoEquipo.getCodigo(), tipoEquipo);
        this.tipoEquipoService.insert(tipoEquipo);
    }

    /**
     * Modifies an existing type of device (tipoEquipo) in the network (red). Also updates the type of device in the database.
     *
     * @param tipoEquipo the type of device (tipoEquipo) to be modified
     * @throws InvalidTipoEquipoException if the type of device does not exist in the network or if there are devices using this type of device
     */
    public void modifyTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        tipoEquipoValidation(tipoEquipo);
        this.tiposEquipos.put(tipoEquipo.getCodigo(), tipoEquipo);
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
        this.tiposEquipos.remove(tipoEquipo.getCodigo());
        this.tipoEquipoService.delete(tipoEquipo);
    }


    /* ----------------------------- TipoPuerto ----------------------------- */

    /**
     * Validates a type of port (tipoPuerto) in the network (red).
     *
     * @param tipoPuerto the type of port (tipoPuerto) to be validated
     * @throws InvalidTipoPuertoException if the type of port does not exist in the network or if there are devices or connections using this type of port
     */
    private void tipoPuertoValidation(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        if (!this.tiposPuertos.containsKey(tipoPuerto.getCodigo())) {
            throw new InvalidTipoPuertoException("No se puede modificar o eliminar el tipo de puerto porque no existe en la red");
        }
        if (this.equipos.values().stream().anyMatch(equipo -> (equipo.getPuertos().stream().anyMatch(puerto -> (puerto.getTipoPuerto().equals(tipoPuerto)))))) {
            throw new InvalidTipoPuertoException("No se puede modificar o eliminar el tipo de puerto porque hay equipos con ese tipo de puerto");
        }
        if (this.conexiones.values().stream().anyMatch(conexion -> (conexion.getPuerto1().equals(tipoPuerto) || conexion.getPuerto2().equals(tipoPuerto)))) {
            throw new InvalidTipoPuertoException("No se puede modificar o eliminar el tipo de puerto porque hay conexiones con ese tipo de puerto");
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
            throw new InvalidTipoPuertoException("No se puede agregar el tipo de puerto porque ya existe en la red");
        }
        this.tiposPuertos.put(tipoPuerto.getCodigo(), tipoPuerto);
        this.tipoPuertoService.insert(tipoPuerto);
    }

    /**
     * Modifies an existing type of port (tipoPuerto) in the network (red). Also updates the type of port in the database.
     *
     * @param tipoPuerto the type of port (tipoPuerto) to be modified
     * @throws InvalidTipoPuertoException if the type of port does not exist in the network or if there are devices or connections using this type of port
     */
    public void modifyTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        tipoPuertoValidation(tipoPuerto);
        this.tiposPuertos.put(tipoPuerto.getCodigo(), tipoPuerto);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Red red)) return false;
        return Objects.equals(nombre, red.nombre);
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
