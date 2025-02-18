package DAOClass;

import enity.Book;

import java.util.List;

public interface DAOBook {
    List<Book> getAllBooks();
    void add(Book book);
    Book upDate(final Book book);
    void delete(Book book);
    Book getByID(int ID);


}
