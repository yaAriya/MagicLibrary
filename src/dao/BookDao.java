package dao;

import enity.Book;

import exceptions.ObjectInitializeException;

import java.util.List;

public interface BookDao {
    List<Book> readAllBooks();

    void add(Book book);

    Book upDate(Book book, int index);

    void delete(Book book);

    Book read(int ID);
}
