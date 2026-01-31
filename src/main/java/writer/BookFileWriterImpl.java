package writer;

import converter.BookConverter;
import entity.Book;
import exceptions.BookFileWriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookFileWriterImpl implements BookFileWriter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";
    private BookConverter bookConverter;

    @Override
    public void addBookToFile(Book book) throws BookFileWriterException {
        try (FileWriter writer = new FileWriter(BOOK_FILE_PATH, true)) {
            writer.write("\n");
            writer.write(bookConverter.convertBookToLine(book));
            writer.flush();
            LOGGER.info("Adding books to file compiled successful");
        } catch (IOException e) {
            LOGGER.error("Adding books to file failed");
            throw new BookFileWriterException(e);
        }
    }

    @Override
    public void writeBookToFile(List<Book> books) throws BookFileWriterException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE_PATH))) {
            for (Book book: books) {
                String bookToLine = bookConverter.convertBookToLine((book));
                writer.write(bookToLine + "\n");
            }
            LOGGER.info("Writing books to file compiled successful");
        } catch (IOException e) {
            LOGGER.error("Writing books to file failed");
            throw new BookFileWriterException(e);
        }
    }

    public void setBookConverter(BookConverter bookConverter){
        this.bookConverter = bookConverter;
    }
}
