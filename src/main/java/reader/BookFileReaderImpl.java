package reader;

import converter.BookConverterImpl;
import entity.Book;
import exceptions.BookFileReaderException;
import exceptions.ConverterException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookFileReaderImpl implements BookFileReader {
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";

    @Override
    public List<Book> readBooksFromFile() throws BookFileReaderException {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE_PATH))) {
            List<Book> books = new ArrayList<>();

            String readLine = reader.readLine();

            while (readLine != null) {
                Book book = bookConverter.convertLineToBook(readLine);
                books.add(book);
                readLine = reader.readLine();
            }
            return books;
        } catch (IOException | ConverterException e) {
            throw new BookFileReaderException(e);
        }
    }
}