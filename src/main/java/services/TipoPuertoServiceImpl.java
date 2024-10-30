package services;

import data.interfaces.DAOTipoPuerto;
import factory.Factory;
import models.TipoPuerto;

import java.util.List;

/**
 * Implementation of the TipoPuertoService interface.
 * Provides methods to manage TipoPuerto entities using DAOTipoPuerto.
 */
public class TipoPuertoServiceImpl implements TipoPuertoService {
    /**
     * DAO\TipoPuerto instance for managing TipoPuerto entities.
     */
    private final DAOTipoPuerto daoTipoPuerto;

    /**
     * Constructs a new TipoPuertoServiceImpl.
     * Initializes the DAOTipoPuerto instance using the Factory.
     */
    public TipoPuertoServiceImpl() {
        daoTipoPuerto = (DAOTipoPuerto) Factory.getInstance("TIPO_PUERTO");
    }

    /**
     * Inserts a new TipoPuerto entity.
     *
     * @param tipoPuerto the TipoPuerto entity to insert
     */
    @Override
    public void insert(TipoPuerto tipoPuerto) {
        daoTipoPuerto.create(tipoPuerto);
    }

    /**
     * Updates an existing TipoPuerto entity.
     *
     * @param oldTipoPuerto the existing TipoPuerto entity to update
     * @param newTipoPuerto the new TipoPuerto entity with updated values
     */
    @Override
    public void update(TipoPuerto oldTipoPuerto, TipoPuerto newTipoPuerto) {
        daoTipoPuerto.update(oldTipoPuerto, newTipoPuerto);
    }

    /**
     * Deletes a TipoPuerto entity.
     *
     * @param tipoPuerto the TipoPuerto entity to delete
     */
    @Override
    public void delete(TipoPuerto tipoPuerto) {
        daoTipoPuerto.delete(tipoPuerto);
    }

    /**
     * Retrieves all TipoPuerto entities.
     *
     * @return a list of all TipoPuerto entities
     */
    @Override
    public List<TipoPuerto> getAll() {
        return daoTipoPuerto.read();
    }
}