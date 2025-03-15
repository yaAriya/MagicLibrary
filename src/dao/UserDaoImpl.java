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

    private List<User> users;
    private UserFileReader userReader;

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
    public void add(User user) throws ObjectInitializeException {
        if (user != null) {
            users.add(user);
        } else {
            throw new IllegalArgumentException("user не должен быть null");
        }
    }

    @Override
    public User upDate(User user) {
        users.set(1, user);
        return users.get(1);
    }

    @Override
    public User read(int ID) throws ObjectInitializeException {
        for (User user : users) {
            if (user.getUserId() == ID) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) throws ObjectInitializeException {
        if (user != null) {
            users.remove(user);
        } else {
            throw new IllegalArgumentException("user не должен быть null");
        }
    }
}
