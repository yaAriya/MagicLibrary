package validator;

import entity.User;

public class UserValidator implements Validator<User> {
   @Override
    public boolean validate(User user) {
        return validateId(user.getId()) && validateName(user.getName()) && validateEmail(user.getEmail()) && validateAge(user.getAge());
    }

    private boolean validateId(long id) {
        return id >= 0;
    }

    private boolean validateName(String name) {
        return name != null;
    }

    private boolean validateEmail(String email) {
        return email != null;
    }

    private boolean validateAge(int age) {
        return age >= 0 && age < 100;
    }
}
