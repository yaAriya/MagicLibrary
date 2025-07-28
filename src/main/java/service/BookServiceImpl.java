package service;

import dao.BookDao;

import dao.BookDaoImpl;

import enity.Book;

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

    public void initializeDataBase() throws BookServiceException {
        try {
            bookDao.initializeDataBase();
        } catch(BookDaoException e){
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
            if (bookValidator.validate(book) == true) {
                bookDao.add(book);
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }

    }

    @Override
    public Book update(Book book) throws BookServiceException {
        try {
            if (bookValidator.validate(book) == true) {
                return bookDao.update(book.getId(), book);
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book read(long id) throws BookServiceException {
        try {
            if (bookDao.read(id) != null) {
                return bookDao.read(id);
            } else {
                throw new EntityNotFoundException("Искаемая Вами книга не найдена");
            }
        } catch (EntityNotFoundException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws BookServiceException {
        try {
            Book readBook = read(id);
            if (id >= 0 && readBook.getUser() == null) {
                bookDao.delete(readBook);
            } else {
                throw new InvalidEntityException("Увы, Вашу книгу нельзя удалить");
            }
        } catch (InvalidEntityException | BookDaoException e) {
            throw new BookServiceException(e);
        }
    }
}
