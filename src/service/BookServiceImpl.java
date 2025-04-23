package service;

import dao.BookDao;

import dao.BookDaoImpl;

import enity.Book;

import exceptions.*;

import validator.BookValidator;
import validator.BookValidatorImpl;

//import validator.Validator;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static BookServiceImpl instance;
    private final BookDao bookDAO;
    private final BookValidator bookValidator;

    public static BookServiceImpl getInstance(){
        if(instance == null){
            instance = new BookServiceImpl();
        }
        return instance;
    }

    public BookServiceImpl() {
        bookDAO = BookDaoImpl.getInstance();
        bookValidator = BookValidatorImpl.getInstance();
    }

    @Override
    public List<Book> readAllBooks() throws BookServiceException {
        try {
            return bookDAO.readAllBooks();
        } catch (ObjectInitializeException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            if (bookValidator.validate(book) == true) {
                bookDAO.add(book);
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
            if (bookValidator.validate(book) == true) {
                return bookDAO.update(book, book.getId());
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book read(long id) throws BookServiceException {
        try {
            if (bookDAO.read(id) != null) {
                return bookDAO.read(id);
            } else {
                throw new EntityNotFoundException("Искаемый Вами объект не найден");
            }
        } catch (EntityNotFoundException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws BookServiceException {
        try {
            if (id>=0) {
                bookDAO.delete(read(id));
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException e) {
            throw new BookServiceException(e);
        }
    }
}
