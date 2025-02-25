package DAOClass;

import enity.User;

import java.util.ArrayList;

import java.util.List;

public class UserDAO implements DAOUser{
    private static List<User> initializeUsers(){
        List<User> users = new ArrayList<>();

        return users;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User upDate(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User read(int ID) {
        List<User> users = initializeUsers();
        for(int i = 0; i< users.size(); i++){
            if(users.get(i).getUserID() == ID) {
                return users.get(i);
            }
        }
        return null;
    }
}
