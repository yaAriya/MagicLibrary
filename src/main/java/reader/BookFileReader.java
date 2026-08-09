package reader;

import entity.Book;
import exceptions.BookFileReaderException;

import java.util.List;

public interface BookFileReader {
    List<Book> readBooksFromFile() throws BookFileReaderException;
}
