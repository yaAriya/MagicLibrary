package validator;

public interface Validator<T> {
    boolean validate(T entity);
}
