package models;

import java.util.Objects;

/**
 * Represents a type of device (TipoEquipo) in the network.
 */
public class TipoEquipo {
    /**
     * The code of the device type.
     */
    private String codigo;
    /**
     * The description of the device type.
     */
    private String descripcion;

    /**
     * Constructs a TipoEquipo with the specified details.
     *
     * @param codigo      the code of the device type
     * @param descripcion the description of the device type
     */
    public TipoEquipo(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Gets the code of the device type.
     *
     * @return the code of the device type
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the code of the device type.
     *
     * @param codigo the code of the device type
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the description of the device type.
     *
     * @return the description of the device type
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the description of the device type.
     *
     * @param descripcion the description of the device type
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoEquipo that)) return false;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "TipoEquipo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}