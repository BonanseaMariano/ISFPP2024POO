package models;

import java.util.Objects;

/**
 * Represents a location (Ubicacion) in the network.
 */
public class Ubicacion {
    /**
     * The code of the location.
     */
    private String codigo;
    /**
     * The description of the location.
     */
    private String descripcion;

    /**
     * Constructs a Ubicacion with the specified details.
     *
     * @param codigo      the code of the location
     * @param descripcion the description of the location
     */
    public Ubicacion(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Gets the code of the location.
     *
     * @return the code of the location
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the code of the location.
     *
     * @param codigo the code of the location
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the description of the location.
     *
     * @return the description of the location
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the description of the location.
     *
     * @param descripcion the description of the location
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ubicacion ubicacion)) return false;
        return Objects.equals(codigo, ubicacion.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}