package writer;

import converter.BookConverter;
import entity.Book;
import exceptions.BookFileWriterException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookFileWriterImpl implements BookFileWriter {
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";
    private static BookConverter bookConverter;

    public void setBookConverter(BookConverter bookConverter){
        BookFileWriterImpl.bookConverter = bookConverter;
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
            for (Book book: books) {
                String bookToLine = bookConverter.convertBookToLine((book));
                writer.write(bookToLine + "\n");
            }
        } catch (IOException e) {
            throw new BookFileWriterException(e);
        }
    }
}
