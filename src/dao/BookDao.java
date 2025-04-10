package dao;

import enity.Book;

import java.util.List;

public interface BookDao {
    List<Book> readAllBooks();

    void add(Book book);

    Book update(Book book, long id);

    void delete(Book book);

    Book read(long id);
}
