package dao;

import enity.User;

import java.io.IOException;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers();

    void add(User user) throws IOException;

    User upDate(User user);

    void delete(User user);

    User read(int ID) throws IOException;
}
