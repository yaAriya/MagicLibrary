package main;

import DAOClass.BookDAO;

import DAOClass.UserDAO;

import enity.Book;

import enity.User;

import java.util.List;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List books = BookDAO.initializeBooks();
        System.out.println(books);

        List users = UserDAO.initializeUsers();
        System.out.println(users);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите свой ID: ");
        int userID = scanner.nextInt();
        System.out.println("Введите ID книги, которую Вы бы хотели найти: ");
        int bookID = scanner.nextInt();
        scanner.close();
    }
}