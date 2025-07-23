package service;

import enity.Book;
import enity.User;

import exceptions.UserDaoException;
import exceptions.UserServiceException;

import java.util.List;

public interface UserService {

    void initializeDataBase() throws UserServiceException, UserDaoException;

    List<User> readAllUsers() throws UserServiceException;

    void add(User user) throws UserServiceException;

    User update(User user) throws UserServiceException;

    User read(long id) throws UserServiceException;

    void delete(long id) throws UserServiceException;

    void rentBook(long userId, long bookId) throws UserServiceException;

    void returnBook(long userId, long bookId) throws UserServiceException;
}
