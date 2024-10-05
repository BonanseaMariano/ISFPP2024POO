package data.interfaces;

import models.Ubicacion;

public interface DAOUbicacion extends CRUD<Ubicacion> {
    void agregarUbicacion(Ubicacion ubicacion);
}
