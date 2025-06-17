package reader;

import enity.Book;

import exceptions.BookFileReaderException;

import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class BookFileReaderImpl implements BookFileReader {
    private final String PARAMETER;
    private final String BOOK_FILE_PATH;
    private static BookFileReaderImpl INSTANCE;
    public static BookFileReader getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BookFileReaderImpl();
        }
        return INSTANCE;
    }

    public BookFileReaderImpl() {
        BOOK_FILE_PATH = "src/main/resources/book.txt";
        PARAMETER = ",";
    }

    @Override
    public List<Book> readBooksFromFile() throws BookFileReaderException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE_PATH));
            List<String> readLinesFromBookFile = new ArrayList<>();

            String readerLines = reader.readLine();

            while(readerLines != null){
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
        String [] parameters = line.split(PARAMETER);

        Book book = new Book();
        book.setId(Integer.parseInt(parameters [0]));
        book.setName(parameters[1]);
        book.setAuthor(parameters[2]);
        book.setPagesNumber(Integer.parseInt(parameters[3]));

        return new Book(book.getId(), book.getName(), book.getAuthor(), book.getPagesNumber());
    }
}