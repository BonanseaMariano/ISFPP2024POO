package models;

import java.util.Objects;

public class TipoCable {
    private String codigo;
    private String descripcion;
    private int velocidad;

    public TipoCable(String codigo, String descripcion, int velocidad) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.velocidad = velocidad;
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

    public int getVelocidad() {
        return velocidad;
    }

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
