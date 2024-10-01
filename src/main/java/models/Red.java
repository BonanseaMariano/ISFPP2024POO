package models;

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

    public void addConexion(Conexion conexion) {
        this.conexiones.add(conexion);
    }

    public void addEquipo(Equipo equipo) {
        this.equipos.put(equipo.getCodigo(), equipo);
    }

    public void agregarUbicacion(Ubicacion ubicacion) {
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
