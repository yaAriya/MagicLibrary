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

import java.util.List;

public class BookDaoImpl implements BookDao {
    private static BookDaoImpl INSTANCE;
    private final List<Book> books;
    private final BookFileReader BOOK_FILE_READER;
    private final BookFileWriter BOOK_FILE_WRITER;

    public static BookDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDaoImpl();
        }
        return INSTANCE;
    }

    private BookDaoImpl() throws ObjectInitializeException {
        try {
            BOOK_FILE_READER = BookFileReaderImpl.getInstance();
            BOOK_FILE_WRITER = BookFileWriterImpl.getInstance();
            books = BOOK_FILE_READER.readBooksFromFile();
        } catch (BookFileReaderException e) {
            throw new ObjectInitializeException(e);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public List<Book> readAllBooks() {
        return getBooks();
    }

    @Override
    public void add(Book book) throws BookDaoException{
        try {
         if(!books.contains(book) && book.getUser() == null){
             books.add(book);
             BOOK_FILE_WRITER.addBookToFile(book);
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
    public void delete(Book book) {
        books.remove(book);
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
