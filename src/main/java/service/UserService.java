package service;

import entity.User;
import exceptions.UserServiceException;

import java.util.List;

public interface UserService {

    void initializeCash() throws UserServiceException;

    List<User> readAllUsers() throws UserServiceException;

    void add(User user) throws UserServiceException;

    User update(User user) throws UserServiceException;

    User read(long id) throws UserServiceException;

    void delete(long id) throws UserServiceException;

    void rentBook(long userId, long bookId) throws UserServiceException;

    void returnBook(long userId, long bookId) throws UserServiceException;
}
