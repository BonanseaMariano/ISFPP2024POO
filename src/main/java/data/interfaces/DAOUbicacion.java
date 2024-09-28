package data.interfaces;

import models.Ubicacion;

public interface DAOUbicacion extends cargaMapa<Ubicacion> {
    void agregarUbicacion(Ubicacion ubicacion);
}
