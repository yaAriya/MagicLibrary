package DAOClass;

import enity.Book;

import java.io.IOException;

import java.util.List;

public interface BookDAO {
    List<Book> readBookFromFile(String filePath) throws IOException;
    void add(Book book);
    Book upDate(final Book book);
    void delete(Book book);
    Book read(int ID, String filePath) throws IOException;


}
