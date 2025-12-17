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
    private static final Logger logger = LogManager.getLogger();
    private static final String readAllBooksQuery = "SELECT b.id AS book_id, b.name AS book_name, author, page_number, u.id AS user_id, u.name AS user_name, email, age FROM books b LEFT JOIN users u ON b.user_id = u.id ORDER BY b.id ";
    private static final String addQuery = "INSERT INTO books (name, author, page_number, user_id) VALUES (?, ?, ?, ?)";
    private static final String readQuery = "SELECT b.id AS book_id, b.name AS book_name, author, page_number, u.id AS user_id, u.name AS user_name, email, age FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE b.id = ?";
    private static final String updateQuery = "UPDATE books SET name = ?, author = ?, page_number = ?, user_id = ? WHERE id = ?";
    private static final String deleteQuery = "DELETE IN books WHERE id = ?";
    private static final String rentOrReturnBookQuery = "UPDATE books SET user_id = ? WHERE id = ?";
    private BookMapper bookMapper;
    private DatabaseConfig databaseConfig;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public List<Book> readAllBooks() throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(readAllBooksQuery)) {

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                books.add(bookMapper.mapRSToObject(resultSet));
            }
            logger.info("Books reading completed successfully");
            return books;
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Books reading failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {

            bookMapper.mapObjectToStatement(preparedStatement, book);
            preparedStatement.executeUpdate();

            logger.info("Book adding completed successfully");
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Book adding failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long id) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuery)) {

            bookMapper.mapObjectIdToStatement(preparedStatement, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logger.info("Book reading completed successfully");
                return bookMapper.mapRSToObject(resultSet);
            }
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Book reading failed");
            throw new BookDaoException(e);
        }
        return null;
    }

    @Override
    public void update(Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            bookMapper.mapUpdateObjectToStatement(preparedStatement, book);
            preparedStatement.executeUpdate();

            logger.info("Books updating completed successfully");
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Book updating failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            bookMapper.mapObjectIdToStatement(preparedStatement, id);
            preparedStatement.executeUpdate();

            logger.info("Books deleting completed successfully");
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Book deleting failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void rentBook(User user, Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(rentOrReturnBookQuery)) {

            book.setUser(user);
            bookMapper.mapRentedBookToStatement(preparedStatement, book);

            preparedStatement.executeUpdate();
            logger.info("Book renting compile successful");
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Book renting failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void returnBook(User user, Book book) throws BookDaoException {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(rentOrReturnBookQuery)) {

            book.setUser(null);
            bookMapper.mapReturnedBookToStatement(preparedStatement, book);

            preparedStatement.executeUpdate();
            logger.info("Book returning compile successful");
        } catch (SQLException | MapperException | DatabaseConfigException e) {
            logger.error("Book returning failed");
            throw new BookDaoException(e);
        }
    }
}
