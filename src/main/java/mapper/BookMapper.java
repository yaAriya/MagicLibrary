package mapper;

import entity.Book;
import entity.User;
import exceptions.MapperException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class BookMapper implements Mapper<Book> {
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
            throw new MapperException(e);
        }
    }

    @Override
    public void mapObjectIdToStatement(PreparedStatement preparedStatement, long id) throws MapperException {
        try {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
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
            throw new MapperException(e);
        }
    }

    public void mapRentedBookToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setLong(1, book.getUser().getId());
            preparedStatement.setLong(2, book.getId());
        } catch (SQLException e) {
            throw new MapperException(e);
        }
    }

    public void mapReturnedBookToStatement(PreparedStatement preparedStatement, Book book) throws MapperException {
        try {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setLong(2, book.getId());
        } catch (SQLException e) {
            throw new MapperException(e);
        }
    }
}
