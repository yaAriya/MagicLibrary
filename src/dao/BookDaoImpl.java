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
    public Book update(Book book, long id) {
        for(int i = 0; i<books.size(); i++){
            if(books.get(i).getId() == id){
                books.set(i, book);
                return books.get(i);
            }
        }
        return null;
    }

    @Override
    public void delete(Book book) {
        books.remove(book);
    }

    @Override
    public Book read(long id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
}
