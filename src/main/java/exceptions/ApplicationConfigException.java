package exceptions;

public class ApplicationConfigException extends Exception {
    public ApplicationConfigException(String message) {
        super(message);
    }

    public ApplicationConfigException() {
        super();
    }

    public ApplicationConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationConfigException(Throwable cause) {
        super(cause);
    }

    protected ApplicationConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
