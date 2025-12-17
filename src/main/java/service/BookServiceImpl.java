package service;

import dao.BookDao;
import entity.Book;
import entity.User;
import exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.BookValidator;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final Logger logger = LogManager.getLogger();
    private BookDao bookDao;
    private BookValidator bookValidator;
    private UserService userService;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setBookValidator(BookValidator bookValidator) {
        this.bookValidator = bookValidator;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            logger.info("Reading all books");
            return bookDao.readAllBooks();
        } catch (ObjectInitializeException | BookDaoException e) {
            logger.error("Reading all books failed");
            throw new BookServiceException(e);
        }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            if (!bookValidator.validate(book) && bookDao.readAllBooks().contains(book)) {
                logger.error("Book validation failed during addition");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            for (Book tempBook : bookDao.readAllBooks()) {
                if (tempBook.getId() == book.getId()) {
                    logger.error("Duplicate book id");
                    throw new InvalidEntityException("A book with this id already exists");
                }
            }
            bookDao.add(book);
        } catch (InvalidEntityException | BookDaoException e) {
            logger.error("Adding books failed");
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book read(long id) throws BookServiceException {
        try {
            if (bookDao.read(id) == null) {
                logger.error("Book is not found");
                throw new EntityNotFoundException("The book you are looking for has not been found");
            }
            return bookDao.read(id);
        } catch (EntityNotFoundException | BookDaoException e) {
            logger.error("Reading books failed");
            throw new BookServiceException(e);
        }
    }

    @Override
    public void update(Book book) throws BookServiceException {
        try {
            Book oldBook = read(book.getId());
            if (!bookValidator.validate(book)) {
                logger.error("Incorrect parameters from the book");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            } else if (oldBook.getUser() != null) {
                logger.error("The old book has user");
                throw new InvalidEntityException("You cannot update rented book");
            } else if (book.getUser() != null) {
                logger.error("The new book has user");
                throw new InvalidEntityException("The updated book should not has user");
            }
            bookDao.update(book);
        } catch (InvalidEntityException | BookDaoException e) {
            logger.error("Updating books failed");
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws BookServiceException {
        try {
            if (id < 0 && read(id).getUser() != null) {
                logger.error("Deleted book has user");
                throw new InvalidEntityException("The deleted book should not has user");
            }
            bookDao.delete(id);
        } catch (InvalidEntityException | BookDaoException e) {
            logger.error("Deleting users failed");
            throw new BookServiceException(e);
        }
    }

    @Override
    public void rentBook(long userId, long bookId) throws BookServiceException {
        try {
            User readUser = userService.read(userId);
            Book readBook = read(bookId);
            if (readUser == null || readBook == null) {
                logger.error("User or book not found");
                throw new InvalidEntityException("User or book not found");
            } else if (readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                logger.error("Book already has user or user already has books");
                throw new InvalidEntityException("Book already has user or user already has books");
            }
            bookDao.rentBook(readUser, readBook);
        } catch (InvalidEntityException | BookDaoException e) {
            logger.error("Renting book failed");
            throw new BookServiceException(e);
        }
    }

    @Override
    public void returnBook(long userId, long bookId) throws BookServiceException {
        try {
            User readUser = userService.read(userId);
            Book readBook = read(bookId);
            if (readUser == null || readBook == null) {
                logger.error("Book or user not found");
                throw new InvalidEntityException("User or book cannot be null");
            } else if (readBook.getUser() == null) {
                logger.error("Book has not user");
                throw new InvalidEntityException("The book has not user or user has not any books");
            }
            bookDao.returnBook(readUser, readBook);

            readUser.getBooks().remove(readBook);
            userService.update(readUser);

            readBook.setUser(null);
            update(readBook);
        } catch (InvalidEntityException | BookDaoException e) {
            logger.error("Returning book failed");
            throw new BookServiceException();
        }
    }
}
