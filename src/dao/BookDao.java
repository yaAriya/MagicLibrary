package dao;

import enity.Book;

import exceptions.BookDaoException;

import java.util.List;

public interface BookDao {
    List<Book> readAllBooks();

    void add(Book book);

    Book read(long id) throws BookDaoException;

    Book update(Book book, long id) throws BookDaoException;

    void delete(Book book);
}
