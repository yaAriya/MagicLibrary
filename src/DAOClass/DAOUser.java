package DAOClass;

import enity.User;

import java.util.List;

public interface DAOUser {
    List<User> getAllUsers();
    void add(User user);
    User upDate(User user);
    void delete(User user);
    User getByID(int ID);
}
