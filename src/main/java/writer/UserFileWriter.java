package writer;

import entity.User;
import exceptions.UserFileWriterException;

import java.util.List;

public interface UserFileWriter {
    void addUserToFile(User user) throws UserFileWriterException;

    void writeUsersToFile(List<User> users) throws UserFileWriterException;
}
