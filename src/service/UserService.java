package service;

import enity.User;

public interface UserService {
    void add(User user);
    User upDate(User user);
    User read(int ID);
    void delete(User user);
}
