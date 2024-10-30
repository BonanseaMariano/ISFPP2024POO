package models;

import java.util.Objects;

/**
 * Represents a port (puerto) in the network.
 */
public class Puerto {
    /**
     * The number of ports.
     */
    private int cantidad;
    /**
     * The type of the port.
     */
    private TipoPuerto tipoPuerto;

    /**
     * Constructs a Puerto with the specified details.
     *
     * @param cantidad   the number of ports
     * @param tipoPuerto the type of the port
     */
    public Puerto(int cantidad, TipoPuerto tipoPuerto) {
        this.cantidad = cantidad;
        this.tipoPuerto = tipoPuerto;
    }

    /**
     * Gets the number of ports.
     *
     * @return the number of ports
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Sets the number of ports.
     *
     * @param cantidad the number of ports
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Gets the type of the port.
     *
     * @return the type of the port
     */
    public TipoPuerto getTipoPuerto() {
        return tipoPuerto;
    }

    /**
     * Sets the type of the port.
     *
     * @param tipoPuerto the type of the port
     */
    public void setTipoPuerto(TipoPuerto tipoPuerto) {
        this.tipoPuerto = tipoPuerto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Puerto puerto)) return false;
        return Objects.equals(tipoPuerto, puerto.tipoPuerto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tipoPuerto);
    }

    @Override
    public String toString() {
        return "Puerto{" +
                "cantidad=" + cantidad +
                ", tipoPuerto=" + tipoPuerto +
                '}';
    }
}