package exceptions;

public class UserFileReaderException extends Exception {
    public UserFileReaderException() {
    }

    public UserFileReaderException(String message) {
        super(message);
    }

    public UserFileReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFileReaderException(Throwable cause) {
        super(cause);
    }

    public UserFileReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
