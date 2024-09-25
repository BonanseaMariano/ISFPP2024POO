package models;

import java.util.Objects;

public class Puerto {
    private int cantidad;
    private TipoPuerto tipoPuerto;

    public Puerto(int cantidad, TipoPuerto tipoPuerto) {
        this.cantidad = cantidad;
        this.tipoPuerto = tipoPuerto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public TipoPuerto getTipoPuerto() {
        return tipoPuerto;
    }

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
