package service;

import enity.Book;

import java.io.IOException;

import java.util.List;

public interface BookService {
    //Book changeBook(Book book);
    List<Book> readAllBooks();

    void add(Book book) throws IOException;

    Book update(Book book);

    Book read(int ID) throws IOException;

    void delete(Book book) throws IOException;
}
