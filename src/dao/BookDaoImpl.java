package dao;

import enity.Book;

import reader.BookFileReader;

import reader.BookFileReaderImpl;

import java.io.IOException;

import java.util.List;

public class BookDaoImpl implements BookDao {
    private List<Book> books;
    private BookFileReader bookFileReader;// зачем вынесли если использвуется единожды. МБ локальной?

    public BookDaoImpl() throws IOException {
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
    public List<Book> readAllBooks() {
        return getBooks();
    }

    @Override
    public void add(Book book) throws IOException {
        if (book != null) {
            books.add(book);
        } else {
            throw new IllegalArgumentException("book не должена быть null");
        }
    }

    @Override
    public Book upDate(Book book) {
    int bookIndex = 0;
        getBooks().set(bookIndex, book);
        return books.get(bookIndex);
    }

        @Override
        public void delete (Book book) throws IOException {
            if (read(book.getBookID()) != null) {
                books.remove(book);
            } else {
                throw new IllegalArgumentException("book не должена быть null");
            }
        }

        @Override
        public Book read ( int ID) throws IOException {
            for (Book book : books) {
                if (book.getBookID() == ID) {
                    return book;
                }
            }
            return null;
        }
    }
