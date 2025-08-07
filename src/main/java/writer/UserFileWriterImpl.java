package writer;

import converter.UserConverterImpl;
import entity.User;
import exceptions.UserFileWriterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UserFileWriterImpl implements UserFileWriter {
    private static UserFileWriterImpl INSTANCE;
    private static final String USER_FILE_PATH = "src/main/resources/user.txt";

    private UserConverterImpl userConverter;

    public static UserFileWriterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserFileWriterImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private UserFileWriterImpl() {
    }

    private static void initializeDependencies(UserFileWriterImpl userFileWriter) {
        userFileWriter.userConverter = UserConverterImpl.getInstance();
    }

    @Override
    public void addUserToFile(User user) throws UserFileWriterException {
        try (FileWriter writer = new FileWriter(USER_FILE_PATH, true)) {
            writer.write("\n");
            writer.write(userConverter.convertUserToLine(user).trim());
            writer.flush();
        } catch (IOException e) {
            throw new UserFileWriterException(e);
        }
    }

    @Override
    public void writeUsersToFile(List<User> users) throws UserFileWriterException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH))) {
            for (int i = 0; i < users.size(); i++) {
                String userToLine = userConverter.convertUserToLine(users.get(i));
                writer.write(userToLine + "\n");
            }
        } catch (IOException e) {
            throw new UserFileWriterException(e);
        }
    }
}
