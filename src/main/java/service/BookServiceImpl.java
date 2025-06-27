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
    private final BookDao bookDao;
    private final Validator<Book> bookValidator;

    public static BookServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookServiceImpl();
        }
        return INSTANCE;
    }

    private BookServiceImpl() {
        bookDao = BookDaoImpl.getInstance();
        bookValidator = BookValidator.getInstance();
    }

    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            return bookDao.readAllBooks();
        } catch (ObjectInitializeException e) {
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
                return bookDao.update(book, book.getId());
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
