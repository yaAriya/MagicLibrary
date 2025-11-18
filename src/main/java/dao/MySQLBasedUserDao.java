package dao;

import config.DatabaseConfig;
import entity.Book;
import entity.User;
import exceptions.UserDaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBasedUserDao implements UserDao {

    private static MySQLBasedUserDao INSTANCE;
    private List<User> users;

    public static MySQLBasedUserDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MySQLBasedUserDao();
        }
        return INSTANCE;
    }

    private MySQLBasedUserDao() {
    }

    @Override
    public void initializeCash() throws UserDaoException {
        users = readAllUsers();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> readAllUsers() throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id, name, email, age FROM users");

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
                users.add(newUser.clone());
            }
            return users;
        } catch (CloneNotSupportedException | SQLException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void add(User user) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, email, age) VALUES (?, ?, ?)");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());

            preparedStatement.executeUpdate();

            System.out.println("Добавление в БД прошло успешно");
        } catch (SQLException e) {
            System.out.println("Добавление в БД провалено");
            throw new UserDaoException(e);
        }
    }

    @Override
    public void addBookToUser(User user, Book book) {
        user.getBooks().add(book);

    }

    @Override
    public User read(long userId) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ? ");
            preparedStatement.setLong(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
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
            }
        } catch (SQLException | CloneNotSupportedException e) {
            throw new UserDaoException(e);
        }
        return null;
    }

    @Override
    public void update(User user) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("Обновление прошло успешно");

        } catch (SQLException e) {
            System.out.println("Обновление провалено");
            throw new UserDaoException(e);
        }
    }

    @Override
    public void delete(User user) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();

            System.out.println("Удаление из БД выполнено");
        } catch (SQLException e) {
            System.out.println("Удаление из БД провалено");
            throw new UserDaoException(e);
        }
    }
}
