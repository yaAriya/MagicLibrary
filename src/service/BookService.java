package service;

import DAOClass.BookDAOImplementation;
import enity.Book;

public class BookService implements ServiceForBook{


    @Override
    public void add(Book book) {
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Book read(int ID) {
        BookDAOImplementation bookDAO = new BookDAOImplementation();
        Book findBookByID = bookDAO.read(ID);
        return findBookByID;
    }

    @Override
    public void delete(Book book) {

    }
}
