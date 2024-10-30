package services;

import models.Conexion;

import java.util.List;

/**
 * Interface for managing Conexion entities.
 */
public interface ConexionService {

    /**
     * Inserts a new Conexion entity.
     *
     * @param conexion the Conexion entity to insert
     */
    void insert(Conexion conexion);

    /**
     * Updates an existing Conexion entity.
     *
     * @param oldConexion the existing Conexion entity to update
     * @param newConexion the new Conexion entity with updated values
     */
    void update(Conexion oldConexion, Conexion newConexion);

    /**
     * Deletes a Conexion entity.
     *
     * @param conexion the Conexion entity to delete
     */
    void delete(Conexion conexion);

    /**
     * Retrieves all Conexion entities.
     *
     * @return a list of all Conexion entities
     */
    List<Conexion> getAll();
}