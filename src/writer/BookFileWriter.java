package writer;

import enity.Book;

import exceptions.BookFileWriterException;

public interface BookFileWriter {
    void addBookToFile(Book book) throws BookFileWriterException;
    String convertBookToLine(Book book);
}
