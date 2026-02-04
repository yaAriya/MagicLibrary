package dao;

import config.DatabaseConfig;
import entity.Book;
import entity.User;
import exceptions.BookDaoException;
import exceptions.DatabaseConfigException;
import exceptions.MapperException;
import mapper.BookMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBasedBookDao implements BookDao {
    private static final Logger LOGGER = LogManager.getLogger(MySQLBasedBookDao.class);
    private static final String READ_ALL_BOOKS_QUERY = "SELECT b.id AS book_id, b.name AS book_name, author, page_number, u.id AS user_id, u.name AS user_name, email, age FROM books b LEFT JOIN users u ON b.user_id = u.id ORDER BY b.id ";
    private static final String ADD_QUERY = "INSERT INTO books (name, author, page_number, user_id) VALUES (?, ?, ?, ?)";
    private static final String READ_QUERY = "SELECT b.id AS book_id, b.name AS book_name, author, page_number, u.id AS user_id, u.name AS user_name, email, age FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE b.id = ?";
    private static final String UPDATE_QUERY = "UPDATE books SET name = ?, author = ?, page_number = ?, user_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE IN books WHERE id = ?";
    private static final String RENT_OR_RETURN_BOOK_QUERY = "UPDATE books SET user_id = ? WHERE id = ?";
    private BookMapper bookMapper;
    private DatabaseConfig databaseConfig;

    @Override
    public List<Book> readAllBooks() throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(READ_ALL_BOOKS_QUERY)) {

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                books.add(bookMapper.mapResultSetToObject(resultSet));
            }
            LOGGER.info("Books reading completed successfully");
            return books;
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            LOGGER.error("Books reading failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY)) {

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }

            preparedStatement.executeUpdate();

            LOGGER.info("Book adding completed successfully");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("Book adding failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long id) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_QUERY)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    LOGGER.info("Book reading completed successfully");
                    return bookMapper.mapResultSetToObject(resultSet);
                }
            }
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            LOGGER.error("Book reading failed");
            throw new BookDaoException(e);
        }
        return null;
    }

    @Override
    public void update(Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setLong(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }
            preparedStatement.setLong(5, book.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Books updating completed successfully");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("Book updating failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("Books deleting completed successfully");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("Book deleting failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void rentBook(User user, Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(RENT_OR_RETURN_BOOK_QUERY)) {

            book.setUser(user);
            user.getBooks().add(book);
            preparedStatement.setLong(1, book.getUser().getId());
            preparedStatement.setLong(2, book.getId());

            preparedStatement.executeUpdate();
            LOGGER.info("Book renting compile successful");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("Book renting failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void returnBook(User user, Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(RENT_OR_RETURN_BOOK_QUERY)) {

            book.setUser(null);
            user.getBooks().removeIf(book1 -> book1.getId() == book.getId());
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setLong(2, book.getId());

            preparedStatement.executeUpdate();
            LOGGER.info("Book returning compile successful");
        } catch (SQLException | DatabaseConfigException e) {
            LOGGER.error("Book returning failed");
            throw new BookDaoException(e);
        }
    }

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
