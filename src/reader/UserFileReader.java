package reader;

import enity.User;

import java.io.IOException;

import java.util.List;

public interface UserFileReader {
    List<User> readUsersFromFile(String filePath) throws IOException;
    User convertLineToUser(String line);
}
