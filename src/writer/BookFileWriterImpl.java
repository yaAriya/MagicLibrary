package writer;

import enity.Book;

import exceptions.BookFileWriterException;

import java.io.FileWriter;

import java.io.IOException;

public class BookFileWriterImpl implements BookFileWriter {

    String filePath = "resources/book.txt";

    private static BookFileWriterImpl INSTANCE;

    public static BookFileWriterImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BookFileWriterImpl();
        }
        return INSTANCE;
    }
    public void addBookToFile(Book book) throws BookFileWriterException {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write("\n");
            writer.write(convertBookToLine(book));
            writer.flush();
        } catch (IOException e){
            throw new BookFileWriterException(e);
        }


    }
    public String convertBookToLine(Book book){
        String parameter = ",";
        String idToString = Long.toString(book.getId());
        String pagesNumberToString = Integer.toString(book.getPagesNumber());
        String bookString = idToString + parameter + book.getName() + parameter + book.getAuthor() + parameter + pagesNumberToString;
        return bookString;
    }
}
