package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers();

    void add(User user);

    User update(User user, long id);

    void delete(User user);

    User read(int id);
}
