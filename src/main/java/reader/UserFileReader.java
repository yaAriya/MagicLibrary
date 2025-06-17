package reader;

import enity.User;

import exceptions.UserFileReaderException;

import java.util.List;

public interface UserFileReader {
    List<User> readUsersFromFile() throws UserFileReaderException;

    User convertLineToUser(String line);
}
