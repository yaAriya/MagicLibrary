package validator;

import enity.User;

public class UserValidatorImpl implements UserValidator {
    private static UserValidatorImpl instance;

    public static UserValidatorImpl getInstance(){
        if (instance == null){
            instance = new UserValidatorImpl();
        }
        return instance;
    }
    @Override
    public boolean validate(User user){
       return validateId(user.getId()) && validateName(user.getName()) && validateEmail(user.getEmail()) && validateAge(user.getAge());
    }

    @Override
    public boolean validateId(long id) {
        return id >= 0;
    }

    @Override
    public boolean validateName(String name) {
        return name != null;
    }

    @Override
    public boolean validateEmail(String email) {
        return email != null;
    }

    @Override
    public boolean validateAge(int age) {
        return age >= 0 && age < 100;
    }
}
