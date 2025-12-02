package writer;

import converter.UserConverter;
import entity.User;
import exceptions.UserFileWriterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserFileWriterImpl implements UserFileWriter {
    private static final String USER_FILE_PATH = "src/main/resources/user.txt";
    private static UserConverter userConverter;

    public void setUserConverter(UserConverter userConverter){
        UserFileWriterImpl.userConverter = userConverter;
    }

    @Override
    public void addUserToFile(User user) throws UserFileWriterException {
        try (FileWriter writer = new FileWriter(USER_FILE_PATH, true)) {
            writer.write(userConverter.convertUserToLine(user));
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
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
        } catch (IOException e) {
            throw new UserFileWriterException(e);
        }
    }
}
