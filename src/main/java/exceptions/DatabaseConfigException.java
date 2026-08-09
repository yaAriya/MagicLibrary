package exceptions;

public class DatabaseConfigException extends Exception{
    public DatabaseConfigException() {
        super();
    }

    public DatabaseConfigException(String message) {
        super(message);
    }

    public DatabaseConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseConfigException(Throwable cause) {
        super(cause);
    }
}
