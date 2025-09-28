package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;
import exceptions.UserFileReaderException;
import exceptions.UserFileWriterException;
import reader.UserFileReader;
import reader.UserFileReaderImpl;
import writer.UserFileWriter;
import writer.UserFileWriterImpl;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl INSTANCE;
    private List<User> users;
    private UserFileReader userFileReader;
    private UserFileWriter userFileWriter;

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
            initializeDependencies(INSTANCE);

        }
        return INSTANCE;
    }

    private UserDaoImpl() {
    }

    private static void initializeDependencies(UserDaoImpl userDao) {
        userDao.userFileReader = UserFileReaderImpl.getInstance();
        userDao.userFileWriter = UserFileWriterImpl.getInstance();
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
    public List<User> readAllUsers() {
        return getUsers().stream()
                .map(user -> {
                    try {
                       return user.clone();
                    } catch (CloneNotSupportedException e){
                        throw new RuntimeException(e);
                    }
                })  .toList();
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
        try{
            user.getBooks().add(book);
            userFileWriter.writeUsersToFile(getUsers());
        } catch (UserFileWriterException e){
            throw new UserDaoException(e);
        }
    }

 @Override
 public User read(long id) {
     return getUsers().stream()
             .filter(user -> user.getId() == id)
             .findAny()
             .map(user -> {
                 try {
                    return user.clone();
                 } catch (CloneNotSupportedException e) {
                     throw new RuntimeException(e);
                 }
             })
             .orElse(null);
 }

      @Override
      public User update (User user) throws UserDaoException {
          try {
              for (int i = 0; i < getUsers().size(); i++) {
                  if (getUsers().get(i).getId() == user.getId()) {
                      User realUser = getUsers().get(i);
                      realUser.setId(user.getId());
                      realUser.setName(user.getName());
                      realUser.setEmail(user.getEmail());
                      realUser.setAge(user.getAge());
                      userFileWriter.writeUsersToFile(getUsers());
                      return getUsers().get(i).clone();
                  }
              }
              return null;
          } catch (CloneNotSupportedException | UserFileWriterException e) {
              throw new UserDaoException(e);
          }
      }

      @Override
      public void delete (User user) throws UserDaoException {
          try {
              getUsers().remove(user);
              userFileWriter.writeUsersToFile(getUsers());
          } catch (UserFileWriterException e) {
              throw new UserDaoException(e);
          }
      }
  }
