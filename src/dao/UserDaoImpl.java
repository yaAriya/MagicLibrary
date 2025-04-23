package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import exceptions.UserFileReaderException;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance; // ПОчему не могу сделать прайват конструктор?

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
    public User update(User user, long id) {
        for(int i= 0; i< users.size(); i++){
            if(users.get(i).getId() == id){
                users.set(i, user);
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public User read(long id) {
        for (User user : users) {
            if (user.getId() == id) {
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
