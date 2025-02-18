package DAOClass;

import enity.User;

import java.util.List;

public interface DAOUser {
    void add(User user);
    User upDate(User user);
    void delete(User user);
    User read(int ID);
}
