package services;

import data.implementations.DAOUbicacionImplFile;
import data.interfaces.DAOUbicacion;
import models.Ubicacion;

import java.util.Map;

public class UbicacionServiceImpl implements UbicacionService {
    private DAOUbicacion daoUbicacion;

    public UbicacionServiceImpl() {
        daoUbicacion = new DAOUbicacionImplFile();
    }

    @Override
    public void insert(Ubicacion ubicacion) {

    }

    @Override
    public void update(Ubicacion ubicacion) {

    }

    @Override
    public void delete(Ubicacion ubicacion) {

    }

    @Override
    public Map<String, Ubicacion> getAll() {
        return daoUbicacion.read();
    }
}
