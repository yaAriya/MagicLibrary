package service;

import DAOClass.BookDAO;

import DAOClass.BookDAOImpl;

import enity.Book;

import java.io.IOException;

import java.util.List;

public class BookServiceImpl implements BookService {
    @Override
    public List<Book> readBookFromFile(String filePath) throws IOException {
        BookDAO bookDAO = new BookDAOImpl();
        return bookDAO.readBookFromFile(filePath);
    }

    @Override
    public void add(Book book) {
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book read(int ID, String filePath) throws IOException {
        BookDAOImpl bookDAO = new BookDAOImpl();
        Book findBookByID = bookDAO.read(ID, filePath);
        return findBookByID;
    }

    @Override
    public void delete(Book book) {

    }
}
