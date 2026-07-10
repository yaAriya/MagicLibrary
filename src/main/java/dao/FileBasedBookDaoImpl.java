package dao;

import entity.Book;
import entity.User;
import exceptions.BookDaoException;
import exceptions.BookFileReaderException;
import exceptions.BookFileWriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.BookFileReader;
import writer.BookFileWriter;

import java.util.ArrayList;
import java.util.List;

public class FileBasedBookDaoImpl implements FileBasedBookDao {
    private static final Logger LOGGER = LogManager.getLogger(FileBasedBookDaoImpl.class);
    private List<Book> books;
    private BookFileReader bookFileReader;
    private BookFileWriter bookFileWriter;

    @Override
    public void initializeCash() throws BookDaoException {
        try {
            books = bookFileReader.readBooksFromFile();
        } catch (BookFileReaderException e) {
            LOGGER.error("Cash initialization failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public List<Book> readAllBooks() throws BookDaoException {
        try {
            List<Book> clonedBooks = new ArrayList<>();
            for (Book book : getBooks()) {
                clonedBooks.add(book.clone());
            }
            LOGGER.info("Books reading completed successfully");
            return clonedBooks;
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Books reading failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException {
        try {
            getBooks().add(book);
            bookFileWriter.addBookToFile(book);
            LOGGER.info("Book adding completed successfully");
        } catch (BookFileWriterException e) {
            LOGGER.error("Book adding failed");
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
            LOGGER.info("Book reading completed successfully");
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Book reading failed");
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
                    realBook.setPages(book.getPages());
                    bookFileWriter.writeBookToFile(getBooks());
                }
            }
            LOGGER.info("Books updating completed successfully");
        } catch (BookFileWriterException e) {
            LOGGER.error("Book updating failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        try {
            getBooks().remove(read(id));
            bookFileWriter.writeBookToFile(getBooks());
            LOGGER.info("Books deleting completed successfully");
        } catch (BookFileWriterException e) {
            LOGGER.error("Book deleting failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void rentBook(User user, Book book) {
        book.setUser(user);
        user.getBooks().add(book);
        LOGGER.info("Book renting compile successful");
    }

    @Override
    public void returnBook(User user, Book book) {
        book.setUser(null);
        user.getBooks().remove(book);
        LOGGER.info("Book returning compile successful");
    }

    public void setBookFileReader(BookFileReader bookFileReader) {
        this.bookFileReader = bookFileReader;
    }

    public void setBookFileWriter(BookFileWriter bookFileWriter) {
        this.bookFileWriter = bookFileWriter;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
