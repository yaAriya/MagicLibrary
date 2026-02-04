package exceptions;

public class ApplicationContextException extends Exception {
    public ApplicationContextException() {
        super();
    }

    public ApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationContextException(Throwable cause) {
        super(cause);
    }

    protected ApplicationContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ApplicationContextException(String message) {
        super(message);
    }

}
