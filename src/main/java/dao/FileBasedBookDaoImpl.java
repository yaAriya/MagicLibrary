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
    private static final Logger logger = LogManager.getLogger();
    private List<Book> books;
    private BookFileReader bookFileReader;
    private BookFileWriter bookFileWriter;

    public void setBookFileReader(BookFileReader bookFileReader) {
        this.bookFileReader = bookFileReader;
    }

    public void setBookFileWriter(BookFileWriter bookFileWriter) {
        this.bookFileWriter = bookFileWriter;
    }

    @Override
    public void initializeCash() throws BookDaoException {
        try {
            books = bookFileReader.readBooksFromFile();
        } catch (BookFileReaderException e) {
            logger.error("Cash initialization failed");
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
            logger.info("Books reading completed successfully");
            return clonedBooks;
        } catch (CloneNotSupportedException e) {
            logger.error("Books reading failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void add(Book book) throws BookDaoException {
        try {
            getBooks().add(book);
            bookFileWriter.addBookToFile(book);
            logger.info("Book adding completed successfully");
        } catch (BookFileWriterException e) {
            logger.error("Book adding failed");
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
            logger.info("Book reading completed successfully");
        } catch (CloneNotSupportedException e) {
            logger.error("Book reading failed");
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
            logger.info("Books updating completed successfully");
        } catch (BookFileWriterException e) {
            logger.error("Book updating failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void delete(long id) throws BookDaoException {
        try {
            getBooks().remove(read(id));
            bookFileWriter.writeBookToFile(getBooks());
            logger.info("Books deleting completed successfully");
        } catch (BookFileWriterException e) {
            logger.error("Book deleting failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public void rentBook(User user, Book book) {
        book.setUser(user);
        user.getBooks().add(book);
        logger.info("Book renting compile successful");
    }

    @Override
    public void returnBook(User user, Book book) {
        book.setUser(null);
        user.getBooks().remove(book);
        logger.info("Book returning compile successful");
    }
}
