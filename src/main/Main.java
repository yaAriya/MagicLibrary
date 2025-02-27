package main;

import enity.Book;

import enity.User;

import service.BookServiceImplementation;

import service.UserServiceImplementation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свой ID: ");
        int userID = scanner.nextInt();
        System.out.println("Введите ID книги, которую Вы бы хотели найти: ");
        int bookID = scanner.nextInt();
        scanner.close();

        UserServiceImplementation userService = new UserServiceImplementation();
        User findUserByID = userService.read(userID);
        System.out.println("Результат поиска Ваших пользовательских данных: " + findUserByID);

        BookServiceImplementation bookService = new BookServiceImplementation();
        Book findBookByID = bookService.read(bookID);
        System.out.println("Результат поиска книги: " + findBookByID);
    }
}