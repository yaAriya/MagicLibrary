package dao;

import cloner.BookCloner;
import entity.Book;
import exceptions.BookDaoException;
import exceptions.BookFileReaderException;
import exceptions.BookFileWriterException;
import reader.BookFileReader;
import reader.BookFileReaderImpl;
import writer.BookFileWriter;
import writer.BookFileWriterImpl;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private static BookDaoImpl INSTANCE;
    private List<Book> books;
    private BookFileReader bookFileReader;
    private BookFileWriter bookFileWriter;
    private BookCloner bookCloner;

    public static BookDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookDaoImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private BookDaoImpl() {
    }

    private static void initializeDependencies(BookDaoImpl bookDao) {
        bookDao.bookFileReader = BookFileReaderImpl.getInstance();
        bookDao.bookFileWriter = BookFileWriterImpl.getInstance();
        bookDao.bookCloner = BookCloner.getInstance();
    }

    @Override
    public void initializeCash() throws BookDaoException {
        try {
            books = bookFileReader.readBooksFromFile();
        } catch (BookFileReaderException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> readAllBooks(){
         return getBooks().stream()
                .map(bookCloner::apply)
                .toList();
    }

    @Override
    public void add(Book book) throws BookDaoException {
        try {
            getBooks().add(book);
            bookFileWriter.addBookToFile(book);
        } catch (BookFileWriterException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public Book read(long id){
        return getBooks().stream()
               .filter(book -> book.getId() == id)
               .findAny()
               .map(bookCloner::apply)
               .orElse(null);
    }

    @Override
    public Book update(Book book) throws BookDaoException {
        try {
            for (int i = 0; i < getBooks().size(); i++) {
                if (getBooks().get(i).getId() == book.getId()) {
                    Book realBook = getBooks().get(i);
                    realBook.setId(book.getId());
                    realBook.setName(book.getName());
                    realBook.setAuthor(book.getAuthor());
                    realBook.setPagesNumber(book.getPagesNumber());
                    bookFileWriter.writeBookToFile(getBooks());
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
            bookFileWriter.writeBookToFile(getBooks());
        } catch (BookFileWriterException e) {
            throw new BookDaoException(e);
        }
    }
}
