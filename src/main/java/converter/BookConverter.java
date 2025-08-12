package converter;

import entity.Book;
import exceptions.ConverterException;

public interface BookConverter {
    Book convertLineToBook(String line) throws ConverterException;

    String convertBookToLine(Book book);
}
