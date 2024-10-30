package exceptions;

/**
 * Exception thrown when an invalid cable type (tipo de cable) is encountered.
 */
public class InvalidTipoCableException extends RuntimeException {
    /**
     * Constructs a new InvalidTipoCableException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidTipoCableException(String message) {
        super(message);
    }
}