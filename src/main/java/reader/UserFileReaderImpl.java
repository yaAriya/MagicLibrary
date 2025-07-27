package reader;

import enity.Book;
import enity.User;

import exceptions.UserFileReaderException;

import service.BookServiceImpl;


import service.UserServiceImpl;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class UserFileReaderImpl implements UserFileReader {
    private static UserFileReaderImpl INSTANCE;

    private static final String USER_FILE_PATH = "src/main/resources/user.txt";

    private static final String parameter = ",";

    private UserServiceImpl userService;

    private BookServiceImpl bookService;

    //private static final String parameterForBooksIdList = "[";

    public static UserFileReaderImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserFileReaderImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private UserFileReaderImpl() {
    }

    private static void initializeDependencies(UserFileReaderImpl userFileReader) {
        userFileReader.userService = UserServiceImpl.getInstance();
        userFileReader.bookService = BookServiceImpl.getInstance();
    }

    @Override
    public List<User> readUsersFromFile() throws UserFileReaderException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH));
            List<String> readLinesFromUserFile = new ArrayList<>();

            String readLine = reader.readLine();

            while (readLine != null) {
                readLinesFromUserFile.add(readLine);
                readLine = reader.readLine();
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
        String[] parameters = line.split(parameter);

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameters[i].trim();
        }

            User user = new User();
            user.setId(Long.parseLong(parameters[0]));
            user.setName(parameters[1]);
            user.setEmail(parameters[2]);
            user.setAge(Integer.parseInt(parameters[3]));
            user.setBooks(new ArrayList<>());
            return user;
    }
}













