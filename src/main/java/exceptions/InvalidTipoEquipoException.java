package exceptions;

/**
 * Exception thrown when an invalid device type (tipo de equipo) is encountered.
 */
public class InvalidTipoEquipoException extends RuntimeException {
    /**
     * Constructs a new InvalidTipoEquipoException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidTipoEquipoException(String message) {
        super(message);
    }
}