package models;

import exceptions.InvalidDireccionIPException;
import utils.Utils;

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
    private boolean estado;

    public Equipo(String codigo, String descripcion, String marca, String modelo, TipoEquipo tipoEquipo, Ubicacion ubicacion, Puerto puerto, String direccionIP, Boolean estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoEquipo = tipoEquipo;
        this.ubicacion = ubicacion;
        this.puertos = new ArrayList<>();
        addPuerto(puerto);
        this.direccionesIp = new ArrayList<>();
        addIP(direccionIP);
        this.estado = estado;
    }

    public Equipo() {
        this.puertos = new ArrayList<>();
        this.direccionesIp = new ArrayList<>();
    }

    public void addPuerto(Puerto puerto) {
        puertos.add(puerto);
    }

    public void removePuerto(Puerto puerto) {
        puertos.remove(puerto);
    }

    public void addIP(String direccionIp) throws InvalidDireccionIPException {
        if (!direccionIp.isBlank() && !Utils.validateIP(direccionIp)) {
            throw new InvalidDireccionIPException("No se puede agregar la dirección IP porque no es válida.");
        }
        direccionesIp.add(direccionIp);
    }

    public void removeIP(String direccionIp) {
        direccionesIp.remove(direccionIp);
    }

    public int getCantidadPuertos() {
        int cantPuertos = 0;
        for (Puerto p : puertos) {
            cantPuertos += p.getCantidad();
        }
        return cantPuertos;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<String> getDireccionesIp() {
        return direccionesIp;
    }

    public void setDireccionesIp(List<String> direccionesIp) {
        this.direccionesIp = direccionesIp;
    }

    public List<Puerto> getPuertos() {
        return puertos;
    }

    public void setPuertos(List<Puerto> puertos) {
        this.puertos = puertos;
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
