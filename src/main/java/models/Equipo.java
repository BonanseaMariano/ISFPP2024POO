package models;

import utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a device (equipo) in the network.
 */
public class Equipo {
    /**
     * The code of the device.
     */
    private String codigo;
    /**
     * The description of the device.
     */
    private String descripcion;
    /**
     * The brand of the device.
     */
    private String marca;
    /**
     * The model of the device.
     */
    private String modelo;
    /**
     * The type of the device.
     */
    private TipoEquipo tipoEquipo;
    /**
     * The location of the device.
     */
    private Ubicacion ubicacion;
    /**
     * The list of ports of the device.
     */
    private List<Puerto> puertos;
    /**
     * The list of IP addresses of the device.
     */
    private List<String> direccionesIp;
    /**
     * The state of the device.
     */
    private boolean estado;

    /**
     * Constructs an Equipo with the specified details.
     *
     * @param codigo      the code of the device
     * @param descripcion the description of the device
     * @param marca       the brand of the device
     * @param modelo      the model of the device
     * @param tipoEquipo  the type of the device
     * @param ubicacion   the location of the device
     * @param puerto      the port of the device
     * @param direccionIP the IP address of the device
     * @param estado      the state of the device
     */
    public Equipo(String codigo, String descripcion, String marca, String modelo, TipoEquipo tipoEquipo, Ubicacion ubicacion, Puerto puerto, String direccionIP, Boolean estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoEquipo = tipoEquipo;
        this.ubicacion = ubicacion;
        this.puertos = new ArrayList<>();
        if(puerto != null) {
            addPuerto(puerto);
        }
        this.direccionesIp = new ArrayList<>();
        if(direccionIP != null) {
            addIP(direccionIP);
        }
        this.estado = estado;
    }

    /**
     * Default constructor for Equipo.
     */
    public Equipo() {
        this.puertos = new ArrayList<>();
        this.direccionesIp = new ArrayList<>();
    }

    /**
     * Adds a port to the device.
     *
     * @param puerto the port to add
     */
    public void addPuerto(Puerto puerto) {
        puertos.add(puerto);
    }

    /**
     * Removes a port from the device.
     *
     * @param puerto the port to remove
     */
    public void removePuerto(Puerto puerto) {
        puertos.remove(puerto);
    }

    /**
     * Adds an IP address to the device.
     *
     * @param direccionIp the IP address to add
     */
    public void addIP(String direccionIp) {
        direccionesIp.add(direccionIp);
    }

    /**
     * Removes an IP address from the device.
     *
     * @param direccionIp the IP address to remove
     */
    public void removeIP(String direccionIp) {
        direccionesIp.remove(direccionIp);
    }

    /**
     * Gets the total number of ports in the device.
     *
     * @return the total number of ports
     */
    public int getCantidadPuertos() {
        int cantPuertos = 0;
        for (Puerto p : puertos) {
            cantPuertos += p.getCantidad();
        }
        return cantPuertos;
    }

    /**
     * Gets the code of the device.
     *
     * @return the code of the device
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the code of the device.
     *
     * @param codigo the code of the device
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the description of the device.
     *
     * @return the description of the device
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the description of the device.
     *
     * @param descripcion the description of the device
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the brand of the device.
     *
     * @return the brand of the device
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Sets the brand of the device.
     *
     * @param marca the brand of the device
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Gets the model of the device.
     *
     * @return the model of the device
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Sets the model of the device.
     *
     * @param modelo the model of the device
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Gets the location of the device.
     *
     * @return the location of the device
     */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    /**
     * Sets the location of the device.
     *
     * @param ubicacion the location of the device
     */
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Gets the type of the device.
     *
     * @return the type of the device
     */
    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    /**
     * Sets the type of the device.
     *
     * @param tipoEquipo the type of the device
     */
    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    /**
     * Checks if the device is active.
     *
     * @return true if the device is active, false otherwise
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Sets the state of the device.
     *
     * @param estado the state of the device
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Gets the list of IP addresses of the device.
     *
     * @return the list of IP addresses
     */
    public List<String> getDireccionesIp() {
        return direccionesIp;
    }

    /**
     * Gets the list of ports of the device.
     *
     * @return the list of ports
     */
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