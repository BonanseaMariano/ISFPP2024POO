package models;

import java.util.Objects;

/**
 * Represents a connection (conexion) between two devices (equipos) in the network.
 */
public class Conexion {
    /**
     * The type of cable used in the connection.
     */
    private TipoCable tipoCable;
    /**
     * The first device in the connection.
     */
    private Equipo equipo1;
    /**
     * The port of the first device.
     */
    private TipoPuerto puerto1;
    /**
     * The second device in the connection.
     */
    private Equipo equipo2;
    /**
     * The port of the second device.
     */
    private TipoPuerto puerto2;

    /**
     * Constructs a Conexion with the specified details.
     *
     * @param tipoCable the type of cable used in the connection
     * @param equipo1   the first device in the connection
     * @param puerto1   the port of the first device
     * @param equipo2   the second device in the connection
     * @param puerto2   the port of the second device
     */
    public Conexion(TipoCable tipoCable, Equipo equipo1, TipoPuerto puerto1, Equipo equipo2, TipoPuerto puerto2) {
        this.tipoCable = tipoCable;
        this.equipo1 = equipo1;
        this.puerto1 = puerto1;
        this.equipo2 = equipo2;
        this.puerto2 = puerto2;
    }

    /**
     * Default constructor for Conexion.
     */
    public Conexion() {
    }

    /**
     * Gets the type of cable used in the connection.
     *
     * @return the type of cable
     */
    public TipoCable getTipoCable() {
        return tipoCable;
    }

    /**
     * Sets the type of cable used in the connection.
     *
     * @param tipoCable the type of cable
     */
    public void setTipoCable(TipoCable tipoCable) {
        this.tipoCable = tipoCable;
    }

    /**
     * Gets the first device in the connection.
     *
     * @return the first device
     */
    public Equipo getEquipo1() {
        return equipo1;
    }

    /**
     * Sets the first device in the connection.
     *
     * @param equipo1 the first device
     */
    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    /**
     * Gets the port of the first device.
     *
     * @return the port of the first device
     */
    public TipoPuerto getPuerto1() {
        return puerto1;
    }

    /**
     * Sets the port of the first device.
     *
     * @param puerto1 the port of the first device
     */
    public void setPuerto1(TipoPuerto puerto1) {
        this.puerto1 = puerto1;
    }

    /**
     * Gets the second device in the connection.
     *
     * @return the second device
     */
    public Equipo getEquipo2() {
        return equipo2;
    }

    /**
     * Sets the second device in the connection.
     *
     * @param equipo2 the second device
     */
    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    /**
     * Gets the port of the second device.
     *
     * @return the port of the second device
     */
    public TipoPuerto getPuerto2() {
        return puerto2;
    }

    /**
     * Sets the port of the second device.
     *
     * @param puerto2 the port of the second device
     */
    public void setPuerto2(TipoPuerto puerto2) {
        this.puerto2 = puerto2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conexion conexion)) return false;
        return Objects.equals(equipo1, conexion.equipo1) && Objects.equals(equipo2, conexion.equipo2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoCable, equipo1, puerto1, equipo2, puerto2);
    }

    @Override
    public String toString() {
        return "Conexion{velocidad=" + tipoCable.getVelocidad() +
                ", equipo1=" + equipo1.getCodigo() +
                " velocidadPuerto=" + puerto1.getVelocidad() + " Mbps" +
                " -> equipo2=" + equipo2.getCodigo() +
                " velocidadPuerto=" + puerto2.getVelocidad() + " Mbps}";
    }
}