package exceptions;

public class PropertyLoaderException extends Exception {
    public PropertyLoaderException() {
        super();
    }

    public PropertyLoaderException(String message) {
        super(message);
    }

    public PropertyLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyLoaderException(Throwable cause) {
        super(cause);
    }
}
