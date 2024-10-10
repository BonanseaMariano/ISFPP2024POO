package services;

import data.implementations.DAOTipoEquipoImplFile;
import data.interfaces.DAOTipoEquipo;
import factory.Factory;
import models.TipoEquipo;

import java.util.List;

public class TipoEquipoServiceImpl implements TipoEquipoService {
    private static DAOTipoEquipo daoTipoEquipo;

    public TipoEquipoServiceImpl() {
        daoTipoEquipo = (DAOTipoEquipo) Factory.getInstance("TIPO_EQUIPO");
    }

    @Override
    public void insert(TipoEquipo tipoEquipo) {
        daoTipoEquipo.create(tipoEquipo);
    }

    @Override
    public void update(TipoEquipo tipoEquipo) {
        daoTipoEquipo.update(tipoEquipo);
    }

    @Override
    public void delete(TipoEquipo tipoEquipo) {
        daoTipoEquipo.delete(tipoEquipo);
    }

    @Override
    public List<TipoEquipo> getAll() {
        return daoTipoEquipo.read();
    }
}
