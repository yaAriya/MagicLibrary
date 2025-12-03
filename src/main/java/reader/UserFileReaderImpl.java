package reader;
import converter.UserConverter;
import entity.User;
import exceptions.UserFileReaderException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserFileReaderImpl implements UserFileReader {
    private static final String USER_FILE_PATH = "src/main/resources/user.txt";
    private UserConverter userConverter;

    public void setUserConverter(UserConverter userConverter){
        this.userConverter = userConverter;
    }

    @Override
    public List<User> readUsersFromFile() throws UserFileReaderException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            List<User> users = new ArrayList<>();

            String readLine = reader.readLine();

            while (readLine != null) {
                User user = userConverter.convertLineToUser(readLine);
                users.add(user);
                readLine = reader.readLine();
            }
            return users;
        } catch (IOException e) {
            throw new UserFileReaderException(e);
        }
    }
}