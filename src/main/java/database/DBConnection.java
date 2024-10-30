package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * This class provides methods to establish and manage a connection to the database.
 * It reads the connection details from the `jdbc.properties` file and ensures the connection
 * is properly closed when the JVM shuts down.
 */
public class DBConnection {
    /**
     * The established database connection.
     */
    private static Connection con = null;

    /**
     * Establishes a connection to the database using details from the `jdbc.properties` file.
     * If the connection is not already established, it initializes the connection and sets up a shutdown hook
     * to close the connection when the JVM shuts down.
     *
     * @return the established database connection
     * @throws RuntimeException if there is an error creating the connection
     */
    public static Connection getConnection() {
        try {
            if (con == null) {
                // Add a shutdown hook to close the connection when the program ends
                Runtime.getRuntime().addShutdownHook(new MiShDwnHook());
                ResourceBundle rb = ResourceBundle.getBundle("jdbc");
                String driver = rb.getString("driver");
                String url = rb.getString("url");
                String usr = rb.getString("usr");
                String pwd = rb.getString("pwd");
                Class.forName(driver);
                con = DriverManager.getConnection(url, usr, pwd);
            }
            return con;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error al crear la conexion", ex);
        }
    }

    /**
     * This class defines a shutdown hook that ensures the database connection is closed
     * just before the program terminates.
     */
    public static class MiShDwnHook extends Thread {
        /**
         * This method is invoked by the JVM just before the program ends, allowing us to close the connection.
         */
        public void run() {
            try {
                Connection con = DBConnection.getConnection();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
