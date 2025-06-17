package reader;

import enity.Book;

import exceptions.BookFileReaderException;

import java.util.List;

public interface BookFileReader {
    List<Book> readBooksFromFile() throws BookFileReaderException;

    Book convertLineToBook(String line);
}
