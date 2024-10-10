package models;

import exceptions.*;
import services.*;
import utils.Utils;

import java.util.*;

public class Red {

    private static Red red;

    private String nombre;
    private List<Conexion> conexiones;
    private Map<String, Equipo> equipos;
    private Map<String, Ubicacion> ubicaciones;
    private List<TipoCable> tiposCables;
    private List<TipoEquipo> tiposEquipos;
    private List<TipoPuerto> tiposPuertos;

    /*----- Servicios -----*/
    private ConexionService conexionService;
    private EquipoService equipoService;
    private UbicacionService ubicacionService;
    private TipoCableService tipoCableService;
    private TipoEquipoService tipoEquipoService;
    private TipoPuertoService tipoPuertoService;

    public Red() {
        this.conexiones = new ArrayList<>();
        this.equipos = new TreeMap<>();
        this.ubicaciones = new TreeMap<>();
        this.tiposCables = new ArrayList<>();
        this.tiposEquipos = new ArrayList<>();
        this.tiposPuertos = new ArrayList<>();

        /*----- Servicios -----*/
        this.conexionService = new ConexionServiceImpl();
        this.equipoService = new EquipoServiceImpl();
        this.ubicacionService = new UbicacionServiceImpl();
        this.tipoCableService = new TipoCableServiceImpl();
        this.tipoEquipoService = new TipoEquipoServiceImpl();
        this.tipoPuertoService = new TipoPuertoServiceImpl();
    }

    public static Red getEmpresa() {
        if(red == null){
            red = new Red();
        }
        return red;
    }



/* ----------------------------- Conexion ----------------------------- */

    /**
     * Adds a connection (conexion) to the network (red).
     *
     * @param conexion the connection (conexion) to be added
     * @throws InvalidEquipoException if either of the equipos in the conexion do not exist in the network
     */
    public void addConexion(Conexion conexion) throws InvalidEquipoException {
        if (conexiones.contains(conexion)){
            throw new InvalidEquipoException("No se puede agregar la conexión porque ya existe.");
        }
        if (!this.equipos.containsKey(conexion.getEquipo1().getCodigo()) || !this.equipos.containsKey(conexion.getEquipo2().getCodigo())) {
            throw new InvalidEquipoException("No se puede agregar la conexión porque " + conexion.getEquipo1().getCodigo() + " y/o " + conexion.getEquipo2().getCodigo() + " no existen en la red.");
        }
        this.conexiones.add(conexion);
        this.conexionService.insert(conexion);
    }

    /**
     * Removes a connection (conexion) from the network (red).
     *
     * @param conexion the connection (conexion) to be removed
     */
    public void deleteConexion(Conexion conexion) throws InvalidConexionException{
        if (!this.conexiones.contains(conexion)){
            throw new InvalidEquipoException("No se puede eliminar la conexión porque no existe.");
        }
        this.conexiones.remove(conexion);
        this.conexionService.delete(conexion);
    }

    public void modifyConnection(Conexion conexion){
        if (!this.conexiones.contains(conexion)){
            throw new InvalidEquipoException("No se puede modificar la conexión porque no existe.");
        }
        int aux = this.conexiones.indexOf(conexion);
        this.conexiones.set(aux, conexion);
        this.conexionService.update(conexion);
    }


/* ----------------------------- Equipo ----------------------------- */

    /**
     * Adds a team (equipo) to the network (red).
     *
     * @param equipo the team (equipo) to be added
     * @throws InvalidUbicacionException if the location (ubicacion) of the team does not exist in the network
     */
    public void addEquipo(Equipo equipo) throws InvalidUbicacionException {
        if(!equipo.getDireccionesIp().stream().anyMatch(Utils::validateIP)){
            throw new InvalidUbicacionException("No se puede agregar porque la IP es invalida " + equipo.getDireccionesIp());
        }
        if (!this.ubicaciones.containsKey(equipo.getUbicacion().getCodigo())) {
            throw new InvalidUbicacionException("No se puede agregar el equipo porque la ubicación " + equipo.getUbicacion().getCodigo() + " no existe en la red.");
        }
        this.equipos.put(equipo.getCodigo(), equipo);
        this.equipoService.insert(equipo);
    }

    /**
     * Removes a device (equipo) from the network (red).
     *
     * @param equipo the device (equipo) to be removed
     * @throws InvalidEquipoException if the equipo is connected to another equipo
     */
    public void deleteEquipo(Equipo equipo) throws InvalidEquipoException {
        if (!this.equipos.containsKey(equipo.getCodigo())) {
            throw new InvalidEquipoException("No se puede eliminar el equipo porque no existe.");
        }
        for (Conexion conexion : this.conexiones) {
            if (conexion.getEquipo1().equals(equipo) || conexion.getEquipo2().equals(equipo)) {
                throw new InvalidEquipoException("No se puede eliminar el equipo porque está conectado a otro equipo.");
            }
        }
        this.equipos.remove(equipo.getCodigo());
        this.equipoService.delete(equipo);
    }


    public void modifyEquipo(Equipo equipo) throws InvalidEquipoException{
        if (!this.equipos.containsKey(equipo.getCodigo())){
            throw new InvalidEquipoException("No se puede modificar el equipo porque no existe.");
        }
        this.equipos.replace(equipo.getCodigo(), equipo);
        this.equipoService.update(equipo);
    }


/* ----------------------------- Ubicacion  ----------------------------- */

    public void addUbicacion(Ubicacion ubicacion) {
        if(this.ubicaciones.containsKey(ubicacion.getCodigo())){
            throw new InvalidUbicacionException("No se puede agregar la ubicación porque ya existe.");
        }
        this.ubicaciones.put(ubicacion.getCodigo(), ubicacion);
        this.ubicacionService.insert(ubicacion);
    }

    /**
     * Removes a location (ubicacion) from the network (red).
     *
     * @param ubicacion the location (ubicacion) to be removed
     * @throws InvalidUbicacionException if there are teams (equipos) in the location
     */
    public void deleteUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        if(!this.ubicaciones.containsKey(ubicacion.getCodigo())){
            throw new InvalidUbicacionException("No se puede eliminar la ubicación porque no existe.");
        }
        if (this.equipos.values().stream().anyMatch(equipo -> equipo.getUbicacion().equals(ubicacion))) {
            throw new InvalidUbicacionException("No se puede eliminar la ubicación porque hay equipos en ella.");
        }
        this.ubicaciones.remove(ubicacion.getCodigo());
        this.ubicacionService.delete(ubicacion);
    }

    public void modifyUbicacion(Ubicacion ubicacion){
        if(!this.ubicaciones.containsKey(ubicacion.getCodigo())){
            throw new InvalidUbicacionException("No se puede modificar la ubicación porque no existe.");
        }
        this.ubicaciones.replace(ubicacion.getCodigo(), ubicacion);
        this.ubicacionService.update(ubicacion);
    }


/* ----------------------------- TipoCable ----------------------------- */

    public void addTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        if(tiposCables.contains(tipoCable)){
            throw new InvalidTipoCableException("No se puede agregar el tipo de cable porque ya existe en la red.");
        }
        this.tiposCables.add(tipoCable);
        this.tipoCableService.insert(tipoCable);
    }

    public void deleteTipoCable(TipoCable tipoCable) throws InvalidTipoCableException {
        if(!this.tiposCables.contains(tipoCable)){
            throw new InvalidTipoCableException("No se puede eliminar el tipo de cable porque no existe en la red.");
        }
        if(this.conexiones.stream().anyMatch(conexion -> (conexion.getTipoCable().equals(tipoCable)))){
            throw new InvalidTipoCableException("No se puede eliminar el tipo de cable porque hay conexiones con ese tipo de cable.");
        }
        this.tiposCables.remove(tipoCable);
        this.tipoCableService.delete(tipoCable);
    }

    public void modifyTipoCable(TipoCable tipoCable){
        if (!this.tiposCables.contains(tipoCable)){
            throw new InvalidTipoCableException("No se puede modificar el tipo de cable porque no existe en la red.");
        }
        int aux = this.tiposCables.indexOf(tipoCable);
        this.tiposCables.set(aux, tipoCable);
        this.tipoCableService.update(tipoCable);
    }


/* ----------------------------- TipoEquipo ----------------------------- */

    public void addTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        if(tiposEquipos.contains(tipoEquipo)){
            throw new InvalidTipoEquipoException("No se puede agregar el tipo de equipo porque ya existe en la red.");
        }
        this.tiposEquipos.add(tipoEquipo);
        this.tipoEquipoService.insert(tipoEquipo);
    }

    public void deleteTipoEquipo(TipoEquipo tipoEquipo) throws InvalidTipoEquipoException {
        if(!this.tiposEquipos.contains(tipoEquipo)){
            throw new InvalidTipoEquipoException("No se puede eliminar el tipo de equipo porque no existe en la red.");
        }
        if(this.equipos.values().stream().anyMatch(equipo -> (equipo.getTipoEquipo().equals(tipoEquipo)))){
            throw new InvalidTipoEquipoException("No se puede eliminar el tipo de equipo porque hay equipos con ese tipo de equipo.");
        }
        this.tiposEquipos.remove(tipoEquipo);
        this.tipoEquipoService.delete(tipoEquipo);
    }

    public void modifyTipoEquipo(TipoEquipo tipoEquipo){
        if (!this.tiposEquipos.contains(tipoEquipo)){
            throw new InvalidTipoEquipoException("No se puede modificar el tipo de equipo porque no existe en la red.");
        }
        int aux = this.tiposEquipos.indexOf(tipoEquipo);
        this.tiposEquipos.set(aux, tipoEquipo);
        this.tipoEquipoService.update(tipoEquipo);
    }

/* ----------------------------- TipoPuerto ----------------------------- */

    public void addTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        if(tiposPuertos.contains(tipoPuerto)){
            throw new InvalidTipoPuertoException("No se puede agregar el tipo de puerto porque ya existe en la red.");
        }
        this.tiposPuertos.add(tipoPuerto);
        this.tipoPuertoService.insert(tipoPuerto);
    }

    public void deleteTipoPuerto(TipoPuerto tipoPuerto) throws InvalidTipoPuertoException {
        if(!this.tiposPuertos.contains(tipoPuerto)){
            throw new InvalidTipoPuertoException("No se puede eliminar el tipo de puerto porque no existe en la red.");
        }
        if(this.equipos.values().stream().anyMatch(equipo -> (equipo.getPuertos().stream().anyMatch(puerto -> (puerto.getTipoPuerto().equals(tipoPuerto)))))){
            throw new InvalidTipoPuertoException("No se puede eliminar el tipo de puerto porque hay puertos con ese tipo de puerto.");
        }
        this.tiposPuertos.remove(tipoPuerto);
        this.tipoPuertoService.delete(tipoPuerto);
    }

    public void modifyTipoPuerto(TipoPuerto tipoPuerto){
        if (!this.tiposPuertos.contains(tipoPuerto)){
            throw new InvalidTipoPuertoException("No se puede modificar el tipo de puerto porque no existe en la red.");
        }
        int aux = this.tiposPuertos.indexOf(tipoPuerto);
        this.tiposPuertos.set(aux, tipoPuerto);
        this.tipoPuertoService.update(tipoPuerto);
    }



    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Conexion> getConexiones() {
        return conexiones;
    }

    public Map<String, Equipo> getEquipos() {
        return equipos;
    }

    public Map<String, Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public List<TipoCable> getTiposCables() {
        return tiposCables;
    }

    public List<TipoEquipo> getTiposEquipos() {
        return tiposEquipos;
    }

    public List<TipoPuerto> getTiposPuertos() {
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
