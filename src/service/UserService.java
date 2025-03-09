package service;

import enity.User;

import java.io.IOException;

import java.util.List;

public interface UserService {
    List<User> readUsersFromFile(String path) throws IOException;
    void add(User user);
    User upDate(User user);
    User read(int ID, String filePath) throws IOException;
    void delete(User user);
}
