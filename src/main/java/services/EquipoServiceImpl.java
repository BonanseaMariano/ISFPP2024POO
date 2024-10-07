package services;

import data.implementations.DAOEquipoImplFile;
import data.interfaces.DAOEquipo;
import models.Equipo;

import java.util.List;

public class EquipoServiceImpl implements EquipoService {
    private DAOEquipo daoEquipo;

    public EquipoServiceImpl() {
        daoEquipo = new DAOEquipoImplFile();
    }

    @Override
    public void insert(Equipo equipo) {
        daoEquipo.create(equipo);
    }

    @Override
    public void update(Equipo equipo) {
        daoEquipo.update(equipo);
    }

    @Override
    public void delete(Equipo equipo) {
        daoEquipo.delete(equipo);
    }

    @Override
    public List<Equipo> getAll() {
        return daoEquipo.read();
    }
}
