package reader;

import enity.Book;

import exceptions.BookFileReaderException;

import service.BookServiceImpl;
import service.UserServiceImpl;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class BookFileReaderImpl implements BookFileReader {
    private static final String PARAMETER = ",";
    private static final String BOOK_FILE_PATH = "src/main/resources/book.txt";

    private static BookFileReaderImpl INSTANCE;

    private BookServiceImpl bookService;

    //private UserServiceImpl userService;

    public static BookFileReaderImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookFileReaderImpl();
           initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private BookFileReaderImpl() {
    }

    private static void initializeDependencies(BookFileReaderImpl bookFileReader) {
        bookFileReader.bookService = BookServiceImpl.getInstance();
    }

    @Override
    public List<Book> readBooksFromFile() throws BookFileReaderException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE_PATH));
            List<String> readLinesFromBookFile = new ArrayList<>();

            String readerLines = reader.readLine();

            while (readerLines != null) {
                readLinesFromBookFile.add(readerLines);
                readerLines = reader.readLine();
            }

            List<Book> books = new ArrayList<>();

            for (String line : readLinesFromBookFile) {
                Book book = convertLineToBook(line);
                books.add(book);
            }
            return books;
        } catch (IOException e) {
            throw new BookFileReaderException(e);
        }
    }

    @Override
    public Book convertLineToBook(String line) {
        String[] parameters = line.split(PARAMETER);

        if (parameters.length <= 4) {
            Book book = new Book();
            book.setId(Integer.parseInt(parameters[0]));
            book.setName(parameters[1]);
            book.setAuthor(parameters[2]);
            book.setPagesNumber(Integer.parseInt(parameters[3]));

            return book;
        } else {
            Book book = new Book();
            book.setId(Integer.parseInt(parameters[0]));
            book.setName(parameters[1]);
            book.setAuthor(parameters[2]);
            book.setPagesNumber(Integer.parseInt(parameters[3]));

               // book.setUser(userService.read(Long.parseLong(parameters[4])));// Если пользователь null не вызывать read Метод
                return book;
        }
    }
}