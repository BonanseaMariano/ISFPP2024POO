package services;

import data.implementations.DAOTipoPuertoImplFile;
import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.util.List;

public class TipoPuertoServiceImpl implements TipoPuertoService {
    private final DAOTipoPuerto daoTipoPuerto;

    public TipoPuertoServiceImpl() {
        daoTipoPuerto = new DAOTipoPuertoImplFile();
    }

    @Override
    public void insert(TipoPuerto tipoPuerto) {
        daoTipoPuerto.create(tipoPuerto);
    }

    @Override
    public void update(TipoPuerto tipoPuerto) {
        daoTipoPuerto.update(tipoPuerto);
    }

    @Override
    public void delete(TipoPuerto tipoPuerto) {
        daoTipoPuerto.delete(tipoPuerto);
    }

    @Override
    public List<TipoPuerto> getAll() {
        return daoTipoPuerto.read();
    }
}
