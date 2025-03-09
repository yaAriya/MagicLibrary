package service;

import DAOClass.UserDAO;

import DAOClass.UserDAOImpl;

import enity.User;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {
    public List<User> readUsersFromFile(String filePath) throws IOException{
        UserDAO userDAO = new UserDAOImpl();// Можно ли вынести вне методов как единую?
       return userDAO.readUsersFromFile(filePath);
    }
    @Override
    public void add(User user, String filePath) throws IOException {
        UserDAO userDAO = new UserDAOImpl();
        userDAO.add(user, filePath);
    }

    @Override
    public User upDate(User user) {
        return null;
    }

    @Override
    public User read(int userID, String filePath) throws IOException {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.read(userID, filePath);
    }

    @Override
    public void delete(User user) {

    }
}
