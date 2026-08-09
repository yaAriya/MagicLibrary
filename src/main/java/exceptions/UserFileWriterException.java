package exceptions;

public class UserFileWriterException extends Exception {
    public UserFileWriterException() {
        super();
    }

    public UserFileWriterException(String message) {
        super(message);
    }

    public UserFileWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFileWriterException(Throwable cause) {
        super(cause);
    }
}
