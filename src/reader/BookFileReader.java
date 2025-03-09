package reader;

import enity.Book;

import java.io.IOException;

import java.util.List;

public interface BookFileReader {
    List<Book> readBooksFromFile(String filePath) throws IOException;
    Book convertLineToBook(String line);
}
