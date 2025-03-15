package service;

import enity.User;
import exceptions.ObjectInitializeException;
import exceptions.UserServiceException;

import java.io.IOException;

import java.util.List;

public interface UserService {
    List<User> readAllUsers();

    void add(User user) throws UserServiceException;

    User upDate(User user);

    User read(int ID) throws UserServiceException;

    void delete(User user) throws UserServiceException;
}
