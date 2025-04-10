package service;

import dao.BookDao;

import dao.BookDaoImpl;

import enity.Book;

import exceptions.BookServiceException;

import exceptions.EntityNotFoundException;

import exceptions.InvalidEntityException;

import exceptions.ObjectInitializeException;

import validator.BookValidator;

import validator.BookValidatorImpl;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDAO;
    private final BookValidator bookValidator;

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
                throw new InvalidEntityException();
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
                throw new InvalidEntityException();
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
    public void delete(Book book) throws BookServiceException {
        try {
            if (bookValidator.validate(book) == true) {
                bookDAO.delete(book);
            } else {
                throw new InvalidEntityException();
            }
        } catch (InvalidEntityException e) {
            throw new BookServiceException(e);
        }
    }
}
