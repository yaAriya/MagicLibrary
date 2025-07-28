package dao;

import enity.User;

import exceptions.UserDaoException;

import exceptions.UserFileReaderException;

import exceptions.UserFileWriterException;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import writer.UserFileWriter;

import writer.UserFileWriterImpl;

import java.util.ArrayList;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl INSTANCE;
    private List<User> users;
    private UserFileReader userFileReader;
    private UserFileWriter userFileWriter;

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
            initializeDependencies(INSTANCE);

        }
        return INSTANCE;
    }

    private UserDaoImpl() {
    }

    private static void initializeDependencies(UserDaoImpl userDao) {
        userDao.userFileReader = UserFileReaderImpl.getInstance();
        userDao.userFileWriter = UserFileWriterImpl.getInstance();
    }

    public void initializeDataBase() throws UserDaoException {
        try {
            users = userFileReader.readUsersFromFile();
        } catch (UserFileReaderException e){
            throw new UserDaoException(e);
        }
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> readAllUsers() throws CloneNotSupportedException {
        List<User> clonedUsers = new ArrayList<>();
        for (User user : getUsers()) {
            clonedUsers.add(user.clone());
        }
        return clonedUsers;
    }

    @Override
    public void add(User user) throws UserDaoException {
        try {
            if (!getUsers().contains(user)) {
                getUsers().add(user);
                userFileWriter.addUserToFile(user);
            }
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public User read(long id) throws UserDaoException {
        try {
            for (User user : getUsers()) {
                if (user.getId() == id) {
                    return user.clone();
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
            for (int i = 0; i < getUsers().size(); i++) {
                if (getUsers().get(i).getId() == id) {
                    getUsers().set(i, user);
                    userFileWriter.updateUserInFile(getUsers());
                    return getUsers().get(i).clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException | UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void delete(User user) throws UserDaoException {
        try {
            getUsers().remove(user);
            userFileWriter.deleteUserFromFile(getUsers());
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }
}
