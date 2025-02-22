package service;

import enity.Book;

public interface ServiceForBook {
    void add(Book book);
    Book update(Book book);
    Book read(int ID);// getByID
    void delete(Book book);
}
