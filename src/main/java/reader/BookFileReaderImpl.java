package reader;

import converter.BookConverter;
import entity.Book;
import exceptions.BookFileReaderException;
import exceptions.ConverterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookFileReaderImpl implements BookFileReader {
    private static final Logger LOGGER = LogManager.getLogger(BookFileReaderImpl.class);
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";
    private final BookConverter bookConverter;

    public BookFileReaderImpl(BookConverter bookConverter){
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
            LOGGER.info("Reading books from file compiled successful");
            return books;
        } catch (IOException | ConverterException e) {
            LOGGER.error("Reading books from file failed");
            throw new BookFileReaderException(e);
        }
    }
}