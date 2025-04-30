package dao;

import enity.Book;

import exceptions.ObjectInitializeException;

import exceptions.BookFileReaderException;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private static BookDaoImpl INSTANCE;

    public static BookDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDaoImpl();
        }
        return INSTANCE;
    }

    private final List<Book> BOOKS;
    private final BookFileReader BOOK_FILE_READER;

    private BookDaoImpl() throws ObjectInitializeException {
        try {
            BOOK_FILE_READER = new BookFileReaderImpl();
            BOOKS = BOOK_FILE_READER.readBooksFromFile();
        } catch (BookFileReaderException e) {
            throw new ObjectInitializeException(e);
        }
    }

    public List<Book> getBooks() {
        return BOOKS;
    }

    @Override
    public List<Book> readAllBooks() {
        return getBooks();
    }

    @Override
    public void add(Book book) {
        BOOKS.add(book);
    }

    @Override
    public Book update(Book book, long id) {
        for(int i = 0; i<BOOKS.size(); i++){
            if(BOOKS.get(i).getId() == id){
                BOOKS.set(i, book);
                return BOOKS.get(i);
            }
        }
        return null;
    }

    @Override
    public void delete(Book book) {
        BOOKS.remove(book);
    }

    @Override
    public Book read(long id) {
        for (Book book : BOOKS) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
}
