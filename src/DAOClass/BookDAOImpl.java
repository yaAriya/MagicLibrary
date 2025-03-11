package DAOClass;

import enity.Book;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.io.IOException;

import java.util.List;

public class BookDAOImpl implements BookDAO {
    private List<Book> books;
    private BookFileReader bookFileReader;

    public BookDAOImpl() throws IOException {
        bookFileReader = new BookFileReaderImpl();
        books = bookFileReader.readBooksFromFile();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> readAllBooks(){
        return getBooks();
    }

    @Override
    public void add(Book book) throws IOException {
        if (book != null) {
            books.add(book);
        } else {
            throw new IllegalArgumentException("book не должен быть null");
        }
    }

    @Override
    public Book upDate(Book book) {
        return null;
    }

    @Override
    public void delete(Book book) throws IOException {
        if(read(book.getBookID())!= null){
            books.remove(book);
        } else {
            System.out.println("Ваш объект не найден");// Налл поинтер эксепш?
        }
    }

    @Override
    public Book read(int ID) throws IOException {
        for (Book book : books) {
            if (book.getBookID() == ID) {
                return book;
            }
        }
        return null;
    }
}
