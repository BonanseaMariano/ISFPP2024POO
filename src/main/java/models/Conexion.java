package models;

import java.util.Objects;

public class Conexion {
    private TipoCable tipoCable;
    private Equipo equipo1;
    private Equipo equipo2;

    public Conexion(TipoCable tipoCable, Equipo equipo1, Equipo equipo2) {
        this.tipoCable = tipoCable;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

    public TipoCable getTipoCable() {
        return tipoCable;
    }

    public void setTipoCable(TipoCable tipoCable) {
        this.tipoCable = tipoCable;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conexion conexion)) return false;
        return Objects.equals(tipoCable, conexion.tipoCable) && Objects.equals(equipo1, conexion.equipo1) && Objects.equals(equipo2, conexion.equipo2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoCable, equipo1, equipo2);
    }

    @Override
    public String toString() {
        String message = "Conexion{" +
                "tipoCable=" + tipoCable.getCodigo() +
                ", velocidad=" + tipoCable.getVelocidad() +
                ", equipo1=" + equipo1.getDireccionesIp();
        for (Puerto puerto : equipo1.getPuertos()) {
            message += " puerto=" + puerto.getTipoPuerto().getCodigo() + " - velocidad=" + puerto.getTipoPuerto().getVelocidad();
        }
        message += ", equipo2=" + equipo2.getDireccionesIp();
        for (Puerto puerto : equipo2.getPuertos()) {
            message += " puerto=" + puerto.getTipoPuerto().getCodigo() + " - velocidad=" + puerto.getTipoPuerto().getVelocidad();
        }
        return message + '}';

    }


}
