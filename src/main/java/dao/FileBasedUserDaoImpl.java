package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;
import exceptions.UserFileReaderException;
import exceptions.UserFileWriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.UserFileReader;
import writer.UserFileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileBasedUserDaoImpl implements FileBasedUserDao {
    private static final Logger logger = LogManager.getLogger();
    private List<User> users;
    private UserFileReader userFileReader;
    private UserFileWriter userFileWriter;

    public void setUserFileReader(UserFileReader userFileReader){
        this.userFileReader = userFileReader;
    }

    public void setUserFileWriter(UserFileWriter userFileWriter){
        this.userFileWriter = userFileWriter;
    }

    @Override
    public void initializeCash() throws UserDaoException {
        try {
            users = userFileReader.readUsersFromFile();
        } catch (UserFileReaderException e) {
            throw new UserDaoException(e);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> readAllUsers() throws UserDaoException {
        try {
            List<User> clonedUsers = new ArrayList<>();
            for (User user : getUsers()) {
                clonedUsers.add(user.clone());
            }
            return clonedUsers;
        } catch (CloneNotSupportedException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void add(User user) throws UserDaoException {
        try {
            getUsers().add(user);
            userFileWriter.addUserToFile(user);
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void addBookToUser(User user, Book book) throws UserDaoException {
        try {
            user.getBooks().add(book);
            userFileWriter.writeUsersToFile(getUsers());
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public User read(long id) throws UserDaoException {
        try {
            for (User user : getUsers()) {
                if (user.getId() == id) {
                    return user.clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            throw new UserDaoException(e);
        }
        return null;
    }

    @Override
    public void update(User user) throws UserDaoException {
        try {
            for (int i = 0; i < getUsers().size(); i++) {
                if (getUsers().get(i).getId() == user.getId()) {
                    User realUser = getUsers().get(i);
                    realUser.setId(user.getId());
                    realUser.setName(user.getName());
                    realUser.setEmail(user.getEmail());
                    realUser.setAge(user.getAge());
                    userFileWriter.writeUsersToFile(getUsers());
                }
            }
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws UserDaoException {
        getUsers().remove(read(id));
        try {
            userFileWriter.writeUsersToFile(getUsers());
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }
}
