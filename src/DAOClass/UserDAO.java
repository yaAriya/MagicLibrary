package DAOClass;

import enity.User;

public interface UserDAO {
    void add(User user);
    User upDate(User user);
    void delete(User user);
    User read(int ID);
}
