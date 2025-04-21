package printer;

import enity.Book;

import enity.User;

import java.util.List;

public class PrinterImpl implements Printer{
    @Override
    public void printAllBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("\n"); // Почему сразу 2 отсупа?
    }

    @Override
    public void printAllUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("\n");
    }

    @Override
    public void printUserObject(User userObject){
        System.out.println("Данные пользователя: " + userObject + "\n");
    }

    public void printBookObject(Book bookObject){
        System.out.println("Данные книги: " + bookObject + "\n");
    }
}

