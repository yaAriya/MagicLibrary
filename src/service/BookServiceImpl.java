package service;

import dao.BookDao;

import dao.BookDaoImpl;

import enity.Book;

import exceptions.BookServiceException;

import exceptions.ObjectInitializeException;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDAO;

    public BookServiceImpl() {
        bookDAO = BookDaoImpl.getInstance();
    }

    @Override
    public List<Book> readAllBooks() {
        return bookDAO.readAllBooks();
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            bookDAO.add(book);
        } catch (ObjectInitializeException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book update(Book book) {
        return bookDAO.upDate(book);
    }

    @Override
    public Book read(int ID) throws BookServiceException {
        try {
            return bookDAO.read(ID);
        } catch (ObjectInitializeException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public void delete(Book book) throws BookServiceException {
        try {
            bookDAO.delete(book);
        } catch (ObjectInitializeException e) {
            throw new BookServiceException(e);
        }
    }
}
