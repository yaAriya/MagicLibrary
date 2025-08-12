package dao;

import entity.Book;
import entity.User;
import exceptions.BookDaoException;

import java.util.List;

public interface BookDao {

    void initializeCash() throws BookDaoException;

    List<Book> getBooks();

    List<Book> readAllBooks() throws CloneNotSupportedException;

    void add(Book book) throws BookDaoException;

    Book read(long id) throws BookDaoException;

    Book update(Book book) throws BookDaoException;

    void delete(Book book) throws BookDaoException;
}
