package factory;

import java.util.Hashtable;
import java.util.ResourceBundle;

public class Factory {
    private static Hashtable<String, Object> instances = new Hashtable<String, Object>();

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
