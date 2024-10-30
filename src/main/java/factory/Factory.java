package factory;

import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Factory class for creating and managing instances of objects.
 */
public class Factory {
    /**
     * A hashtable to store instances of objects.
     * The key is the name of the object, and the value is the instance of the object.
     */
    private static Hashtable<String, Object> instances = new Hashtable<>();

    /**
     * Retrieves an instance of the specified object.
     * If the instance does not exist, it is created and stored in the hashtable.
     *
     * @param objName the name of the object to retrieve
     * @return the instance of the specified object
     * @throws RuntimeException if there is an error creating the instance
     */
    public static Object getInstance(String objName) {
        try {
            Object obj = instances.get(objName);
            if (obj == null) {
                ResourceBundle rb = ResourceBundle.getBundle("factory");
                String sClassname = rb.getString(objName);
                obj = Class.forName(sClassname).newInstance();
                instances.put(objName, obj);
            }
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}