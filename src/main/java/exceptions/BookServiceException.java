package exceptions;

public class BookServiceException extends RuntimeException {
    public BookServiceException() {
    }

    public BookServiceException(String message) {
        super(message);
    }

    public BookServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookServiceException(Throwable cause) {
        super(cause);
    }

    public BookServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
