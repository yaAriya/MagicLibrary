package service;

import enity.User;

import java.io.IOException;

import java.util.List;

public interface UserService {
    List<User> readAllUsers();

    void add(User user) throws IOException;

    User upDate(User user);

    User read(int ID) throws IOException;

    void delete(User user);
}
