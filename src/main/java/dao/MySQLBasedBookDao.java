package dao;

import config.DatabaseConfig;
import entity.Book;
import exceptions.BookDaoException;
import exceptions.UserDaoException;

import java.sql.*;
import java.util.*;

public class MySQLBasedBookDao implements BookDao{
    private static MySQLBasedBookDao INSTANCE;
    private List<Book> books;
    private UserDao userDao;

    public static MySQLBasedBookDao getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MySQLBasedBookDao();
            initializeDependencies(INSTANCE);
        }
            return INSTANCE;
    }

    private static void initializeDependencies(MySQLBasedBookDao mySQLBasedBookDao){
        mySQLBasedBookDao.userDao = MySQLBasedUserDao.getInstance();
    }

    private MySQLBasedBookDao(){}

    @Override
    public void initializeCash() throws BookDaoException {
        books = readAllBooks();
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> readAllBooks() throws BookDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            List<Book> books = new ArrayList<>();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id, name, author, page_number, user_id  FROM books ");

            while (resultSet.next()) {
                Book newBook = new Book();
                long id = resultSet.getLong("id");
                newBook.setId(id);
                String name = resultSet.getString("name");
                newBook.setName(name);
                String author = resultSet.getString("author");
                newBook.setAuthor(author);
                int pageNumber = resultSet.getInt("page_number");
                newBook.setPagesNumber(pageNumber);
                long userId = resultSet.getLong("user_id");
                newBook.setUser(userDao.read(userId));
                books.add(newBook.clone());
            }
            return books;
        } catch (SQLException | CloneNotSupportedException | UserDaoException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException{
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (name, author, page_number, user_id) VALUES (?, ?, ?, ?)");

            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getPagesNumber());
            if (book.getUser() != null) {
                preparedStatement.setLong(4, book.getUser().getId());
            } else {
                preparedStatement.setLong(4, Types.BIGINT);
            }
            preparedStatement.executeUpdate();
            System.out.println("Добавление в БД прошло успешно");

        } catch (SQLException e) {
            System.out.println("Добавление в БД провалено");
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long bookId) throws BookDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT FROM books WHERE id = ?");
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Book newBook = new Book();
                long id = resultSet.getLong("id");
                newBook.setId(id);
                String name = resultSet.getString("name");
                newBook.setName(name);
                String author = resultSet.getString("author");
                newBook.setAuthor(author);
                int pageNumber = resultSet.getInt("page_number");
                newBook.setPagesNumber(pageNumber);
                long userId = resultSet.getLong("user_id");
                newBook.setUser(userDao.read(userId));
                return newBook.clone();
            }
        } catch (SQLException | CloneNotSupportedException | UserDaoException e) {
            throw new BookDaoException(e);
        }
        return null;
    }

    @Override
    public void update(Book book) throws BookDaoException{
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SET IN books WHERE id = ?");
            preparedStatement.setLong(1, book.getId());
            preparedStatement.executeUpdate();

            System.out.println("Обновление прошло успешно");
        } catch (SQLException e) {
            System.out.println("Обновление провалено");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(Book book) throws BookDaoException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE IN books WHERE id = ?");
            preparedStatement.setLong(1, book.getId());
            preparedStatement.executeUpdate();

            System.out.println("Удаление из БД выполнено");

        } catch (SQLException e) {
            System.out.println("Удаление из БД провалено");
            throw new BookDaoException(e);
        }
    }
}
