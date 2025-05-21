package writer;

import enity.User;

import exceptions.UserFileWriterException;

import java.io.FileWriter;

import java.io.IOException;

public class UserFileWriterImpl implements UserFileWriter {
    private static UserFileWriterImpl INSTANCE;
    String filePath;

    public static  UserFileWriterImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserFileWriterImpl();
        } return INSTANCE;
    }

    public UserFileWriterImpl(){
        filePath = "resources/user.txt";
    }

    @Override
    public void addUsersToFile(User user) throws UserFileWriterException {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write("\n");
            writer.write(convertUserToLine(user));
            writer.flush();
        } catch (IOException e){
            throw new UserFileWriterException(e);
        }
    }

    @Override
    public String convertUserToLine(User user){
        String parameter = ",";
        String idToString = Long.toString(user.getId());
        String ageToString = Integer.toString(user.getAge());
        String booksToString = user.getBooks().toString();
        String userString = idToString + parameter  + user.getName()  + parameter + user.getEmail()  + parameter + ageToString  + parameter + booksToString;
        return  userString;
    }
}
