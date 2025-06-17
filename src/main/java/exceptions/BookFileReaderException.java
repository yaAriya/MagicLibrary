package exceptions;

public class BookFileReaderException extends Exception{
    public BookFileReaderException() {
    }

    public BookFileReaderException(String message) {
        super(message);
    }

    public BookFileReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookFileReaderException(Throwable cause) {
        super(cause);
    }

    public BookFileReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
