package models;

import exceptions.InvalidEquipoException;
import exceptions.InvalidUbicacionException;

import java.util.*;

public class Red {
    private String nombre;
    private List<Conexion> conexiones;
    private Map<String, Equipo> equipos;
    private Map<String, Ubicacion> ubicaciones;

    public Red(String nombre) {
        this.nombre = nombre;
        this.conexiones = new ArrayList<>();
        this.equipos = new TreeMap<>();
        this.ubicaciones = new TreeMap<>();
    }

    /**
     * Adds a connection (conexion) to the network (red).
     *
     * @param conexion the connection (conexion) to be added
     * @throws InvalidEquipoException if either of the equipos in the conexion do not exist in the network
     */
    public void addConexion(Conexion conexion) throws InvalidEquipoException {
        if (!this.equipos.containsKey(conexion.getEquipo1().getCodigo()) || !this.equipos.containsKey(conexion.getEquipo2().getCodigo())) {
            throw new InvalidEquipoException("No se puede agregar la conexión porque " + conexion.getEquipo1().getCodigo() + " y/o " + conexion.getEquipo2().getCodigo() + " no existen en la red.");
        }
        this.conexiones.add(conexion);
    }

    /**
     * Removes a connection (conexion) from the network (red).
     *
     * @param conexion the connection (conexion) to be removed
     */
    public void deleteConexion(Conexion conexion) {
        this.conexiones.remove(conexion);
    }

    /**
     * Adds a team (equipo) to the network (red).
     *
     * @param equipo the team (equipo) to be added
     * @throws InvalidUbicacionException if the location (ubicacion) of the team does not exist in the network
     */
    public void addEquipo(Equipo equipo) throws InvalidUbicacionException {
        if (!this.ubicaciones.containsKey(equipo.getUbicacion().getCodigo())) {
            throw new InvalidUbicacionException("No se puede agregar el equipo porque la ubicación " + equipo.getUbicacion().getCodigo() + " no existe en la red.");
        }
        this.equipos.put(equipo.getCodigo(), equipo);
    }

    /**
     * Removes a device (equipo) from the network (red).
     *
     * @param equipo the device (equipo) to be removed
     * @throws InvalidEquipoException if the equipo is connected to another equipo
     */
    public void deleteEquipo(Equipo equipo) throws InvalidEquipoException {
        for (Conexion conexion : this.conexiones) {
            if (conexion.getEquipo1().equals(equipo) || conexion.getEquipo2().equals(equipo)) {
                throw new InvalidEquipoException("No se puede eliminar el equipo porque está conectado a otro equipo.");
            }
        }
        this.equipos.remove(equipo.getCodigo());
    }

    public void addUbicacion(Ubicacion ubicacion) {
        this.ubicaciones.put(ubicacion.getCodigo(), ubicacion);
    }

    /**
     * Removes a location (ubicacion) from the network (red).
     *
     * @param ubicacion the location (ubicacion) to be removed
     * @throws InvalidUbicacionException if there are teams (equipos) in the location
     */
    public void deleteUbicacion(Ubicacion ubicacion) throws InvalidUbicacionException {
        if (this.equipos.values().stream().anyMatch(equipo -> equipo.getUbicacion().equals(ubicacion))) {
            throw new InvalidUbicacionException("No se puede eliminar la ubicación porque hay equipos en ella.");
        }
        this.ubicaciones.remove(ubicacion.getCodigo());
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
