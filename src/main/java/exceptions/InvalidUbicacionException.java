package exceptions;

/**
 * Exception thrown when an invalid location (ubicacion) is encountered.
 */
public class InvalidUbicacionException extends RuntimeException {
    /**
     * Constructs a new InvalidUbicacionException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidUbicacionException(String message) {
        super(message);
    }
}