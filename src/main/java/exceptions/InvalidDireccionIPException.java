package exceptions;

/**
 * Exception thrown when an invalid IP address (direccion IP) is encountered.
 */
public class InvalidDireccionIPException extends RuntimeException {
    /**
     * Constructs a new InvalidDireccionIPException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidDireccionIPException(String message) {
        super(message);
    }
}