package services;

import data.implementations.DAOTipoCableImplFile;
import data.interfaces.DAOTipoCable;
import models.TipoCable;

import java.util.Map;

public class TipoCableServiceImpl implements TipoCableService {
    private DAOTipoCable daoTipoCable;

    public TipoCableServiceImpl() {
        daoTipoCable = new DAOTipoCableImplFile();
    }

    @Override
    public void insert(TipoCable tipoCable) {

    }

    @Override
    public void update(TipoCable tipoCable) {

    }

    @Override
    public void delete(TipoCable tipoCable) {

    }

    @Override
    public Map<String, TipoCable> getAll() {
        return daoTipoCable.read();
    }
}
