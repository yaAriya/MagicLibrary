package service;

import dao.BookDao;
import entity.Book;
import entity.User;
import exceptions.BookServiceException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidEntityException;
import exceptions.ObjectInitializeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validator.BookValidator;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);
    private final BookDao bookDao;
    private final BookValidator bookValidator;
    private final UserService userService;

    public BookServiceImpl(UserService userService, BookDao bookDao, BookValidator bookValidator) {
        this.userService = userService;
        this.bookDao = bookDao;
        this.bookValidator = bookValidator;
    }

/*    public void initializeCache() { // Work with file based DAOs (!!Adding initializeCache() into interfaces)
        try {
            List<Book> books = bookDao.initializeCache();
            userService.initializeCache(books);
        } catch (BookDaoException | UserServiceException e) {
            throw new BookServiceException(e);
        }
    }*/

    @Transactional(readOnly = true)
    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            LOGGER.info("Reading all books");
            return bookDao.readAllBooks();
        } catch (ObjectInitializeException | DataAccessException e) {
            LOGGER.error("Reading all books failed", e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            List<Book> books = bookDao.readAllBooks();
            if (!bookValidator.validate(book) && books.contains(book)) {
                LOGGER.error("Book validation failed during addition");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            for (Book tempBook : books) {
                if (tempBook.getId() == book.getId()) {
                    LOGGER.error("Duplicate book id");
                    throw new InvalidEntityException("A book with this id already exists");
                }
            }
            bookDao.add(book);
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Adding books failed", e);
            throw new BookServiceException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book read(long id) throws BookServiceException {
        try {
            if (bookDao.read(id) == null) {
                LOGGER.error("Book is not found");
                throw new EntityNotFoundException("The book you are looking for has not been found");
            }
            return bookDao.read(id);
        } catch (EntityNotFoundException | DataAccessException e) {
            LOGGER.error("Reading books failed", e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public void update(Book book) throws BookServiceException {
        try {
            if (!bookValidator.validate(book)) {
                LOGGER.error("Incorrect parameters from the book");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            bookDao.update(book);
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Updating books failed", e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws BookServiceException {
        try {
            if (id < 0 && read(id).getUser() != null) {
                LOGGER.error("Deleted book has user");
                throw new InvalidEntityException("The deleted book should not has user");
            }
            bookDao.delete(id);
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Deleting users failed", e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public void rentBook(long userId, long bookId) throws BookServiceException {
        try {
            User readUser = userService.read(userId);
            Book readBook = read(bookId);
            if (readUser == null || readBook == null) {
                LOGGER.error("User or book not found");
                throw new InvalidEntityException("User or book not found");
            } else if (readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                LOGGER.error("Book already has user or user already has books");
                throw new InvalidEntityException("Book already has user or user already has books");
            }
            bookDao.rentBook(readUser, readBook);
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Renting book failed", e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public void returnBook(long userId, long bookId) throws BookServiceException {
        try {
            User readUser = userService.read(userId);
            Book readBook = read(bookId);
            if (readUser == null || readBook == null) {
                LOGGER.error("Book or user not found");
                throw new InvalidEntityException("User or book cannot be null");
            } else if (readBook.getUser() == null || readUser.getBooks().isEmpty()) {
                LOGGER.error("User has not any books or book has not user");
                throw new InvalidEntityException("The book has not user or user has not any books");
            }
            bookDao.returnBook(readUser, readBook);
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Returning book failed", e);
            throw new BookServiceException();
        }
    }
}
