package reader;

import enity.Book;

import java.io.IOException;

public interface ResourceFileReader {
    void readBooksFromFile(String filePath) throws IOException;
    Book convertLineToBook(String line);
}
