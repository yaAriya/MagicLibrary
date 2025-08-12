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
    public void printAllBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("\n");
    }

    @Override
    public void printAllUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("\n");
    }

    @Override
    public void printUserObject(User userObject) {
        System.out.println("Данные пользователя: " + userObject + "\n");
    }

    public void printBookObject(Book bookObject) {
        System.out.println("Данные книги: " + bookObject + "\n");
    }

    @Override
    public void printUserBooks(List<Book> userBooks) {
        System.out.println("Книги, выданные на руки пользователю: " + "\n");
        for (Book userBook : userBooks) {
            System.out.println(userBook);
        }
        System.out.println("\n");
    }
}

