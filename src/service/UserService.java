package service;

import enity.User;

import exceptions.UserServiceException;

import java.util.List;

public interface UserService {
    List<User> readAllUsers() throws UserServiceException;

    void add(User user) throws UserServiceException;

    User update(User user) throws UserServiceException;

    User read(int Id) throws UserServiceException;

    void delete(User user) throws UserServiceException;
}
