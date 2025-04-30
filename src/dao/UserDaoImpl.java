package dao;

import enity.User;

import exceptions.ObjectInitializeException;

import exceptions.UserFileReaderException;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl INSTANCE; // ПОчему не могу сделать прайват конструктор?

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
        }
        return INSTANCE;
    }

    private final List<User> USERS;
    private final UserFileReader USER_READER;

    public UserDaoImpl() throws ObjectInitializeException {
        try {
            USER_READER= new UserFileReaderImpl();
            USERS = USER_READER.readUsersFromFile();
        } catch (UserFileReaderException e) {
            throw new ObjectInitializeException(e);
        }
    }

    public List<User> getUsers() {
        return USERS;
    }

    public List<User> readAllUsers() {
        return getUsers();
    }

    @Override
    public void add(User user){
            USERS.add(user);
    }

    @Override
    public User update(User user, long id) {
        for(int i= 0; i< USERS.size(); i++){
            if(USERS.get(i).getId() == id){
                USERS.set(i, user);
                return USERS.get(i);
            }
        }
        return null;
    }

    @Override
    public User read(long id) {
        for (User user : USERS) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) {
            USERS.remove(user);
    }
}
