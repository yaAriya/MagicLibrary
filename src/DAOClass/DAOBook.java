package DAOClass;

import enity.Book;

import java.util.List;

public interface DAOBook {
    void add(Book book);
    Book upDate(final Book book);
    void delete(Book book);
    Book read(int ID);


}
