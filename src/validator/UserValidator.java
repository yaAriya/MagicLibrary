package validator;

import enity.User;

public interface UserValidator {
    boolean validate(User user);

    boolean validateId(long id);

    boolean validateName(String name);

    boolean validateEmail(String email);

    boolean validateAge(int age);


}
