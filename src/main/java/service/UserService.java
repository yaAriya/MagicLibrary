package service;

import entity.Book;
import entity.User;
import exceptions.UserServiceException;

import java.util.List;

public interface UserService {
    List<User> readAllUsers();

    void add(User user) throws UserServiceException;

    User read(long id) throws UserServiceException;

    void update(User user) throws UserServiceException;

    void delete(long id) throws UserServiceException;
}
