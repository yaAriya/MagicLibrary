package dao;

import enity.Book;

import exceptions.BookDaoException;

import exceptions.BookFileWriterException;
import exceptions.ObjectInitializeException;

import exceptions.BookFileReaderException;

import reader.BookFileReader;

import reader.BookFileReaderImpl;
import writer.BookFileWriter;
import writer.BookFileWriterImpl;

import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private static BookDaoImpl INSTANCE;
    private final List<Book> books;
    private final BookFileReader bookFileReader;
    private final BookFileWriter bookFileWriter;

    public static BookDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDaoImpl();
        }
        return INSTANCE;
    }

    private BookDaoImpl() throws ObjectInitializeException {
        try {
            bookFileReader = BookFileReaderImpl.getInstance();
            bookFileWriter = BookFileWriterImpl.getInstance();
            books = bookFileReader.readBooksFromFile();
        } catch (BookFileReaderException e) {
            throw new ObjectInitializeException(e);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public List<Book> readAllBooks() {
        List<Book> booksCopy = new ArrayList<>();
        booksCopy.addAll(getBooks());
        return booksCopy;
    }

    @Override
    public void add(Book book) throws BookDaoException{
        try {
         if(!books.contains(book) && book.getUser() == null){
             books.add(book);
             bookFileWriter.addBookToFile(book);
         }
        } catch (BookFileWriterException e){
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book update(Book book, long id) throws BookDaoException {
        try {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getId() == id) {
                    books.set(i, book);
                    return (Book) books.get(i).clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            throw new BookDaoException(e);
        }

    }

    @Override
    public void delete(Book book) throws BookDaoException {
        try {
            books.remove(book);
            bookFileWriter.deleteBookFromFile(books);
        } catch (BookFileWriterException e){
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long id) throws BookDaoException {
        try {
            for (Book book : books) {
                if (book.getId() == id) {
                    return (Book) book.clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            throw new BookDaoException(e);
        }
    }
}
