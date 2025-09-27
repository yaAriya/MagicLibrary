package printer;

import entity.Book;
import entity.User;

import java.util.List;

public class PrinterImpl implements Printer {
    private static PrinterImpl INSTANCE;

    public static PrinterImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PrinterImpl();
        }
        return INSTANCE;
    }

    @Override
    public void printAllBooks(List<Book> books){
        books
            .forEach(System.out::println);
        System.out.println("\n");
    }

    @Override
    public void printAllUsers(List<User> users) {
       users
            .forEach(System.out::println);
        System.out.println("\n");
    }

    @Override
    public void printUserObject(User userObject) {
        System.out.println("Данные пользователя: " + userObject + "\n");
    }

    @Override
    public void printBookObject(Book bookObject) {
        System.out.println("Данные книги: " + bookObject + "\n");
    }

    @Override
    public void printUserBooks(List<Book> userBooks) {
        System.out.println("Книги, выданные на руки пользователю: " + "\n");
        userBooks
                .forEach(System.out::println);
        System.out.println("\n");
    }
}

