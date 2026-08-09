package exceptions;

public class HibernateException extends Exception {
    public HibernateException() {
        super();
    }

    public HibernateException(String message, Throwable cause) {
        super(message, cause);
    }

    public HibernateException(Throwable cause) {
        super(cause);
    }

    protected HibernateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HibernateException(String message) {
        super(message);
    }
}
