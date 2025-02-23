package main;

import enity.Book;

import enity.User;

import service.BookService;

import service.UserService;

import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свой ID: ");
        int userID = scanner.nextInt();
        System.out.println("Введите ID книги, которую Вы бы хотели найти: ");
        int bookID = scanner.nextInt();
        scanner.close();

        UserService userService = new UserService();
        User findUserByID = userService.read(userID);
        System.out.println("Результат поиска Ваших пользовательских данных: " + findUserByID);

        BookService bookService = new BookService();
        Book findBookByID = bookService.read(bookID);
        System.out.println("Результат поиска книги: " + findBookByID);
    }
}