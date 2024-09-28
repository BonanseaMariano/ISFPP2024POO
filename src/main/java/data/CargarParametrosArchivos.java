package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargarParametrosArchivos {

    private static String archivoTiposEquipos;
    private static String archivoTiposCables;
    private static String archivoTiposPuertos;
    public static String archivoUbicaciones;
    public static String archivoEquipos;
    public static String archivoConexiones;

    /**
     * Loads the properties from the "config.properties" file and sets the values
     * of the static variables `archivoTiposEquipos`, `archivoTiposCables`, and
     * `archivoTiposPuertos` based on the corresponding property keys.
     *
     * @throws IOException if an I/O error occurs while reading the properties file
     */
    public static void parametros() {
        Properties prop = new Properties();

        InputStream input = null;
        try {
            input = new FileInputStream("src/main/java/config/config.properties");
            prop.load(input);
        } catch (IOException e) {
            System.err.println("Error al cargar archivo de confguracion");
            throw new RuntimeException(e);
        }

        archivoTiposEquipos = prop.getProperty("tiposEquipos");
        archivoTiposCables = prop.getProperty("tiposCables");
        archivoTiposPuertos = prop.getProperty("tiposPuertos");
        archivoUbicaciones = prop.getProperty("ubicaciones");
        archivoEquipos = prop.getProperty("equipos");
        archivoConexiones = prop.getProperty("conexiones");

    }

    public static String getArchivoTiposEquipos() {
        return archivoTiposEquipos;
    }

    public static String getArchivoTiposCables() {
        return archivoTiposCables;
    }

    public static String getArchivoTiposPuertos() {
        return archivoTiposPuertos;
    }

    public static String getArchivoUbicaciones() {
        return archivoUbicaciones;
    }

    public static String getArchivoEquipos() {
        return archivoEquipos;
    }

    public static String getArchivoConexiones() {
        return archivoConexiones;
    }
}
