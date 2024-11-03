package data.interfaces;

import models.Equipo;
import models.Puerto;


/**
 * DAOEquipo interface that extends the generic CRUD interface for Equipo objects.
 */
public interface DAOEquipo extends CRUD<Equipo> {

    /**
     * Creates a new IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the IP address will be added
     * @param IP     the IP address to be added
     */
    void createIp(Equipo equipo, String IP);

    /**
     * Deletes an IP address from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the IP address will be deleted
     * @param IP     the IP address to be deleted
     */
    void deleteIp(Equipo equipo, String IP);

    /**
     * Updates an existing IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity for which the IP address will be updated
     * @param oldIP  the existing IP address to be updated
     * @param newIP  the new IP address with updated values
     */
    void updateIp(Equipo equipo, String oldIP, String newIP);

    /**
     * Creates a new port for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the port will be added
     * @param puerto the Puerto entity representing the port to be added
     */
    void createPort(Equipo equipo, Puerto puerto);

    /**
     * Deletes a port from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the port will be deleted
     * @param puerto the Puerto entity representing the port to be deleted
     */
    void deletePort(Equipo equipo, Puerto puerto);

    /**
     * Updates an existing port for the specified Equipo.
     *
     * @param equipo  the Equipo entity for which the port will be updated
     * @param oldPort the existing Puerto entity to be updated
     * @param newPort the new Puerto entity with updated values
     */
    void updatePort(Equipo equipo, Puerto oldPort, Puerto newPort);
}