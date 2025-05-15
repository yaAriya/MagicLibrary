package dao;

import enity.Book;

import exceptions.BookDaoException;
import exceptions.ObjectInitializeException;

import exceptions.BookFileReaderException;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private static BookDaoImpl INSTANCE;
    private final List<Book> books;
    private final BookFileReader BOOK_FILE_READER;

    public static BookDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDaoImpl();
        }
        return INSTANCE;
    }

    private BookDaoImpl() throws ObjectInitializeException {
        try {
            BOOK_FILE_READER = new BookFileReaderImpl();
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
    public void add(Book book) {
        books.add(book);
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
