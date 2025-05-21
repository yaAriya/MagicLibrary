package reader;

import enity.User;

import exceptions.UserFileReaderException;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class UserFileReaderImpl implements UserFileReader {
    private static UserFileReaderImpl INSTANCE;
    String userFilePath;

    public static UserFileReaderImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserFileReaderImpl();
        }
        return INSTANCE;
    }

    public UserFileReaderImpl() {
        userFilePath = "resources/user.txt";
    }

    @Override
    public List<User> readUsersFromFile() throws UserFileReaderException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFilePath));
            List<String> readLinesFromUserFile = new ArrayList<>();

            String readerLine = reader.readLine();

            while (readerLine != null){
                readLinesFromUserFile.add(readerLine);
                readerLine = reader.readLine();
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

        for(String parameter: parameters){
            parameter.trim();
        }

        User user = new User();
        user.setId(Long.parseLong(parameters[0]));
        user.setName(parameters[1]);
        user.setEmail(parameters[2]);
        user.setAge(Integer.parseInt(parameters[3]));

        return new User(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }
}
