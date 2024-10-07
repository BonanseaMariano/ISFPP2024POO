package services;

import models.TipoEquipo;

import java.util.Map;

public interface TipoEquipoService {
    void insert(TipoEquipo tipoEquipo);

    void update(TipoEquipo tipoEquipo);

    void delete(TipoEquipo tipoEquipo);

    Map<String, TipoEquipo> getAll();
}
