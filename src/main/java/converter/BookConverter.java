package converter;

import entity.Book;

public interface BookConverter {
    Book convertLineToBook(String line);

    String convertBookToLine(Book book);
}
