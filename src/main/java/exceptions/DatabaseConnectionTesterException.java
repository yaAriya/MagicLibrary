package exceptions;

public class DatabaseConnectionTesterException extends Exception{
    public DatabaseConnectionTesterException() {
        super();
    }

    public DatabaseConnectionTesterException(String message) {
        super(message);
    }

    public DatabaseConnectionTesterException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConnectionTesterException(Throwable cause) {
        super(cause);
    }
}
