package DAOClass;

import enity.User;

import java.io.IOException;

import java.util.List;

public interface UserDAO {
    List<User> readUsersFromFile(String path) throws IOException;
    void add(User user);
    User upDate(User user);
    void delete(User user);
    User read(int ID, String filePath) throws IOException;
}
