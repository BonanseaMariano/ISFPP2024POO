package services;

import data.implementations.DAOEquipoImplFile;
import data.interfaces.DAOEquipo;
import models.Equipo;

import java.util.Map;

public class EquipoServiceImpl implements EquipoService {
    private DAOEquipo daoEquipo;

    public EquipoServiceImpl() {
        daoEquipo = new DAOEquipoImplFile();
    }

    @Override
    public void insert(Equipo equipo) {
        daoEquipo.update(equipo);
    }

    @Override
    public void update(Equipo equipo) {

    }

    @Override
    public void delete(Equipo equipo) {

    }

    @Override
    public Map<String, Equipo> getAll() {
        return daoEquipo.read();
    }
}
