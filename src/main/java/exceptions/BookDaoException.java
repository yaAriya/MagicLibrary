package exceptions;

public class BookDaoException extends Exception {
    public BookDaoException() {
        super();
    }

    public BookDaoException(String message) {
        super(message);
    }

    public BookDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookDaoException(Throwable cause) {
        super(cause);
    }
}
