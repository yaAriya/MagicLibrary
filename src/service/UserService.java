package service;

import enity.Book;
import enity.User;

import exceptions.UserServiceException;

import java.util.List;

public interface UserService {
    List<User> readAllUsers() throws UserServiceException;

    void add(User user) throws UserServiceException;

    User update(User user) throws UserServiceException;

    User read(int Id) throws UserServiceException;

    void delete(User user) throws UserServiceException;

    List<Book> rentBook(User user, Book book) throws UserServiceException;

    List<Book> returnBook(User user, Book book) throws UserServiceException;
}
