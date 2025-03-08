package DAOClass;

import enity.User;

import reader.UserFileReader;

import reader.UserFileReaderImpl;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    public void readUsersFromFile (String filePath) throws IOException {
        UserFileReader userReader = new UserFileReaderImpl();
        List<User> users = new ArrayList<>();
        userReader.readUsersFromFile(filePath);
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
    public User read(int ID) {
       /* List<User> users = initializeUsers();
        for(int i = 0; i< users.size(); i++){
            if(users.get(i).getUserID() == ID) {
                return users.get(i);
            }
        }*/
        return null;
    }
}
