package writer;

import enity.Book;

import exceptions.BookFileWriterException;

import java.io.BufferedWriter;

import java.io.FileWriter;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class BookFileWriterImpl implements BookFileWriter {

    private static final String BOOK_FILE_PATH ="src/main/resources/book.txt";

    private static BookFileWriterImpl INSTANCE;

    public static BookFileWriterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookFileWriterImpl();
        }
        return INSTANCE;
    }

    private BookFileWriterImpl(){
    }

    public void addBookToFile(Book book) throws BookFileWriterException {
        try {
            FileWriter writer = new FileWriter(BOOK_FILE_PATH, true);
            writer.write("\n");
            writer.write(convertBookToLine(book));
            writer.flush();
        } catch (IOException e) {
            throw new BookFileWriterException(e);
        }


    }

    public void deleteBookFromFile(List<Book> books) throws BookFileWriterException {
        try {
            List<String> booksToLine = new ArrayList<>();

            for (int i = 0; i < books.size(); i++) {
                booksToLine.add(convertBookToLine(books.get(i)));
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE_PATH));

            for (int i = 0; i < booksToLine.size(); i++) {
                writer.write(booksToLine.get(i) + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new BookFileWriterException(e);
        }

    }

    public String convertBookToLine(Book book) {
        String parameter = ",";
        String idToString = Long.toString(book.getId());
        String pagesNumberToString = Integer.toString(book.getPagesNumber());
        String bookString = idToString + parameter + book.getName() + parameter + book.getAuthor() + parameter + pagesNumberToString;
        return bookString;
    }
}
