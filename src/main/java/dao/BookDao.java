package dao;

import entity.Book;
import exceptions.BookDaoException;

import java.util.List;

public interface BookDao {

    void initializeCash() throws BookDaoException;

    List<Book> getBooks();

    void setBooks(List<Book> books);

    List<Book> readAllBooks() throws BookDaoException;

    void add(Book book) throws BookDaoException;

    Book read(long id) throws BookDaoException;

    void update(Book book) throws BookDaoException;

    void delete(Book book) throws BookDaoException;
}
