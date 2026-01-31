package mapper;

import entity.Book;
import entity.User;
import exceptions.MapperException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements Mapper<User> {
    private static final Logger LOGGER = LogManager.getLogger(UserMapper.class);
    private static final String ID_COLUMN = "user_id";
    private static final String NAME_COLUMN = "user_name";
    private static final String EMAIL_COLUMN = "email";
    private static final String AGE_COLUMN = "age";
    private BookMapper bookMapper;

    @Override
    public User mapResultSetToObject(ResultSet resultSet) throws MapperException {
        try {
            User mappedUser = new User();
            long id = resultSet.getLong(ID_COLUMN);
            mappedUser.setId(id);
            String name = resultSet.getString(NAME_COLUMN);
            mappedUser.setName(name);
            String email = resultSet.getString(EMAIL_COLUMN);
            mappedUser.setEmail(email);
            int age = resultSet.getInt(AGE_COLUMN);
            mappedUser.setAge(age);

            List<Book> userBooks = new ArrayList<>();
            while (!resultSet.wasNull()){
                Book userBook = bookMapper.mapResultSetToObjectWithoutDependencies(resultSet);
                userBooks.add(userBook);
            }
            mappedUser.setBooks(userBooks);

                return mappedUser;
        } catch (SQLException e) {
            LOGGER.error("Mapping ResultSet to User failed");
            throw new MapperException(e);
        }
    }

    public User mapResultSetToObjectWithoutDependencies(ResultSet resultSet) throws MapperException{
        try {
            User mappedUser = new User();
            long id = resultSet.getLong(ID_COLUMN);
            mappedUser.setId(id);
            String name = resultSet.getString(NAME_COLUMN);
            mappedUser.setName(name);
            String email = resultSet.getString(EMAIL_COLUMN);
            mappedUser.setEmail(email);
            int age = resultSet.getInt(AGE_COLUMN);
            mappedUser.setAge(age);

            return mappedUser;
        } catch (SQLException e) {
            LOGGER.error("Failed to map ResultSet to User");
            throw new MapperException(e);
        }
    }

    public void setBookMapper(BookMapper bookMapper){
        this.bookMapper = bookMapper;
    }

}