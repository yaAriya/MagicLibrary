package exceptions;

public class BookFileWriterException extends Exception{
    public BookFileWriterException() {
        super();
    }

    public BookFileWriterException(String message) {
        super(message);
    }

    public BookFileWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookFileWriterException(Throwable cause) {
        super(cause);
    }
}
