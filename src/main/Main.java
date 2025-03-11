package main;

import enity.Book;

import enity.User;

import printer.Printer;

import printer.PrinterImpl;

import service.BookService;

import service.BookServiceImpl;

import service.UserService;

import service.UserServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        BookService bookService = new BookServiceImpl();
        UserService userService = new UserServiceImpl();
        Printer printer = new PrinterImpl();


        printer.printAllUsers(userService.readAllUsers());
        printer.printAllBooks(bookService.readAllBooks());

        printer.printUserObject(userService.read(1));
        printer.printBookObject(bookService.read(1));


        userService.add(new User(5,"Vika","Vichik@gmail.com",15));
        printer.printAllUsers(userService.readAllUsers());

        bookService.add(new Book(3,"Gone with the Wind", "Margaret Mitchell", 333));
        printer.printAllBooks(bookService.readAllBooks());

        userService.delete(new User(5,"Vika","Vichik@gmail.com",15));
        printer.printAllUsers(userService.readAllUsers());

        bookService.delete(new Book(3,"Gone with the Wind", "Margaret Mitchell", 333));
        printer.printAllBooks(bookService.readAllBooks());
    }
}