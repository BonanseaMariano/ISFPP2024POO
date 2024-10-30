package services;

import models.Equipo;

import java.util.List;

/**
 * Interface for managing Equipo entities.
 */
public interface EquipoService {

    /**
     * Inserts a new Equipo entity.
     *
     * @param equipo the Equipo entity to insert
     */
    void insert(Equipo equipo);

    /**
     * Updates an existing Equipo entity.
     *
     * @param oldEquipo the existing Equipo entity to update
     * @param newEquipo the new Equipo entity with updated values
     */
    void update(Equipo oldEquipo, Equipo newEquipo);

    /**
     * Deletes an Equipo entity.
     *
     * @param equipo the Equipo entity to delete
     */
    void delete(Equipo equipo);

    /**
     * Retrieves all Equipo entities.
     *
     * @return a list of all Equipo entities
     */
    List<Equipo> getAll();
}