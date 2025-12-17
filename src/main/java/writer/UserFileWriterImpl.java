package writer;

import converter.UserConverter;
import entity.User;
import exceptions.UserFileWriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserFileWriterImpl implements UserFileWriter {
    private static final Logger logger = LogManager.getLogger();
    private static final String USER_FILE_PATH = "src/main/resources/user.txt";
    private UserConverter userConverter;

    public void setUserConverter(UserConverter userConverter){
        this.userConverter = userConverter;
    }

    @Override
    public void addUserToFile(User user) throws UserFileWriterException {
        try (FileWriter writer = new FileWriter(USER_FILE_PATH, true)) {
            writer.write(userConverter.convertUserToLine(user));
            writer.write("\n");
            writer.flush();
            logger.info("Adding users to file compiled successful");
        } catch (IOException e) {
            logger.error("Adding users to file failed");
            throw new UserFileWriterException(e);
        }
    }

    @Override
    public void writeUsersToFile(List<User> users) throws UserFileWriterException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            for (User user : users) {
                String userToLine = userConverter.convertUserToLine(user);
                writer.write(userToLine + "\n");
            }
            logger.info("Writing users to file compiled successful");
        } catch (IOException e) {
            logger.error("Writing users to file failed");
            throw new UserFileWriterException(e);
        }
    }
}
