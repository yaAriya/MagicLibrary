package reader;

import entity.User;
import exceptions.UserFileReaderException;

import java.util.List;

public interface UserFileReader {
    List<User> readUsersFromFile() throws UserFileReaderException;
}
