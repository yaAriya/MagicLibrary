package mapper;

import entity.Book;
import entity.User;
import exceptions.MapperException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BookMapper implements Mapper<Book> {
    private static final Logger logger = LogManager.getLogger();
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Book mapRSToObject(ResultSet resultSet) throws MapperException {
        try {
            Book mappedBook = new Book();
            long id = resultSet.getLong("book_id");
            mappedBook.setId(id);
            String bookName = resultSet.getString("book_name");
            mappedBook.setName(bookName);
            String author = resultSet.getString("author");
            mappedBook.setAuthor(author);
            int pageNumber = resultSet.getInt("page_number");
            mappedBook.setPagesNumber(pageNumber);

            if (!resultSet.wasNull() && resultSet.getLong("user_id") > 0) {
                User mappedUser = userMapper.mapRSToObject(resultSet);
                mappedBook.setUser(mappedUser);
            } else {
                mappedBook.setUser(null);
            }
            return mappedBook;
        } catch (SQLException e) {
            logger.error("Failed to map ResultSet to Book");
            throw new MapperException(e);
        }
    }

    @Override
    public void mapObjectToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
        } catch (SQLException e) {
            logger.error("Failed to map book to statement");
            throw new MapperException(e);
        }
    }

    @Override
    public void mapObjectIdToStatement(PreparedStatement preparedStatement, long id) throws MapperException {
        try {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            logger.error("Failed to map book id to statement");
            throw new MapperException(e);
        }
    }

    @Override
    public void mapUpdateObjectToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setLong(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setLong(5, book.getId());
        } catch (SQLException e) {
            logger.error("Failed to map updated book to statement");
            throw new MapperException(e);
        }
    }

    public void mapRentedBookToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setLong(1, book.getUser().getId());
            preparedStatement.setLong(2, book.getId());
        } catch (SQLException e) {
            logger.error("Failed to map rented book to statement");
            throw new MapperException(e);
        }
    }

    public void mapReturnedBookToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setLong(2, book.getId());
        } catch (SQLException e) {
            logger.error("Failed to map returned book to statement");
            throw new MapperException(e);
        }
    }
}
