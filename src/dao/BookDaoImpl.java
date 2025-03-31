package dao;

import enity.Book;

import exceptions.ObjectInitializeException;

import exceptions.BookFileReaderException;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private static BookDaoImpl instance;

    public static BookDaoImpl getInstance() {
        if (instance == null) {
            instance = new BookDaoImpl();
        }
        return instance;
    }

    private final List<Book> books;
    private final BookFileReader bookFileReader;

    private BookDaoImpl() throws ObjectInitializeException {
        try {
            bookFileReader = new BookFileReaderImpl();
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
        return getBooks();
    }

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public Book upDate(Book book, int index) {
        getBooks().set(index, book);
        return books.get(index);
    }

    @Override
    public void delete(Book book) {
        books.remove(book);
    }

    @Override
    public Book read(int ID) {
        for (Book book : books) {
            if (book.getBookID() == ID) {
                return book;
            }
        }
        return null;
    }
}
