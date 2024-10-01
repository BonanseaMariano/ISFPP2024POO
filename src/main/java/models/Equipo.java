package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipo {
    private String codigo;
    private String descripcion;
    private String marca;
    private String modelo;
    private TipoEquipo tipoEquipo;
    private Ubicacion ubicacion;
    private List<Puerto> puertos;
    private List<String> direccionesIp;

    public Equipo(String codigo, String descripcion, String marca, String modelo, TipoEquipo tipoEquipo, Ubicacion ubicacion, Puerto puerto, String direccionIP) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoEquipo = tipoEquipo;
        this.ubicacion = ubicacion;
        this.puertos = new ArrayList<>();
        agregarPuerto(puerto);
        this.direccionesIp = new ArrayList<>();
        agregarDireccionIp(direccionIP);
    }

    public void agregarPuerto(Puerto puerto) {
        puertos.add(puerto);
    }

    public void agregarDireccionIp(String direccionIp) {
        direccionesIp.add(direccionIp);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public List<String> getDireccionesIp() {
        return direccionesIp;
    }

    public List<Puerto> getPuertos() {
        return puertos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipo equipo)) return false;
        return Objects.equals(codigo, equipo.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", direccionesIp=" + direccionesIp +
                ", ubicacion=" + ubicacion +
                ", puertos=" + puertos +
                ", tipoEquipo=" + tipoEquipo +
                '}';
    }
}
