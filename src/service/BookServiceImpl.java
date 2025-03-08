package service;

import DAOClass.BookDAO;

import DAOClass.BookDAOImpl;

import enity.Book;

import java.io.IOException;

public class BookServiceImpl implements BookService {
    @Override
    public void readBookFromFile(String filePath) throws IOException {
        BookDAO bookDAO = new BookDAOImpl();
        bookDAO.readBookFromFile(filePath);
    }

    @Override
    public void add(Book book) {
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book read(int ID) {
        BookDAOImpl bookDAO = new BookDAOImpl();
        Book findBookByID = bookDAO.read(ID);
        return findBookByID;
    }

    @Override
    public void delete(Book book) {

    }
}
