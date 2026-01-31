package mapper;

import entity.Book;
import entity.User;
import exceptions.MapperException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements Mapper<Book> {
    private static final Logger LOGGER = LogManager.getLogger(BookMapper.class);
    private static final String ID_COLUMN = "book_id";
    private static final String NAME_COLUMN = "book_name";
    private static final String AUTHOR_COLUMN = "author";
    private static final String PAGE_NUMBER_COLUMN = "page_number";
    private static final String USER_ID_COLUMN = "user_id";
    private UserMapper userMapper;

    @Override
    public Book mapResultSetToObject(ResultSet resultSet) throws MapperException {
        try {
            Book mappedBook = new Book();
            long id = resultSet.getLong(ID_COLUMN);
            mappedBook.setId(id);
            String bookName = resultSet.getString(NAME_COLUMN);
            mappedBook.setName(bookName);
            String author = resultSet.getString(AUTHOR_COLUMN);
            mappedBook.setAuthor(author);
            int pageNumber = resultSet.getInt(PAGE_NUMBER_COLUMN);
            mappedBook.setPagesNumber(pageNumber);

            if (!resultSet.wasNull() && resultSet.getLong(USER_ID_COLUMN) > 0) {
                User mappedUser = userMapper.mapResultSetToObjectWithoutDependencies(resultSet);
                mappedBook.setUser(mappedUser);
            } else {
                mappedBook.setUser(null);
            }
            return mappedBook;
        } catch (SQLException e) {
            LOGGER.error("Mapping ResultSet to Book failed");
            throw new MapperException(e);
        }
    }

    @Override
    public Book mapResultSetToObjectWithoutDependencies(ResultSet resultSet) throws MapperException {
        try {
            Book mappedBook = new Book();
            long id = resultSet.getLong(ID_COLUMN);
            mappedBook.setId(id);
            String bookName = resultSet.getString(NAME_COLUMN);
            mappedBook.setName(bookName);
            String author = resultSet.getString(AUTHOR_COLUMN);
            mappedBook.setAuthor(author);
            int pageNumber = resultSet.getInt(PAGE_NUMBER_COLUMN);
            mappedBook.setPagesNumber(pageNumber);
            return mappedBook;
        } catch (SQLException e){
            LOGGER.error("Failed to map ResultSet to Book");
            throw new MapperException(e);
        }
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
