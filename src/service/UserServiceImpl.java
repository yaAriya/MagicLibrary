package service;

import dao.UserDao;

import dao.UserDaoImpl;

import enity.User;

import exceptions.ObjectInitializeException;

import exceptions.UserServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
    }

    @Override
    public List<User> readAllUsers() {
        return userDao.readAllUsers();
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            userDao.add(user);
        } catch (ObjectInitializeException e) {
            throw new UserServiceException(e);
        }

    }

    @Override
    public User upDate(User user) {
        return userDao.upDate(user);
    }

    @Override
    public User read(int userID) throws UserServiceException {
        return userDao.read(userID);
    }

    @Override
    public void delete(User user) throws UserServiceException {
        userDao.delete(user);
    }
}
