package data.interfaces;

import java.util.List;

/**
 * Generic CRUD (Create, Read, Update, Delete) interface.
 *
 * @param <T> the type of objects that this interface will handle
 */
public interface CRUD<T> {

    /**
     * Creates a new object.
     *
     * @param t the object to create
     */
    void create(T t);

    /**
     * Reads all objects.
     *
     * @return a list of all objects
     */
    List<T> read();

    /**
     * Updates an existing object.
     *
     * @param o the original object
     * @param n the new object
     */
    void update(T o, T n);

    /**
     * Deletes an object.
     *
     * @param t the object to delete
     */
    void delete(T t);
}