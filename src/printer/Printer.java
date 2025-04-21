package printer;

import enity.Book;
import enity.User;

import java.util.List;

public interface Printer {
    void printAllBooks(List<Book> books);
    void printAllUsers(List<User> users);
    void printUserObject(User userObject);
    void printBookObject(Book bookObject);
}
