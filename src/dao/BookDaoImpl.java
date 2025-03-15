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

    private List<Book> books;
    private BookFileReader bookFileReader;

    private BookDaoImpl() throws ObjectInitializeException {//Object initialEx.
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
    public void add(Book book) throws ObjectInitializeException {
        if (book != null) {
            books.add(book);
        } else {
            throw new IllegalArgumentException("book не должена быть null");
        }
    }

    @Override
    public Book upDate(Book book) {
        int bookIndex = 0;
        getBooks().set(bookIndex, book);
        return books.get(bookIndex);
    }

    @Override
    public void delete(Book book) throws ObjectInitializeException {
        if (read(book.getBookID()) != null) {
            books.remove(book);
        } else {
            throw new IllegalArgumentException("book не должена быть null");
        }
    }

    @Override
    public Book read(int ID) throws ObjectInitializeException {
        for (Book book : books) {
            if (book.getBookID() == ID) {
                return book;
            }
        }
        return null;
    }
}
