package services;

import data.implementations.DAOTipoCableImplFile;
import data.interfaces.DAOTipoCable;
import factory.Factory;
import models.TipoCable;

import java.util.List;

public class TipoCableServiceImpl implements TipoCableService {
    private final DAOTipoCable daoTipoCable;

    public TipoCableServiceImpl() {
        daoTipoCable = (DAOTipoCable) Factory.getInstance("TIPO_CABLE");
    }

    @Override
    public void insert(TipoCable tipoCable) {
        daoTipoCable.create(tipoCable);
    }

    @Override
    public void update(TipoCable tipoCable) {
        daoTipoCable.update(tipoCable);
    }

    @Override
    public void delete(TipoCable tipoCable) {
        daoTipoCable.delete(tipoCable);
    }

    @Override
    public List<TipoCable> getAll() {
        return daoTipoCable.read();
    }
}
