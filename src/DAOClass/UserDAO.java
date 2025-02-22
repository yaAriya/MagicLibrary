package DAOClass;

import enity.User;

import java.util.ArrayList;

import java.util.List;

public class UserDAO implements DAOUser{
    public static List<User> initializeUsers(){
        List<User> users = new ArrayList<>();

        User firstUser = new  User("Nick", "NickNick@gmail.com", 14, 134, null);
        User secondUser = new User("Elsa", "Elisa@gmail.com", 16, 245, null);
        User thirdUser = new User("Petr", "Petya@gmail.com", 10, 356, null);
        User fourthUser = new User("Lisa", "Lisochka@gmail.com", 11, 467, null);
        User fifthUser = new User("Pasha", "Pashka@gmail.com", 19, 578, null);
        User sixthUser = new User("Vladimir", "Vovchik@gmail.com", 15, 689, null);

        users.add(firstUser);
        users.add(secondUser);
        users.add(thirdUser);
        users.add(fourthUser);
        users.add(fifthUser);
        users.add(sixthUser);


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
        return null;
    }
}
