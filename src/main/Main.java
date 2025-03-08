package main;

import service.BookService;

import service.BookServiceImpl;

import service.UserService;

import service.UserServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
       /* Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свой ID: ");
        int userID = scanner.nextInt();
        System.out.println("Введите ID книги, которую Вы бы хотели найти: ");
        int bookID = scanner.nextInt();
        scanner.close();

        UserServiceImpl userService = new UserServiceImpl();
        User findUserByID = userService.read(userID);
        System.out.println("Результат поиска Ваших пользовательских данных: " + findUserByID);

        BookServiceImpl bookService = new BookServiceImpl();
        Book findBookByID = bookService.read(bookID);
        System.out.println("Результат поиска книги: " + findBookByID);
    */
        String bookFilePath = "resources/book.txt";
        String userFilePath = "resources/user.txt";

        BookService bookService = new BookServiceImpl();
        bookService.readBookFromFile(bookFilePath);

        UserService userService = new UserServiceImpl();
        userService.readUsersFromFile(userFilePath);
    }
}