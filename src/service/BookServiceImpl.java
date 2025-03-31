package service;

import dao.BookDao;

import dao.BookDaoImpl;

import enity.Book;

import exceptions.BookServiceException;

import exceptions.EntityNotFoundException;
import exceptions.ObjectInitializeException;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao bookDAO;

    public BookServiceImpl() {
        bookDAO = BookDaoImpl.getInstance();// Почему реализация в Дао, а не в сервисе, если мы с Дао именно с сервиса работаем?
    }

    @Override
    public List<Book> readAllBooks() {
       try {
           return bookDAO.readAllBooks();
       } catch (ObjectInitializeException e){
           throw new BookServiceException(e);
       }
    }

    @Override
    public void add(Book book) throws BookServiceException {
        try {
            if (book != null) {
                bookDAO.add(book);
            } else {
                throw new EntityNotFoundException("Такая книга отсутствует");
            }
        } catch (EntityNotFoundException e) {
            throw new BookServiceException(e);
        }

    }

    @Override
    public Book update(Book book, int index) throws BookServiceException {
        try {
            if (bookDAO.upDate(book, index) != null) {
                return bookDAO.upDate(book, index);
            } else {
                throw new EntityNotFoundException("Объект, который вы хотели бы обновить не найден");
            }
        } catch (EntityNotFoundException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book read(int ID) throws BookServiceException {
        try {
            if (bookDAO.read(ID) != null) {
                return bookDAO.read(ID);
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
            if (book != null) {
                bookDAO.delete(book);
            } else {
                throw new EntityNotFoundException("Удаляемая Вами книга не найдена");
            }
        } catch (EntityNotFoundException e) {
            throw new BookServiceException(e);
        }
    }
}
