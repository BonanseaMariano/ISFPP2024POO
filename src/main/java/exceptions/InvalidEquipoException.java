package exceptions;

/**
 * Exception thrown when an invalid device (equipo) is encountered.
 */
public class InvalidEquipoException extends RuntimeException {
    /**
     * Constructs a new InvalidEquipoException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidEquipoException(String message) {
        super(message);
    }
}