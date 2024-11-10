package observer;

import models.Equipo;

/**
 * The Subject interface defines the methods that classes
 * acting as subjects in the Observer pattern must implement.
 * It allows adding, removing, and notifying observers.
 */
public interface Subject {

    /**
     * Adds an observer to the list of observers.
     *
     * @param observer the observer to add
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers with the provided equipment.
     *
     * @param equipo the equipment passed to the observers
     */
    void notifyObservers(Equipo equipo);
}