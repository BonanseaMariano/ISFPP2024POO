package services;

import models.TipoCable;

import java.util.List;

/**
 * Interface for managing TipoCable entities.
 */
public interface TipoCableService {

    /**
     * Inserts a new TipoCable entity.
     *
     * @param tipoCable the TipoCable entity to insert
     */
    void insert(TipoCable tipoCable);

    /**
     * Updates an existing TipoCable entity.
     *
     * @param tipoCable the TipoCable entity to update
     */
    void update(TipoCable tipoCable);

    /**
     * Deletes a TipoCable entity.
     *
     * @param tipoCable the TipoCable entity to delete
     */
    void delete(TipoCable tipoCable);

    /**
     * Retrieves all TipoCable entities.
     *
     * @return a list of all TipoCable entities
     */
    List<TipoCable> getAll();
}