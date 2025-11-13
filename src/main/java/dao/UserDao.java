package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;

import java.util.List;
import java.util.Map;

public interface UserDao {

    void initializeCash() throws UserDaoException;

    List<User> getUsers();

    Map<Long,User> readAllUsersFromDatabase();

    List<User> readAllUsersFromFile();

    void addToDatabase(User user);

    void addToFile(User user) throws UserDaoException;

    void addBookToUser(User user, Book book) throws UserDaoException;

    User readFromDatabase(long id) throws UserDaoException;

    User readFromFile(long id);

    void updateInDatabase(User user);

    void updateInFile(User user) throws UserDaoException;

    void deleteFromDatabase(User user);

    void deleteFromFile(User user) throws UserDaoException;
}
