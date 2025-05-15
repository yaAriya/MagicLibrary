package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import exceptions.UserDaoException;

import exceptions.UserFileReaderException;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl INSTANCE; 
    private final List<User> users;
    private final UserFileReader USER_READER;

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
        }
        return INSTANCE;
    }

    public UserDaoImpl() throws ObjectInitializeException {
        try {
            USER_READER = new UserFileReaderImpl();
            users = USER_READER.readUsersFromFile();
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
    public void add(User user) {
        users.add(user);
    }

    @Override
    public User read(long id) throws UserDaoException {
        try {
            for (User user : users) {
                if (user.getId() == id) {
                    return (User) user.clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public User update(User user, long id) throws UserDaoException {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == id) {
                    users.set(i, user);
                    return (User) users.get(i).clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
