package reader;

import enity.User;

import java.io.IOException;

public interface UserFileReader {
    void readUsersFromFile(String filePath) throws IOException;
    User convertLineToUser(String line);
}
