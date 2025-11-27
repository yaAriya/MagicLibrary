package dao;

import entity.Book;
import exceptions.BookDaoException;
import exceptions.BookFileReaderException;
import exceptions.BookFileWriterException;
import reader.BookFileReader;
import reader.BookFileReaderImpl;
import writer.BookFileWriter;
import writer.BookFileWriterImpl;

import java.util.ArrayList;
import java.util.List;

public class FileBasedBookDao implements BookDao {
    private static FileBasedBookDao INSTANCE;
    private List<Book> books;
    private BookFileReader bookFileReader;
    private BookFileWriter bookFileWriter;

    public static FileBasedBookDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileBasedBookDao();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private FileBasedBookDao() {
    }

    private static void initializeDependencies(FileBasedBookDao bookDao) {
        bookDao.bookFileReader = BookFileReaderImpl.getInstance();
        bookDao.bookFileWriter = BookFileWriterImpl.getInstance();
    }

    public void initializeCash() throws BookDaoException {
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
    public List<Book> readAllBooks() throws BookDaoException {
        try {
            List<Book> clonedBooks = new ArrayList<>();
            for (Book book : getBooks()) {
                clonedBooks.add(book.clone());
            }
            return clonedBooks;
        } catch (CloneNotSupportedException e) {
            throw new BookDaoException(e);
        }
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
    public Book read(long id) throws BookDaoException {
        try{
            for(Book book: getBooks()){
                if(book.getId() == id){
                    return book.clone();
                }
            }
        } catch(CloneNotSupportedException e){
            throw new BookDaoException(e);
        }
        return null;
    }

    @Override
    public void update(Book book) throws BookDaoException {
        try {
            for (int i = 0; i < getBooks().size(); i++) {
                if (getBooks().get(i).getId() == book.getId()) {
                    Book realBook = getBooks().get(i);
                    realBook.setId(book.getId());
                    realBook.setName(book.getName());
                    realBook.setAuthor(book.getAuthor());
                    realBook.setPagesNumber(book.getPagesNumber());
                    bookFileWriter.writeBookToFile(getBooks());
                }
            }
        } catch (BookFileWriterException e) {
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        try {
            getBooks().remove(read(id));
            bookFileWriter.writeBookToFile(getBooks());
        } catch (BookFileWriterException e) {
            throw new BookDaoException(e);
        }
    }
}
