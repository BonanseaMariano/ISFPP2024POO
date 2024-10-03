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

    public void addConexion(Conexion conexion) throws InvalidEquipoException {
        if (!this.equipos.containsKey(conexion.getEquipo1().getCodigo()) || !this.equipos.containsKey(conexion.getEquipo2().getCodigo())) {
            throw new InvalidEquipoException("No se puede agregar la conexión porque " + conexion.getEquipo1().getCodigo() + " y/o " + conexion.getEquipo2().getCodigo() + " no existen en la red.");
        }
        this.conexiones.add(conexion);
    }

    public void addEquipo(Equipo equipo) throws InvalidUbicacionException {
        if (!this.ubicaciones.containsKey(equipo.getUbicacion().getCodigo())) {
            throw new InvalidUbicacionException("No se puede agregar el equipo porque la ubicación " + equipo.getUbicacion().getCodigo() + " no existe en la red.");
        }
        this.equipos.put(equipo.getCodigo(), equipo);
    }

    public void addUbicacion(Ubicacion ubicacion) {
        this.ubicaciones.put(ubicacion.getCodigo(), ubicacion);
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
