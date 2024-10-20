package services;

import models.Equipo;

import java.util.List;

public interface EquipoService {
    void insert(Equipo equipo);

    void update(Equipo oldEquipo, Equipo newEquipo);

    void delete(Equipo equipo);

   List<Equipo> getAll();
}
