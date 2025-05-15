package dao;

import enity.User;

import exceptions.ObjectInitializeException;
import exceptions.UserDaoException;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers();

    void add(User user);

    User read(long id) throws UserDaoException;

    User update(User user, long id) throws UserDaoException;

    void delete(User user);
}
