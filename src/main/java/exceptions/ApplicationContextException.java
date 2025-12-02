package exceptions;

public class ApplicationContextException extends Exception{
    public ApplicationContextException() {
        super();
    }

    public ApplicationContextException(String message) {
        super(message);
    }

    public ApplicationContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationContextException(Throwable cause) {
        super(cause);
    }
}
