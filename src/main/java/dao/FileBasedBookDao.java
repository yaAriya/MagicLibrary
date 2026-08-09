package dao;

import entity.Book;
import entity.User;
import exceptions.BookDaoException;
import exceptions.BookFileReaderException;
import exceptions.BookFileWriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import reader.BookFileReader;
import writer.BookFileWriter;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FileBasedBookDao implements BookDao {
    private static final Logger LOGGER = LogManager.getLogger(FileBasedBookDao.class);
    private List<Book> books;
    private final BookFileReader bookFileReader;
    private final BookFileWriter bookFileWriter;

    public FileBasedBookDao(BookFileReader bookFileReader,BookFileWriter bookFileWriter) {
        this.bookFileReader = bookFileReader;
        this.bookFileWriter = bookFileWriter;
    }

    public List<Book> initializeCache() throws BookDaoException {
        try {
            return bookFileReader.readBooksFromFile();
        } catch (BookFileReaderException e) {
            LOGGER.error("Cash initialization failed");
            throw new BookDaoException(e);
        }
    }

    @Override
    public List<Book> readAllBooks() {
        try {
            List<Book> clonedBooks = new ArrayList<>();
            for (Book book : getBooks()) {
                clonedBooks.add(book.clone());
            }
            LOGGER.info("Books reading completed successfully");
            return clonedBooks;
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Books reading failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Book book) {
        try {
            getBooks().add(book);
            bookFileWriter.addBookToFile(book);
            LOGGER.info("Book adding completed successfully");
        } catch (BookFileWriterException e) {
            LOGGER.error("Book adding failed");
            try {
                throw new BookDaoException(e);
            } catch (BookDaoException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Book read(long id) {
        try {
            for (Book book : getBooks()) {
                if (book.getId() == id) {
                    return book.clone();
                }
            }
            LOGGER.info("Book reading completed successfully");
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Book reading failed");
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Book book) {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            getBooks().remove(read(id));
            bookFileWriter.writeBookToFile(getBooks());
            LOGGER.info("Books deleting completed successfully");
        } catch (BookFileWriterException e) {
            LOGGER.error("Book deleting failed");
            throw new RuntimeException(e);
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
