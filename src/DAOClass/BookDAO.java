package DAOClass;

import enity.Book;

import java.io.IOException;

public interface BookDAO {
    void readBookFromFile(String filePath) throws IOException;
    void add(Book book);
    Book upDate(final Book book);
    void delete(Book book);
    Book read(int ID);


}
