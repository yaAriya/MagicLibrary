package dao;

import entity.Book;
import entity.User;
import exceptions.BookDaoException;

import java.util.List;

public interface BookDao {

    List<Book> readAllBooks() throws BookDaoException;

    void add(Book book) throws BookDaoException;

    Book read(long id) throws BookDaoException;

    void update(Book book) throws BookDaoException;

    void delete(long id) throws BookDaoException;

    void rentBook(User user, Book book) throws BookDaoException;

    void returnBook(User user, Book book) throws BookDaoException;
}
