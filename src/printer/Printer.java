package printer;

import enity.Book;
import enity.User;

import java.util.List;

public class Printer {
    public static void printBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public static void printUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }
}

