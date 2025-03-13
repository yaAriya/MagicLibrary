package service;

import DAOClass.UserDAO;

import DAOClass.UserDAOImpl;

import enity.User;

import java.io.IOException;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDAO userDAO = new UserDAOImpl();

    public UserServiceImpl() throws IOException {
    }

    @Override
    public List<User> readAllUsers() {
        return userDAO.readAllUsers();
    }

    @Override
    public void add(User user) throws IOException {
        userDAO.add(user);
    }

    @Override
    public User upDate(User user) {
        return userDAO.upDate(user);
    }

    @Override
    public User read(int userID) throws IOException {
        return userDAO.read(userID);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
}
