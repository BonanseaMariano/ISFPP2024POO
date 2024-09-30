package data.interfaces;

import models.Ubicacion;

public interface DAOUbicacion extends CargaMapa<Ubicacion> {
    void agregarUbicacion(Ubicacion ubicacion);
}
