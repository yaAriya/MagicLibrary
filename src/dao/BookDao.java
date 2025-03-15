package dao;

import enity.Book;

import exceptions.ObjectInitializeException;

import java.util.List;

public interface BookDao {
    List<Book> readAllBooks();

    void add(Book book) throws ObjectInitializeException;

    Book upDate(Book book);

    void delete(Book book) throws ObjectInitializeException;

    Book read(int ID) throws ObjectInitializeException;


}
