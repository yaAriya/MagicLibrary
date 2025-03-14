package dao;

import enity.Book;

import java.io.IOException;

import java.util.List;

public interface BookDao {
    List<Book> readAllBooks();

    void add(Book book) throws IOException;

    Book upDate(Book book);

    void delete(Book book) throws IOException;

    Book read(int ID) throws IOException;


}
