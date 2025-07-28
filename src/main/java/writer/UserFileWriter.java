package writer;

import enity.User;

import exceptions.UserFileWriterException;

import java.util.List;

public interface UserFileWriter {
    void addUserToFile(User user) throws UserFileWriterException;

    void deleteUserFromFile(List<User> users) throws UserFileWriterException;

    void updateUserInFile(List<User> users) throws UserFileWriterException;

    String convertUserToLine(User user);
}
