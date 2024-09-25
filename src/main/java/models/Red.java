package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Red {
    private String nombre;
    private List<Conexion> conexiones;
    private List<Equipo> equipos;
    private List<Ubicacion> ubicaciones;

    public Red(String nombre) {
        this.nombre = nombre;
        this.conexiones = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.ubicaciones = new ArrayList<>();
    }

    public void agregarConexion(Conexion conexion) {
        this.conexiones.add(conexion);
    }

    public void agregarEquipo(Equipo equipo) {
        this.equipos.add(equipo);
    }

    public void agregarUbicacion(Ubicacion ubicacion) {
        this.ubicaciones.add(ubicacion);
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

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Ubicacion> getUbicaciones() {
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
