package DAOClass;

import enity.User;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.io.IOException;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private List<User> users;
    private UserFileReader userReader;

    public UserDAOImpl() throws IOException {
        userReader = new UserFileReaderImpl();
        users = userReader.readUsersFromFile();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> readAllUsers() {
        return getUsers();
    }

    @Override
    public void add(User user) throws IOException {
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
    public User read(int ID) throws IOException {
        for (User user : users) {
            if (user.getUserId() == ID) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(User user) {
        if (user != null) {
            users.remove(user);
        } else {
            throw new IllegalArgumentException("user не должен быть null");
        }
    }
}
