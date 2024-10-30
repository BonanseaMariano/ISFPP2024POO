package exceptions;

/**
 * Exception thrown when an invalid connection (conexion) is encountered.
 */
public class InvalidConexionException extends RuntimeException {
    /**
     * Constructs a new InvalidConexionException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidConexionException(String message) {
        super(message);
    }
}