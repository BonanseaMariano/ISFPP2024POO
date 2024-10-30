package services;

import data.interfaces.DAOUbicacion;
import factory.Factory;
import models.Ubicacion;

import java.util.List;

/**
 * Implementation of the UbicacionService interface.
 * Provides methods to manage Ubicacion entities using DAOUbicacion.
 */
public class UbicacionServiceImpl implements UbicacionService {
    /**
     * DAO\Ubicacion instance for managing Ubicacion entities.
     */
    private final DAOUbicacion daoUbicacion;

    /**
     * Constructs a new UbicacionServiceImpl.
     * Initializes the DAOUbicacion instance using the Factory.
     */
    public UbicacionServiceImpl() {
        daoUbicacion = (DAOUbicacion) Factory.getInstance("UBICACION");
    }

    /**
     * Inserts a new Ubicacion entity.
     *
     * @param ubicacion the Ubicacion entity to insert
     */
    @Override
    public void insert(Ubicacion ubicacion) {
        daoUbicacion.create(ubicacion);
    }

    /**
     * Updates an existing Ubicacion entity.
     *
     * @param oldUbicacion the existing Ubicacion entity to update
     * @param newUbicacion the new Ubicacion entity with updated values
     */
    @Override
    public void update(Ubicacion oldUbicacion, Ubicacion newUbicacion) {
        daoUbicacion.update(oldUbicacion, newUbicacion);
    }

    /**
     * Deletes a Ubicacion entity.
     *
     * @param ubicacion the Ubicacion entity to delete
     */
    @Override
    public void delete(Ubicacion ubicacion) {
        daoUbicacion.delete(ubicacion);
    }

    /**
     * Retrieves all Ubicacion entities.
     *
     * @return a list of all Ubicacion entities
     */
    @Override
    public List<Ubicacion> getAll() {
        return daoUbicacion.read();
    }
}