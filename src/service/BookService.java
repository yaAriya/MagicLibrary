package service;

import enity.Book;

import java.io.IOException;

import java.util.List;

public interface BookService {
    List<Book> readBookFromFile(String filePath) throws IOException;
    void add(Book book);
    Book update(Book book);
    Book read(int ID, String filePath) throws IOException;
    void delete(Book book);
}
