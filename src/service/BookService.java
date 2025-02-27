package service;

import enity.Book;

public interface BookService {
    void add(Book book);
    Book update(Book book);
    Book read(int ID);// getByID
    void delete(Book book);
}
