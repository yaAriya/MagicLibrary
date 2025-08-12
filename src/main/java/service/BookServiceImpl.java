package service;

import dao.BookDao;
import dao.BookDaoImpl;
import entity.Book;
import exceptions.*;
import validator.BookValidator;
import validator.Validator;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static BookServiceImpl INSTANCE;
    private BookDao bookDao;
    private Validator<Book> bookValidator;

    public static BookServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookServiceImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private BookServiceImpl() {
    }

    private static void initializeDependencies(BookServiceImpl bookService) {
        bookService.bookDao = BookDaoImpl.getInstance();
        bookService.bookValidator = BookValidator.getInstance();
    }

    @Override
    public void initializeCash() throws BookServiceException {
        try {
            bookDao.initializeCash();
        } catch (BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            return bookDao.readAllBooks();
        } catch (ObjectInitializeException | CloneNotSupportedException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            if (bookValidator.validate(book) == false && bookDao.getBooks().contains(book)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
            bookDao.add(book);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book read(long id) throws BookServiceException {
        try {
            if (bookDao.read(id) == null) {
                throw new EntityNotFoundException("Такой книги нет");
            }
            return bookDao.read(id);
        } catch (EntityNotFoundException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book update(Book book) throws BookServiceException {
        try {
            Book oldBook = read(book.getId());
            if (bookValidator.validate(book) == false) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            } else if (oldBook.getUser() != null) {
                throw new InvalidEntityException("Вы не можете обновить уже арендованную книгу");
            } else if (book.getUser() != null) {
                throw new InvalidEntityException("Пользователь обновляемой книги должен отсутствовать");
            }
            return bookDao.update(book);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws BookServiceException {
        try {
            Book readBook = read(id);
            if (id < 0 && readBook.getUser() != null) {
                throw new InvalidEntityException("Увы, Вашу книгу нельзя удалить");
            }
            bookDao.delete(readBook);
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }
}
