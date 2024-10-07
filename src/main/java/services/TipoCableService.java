package services;

import models.TipoCable;

import java.util.Map;

public interface TipoCableService {
    void insert(TipoCable tipoCable);

    void update(TipoCable tipoCable);

    void delete(TipoCable tipoCable);

    Map<String, TipoCable> getAll();
}
