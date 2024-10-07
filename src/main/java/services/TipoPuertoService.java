package services;

import models.TipoPuerto;

import java.util.Map;

public interface TipoPuertoService {
    void insert(TipoPuerto tipoPuerto);

    void update(TipoPuerto tipoPuerto);

    void delete(TipoPuerto tipoPuerto);

    Map<String, TipoPuerto> getAll();
}
