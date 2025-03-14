package service;

import dao.UserDao;

import dao.UserDaoImpl;

import enity.User;

import java.io.IOException;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    public UserServiceImpl() throws IOException {
    }

    @Override
    public List<User> readAllUsers() {
        return userDao.readAllUsers();
    }

    @Override
    public void add(User user) throws IOException {
        userDao.add(user);
    }

    @Override
    public User upDate(User user) {
        return userDao.upDate(user);
    }

    @Override
    public User read(int userID) throws IOException {
        return userDao.read(userID);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
}
