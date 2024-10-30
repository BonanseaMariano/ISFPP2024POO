package services;

import data.interfaces.DAOTipoCable;
import factory.Factory;
import models.TipoCable;

import java.util.List;

/**
 * Implementation of the TipoCableService interface.
 * Provides methods to manage TipoCable entities using DAOTipoCable.
 */
public class TipoCableServiceImpl implements TipoCableService {
    /**
     * DAO\TipoCable instance for managing TipoCable entities.
     */
    private final DAOTipoCable daoTipoCable;

    /**
     * Constructs a new TipoCableServiceImpl.
     * Initializes the DAOTipoCable instance using the Factory.
     */
    public TipoCableServiceImpl() {
        daoTipoCable = (DAOTipoCable) Factory.getInstance("TIPO_CABLE");
    }

    /**
     * Inserts a new TipoCable entity.
     *
     * @param tipoCable the TipoCable entity to insert
     */
    @Override
    public void insert(TipoCable tipoCable) {
        daoTipoCable.create(tipoCable);
    }

    /**
     * Updates an existing TipoCable entity.
     *
     * @param oldTipoCable the existing TipoCable entity to update
     * @param newTipoCable the new TipoCable entity with updated values
     */
    @Override
    public void update(TipoCable oldTipoCable, TipoCable newTipoCable) {
        daoTipoCable.update(oldTipoCable, newTipoCable);
    }

    /**
     * Deletes a TipoCable entity.
     *
     * @param tipoCable the TipoCable entity to delete
     */
    @Override
    public void delete(TipoCable tipoCable) {
        daoTipoCable.delete(tipoCable);
    }

    /**
     * Retrieves all TipoCable entities.
     *
     * @return a list of all TipoCable entities
     */
    @Override
    public List<TipoCable> getAll() {
        return daoTipoCable.read();
    }
}