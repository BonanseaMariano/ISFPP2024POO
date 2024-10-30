package models;

import java.util.Objects;

/**
 * Represents a type of cable (TipoCable) used in the network.
 */
public class TipoCable {
    /**
     * The code of the cable type.
     */
    private String codigo;
    /**
     * The description of the cable type.
     */
    private String descripcion;
    /**
     * The speed of the cable type in Mbps.
     */
    private int velocidad;

    /**
     * Constructs a TipoCable with the specified details.
     *
     * @param codigo      the code of the cable type
     * @param descripcion the description of the cable type
     * @param velocidad   the speed of the cable type in Mbps
     */
    public TipoCable(String codigo, String descripcion, int velocidad) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.velocidad = velocidad;
    }

    /**
     * Gets the code of the cable type.
     *
     * @return the code of the cable type
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the code of the cable type.
     *
     * @param codigo the code of the cable type
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the description of the cable type.
     *
     * @return the description of the cable type
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the description of the cable type.
     *
     * @param descripcion the description of the cable type
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the speed of the cable type in Mbps.
     *
     * @return the speed of the cable type in Mbps
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Sets the speed of the cable type in Mbps.
     *
     * @param velocidad the speed of the cable type in Mbps
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCable tipoCable)) return false;
        return Objects.equals(codigo, tipoCable.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "TipoCable{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", velocidad=" + velocidad +
                '}';
    }
}