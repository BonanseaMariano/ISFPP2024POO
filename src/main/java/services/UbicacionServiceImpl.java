package services;

import data.implementations.DAOUbicacionImplFile;
import data.interfaces.DAOUbicacion;
import models.Ubicacion;

import java.util.List;

public class UbicacionServiceImpl implements UbicacionService {
    private DAOUbicacion daoUbicacion;

    public UbicacionServiceImpl() {
        daoUbicacion = new DAOUbicacionImplFile();
    }

    @Override
    public void insert(Ubicacion ubicacion) {
        daoUbicacion.create(ubicacion);
    }

    @Override
    public void update(Ubicacion ubicacion) {
        daoUbicacion.update(ubicacion);
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
