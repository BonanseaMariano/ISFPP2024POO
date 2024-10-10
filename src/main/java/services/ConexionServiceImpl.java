package services;

import data.interfaces.DAOConexion;
import factory.Factory;
import models.Conexion;

import java.util.List;

public class ConexionServiceImpl implements ConexionService {
    private final DAOConexion daoConexion;

    public ConexionServiceImpl() {
        daoConexion = (DAOConexion) Factory.getInstance("CONEXION");
    }

    @Override
    public void insert(Conexion conexion) {
        daoConexion.create(conexion);
    }

    @Override
    public void update(Conexion conexion) {
        daoConexion.update(conexion);
    }

    @Override
    public void delete(Conexion conexion) {
        daoConexion.delete(conexion);
    }

    @Override
    public List<Conexion> getAll() {
        return daoConexion.read();
    }
}
