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
    private List<Book> books;
    private BookFileReader bookFileReader;
    private BookFileWriter bookFileWriter;

    public static BookDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDaoImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private BookDaoImpl() {
    }

    private static void initializeDependencies(BookDaoImpl bookDao) throws ObjectInitializeException {
        bookDao.bookFileReader = BookFileReaderImpl.getInstance();
        bookDao.bookFileWriter = BookFileWriterImpl.getInstance();
    }

    public void initializeDataBase() throws BookDaoException {
        try {
            books = bookFileReader.readBooksFromFile();
        } catch (BookFileReaderException e) {
            throw new BookDaoException(e);
        }

    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> readAllBooks() throws CloneNotSupportedException {
        List<Book> clonedBooks = new ArrayList<>();

        for (Book book : getBooks()) {
            clonedBooks.add(book.clone());
        }
        return clonedBooks;
    }

    @Override
    public void add(Book book) throws BookDaoException {
        try {
            if (!getBooks().contains(book)) {
                getBooks().add(book);
                bookFileWriter.addBookToFile(book);
            }
        } catch (BookFileWriterException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book update(long id, Book updateBook) throws BookDaoException {
        try {
            for (int i = 0; i < getBooks().size(); i++) {
                if (getBooks().get(i).getId() == id) {
                    getBooks().set(i, updateBook);
                    bookFileWriter.updateBookInFile(getBooks());
                    return getBooks().get(i).clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException | BookFileWriterException e) {
            throw new BookDaoException(e);
        }

    }

    @Override
    public void delete(Book book) throws BookDaoException {
        try {
            getBooks().remove(book);
            bookFileWriter.deleteBookFromFile(getBooks());
        } catch (BookFileWriterException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long id) throws BookDaoException {
        try {
            for (Book book : getBooks()) {
                if (book.getId() == id) {
                    return book.clone();
                }
            }
            return null;
        } catch (CloneNotSupportedException e) {
            throw new BookDaoException(e);
        }
    }
}
