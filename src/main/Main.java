package main;

import printer.Printer;

import printer.PrinterImpl;

import service.BookService;

import service.BookServiceImpl;

import service.UserService;

import service.UserServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        String bookFilePath = "resources/book.txt"; //Принтер лучше из мэйна, но тогда возвратный тип или сразу в ридере?
        String userFilePath = "resources/user.txt";

        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        Printer printer = new PrinterImpl();


        printer.printAllUsers(userService.readUsersFromFile(userFilePath));
        printer.printAllBooks(bookService.readBookFromFile(bookFilePath));

        printer.printUserObject(userService.read(1,userFilePath));
        printer.printBookObject(bookService.read(1, bookFilePath));

        

    }
}