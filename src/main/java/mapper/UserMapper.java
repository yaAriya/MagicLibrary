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
import java.util.Objects;

public class UserMapper implements Mapper<User> {
    private static final Logger LOGGER = LogManager.getLogger(UserMapper.class);
    private static final String ID_COLUMN = "user_id";
    private static final String NAME_COLUMN = "user_name";
    private static final String EMAIL_COLUMN = "email";
    private static final String AGE_COLUMN = "age";
    private static final String BOOK_ID_COLUMN = "book_id";
    private BookMapper bookMapper;


    @Override
    public User mapResultSetToObject(ResultSet resultSet) throws MapperException {
        try {
            User mappedUser = mapResultSetToObjectWithoutDependencies(resultSet);

            List<Book> userBooks = new ArrayList<>();
            boolean duplicateUser = false;
            while (resultSet.next() && !resultSet.wasNull() && !duplicateUser && resultSet.getLong(BOOK_ID_COLUMN) > 0) {
                Book userBook = bookMapper.mapResultSetToObjectWithoutDependencies(resultSet);
                if (!userBooks.contains(userBook)) {
                    userBooks.add(userBook);
                } else {
                    duplicateUser = true;
                }
            }
            mappedUser.setBooks(userBooks);

            return mappedUser;
        } catch (SQLException e) {
            LOGGER.error("Mapping ResultSet to User failed");
            throw new MapperException(e);
        }
    }

    public User mapResultSetToObjectWithoutDependencies(ResultSet resultSet) throws SQLException {
        LOGGER.info("mapResultSetToUserWithoutDependencies begin");
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
    }

    public List<User> mapResultSetToObjects(ResultSet resultSet) throws MapperException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User mappedUser = mapResultSetToObject(resultSet);
                User user = findDuplicateUser(users, mappedUser.getId());

                if (!(user == null)) {
                    user.getBooks().add(mappedUser.getBooks().getFirst());
                } else {
                    users.add(mappedUser);
                }
            }
        } catch (SQLException e) {
            throw new MapperException(e);
        }
        return null;
    }

    private User findDuplicateUser(List<User> users, Long userId) {
        return users.stream()
                .filter(Objects::nonNull)
                .filter(user -> user.getId() == userId)
                .findFirst()
                .orElse(null);

    }

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

}