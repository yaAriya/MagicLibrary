package service;

import DAOClass.UserDAO;

import enity.User;

public class UserService implements ServiceForUser{
    @Override
    public void add(User user) {

    }

    @Override
    public User upDate(User user) {
        return null;
    }

    @Override
    public User read(int userID) {
        UserDAO userDAO = new UserDAO();
        User findUserByID = userDAO.read(userID);
        return findUserByID;
    }

    @Override
    public void delete(User user) {

    }
}
