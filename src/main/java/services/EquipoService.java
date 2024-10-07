package services;

import models.Equipo;

import java.util.Map;

public interface EquipoService {
    void insert(Equipo equipo);

    void update(Equipo equipo);

    void delete(Equipo equipo);

    Map<String, Equipo> getAll();
}
