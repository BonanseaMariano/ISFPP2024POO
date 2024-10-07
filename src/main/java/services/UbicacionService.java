package services;

import models.Ubicacion;

import java.util.List;

public interface UbicacionService {
    void insert(Ubicacion ubicacion);

    void update(Ubicacion ubicacion);

    void delete(Ubicacion ubicacion);

    List<Ubicacion> getAll();
}
