package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * DBCreator is a utility class for creating and initializing a SQLite database.
 * It provides methods to create the database if it does not exist and to execute SQL statements from a file.
 */
public class DBCreator {

    /**
     * The file path of the SQLite database.
     */
    private static final String DB_FILE_PATH = "src/main/resources/database/RedLan.db";

    /**
     * The file path of the SQL file containing DDL statements.
     */
    private static final String SQL_FILE_PATH = "src/main/resources/database/RedLan.sql";

    /**
     * The main method that serves as the entry point for the DBCreator utility.
     * It creates the database if it does not exist and executes the SQL file.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        createDatabaseIfNotExists(DB_FILE_PATH);
        executeSQLFile(DB_FILE_PATH, SQL_FILE_PATH);
    }

    /**
     * Creates a SQLite database with the specified file path if it does not already exist.
     *
     * @param dbFilePath the file path of the database to create
     */
    public static void createDatabaseIfNotExists(String dbFilePath) {
        Connection con = null;
        try {
            // Connect to the SQLite database (it will be created if it does not exist)
            con = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
            System.out.println("Database " + dbFilePath + " created or already exists.");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error creating database", ex);
        } finally {
            try {
                // Close the connection
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Executes all DDL statements from the specified SQL file.
     *
     * @param dbFilePath the file path of the database to connect to
     * @param filePath   the path to the SQL file
     */
    public static void executeSQLFile(String dbFilePath, String filePath) {
        Connection con = null;
        Statement stmt = null;
        try {
            // Connect to the SQLite database
            con = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
            // Create a statement to execute SQL queries
            stmt = con.createStatement();
            // Read the SQL file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line);
            }
            reader.close();
            // Split the SQL file content into individual statements
            String[] sqlStatements = sqlBuilder.toString().split(";");
            // Execute each SQL statement
            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty()) {
                    stmt.executeUpdate(sql);
                }
            }
            System.out.println("SQL file executed successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error executing SQL file", ex);
        } finally {
            try {
                // Close the statement
                if (stmt != null) stmt.close();
                // Close the connection
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}