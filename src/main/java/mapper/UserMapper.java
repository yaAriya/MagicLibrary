package mapper;

import entity.User;
import exceptions.MapperException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public User mapRSToObject(ResultSet resultSet) throws MapperException {
        try {
            User mappedUser = new User();
            long id = resultSet.getLong("user_id");
            mappedUser.setId(id);
            String name = resultSet.getString("user_name");
            mappedUser.setName(name);
            String email = resultSet.getString("email");
            mappedUser.setEmail(email);
            int age = resultSet.getInt("age");
            mappedUser.setAge(age);

            return mappedUser;
        } catch (SQLException e) {
            logger.error("Failed to map ResultSet to User");
            throw new MapperException(e);
        }
    }

    @Override
    public void mapObjectToStatement(PreparedStatement preparedStatement, User user) throws MapperException {
        try {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
        } catch (SQLException e) {
            logger.error("Failed to map user to statement");
            throw new MapperException(e);
        }
    }

    @Override
    public void mapObjectIdToStatement(PreparedStatement preparedStatement, long id) throws MapperException {
        try {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            logger.error("Failed to map user id to statement");
            throw new MapperException(e);
        }
    }

    @Override
    public void mapUpdateObjectToStatement(PreparedStatement preparedStatement, User user) throws MapperException {
        try {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setLong(4, user.getId());
        } catch (SQLException e) {
            logger.error("Failed to map update user to statement");
            throw new MapperException(e);
        }
    }
}
