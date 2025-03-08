package reader;

import enity.User;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class UserFileReaderImpl implements UserFileReader{
    @Override
    public void readUsersFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> readLinesFromUserFile = new ArrayList<>();

        for(int i = 0; i<4; i++){
            String userLine = reader.readLine();
            readLinesFromUserFile.add(userLine);
        }

        List<User> users = new ArrayList<>();

         for (String line: readLinesFromUserFile){
             User user = convertLineToUser(line);
             users.add(user);
             System.out.println(user);

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

        User userObj = new User(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserAge());

        return userObj;
    }
}
