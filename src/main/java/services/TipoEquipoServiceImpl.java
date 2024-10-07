package services;

import data.implementations.DAOTipoEquipoImplFile;
import data.interfaces.DAOTipoEquipo;
import models.TipoEquipo;

import java.util.Map;

public class TipoEquipoServiceImpl implements TipoEquipoService {
    private static DAOTipoEquipo daoTipoEquipo;

    public TipoEquipoServiceImpl() {
        daoTipoEquipo = new DAOTipoEquipoImplFile();
    }

    @Override
    public void insert(TipoEquipo tipoEquipo) {

    }

    @Override
    public void update(TipoEquipo tipoEquipo) {

    }

    @Override
    public void delete(TipoEquipo tipoEquipo) {

    }

    @Override
    public Map<String, TipoEquipo> getAll() {
        return daoTipoEquipo.read();
    }
}
