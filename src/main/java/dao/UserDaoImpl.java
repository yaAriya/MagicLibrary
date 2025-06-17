package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import exceptions.UserDaoException;

import exceptions.UserFileReaderException;

import exceptions.UserFileWriterException;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import writer.UserFileWriter;

import writer.UserFileWriterImpl;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl INSTANCE; 
    private final List<User> users;
    private final UserFileReader USER_READER;
    private final UserFileWriter USER_WRITER;

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
        }
        return INSTANCE;
    }

    public UserDaoImpl() throws ObjectInitializeException {
        try {
            USER_READER = UserFileReaderImpl.getInstance();
            users = USER_READER.readUsersFromFile();
            USER_WRITER = UserFileWriterImpl.getInstance();
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
    public void add(User user) throws UserDaoException {
        try {
            if(!users.contains(user)) {
                users.add(user);
                USER_WRITER.addUserToFile(user);
            }
        } catch(UserFileWriterException e){
            throw new UserDaoException(e);
        }
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
    public void delete(User user) throws UserDaoException {
        try {
            users.remove(user);     // в коллекции его уже нет на момент вызова WriterА
            USER_WRITER.deleteUserFromFile(users);  //переименовать в запись информации
        } catch (UserFileWriterException e){
            throw new UserDaoException(e);
        }
    }
}
