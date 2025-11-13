package dao;

import config.DatabaseConfig;
import entity.Book;
import entity.User;
import exceptions.UserDaoException;
import exceptions.UserFileReaderException;
import exceptions.UserFileWriterException;
import reader.UserFileReader;
import reader.UserFileReaderImpl;
import writer.UserFileWriter;
import writer.UserFileWriterImpl;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<Long, User> readAllUsersFromDatabase() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            Map<Long, User> users = new HashMap<>();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id, name, email, age FROM users");
            System.out.println("Список всех пользователей");

            while (resultSet.next()) {
                User newUser = new User();
                long id = resultSet.getLong("id");
                newUser.setId(id);
                String name = resultSet.getString("name");
                newUser.setName(name);
                String email = resultSet.getString("email");
                newUser.setEmail(email);
                int age = resultSet.getInt("age");
                newUser.setAge(age);
                users.put(id, newUser.clone());
            }
            return users;
        } catch (CloneNotSupportedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> readAllUsersFromFile() {
        return getUsers().stream()
                .map(user -> {
                    try {
                        return user.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    @Override
    public void addToDatabase(User user) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            final String sqlQuery = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());

            preparedStatement.executeUpdate();

            System.out.println("Добавление в БД прошло успешно");

        } catch (SQLException e) {
            System.out.println("Добавление в БД провалено");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addToFile(User user) throws UserDaoException {
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
    public User readFromDatabase(long userId) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT FROM users WHERE id = ? ");
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            User newUser = new User();
            long id = resultSet.getLong("id");
            newUser.setId(id);
            String name = resultSet.getString("name");
            newUser.setName(name);
            String email = resultSet.getString("email");
            newUser.setEmail(email);
            int age = resultSet.getInt("age");
            newUser.setAge(age);
            return newUser.clone();
        } catch (CloneNotSupportedException e) {
            throw new UserDaoException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User readFromFile(long id) {
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
    public void updateInDatabase(User user) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sqlQuery = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateInFile(User user) throws UserDaoException {
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
    public void deleteFromDatabase(User user) {
        try (Connection connection = DatabaseConfig.getConnection()) {

            String sqlQuery = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("Удаление из БД выполнено");

        } catch (SQLException e) {
            System.out.println("Удаление из БД провалено");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFromFile(User user) throws UserDaoException {
        getUsers().remove(user);
        try {
            userFileWriter.writeUsersToFile(getUsers());
        } catch (UserFileWriterException e) {
            throw new UserDaoException(e);
        }
    }
}
