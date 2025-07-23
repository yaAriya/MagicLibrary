package dao;

import enity.User;

import exceptions.UserDaoException;

import java.util.List;

public interface UserDao {
    void initializeDataBase() throws UserDaoException;

    List<User> readAllUsers() throws CloneNotSupportedException;

    void add(User user) throws UserDaoException;

    User read(long id) throws UserDaoException;

    User update(User user, long id) throws UserDaoException;

    void delete(User user) throws UserDaoException;
}
