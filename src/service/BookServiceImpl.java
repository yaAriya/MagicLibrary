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
    private final BookDao BOOK_DAO;
    private final Validator<Book> BOOK_VALIDATOR;

    public static BookServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookServiceImpl();
        }
        return INSTANCE;
    }

    public BookServiceImpl() {
        BOOK_DAO = BookDaoImpl.getInstance();
        BOOK_VALIDATOR = BookValidator.getInstance();
    }

    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            return BOOK_DAO.readAllBooks();
        } catch (ObjectInitializeException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            if (BOOK_VALIDATOR.validate(book) == true) {
                BOOK_DAO.add(book);
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException e) {
            throw new BookServiceException(e);
        }

    }

    @Override
    public Book update(Book book) throws BookServiceException {
        try {
            if (BOOK_VALIDATOR.validate(book) == true) {
                return BOOK_DAO.update(book, book.getId());
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
            if (BOOK_DAO.read(id) != null) {
                return BOOK_DAO.read(id);
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
                BOOK_DAO.delete(read(id));
            } else {
                throw new InvalidEntityException("Увы, Вашу книгу нельзя удалить");
            }
        } catch (InvalidEntityException e) {
            throw new BookServiceException(e);
        }
    }
}
