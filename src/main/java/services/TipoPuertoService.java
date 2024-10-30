package services;

import models.TipoPuerto;

import java.util.List;

/**
 * Interface for managing TipoPuerto entities.
 */
public interface TipoPuertoService {

    /**
     * Inserts a new TipoPuerto entity.
     *
     * @param tipoPuerto the TipoPuerto entity to insert
     */
    void insert(TipoPuerto tipoPuerto);

    /**
     * Updates an existing TipoPuerto entity.
     *
     * @param oldTipoPuerto the existing TipoPuerto entity to update
     * @param newTipoPuerto the new TipoPuerto entity with updated values
     */
    void update(TipoPuerto oldTipoPuerto, TipoPuerto newTipoPuerto);

    /**
     * Deletes a TipoPuerto entity.
     *
     * @param tipoPuerto the TipoPuerto entity to delete
     */
    void delete(TipoPuerto tipoPuerto);

    /**
     * Retrieves all TipoPuerto entities.
     *
     * @return a list of all TipoPuerto entities
     */
    List<TipoPuerto> getAll();
}