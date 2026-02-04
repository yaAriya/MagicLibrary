package dao;

import config.DatabaseConfig;
import entity.User;
import exceptions.DatabaseConfigException;
import exceptions.MapperException;
import exceptions.UserDaoException;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MySQLBasedUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLBasedUserDao.class);
    private static final String READ_ALL_USERS_QUERY = "SELECT u.id AS user_id, u.name AS user_name, email, age, b.id AS book_id, b.name AS book_name, author, page_number FROM users u LEFT JOIN books b ON u.id = b.user_id ORDER BY u.id";
    private static final String ADD_QUERY = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
    private static final String READ_QUERY = "SELECT u.id AS user_id, u.name AS user_name, email, age, b.id AS book_id, b.name AS book_name, author, page_number FROM users u LEFT JOIN books b ON b.user_id = u.id WHERE u.id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private UserMapper userMapper;
    private DatabaseConfig databaseConfig;

    @Override
    public List<User> readAllUsers() throws UserDaoException {
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(READ_ALL_USERS_QUERY)) {

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User mappedUser = userMapper.mapResultSetToObject(resultSet);
                User user = findDuplicateUser(users, mappedUser.getId());

                if (!(user == null)) {
                    user.getBooks().add(mappedUser.getBooks().getFirst());
                } else {
                    users.add(mappedUser);
                }
            }
            LOGGER.info("Users reading completed successfully");
            return users;
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            LOGGER.error("Users reading failed");
            throw new UserDaoException(e);
        }
    }

    private User findDuplicateUser(List<User> users, Long userId) {
        return users.stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(null);

    }

    @Override
    public void add(User user) throws UserDaoException {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();

            LOGGER.info("User adding completed successfully");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("User adding failed");
            throw new UserDaoException(e);
        }
    }

    @Override
    public User read(long id) throws UserDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_QUERY)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                User user = userMapper.mapResultSetToObject(resultSet);
                while (resultSet.next()) {
                    User mappedUser = userMapper.mapResultSetToObject(resultSet);
                    user.getBooks().add(mappedUser.getBooks().getFirst());
                }
                LOGGER.info("User reading completed successfully");
                return user;
            }

        } catch (SQLException | MapperException | DatabaseConfigException e) {
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(User user) throws UserDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("Users updating completed successfully");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("User updating failed");
            throw new UserDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws UserDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("Users deleting completed successfully");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("User deleting failed");
            throw new UserDaoException(e);
        }
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
