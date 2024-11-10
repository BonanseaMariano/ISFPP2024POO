package observer;

import models.Equipo;

/**
 * The Observer interface defines the method that classes
 * acting as observers in the Observer pattern must implement.
 */
public interface Observer {

    /**
     * Method called to update the observer with the provided equipment.
     *
     * @param equipo the equipment passed to the observer
     */
    void update(Equipo equipo);
}