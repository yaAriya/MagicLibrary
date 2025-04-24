package validator;

import enity.User;

public interface Validator<T> {
    boolean validate(T entity);
}
