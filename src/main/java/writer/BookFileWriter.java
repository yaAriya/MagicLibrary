package writer;

import entity.Book;
import exceptions.BookFileWriterException;

import java.util.List;

public interface BookFileWriter {
    void addBookToFile(Book book) throws BookFileWriterException;

    void writeBookToFile(List<Book> books) throws BookFileWriterException;
}
