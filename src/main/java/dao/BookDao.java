package dao;

import entity.Book;
import exceptions.BookDaoException;

import java.util.List;

public interface BookDao {

    void initializeCash() throws BookDaoException;

    List<Book> getBooks();

    List<Book> readAllBooks();

    void add(Book book) throws BookDaoException;

    Book read(long id);

    Book update(Book book) throws BookDaoException;

    void delete(Book book) throws BookDaoException;
}
