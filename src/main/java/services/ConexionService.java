package services;

import models.Conexion;

import java.util.List;

public interface ConexionService {
    void insert(Conexion conexion);

    void update(Conexion conexion);

    void delete(Conexion conexion);

    List<Conexion> getAll();
}
