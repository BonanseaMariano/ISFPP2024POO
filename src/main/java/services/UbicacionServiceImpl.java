package services;

import data.interfaces.DAOUbicacion;
import factory.Factory;
import models.Ubicacion;

import java.util.List;

public class UbicacionServiceImpl implements UbicacionService {
    private final DAOUbicacion daoUbicacion;

    public UbicacionServiceImpl() {
        daoUbicacion = (DAOUbicacion) Factory.getInstance("UBICACION");
    }

    @Override
    public void insert(Ubicacion ubicacion) {
        daoUbicacion.create(ubicacion);
    }

    @Override
    public void update(Ubicacion oldUbicacion, Ubicacion newUbicacion) {
        daoUbicacion.update(oldUbicacion, newUbicacion);
    }

    @Override
    public void delete(Ubicacion ubicacion) {
        daoUbicacion.delete(ubicacion);
    }

    @Override
    public List<Ubicacion> getAll() {
        return daoUbicacion.read();
    }
}
