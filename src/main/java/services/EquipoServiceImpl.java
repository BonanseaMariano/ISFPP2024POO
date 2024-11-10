package services;

import data.interfaces.DAOEquipo;
import factory.Factory;
import models.Equipo;
import models.Puerto;

import java.util.List;

/**
 * Implementation of the EquipoService interface.
 * Provides methods to manage Equipo entities using DAOEquipo.
 */
public class EquipoServiceImpl implements EquipoService {
    /**
     * DAOEquipo instance for managing Equipo entities.
     */
    private final DAOEquipo daoEquipo;

    /**
     * Constructs a new EquipoServiceImpl.
     * Initializes the DAOEquipo instance using the Factory.
     */
    public EquipoServiceImpl() {
        daoEquipo = (DAOEquipo) Factory.getInstance("EQUIPO");
    }

    /**
     * Inserts a new Equipo entity.
     *
     * @param equipo the Equipo entity to insert
     */
    @Override
    public void insert(Equipo equipo) {
        daoEquipo.create(equipo);
    }

    /**
     * Inserts a new port for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the port will be added
     * @param puerto the Puerto entity representing the port to be added
     */
    @Override
    public void insertPort(Equipo equipo, Puerto puerto) {
        daoEquipo.createPort(equipo, puerto);
    }

    /**
     * Inserts a new IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity to which the IP address will be added
     * @param ip     the IP address to be added
     */
    @Override
    public void insertIp(Equipo equipo, String ip) {
        daoEquipo.createIp(equipo, ip);
    }


    /**
     * Updates an existing Equipo entity.
     *
     * @param equipo the Equipo entity to update
     */
    @Override
    public void update(Equipo equipo) {
        daoEquipo.update(equipo);
    }

    /**
     * Updates an existing port for the specified Equipo.
     *
     * @param equipo    the Equipo entity for which the port will be updated
     * @param oldPuerto the existing Puerto entity to be updated
     * @param newPuerto the new Puerto entity with updated values
     */
    @Override
    public void updatePort(Equipo equipo, Puerto oldPuerto, Puerto newPuerto) {
        daoEquipo.updatePort(equipo, oldPuerto, newPuerto);
    }

    /**
     * Updates an existing IP address for the specified Equipo.
     *
     * @param equipo the Equipo entity for which the IP address will be updated
     * @param oldIp  the existing IP address to be updated
     * @param newIp  the new IP address with updated values
     */
    @Override
    public void updateIp(Equipo equipo, String oldIp, String newIp) {
        daoEquipo.updateIp(equipo, oldIp, newIp);
    }

    /**
     * Deletes an Equipo entity.
     *
     * @param equipo the Equipo entity to delete
     */
    @Override
    public void delete(Equipo equipo) {
        daoEquipo.delete(equipo);
    }

    /**
     * Deletes a port from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the port will be deleted
     * @param puerto the Puerto entity representing the port to be deleted
     */
    @Override
    public void deletePort(Equipo equipo, Puerto puerto) {
        daoEquipo.deletePort(equipo, puerto);
    }

    /**
     * Deletes an IP address from the specified Equipo.
     *
     * @param equipo the Equipo entity from which the IP address will be deleted
     * @param ip     the IP address to be deleted
     */
    @Override
    public void deleteIp(Equipo equipo, String ip) {
        daoEquipo.deleteIp(equipo, ip);
    }

    /**
     * Retrieves all Equipo entities.
     *
     * @return a list of all Equipo entities
     */
    @Override
    public List<Equipo> getAll() {
        return daoEquipo.read();
    }
}