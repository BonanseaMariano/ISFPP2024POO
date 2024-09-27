package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargarParametros {

    private static String archivoComputadora;
    private static String archivoRouter;
    private static String archivoConexion;

    /**
     * Loads the properties from the "config.properties" file and sets the values
     * of the static variables `archivoComputadora`, `archivoRouter`, and
     * `archivoConexion` based on the corresponding property keys.
     *
     * @throws IOException if an I/O error occurs while reading the properties file
     */
    public static void parametros() throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        // load a properties file
        prop.load(input);
        // get the property value
        archivoComputadora = prop.getProperty("computadora");
        archivoRouter = prop.getProperty("router");
        archivoConexion = prop.getProperty("conexion");
    }

    public static String getArchivoComputadora() {
        return archivoComputadora;
    }

    public static String getArchivoRouter() {
        return archivoRouter;
    }

    public static String getArchivoConexion() {
        return archivoConexion;
    }

}
