package services;

import models.TipoEquipo;

import java.util.List;

/**
 * Interface for managing TipoEquipo entities.
 */
public interface TipoEquipoService {

    /**
     * Inserts a new TipoEquipo entity.
     *
     * @param tipoEquipo the TipoEquipo entity to insert
     */
    void insert(TipoEquipo tipoEquipo);

    /**
     * Updates an existing TipoEquipo entity.
     *
     * @param oldTipoEquipo the existing TipoEquipo entity to update
     * @param newTipoEquipo the new TipoEquipo entity with updated values
     */
    void update(TipoEquipo oldTipoEquipo, TipoEquipo newTipoEquipo);

    /**
     * Deletes a TipoEquipo entity.
     *
     * @param tipoEquipo the TipoEquipo entity to delete
     */
    void delete(TipoEquipo tipoEquipo);

    /**
     * Retrieves all TipoEquipo entities.
     *
     * @return a list of all TipoEquipo entities
     */
    List<TipoEquipo> getAll();
}