package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;

import java.util.List;

public interface UserDao {

    void initializeCash() throws UserDaoException;

    List<User> getUsers();

    void setUsers(List<User> users);

    List<User> readAllUsers() throws UserDaoException;

    void add(User user) throws UserDaoException;

    void addBookToUser(User user, Book book) throws UserDaoException;

    User read(long id) throws UserDaoException;

    void update(User user) throws UserDaoException;

    void delete(User user) throws UserDaoException;

}
