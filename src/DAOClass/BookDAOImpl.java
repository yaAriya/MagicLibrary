package DAOClass;

import enity.Book;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.io.IOException;

import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public List<Book> readBookFromFile(String filePath) throws IOException {
        BookFileReader bookFileReader = new BookFileReaderImpl();
        return bookFileReader.readBooksFromFile(filePath);
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
    public Book read(int ID, String filePath) throws IOException {
        List<Book> books = readBookFromFile(filePath);
        for(int i = 0; i< books.size(); i++) {
            if (books.get(i).getBookID() == ID) {
                return books.get(i);
            }
        }
        return null;
    }
}
