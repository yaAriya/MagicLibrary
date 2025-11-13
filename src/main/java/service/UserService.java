package service;

import entity.User;
import exceptions.UserServiceException;

import java.util.List;
import java.util.Map;

public interface UserService {

    void initializeCash() throws UserServiceException;

    Map<Long,User> readAllUsersFromDatabase();

    List<User> readAllUsersFromFile() throws UserServiceException;

    void addToDatabase(User user) throws UserServiceException;

    void addToFile(User user) throws UserServiceException;

    User readFromDatabase(long id);

    User readFromFile(long id) throws UserServiceException;

    void updateInDatabase(User user);

    void updateInFile(User user) throws UserServiceException;

    void deleteFromDatabase(long id);

    void deleteFromFile(long id) throws UserServiceException;

    void rentBook(long userId, long bookId) throws UserServiceException;

    void returnBook(long userId, long bookId) throws UserServiceException;
}
