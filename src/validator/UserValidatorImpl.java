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
       return user != null;
    }
}
