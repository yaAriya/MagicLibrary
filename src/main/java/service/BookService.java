package service;

import entity.Book;
import exceptions.BookServiceException;

import java.util.List;

public interface BookService {

    List<Book> readAllBooks() throws BookServiceException;

    void add(Book book) throws BookServiceException;

    void update(Book book) throws BookServiceException;

    Book read(long id) throws BookServiceException;

    void delete(long id) throws BookServiceException;

    void rentBook(long userId, long bookId) throws BookServiceException;

    void returnBook(long userId, long bookId) throws BookServiceException;
}
