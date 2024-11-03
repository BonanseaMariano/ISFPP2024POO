package exceptions;

/**
 * Exception thrown when an invalid port is encountered in a device.
 */
public class InvalidPuertoEquipoException extends RuntimeException {
    /**
     * Constructs a new InvalidPuertoEquipoException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidPuertoEquipoException(String message) {
        super(message);
    }
}