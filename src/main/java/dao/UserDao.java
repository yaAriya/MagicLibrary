package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;

import java.util.List;

public interface UserDao {
    void initializeCash() throws UserDaoException;

    List<User> getUsers();

    List<User> readAllUsers() throws CloneNotSupportedException;

    void add(User user) throws UserDaoException;

    void addBookToUser(User user, Book book);

    User read(long id) throws UserDaoException;

    User update(User user) throws UserDaoException;

    void delete(User user) throws UserDaoException;
}
