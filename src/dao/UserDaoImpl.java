package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import exceptions.UserFileReaderException;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    private final List<User> users;
    private final UserFileReader userReader;

    public UserDaoImpl() throws ObjectInitializeException {
        try {
            userReader = new UserFileReaderImpl();
            users = userReader.readUsersFromFile();
        } catch (UserFileReaderException e) {
            throw new ObjectInitializeException(e);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> readAllUsers() {
        return getUsers();
    }

    @Override
    public void add(User user){
            users.add(user);
    }

    @Override
    public User upDate(User user, int index) {
        users.set(1, user);
        return users.get(1);
    }

    @Override
    public User read(int ID) {
        for (User user : users) {
            if (user.getUserId() == ID) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) {
            users.remove(user);
    }
}
