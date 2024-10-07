package services;

import models.Ubicacion;

import java.util.Map;

public interface UbicacionService {
    void insert(Ubicacion ubicacion);

    void update(Ubicacion ubicacion);

    void delete(Ubicacion ubicacion);

    Map<String, Ubicacion> getAll();
}
