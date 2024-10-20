package exceptions;

public class NoAvailablePortsException extends RuntimeException {
    public NoAvailablePortsException(String message) {
        super(message);
    }
}
