package services;

import data.interfaces.DAOTipoEquipo;
import factory.Factory;
import models.TipoEquipo;

import java.util.List;

/**
 * Implementation of the TipoEquipoService interface.
 * Provides methods to manage TipoEquipo entities using DAOTipoEquipo.
 */
public class TipoEquipoServiceImpl implements TipoEquipoService {
    /**
     * DAO\TipoEquipo instance for managing TipoEquipo entities.
     */
    private static DAOTipoEquipo daoTipoEquipo;

    /**
     * Constructs a new TipoEquipoServiceImpl.
     * Initializes the DAOTipoEquipo instance using the Factory.
     */
    public TipoEquipoServiceImpl() {
        daoTipoEquipo = (DAOTipoEquipo) Factory.getInstance("TIPO_EQUIPO");
    }

    /**
     * Inserts a new TipoEquipo entity.
     *
     * @param tipoEquipo the TipoEquipo entity to insert
     */
    @Override
    public void insert(TipoEquipo tipoEquipo) {
        daoTipoEquipo.create(tipoEquipo);
    }

    /**
     * Updates an existing TipoEquipo entity.
     *
     * @param tipoEquipo the TipoEquipo entity to update
     */
    @Override
    public void update(TipoEquipo tipoEquipo) {
        daoTipoEquipo.update(tipoEquipo);
    }

    /**
     * Deletes a TipoEquipo entity.
     *
     * @param tipoEquipo the TipoEquipo entity to delete
     */
    @Override
    public void delete(TipoEquipo tipoEquipo) {
        daoTipoEquipo.delete(tipoEquipo);
    }

    /**
     * Retrieves all TipoEquipo entities.
     *
     * @return a list of all TipoEquipo entities
     */
    @Override
    public List<TipoEquipo> getAll() {
        return daoTipoEquipo.read();
    }
}