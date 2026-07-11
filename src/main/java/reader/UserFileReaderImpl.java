package reader;
import converter.UserConverter;
import entity.User;
import exceptions.UserFileReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserFileReaderImpl implements UserFileReader {
    private static final Logger LOGGER = LogManager.getLogger(UserFileReaderImpl.class);
    private static final String USER_FILE_PATH = "src/main/resources/user.txt";
    private final UserConverter userConverter;


    public UserFileReaderImpl(UserConverter userConverter){
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
            LOGGER.info("Reading users from file compiled successful");
            return users;
        } catch (IOException e) {
            LOGGER.error("Reading users from file failed");
            throw new UserFileReaderException(e);
        }
    }
}