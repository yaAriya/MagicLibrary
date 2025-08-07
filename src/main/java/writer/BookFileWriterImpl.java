package writer;

import converter.BookConverterImpl;
import entity.Book;
import exceptions.BookFileWriterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookFileWriterImpl implements BookFileWriter {

    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";

    private static BookFileWriterImpl INSTANCE;

    private BookConverterImpl bookConverter;

    public static BookFileWriterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookFileWriterImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private BookFileWriterImpl() {
    }


    private static void initializeDependencies(BookFileWriterImpl bookFileWriter) {
        bookFileWriter.bookConverter = BookConverterImpl.getInstance();

    }

    @Override
    public void addBookToFile(Book book) throws BookFileWriterException {
        try (FileWriter writer = new FileWriter(BOOK_FILE_PATH, true)) {
            writer.write("\n");
            writer.write(bookConverter.convertBookToLine(book));
            writer.flush();
        } catch (IOException e) {
            throw new BookFileWriterException(e);
        }
    }

    @Override
    public void writeBookToFile(List<Book> books) throws BookFileWriterException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE_PATH))) {
            for (int i = 0; i < books.size(); i++) {
                String bookToLine = bookConverter.convertBookToLine(books.get(i));
                writer.write(bookToLine + "\n");
            }
        } catch (IOException e) {
            throw new BookFileWriterException(e);
        }
    }
}
