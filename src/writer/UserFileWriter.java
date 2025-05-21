package writer;

import enity.User;

import exceptions.UserFileWriterException;

public interface UserFileWriter {
    void addUsersToFile(User user) throws UserFileWriterException;

    String convertUserToLine(User user);
}
