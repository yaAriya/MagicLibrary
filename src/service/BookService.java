package service;

import enity.Book;

import java.io.IOException;

public interface BookService {
    void readBookFromFile(String filePath) throws IOException;
    void add(Book book);
    Book update(Book book);
    Book read(int ID);// getByID
    void delete(Book book);
}
