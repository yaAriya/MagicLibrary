package cloner;

import entity.Book;
import function.CustomFunction;

public class BookCloner implements CustomFunction <Book, Book>{

    private static BookCloner INSTANCE;

    private BookCloner(){
    }

    public static BookCloner getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BookCloner();
        }
        return INSTANCE;
    }

    @Override
    public Book apply(Book book) throws RuntimeException {
        try {
            return book.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}