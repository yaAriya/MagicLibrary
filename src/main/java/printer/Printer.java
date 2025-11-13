package printer;

import entity.Book;
import entity.User;

import java.util.List;
import java.util.Map;

public interface Printer {
    void printAllUsersFromMap(Map<Long, User> users);

    void printAllBooks(List<Book> books);

    void printAllUsers(List<User> users);

    void printUserObject(User userObject);

    void printBookObject(Book bookObject);

    void printUserBooks(List<Book> userBooks);
}
