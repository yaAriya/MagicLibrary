package DAOClass;

import enity.Book;

public interface BookDAO {
    void add(Book book);
    Book upDate(final Book book);
    void delete(Book book);
    Book read(int ID);


}
