package reader;

import converter.BookConverter;
import entity.Book;
import exceptions.BookFileReaderException;
import exceptions.ConverterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookFileReaderImpl implements BookFileReader {
    private static final Logger logger = LogManager.getLogger();
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";
    private BookConverter bookConverter;

    public void setBookConverter(BookConverter bookConverter){
        this.bookConverter = bookConverter;
    }

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