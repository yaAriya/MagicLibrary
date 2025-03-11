package DAOClass;

import enity.Book;

import java.io.IOException;

import java.util.List;

public interface BookDAO {
    List<Book> readAllBooks();
    void add(Book book) throws IOException;
    Book upDate(final Book book);
    void delete(Book book) throws IOException;
    Book read(int ID) throws IOException;


}
