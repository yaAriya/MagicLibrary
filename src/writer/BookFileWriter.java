package writer;

import enity.Book;

import exceptions.BookFileWriterException;

import java.util.List;

public interface BookFileWriter {
    void addBookToFile(Book book) throws BookFileWriterException;

    void deleteBookFromFile(List<Book> books) throws BookFileWriterException;

    String convertBookToLine(Book book);
}
