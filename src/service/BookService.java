package service;

import enity.Book;

import exceptions.BookServiceException;

import java.util.List;

public interface BookService {
    List<Book> readAllBooks() throws BookServiceException;

    void add(Book book) throws BookServiceException;

    Book update(Book book, int index) throws BookServiceException;

    Book read(int ID) throws BookServiceException;

    void delete(Book book) throws BookServiceException;
}
