package service;

import DAOClass.BookDAO;

import DAOClass.BookDAOImpl;

import enity.Book;

import java.io.IOException;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookDAO bookDAO = new BookDAOImpl();

    public BookServiceImpl() throws IOException {
    }

    @Override
    public List<Book> readAllBooks(){
         return bookDAO.readAllBooks();
    }

    @Override
    public void add(Book book) throws IOException {
        bookDAO.add(book);
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book read(int ID) throws IOException {
        return bookDAO.read(ID);
    }

    @Override
    public void delete(Book book) throws IOException {
        bookDAO.delete(book);

    }
}
