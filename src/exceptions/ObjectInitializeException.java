package exceptions;

public class ObjectInitializeException extends RuntimeException{
    public ObjectInitializeException() {
    }

    public ObjectInitializeException(String message) {
        super(message);
    }

    public ObjectInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectInitializeException(Throwable cause) {
        super(cause);
    }

    public ObjectInitializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
