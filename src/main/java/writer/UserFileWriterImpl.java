package writer;

import enity.User;

import exceptions.UserFileWriterException;

import java.io.*;

import java.util.ArrayList;

import java.util.List;

public class UserFileWriterImpl implements UserFileWriter {
    private static UserFileWriterImpl INSTANCE;
    String filePath;

    public static UserFileWriterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserFileWriterImpl();
        }
        return INSTANCE;
    }

    public UserFileWriterImpl() {
        filePath = "resources/user.txt";
    }

    @Override
    public void addUserToFile(User user) throws UserFileWriterException {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write("\n");
            writer.write(convertUserToLine(user).trim());
            writer.flush();
        } catch (IOException e) {
            throw new UserFileWriterException(e);
        }
    }

    @Override
    public void deleteUserFromFile(List<User> users) throws UserFileWriterException {
        try {
            List<String> usersToLine = new ArrayList<>();

            for (int i = 0; i < users.size(); i++) {
                String userToLine = convertUserToLine(users.get(i));
                usersToLine.add(userToLine); // вот здесь у меня уже есть список юзеров типа строка
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            for (int i = 0; i < usersToLine.size(); i++) {
                writer.write(usersToLine.get(i) + "\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new UserFileWriterException(e);
        }
    }

    @Override
    public String convertUserToLine(User user) {
        String parameter = ",";
        String idToString = Long.toString(user.getId());
        String ageToString = Integer.toString(user.getAge());
        String booksToString = user.getBooks().toString();
        String userString = idToString + parameter + user.getName() + parameter + user.getEmail() + parameter + ageToString + parameter + booksToString;
        return userString;
    }
}
