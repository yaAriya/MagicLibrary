package dao;

import entity.Book;
import entity.User;

import java.util.List;

public interface BookDao {
    List<Book> readAllBooks();

    void add(Book book);

    Book read(long id);

    void update(Book book);

    void delete(long id);

    void rentBook(User user, Book book);

    void returnBook(User user, Book book);
}
