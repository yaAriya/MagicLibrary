package service;

import enity.User;

import java.io.IOException;

public interface UserService {
    void readUsersFromFile(String path) throws IOException;
    void add(User user);
    User upDate(User user);
    User read(int ID);
    void delete(User user);
}
