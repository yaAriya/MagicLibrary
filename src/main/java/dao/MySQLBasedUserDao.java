package dao;

import config.DatabaseConfig;
import entity.Book;
import entity.User;
import exceptions.MapperException;
import exceptions.UserDaoException;
import mapper.UserMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBasedUserDao implements UserDao {
    private static final String readAllUsersQuery = "SELECT id, name, email, age FROM users";
    private static final String addQuery = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
    private static final String readQuery = "SELECT * FROM users WHERE id = ? ";
    private static final String updateQuery = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
    private static final String deleteQuery = "DELETE FROM users WHERE id = ?";
    private static UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper){
        MySQLBasedUserDao.userMapper = userMapper;
    }

    @Override
    public List<User> readAllUsers() throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(readAllUsersQuery);

            while (resultSet.next()) {
                users.add(userMapper.mapRSToObject(resultSet));
            }
            return users;
        } catch (SQLException | MapperException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void add(User user) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addQuery);
            userMapper.mapObjectToStatement(preparedStatement, user);

            preparedStatement.executeUpdate();
        } catch (SQLException | MapperException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void addBookToUser(User user, Book book) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            //user.getBooks().add(book);

        } catch (SQLException e){
            throw new UserDaoException(e);
        }
    }

    @Override
    public User read(long id) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuery)) {

            userMapper.mapObjectIdToStatement(preparedStatement, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return userMapper.mapRSToObject(resultSet);
            }
        } catch (SQLException | MapperException e) {
            throw new UserDaoException(e);
        }
        return null;
    }

    @Override
    public void update(User user) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            userMapper.mapUpdateObjectToStatement(preparedStatement, user);
            preparedStatement.executeUpdate();

        } catch (SQLException | MapperException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws UserDaoException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            userMapper.mapObjectIdToStatement(preparedStatement, id);
            preparedStatement.executeUpdate();

        } catch (SQLException | MapperException e) {
            throw new UserDaoException(e);
        }
    }
}
