package service;

import enity.User;

public interface ServiceForUser {
    void add(User user);
    User upDate(User user);
    User read(int ID);
    void delete(User user);
}
