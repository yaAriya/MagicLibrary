package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import java.util.List;

public interface UserDao {
    List<User> readAllUsers();

    void add(User user) throws ObjectInitializeException;

    User upDate(User user);

    void delete(User user) throws ObjectInitializeException;

    User read(int ID) throws ObjectInitializeException;
}
