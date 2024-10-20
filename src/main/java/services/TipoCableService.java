package services;

import models.TipoCable;

import java.util.List;

public interface TipoCableService {
    void insert(TipoCable tipoCable);

    void update(TipoCable oldTipoCable, TipoCable newTipoCable);

    void delete(TipoCable tipoCable);

    List<TipoCable> getAll();
}
