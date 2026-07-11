package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers() throws UserDaoException;

    void add(User user) throws UserDaoException;

    User read(long id) throws UserDaoException;

    void update(User user) throws UserDaoException;

    void delete(long id) throws UserDaoException;

}
