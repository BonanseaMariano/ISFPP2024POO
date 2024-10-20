package services;

import models.TipoPuerto;

import java.util.List;

public interface TipoPuertoService {
    void insert(TipoPuerto tipoPuerto);

    void update(TipoPuerto oldTipoPuerto, TipoPuerto newTipoPuerto);

    void delete(TipoPuerto tipoPuerto);

    List<TipoPuerto> getAll();
}
