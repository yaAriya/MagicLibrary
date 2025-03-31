package reader;

import enity.User;

import exceptions.UserFileReaderException;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class UserFileReaderImpl implements UserFileReader {

    String userFilePath;

    public UserFileReaderImpl() {
        userFilePath = "resources/user.txt";
    }

    @Override
    public List<User> readUsersFromFile() throws UserFileReaderException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFilePath));
            List<String> readLinesFromUserFile = new ArrayList<>();

            for (int i = 0; i < 5; i++) {//Нужно сделать не <5 а < колва строчек
                String userLine = reader.readLine();
                readLinesFromUserFile.add(userLine);
            }

            List<User> users = new ArrayList<>();

            for (String line : readLinesFromUserFile) {
                User user = convertLineToUser(line);
                users.add(user);
            }
            return users;
        } catch (IOException e) {
            throw new UserFileReaderException(e);
        }
    }

    @Override
    public User convertLineToUser(String line) {
        String[] parameters = line.split(",");

        User user = new User();
        user.setUserId(Integer.parseInt(parameters[0]));
        user.setUserName(parameters[1]);
        user.setUserEmail(parameters[2]);
        user.setUserAge(Integer.parseInt(parameters[3]));

        return new User(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserAge());
    }
}
