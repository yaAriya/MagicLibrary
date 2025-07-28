package writer;

import enity.User;

import exceptions.UserFileWriterException;

import java.io.*;

import java.util.ArrayList;

import java.util.List;

public class UserFileWriterImpl implements UserFileWriter {
    private static UserFileWriterImpl INSTANCE;
    private static final String USER_FILE_PATH = "src/main/resources/user.txt";

    public static UserFileWriterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserFileWriterImpl();
        }
        return INSTANCE;
    }

    private UserFileWriterImpl() {
    }

    @Override
    public void addUserToFile(User user) throws UserFileWriterException {
        try {
            FileWriter writer = new FileWriter(USER_FILE_PATH, true);
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
                usersToLine.add(userToLine);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH));

            for (int i = 0; i < usersToLine.size(); i++) {
                writer.write(usersToLine.get(i) + "\n");
            }
            writer.close();

        } catch (IOException e) {
            throw new UserFileWriterException(e);
        }
    }

    public void updateUserInFile(List<User> users) throws UserFileWriterException {
        try {
            List<String> usersToLine = new ArrayList<>();

            for (int i = 0; i < users.size(); i++) {
                usersToLine.add(convertUserToLine(users.get(i)));
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_PATH));

            for (int i = 0; i < usersToLine.size(); i++) {
                writer.write(usersToLine.get(i) + "\n");
            }
            writer.close();
        }catch (IOException e){
            throw new UserFileWriterException(e);
        }
    }

    @Override
    public String convertUserToLine(User user) {
        String parameter = ",";
        String idToString = Long.toString(user.getId());
        String ageToString = Integer.toString(user.getAge());

        if (user.getBooks().size() == 0) {
            return idToString + parameter + user.getName() + parameter + user.getEmail() + parameter + ageToString;
        } else {
            List<Long> booksId = new ArrayList<>();
            for (int i = 0; i < user.getBooks().size(); i++) {
                booksId.add(user.getBooks().get(i).getId());
            }
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < booksId.size(); i++) {
                sb.append(booksId.get(i));
                if (i < (booksId.size() - 1)) {
                    sb.append(",");
                }
            }

            String booksIdToString = sb.toString();

            return idToString + parameter + user.getName() + parameter + user.getEmail() + parameter + ageToString + parameter + booksIdToString;
        }
    }
}
