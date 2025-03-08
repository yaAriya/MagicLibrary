package DAOClass;

import enity.User;

import java.io.IOException;

public interface UserDAO {
    void readUsersFromFile(String path) throws IOException;
    void add(User user);
    User upDate(User user);
    void delete(User user);
    User read(int ID);
}
