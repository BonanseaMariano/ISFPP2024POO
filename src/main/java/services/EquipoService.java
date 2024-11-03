package services;

import models.Equipo;
import models.Puerto;

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
     * Inserts a new port for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the port will be added
     * @param puerto the Puerto entity representing the port to be added
     */
    void insertPort(Equipo equipo, Puerto puerto);

    /**
     * Inserts a new IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the IP address will be added
     * @param ip     the IP address to be added
     */
    void insertIp(Equipo equipo, String ip);

    /**
     * Updates an existing Equipo entity.
     *
     * @param oldEquipo the existing Equipo entity to update
     * @param newEquipo the new Equipo entity with updated values
     */
    void update(Equipo oldEquipo, Equipo newEquipo);

    /**
     * Updates an existing port for the specified Equipo.
     *
     * @param equipo    the Equipo entity for which the port will be updated
     * @param oldPuerto the existing Puerto entity to be updated
     * @param newPuerto the new Puerto entity with updated values
     */
    void updatePort(Equipo equipo, Puerto oldPuerto, Puerto newPuerto);

    /**
     * Updates an existing IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity for which the IP address will be updated
     * @param oldIp  the existing IP address to be updated
     * @param newIp  the new IP address with updated values
     */
    void updateIp(Equipo equipo, String oldIp, String newIp);

    /**
     * Deletes an Equipo entity.
     *
     * @param equipo the Equipo entity to delete
     */
    void delete(Equipo equipo);

    /**
     * Deletes a port from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the port will be deleted
     * @param puerto the Puerto entity representing the port to be deleted
     */
    void deletePort(Equipo equipo, Puerto puerto);

    /**
     * Deletes an IP address from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the IP address will be deleted
     * @param ip     the IP address to be deleted
     */
    void deleteIp(Equipo equipo, String ip);

    /**
     * Retrieves all Equipo entities.
     *
     * @return a list of all Equipo entities
     */
    List<Equipo> getAll();
}