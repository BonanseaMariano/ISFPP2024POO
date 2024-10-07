package services;

import data.implementations.DAOConexionImplFile;
import data.interfaces.DAOConexion;
import models.Conexion;

import java.util.List;

public class ConexionServiceImpl implements ConexionService {
    private DAOConexion daoConexion;

    public ConexionServiceImpl() {
        daoConexion = new DAOConexionImplFile();
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