package services;

import data.interfaces.DAOEquipo;
import factory.Factory;
import models.Equipo;

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
     * Updates an existing Equipo entity.
     *
     * @param oldEquipo the existing Equipo entity to update
     * @param newEquipo the new Equipo entity with updated values
     */
    @Override
    public void update(Equipo oldEquipo, Equipo newEquipo) {
        daoEquipo.update(oldEquipo, newEquipo);
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
     * Retrieves all Equipo entities.
     *
     * @return a list of all Equipo entities
     */
    @Override
    public List<Equipo> getAll() {
        return daoEquipo.read();
    }
}