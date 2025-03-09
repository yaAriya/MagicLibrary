package DAOClass;

import enity.User;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    public List<User> readUsersFromFile (String filePath) throws IOException {// сделать возвратным?
        UserFileReader userReader = new UserFileReaderImpl();
        List<User> users = userReader.readUsersFromFile(filePath);
        return users;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public User upDate(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User read(int ID, String filePath) throws IOException {
       List<User> users = readUsersFromFile(filePath);
        for(int i = 0; i< users.size(); i++){
            if(users.get(i).getUserId() == ID) {
                return users.get(i);
            }
        }
        return null;
    }
}
