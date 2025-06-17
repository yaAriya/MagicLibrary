package writer;

import enity.User;

import exceptions.UserFileWriterException;

import java.util.List;

public interface UserFileWriter {
    void addUserToFile(User user) throws UserFileWriterException;

    String convertUserToLine(User user);

    void deleteUserFromFile(List<User> users) throws UserFileWriterException;
}
