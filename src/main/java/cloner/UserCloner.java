package cloner;

import entity.User;
import function.CustomFunction;

public class UserCloner implements CustomFunction <User, User>{

    private static UserCloner INSTANCE;

    private UserCloner(){
    }

    public static UserCloner getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserCloner();
        }
         return INSTANCE;
    }

    @Override
    public User apply(User user) throws RuntimeException {
        try {
            return user.clone();
        } catch(CloneNotSupportedException e){
            throw new RuntimeException();
        }
    }
}