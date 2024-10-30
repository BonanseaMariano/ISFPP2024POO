package models;

import java.util.Objects;

/**
 * Represents a type of port (TipoPuerto) in the network.
 */
public class TipoPuerto {
    /**
     * The code of the port type.
     */
    private String codigo;
    /**
     * The description of the port type.
     */
    private String descripcion;
    /**
     * The speed of the port type in Mbps.
     */
    private int velocidad;
    /**
     * Constructs a TipoPuerto with the specified details.
     *
     * @param codigo      the code of the port type
     * @param descripcion the description of the port type
     * @param velocidad   the speed of the port type in Mbps
     */
    public TipoPuerto(String codigo, String descripcion, int velocidad) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.velocidad = velocidad;
    }

    /**
     * Gets the code of the port type.
     *
     * @return the code of the port type
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the code of the port type.
     *
     * @param codigo the code of the port type
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the description of the port type.
     *
     * @return the description of the port type
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the description of the port type.
     *
     * @param descripcion the description of the port type
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the speed of the port type in Mbps.
     *
     * @return the speed of the port type in Mbps
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Sets the speed of the port type in Mbps.
     *
     * @param velocidad the speed of the port type in Mbps
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoPuerto that)) return false;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "TipoPuerto{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", velocidad=" + velocidad +
                '}';
    }
}