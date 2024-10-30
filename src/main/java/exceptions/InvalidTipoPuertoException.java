package exceptions;

/**
 * Exception thrown when an invalid port type (tipo de puerto) is encountered.
 */
public class InvalidTipoPuertoException extends RuntimeException {
    /**
     * Constructs a new InvalidTipoPuertoException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidTipoPuertoException(String message) {
        super(message);
    }
}