package initializer;

import DAOClass.BookDAOImplementation;
import enity.Book;

import java.util.ArrayList;

import java.util.List;

public class BookInitializer {
    public Book splitBookString(String bookText) {
        Book bookObject = new Book();
        List<Book> books = new ArrayList<>();
        String[] bookString = bookText.split(",");
        for (String word : bookString) {
            System.out.println(word); // в итоге возвращем книгу с полями типа Book
        }
        return bookObject;
    }
}
