package reader;

import converter.BookConverterImpl;
import entity.Book;
import exceptions.BookFileReaderException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookFileReaderImpl implements BookFileReader {
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";

    private static BookFileReaderImpl INSTANCE;
    private BookConverterImpl bookConverter;

    public static BookFileReaderImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookFileReaderImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private BookFileReaderImpl() {
    }

    private static void initializeDependencies(BookFileReaderImpl bookFileReader) {
        bookFileReader.bookConverter = BookConverterImpl.getInstance();
    }

    @Override
    public List<Book> readBooksFromFile() throws BookFileReaderException {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE_PATH))) {
            List<Book> books = new ArrayList<>();

            String readerLines = reader.readLine();

            while (readerLines != null) {
                Book book = bookConverter.convertLineToBook(readerLines);
                books.add(book);
                readerLines = reader.readLine();
            }
            return books;
        } catch (IOException e) {
            throw new BookFileReaderException(e);
        }
    }
}