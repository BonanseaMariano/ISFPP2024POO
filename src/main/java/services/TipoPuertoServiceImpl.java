package services;

import data.implementations.DAOTipoPuertoImplFile;
import data.interfaces.DAOTipoPuerto;
import models.TipoPuerto;

import java.util.Map;

public class TipoPuertoServiceImpl implements TipoPuertoService {
    private DAOTipoPuerto daoTipoPuerto;

    public TipoPuertoServiceImpl() {
        daoTipoPuerto = new DAOTipoPuertoImplFile();
    }

    @Override
    public void insert(TipoPuerto tipoPuerto) {

    }

    @Override
    public void update(TipoPuerto tipoPuerto) {

    }

    @Override
    public void delete(TipoPuerto tipoPuerto) {

    }

    @Override
    public Map<String, TipoPuerto> getAll() {
        return daoTipoPuerto.read();
    }
}
