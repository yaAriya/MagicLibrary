package DAOClass;

import enity.Book;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.io.IOException;

public class BookDAOImpl implements BookDAO {
    @Override
    public void readBookFromFile(String filePath) throws IOException {
        BookFileReader bookFileReader = new BookFileReaderImpl();
        bookFileReader.readBooksFromFile(filePath);
    }

    @Override
    public void add(Book book) {

    }

    @Override
    public Book upDate(Book book) {
        return null;
    }

    @Override
    public void delete(Book book) {

    }

    @Override
    public Book read(int ID) {
        /*BookDAOImpl bookDAO = BookDAOImpl();
        List<Book> books = bookDAO.initializeBooks();
        for(int i = 0; i< books.size(); i++){
            if(books.get(i).getBookID() == ID){
                return books.get(i);
            }
        }*/
        return null;

    }
}
