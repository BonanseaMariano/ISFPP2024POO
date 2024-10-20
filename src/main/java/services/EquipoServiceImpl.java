package services;

import data.interfaces.DAOEquipo;
import factory.Factory;
import models.Equipo;

import java.util.List;

public class EquipoServiceImpl implements EquipoService {
    private final DAOEquipo daoEquipo;

    public EquipoServiceImpl() {
        daoEquipo = (DAOEquipo) Factory.getInstance("EQUIPO");
    }

    @Override
    public void insert(Equipo equipo) {
        daoEquipo.create(equipo);
    }

    @Override
    public void update(Equipo oldEquipo, Equipo newEquipo) {
        daoEquipo.update(oldEquipo, newEquipo);
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
