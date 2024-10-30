package services;

import models.Ubicacion;

import java.util.List;

/**
 * Interface for managing Ubicacion entities.
 */
public interface UbicacionService {

    /**
     * Inserts a new Ubicacion entity.
     *
     * @param ubicacion the Ubicacion entity to insert
     */
    void insert(Ubicacion ubicacion);

    /**
     * Updates an existing Ubicacion entity.
     *
     * @param oldUbicacion the existing Ubicacion entity to update
     * @param newUbicacion the new Ubicacion entity with updated values
     */
    void update(Ubicacion oldUbicacion, Ubicacion newUbicacion);

    /**
     * Deletes a Ubicacion entity.
     *
     * @param ubicacion the Ubicacion entity to delete
     */
    void delete(Ubicacion ubicacion);

    /**
     * Retrieves all Ubicacion entities.
     *
     * @return a list of all Ubicacion entities
     */
    List<Ubicacion> getAll();
}