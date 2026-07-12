package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers();

    void add(User user);

    User read(long id);

    void update(User user);

    void delete(long id);

}
