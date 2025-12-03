package dao;

import config.DatabaseConfig;
import entity.Book;
import exceptions.BookDaoException;
import exceptions.MapperException;
import mapper.BookMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBasedBookDao implements BookDao {
    //private static final String readAllBooksQuery = "SELECT id, name, author, page_number, user_id  FROM books ";
    private static final String readAllBooksQuery = "SELECT b.id AS book_id, b.name AS book_name, author, page_number, u.id AS user_id, u.name AS user_name, email, age FROM books b JOIN users u ON b.user_id = u.id ORDERED BY b.id ";
    private static final String addQuery = "INSERT INTO books (name, author, page_number, user_id) VALUES (?, ?, ?, ?)";
    private static final String readQuery = "SELECT * FROM books WHERE id = ?";
    private static final String updateQuery = "UPDATE books SET name = ?, author = ?, page_number = ?, user_id = ? WHERE id = ?";
    private static final String deleteQuery = "DELETE IN books WHERE id = ?";
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Book> readAllBooks() throws BookDaoException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(readAllBooksQuery)) {

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                books.add(bookMapper.mapRSToObject(resultSet));
            }
            return books;
        } catch (SQLException | MapperException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {

            bookMapper.mapObjectToStatement(preparedStatement, book);
            preparedStatement.executeUpdate();

        } catch (SQLException | MapperException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long id) throws BookDaoException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readQuery)) {

            bookMapper.mapObjectIdToStatement(preparedStatement, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return bookMapper.mapRSToObject(resultSet);
            }
        } catch (SQLException | MapperException e) {
            throw new BookDaoException(e);
        }
        return null;
    }

    @Override
    public void update(Book book) throws BookDaoException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            bookMapper.mapUpdateObjectToStatement(preparedStatement, book);
            preparedStatement.executeUpdate();

        } catch (SQLException | MapperException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            bookMapper.mapObjectIdToStatement(preparedStatement, id);
            preparedStatement.executeUpdate();

        } catch (SQLException | MapperException e) {
            throw new BookDaoException(e);
        }
    }
}
