package services;

import data.interfaces.DAOConexion;
import factory.Factory;
import models.Conexion;

import java.util.List;

/**
 * Implementation of the ConexionService interface.
 * Provides methods to manage Conexion entities using DAOConexion.
 */
public class ConexionServiceImpl implements ConexionService {
    /**
     * DAOConexion instance for managing Conexion entities.
     */
    private final DAOConexion daoConexion;

    /**
     * Constructs a new ConexionServiceImpl.
     * Initializes the DAOConexion instance using the Factory.
     */
    public ConexionServiceImpl() {
        daoConexion = (DAOConexion) Factory.getInstance("CONEXION");
    }

    /**
     * Inserts a new Conexion entity.
     *
     * @param conexion the Conexion entity to insert
     */
    @Override
    public void insert(Conexion conexion) {
        daoConexion.create(conexion);
    }

    /**
     * Updates an existing Conexion entity.
     *
     * @param conexion the Conexion entity to update
     */
    @Override
    public void update(Conexion conexion) {
        daoConexion.update(conexion);
    }

    /**
     * Deletes a Conexion entity.
     *
     * @param conexion the Conexion entity to delete
     */
    @Override
    public void delete(Conexion conexion) {
        daoConexion.delete(conexion);
    }

    /**
     * Retrieves all Conexion entities.
     *
     * @return a list of all Conexion entities
     */
    @Override
    public List<Conexion> getAll() {
        return daoConexion.read();
    }
}