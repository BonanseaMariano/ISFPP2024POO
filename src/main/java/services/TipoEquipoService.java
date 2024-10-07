package services;

import models.TipoEquipo;

import java.util.List;

public interface TipoEquipoService {
    void insert(TipoEquipo tipoEquipo);

    void update(TipoEquipo tipoEquipo);

    void delete(TipoEquipo tipoEquipo);

    List<TipoEquipo> getAll();
}
