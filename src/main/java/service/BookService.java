package service;

import enity.Book;

import exceptions.BookDaoException;

import exceptions.BookServiceException;

import java.util.List;

public interface BookService {

    void initializeDataBase() throws BookServiceException, BookDaoException;

    List<Book> readAllBooks() throws BookServiceException;

    void add(Book book) throws BookServiceException;

    Book update(Book book) throws BookServiceException;

    Book read(long id) throws BookServiceException;

    void delete(long id) throws BookServiceException;
}
