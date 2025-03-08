package service;

import DAOClass.UserDAO;

import DAOClass.UserDAOImpl;

import enity.User;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void readUsersFromFile(String filePath) throws IOException{
        UserDAO userDAO = new UserDAOImpl();
        userDAO.readUsersFromFile(filePath);
    }
    @Override
    public void add(User user) {
    }

    @Override
    public User upDate(User user) {
        return null;
    }

    @Override
    public User read(int userID) {
        UserDAO userDAO = new UserDAOImpl();
        User findUserByID = userDAO.read(userID);
        return findUserByID;
    }

    @Override
    public void delete(User user) {

    }
}
