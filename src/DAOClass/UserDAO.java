package DAOClass;

import enity.User;

import java.util.ArrayList;

import java.util.List;

public class UserDAO {
    public List<User> initializerCurrentUser(){
        List <User> currentUsers = new ArrayList<>();

        User firstUser = new  User("Nick", "NickNick@gmail.com", 14, 134);
        User secondUser = new User("Elsa", "Elisa@gmail.com", 16, 245);
        User thirdUser = new User("Petr", "Petya@gmail.com", 10, 356);

        currentUsers.add(firstUser);
        currentUsers.add(secondUser);
        currentUsers.add(thirdUser);

        return currentUsers;
    }

    public List<User> initializerExpiredUsers(){
        List <User> expiredUsers = new ArrayList<>();

        User firstUser = new User("Lisa", "Lisochka@gmail.com", 11, 467);
        User secondUser = new User("Pasha", "Pashka@gmail.com", 19, 578);
        User thirdUser = new User("Vladimir", "Vovchik@gmail.com", 15, 689);

        expiredUsers.add(firstUser);
        expiredUsers.add(secondUser);
        expiredUsers.add(thirdUser);

    return expiredUsers;
    }

}
