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
     * @param ubicacion the Ubicacion entity to update
     */
    void update(Ubicacion ubicacion);

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