package main;

import enity.Book;

import enity.User;

import exceptions.BookServiceException;

import exceptions.MainException;

import exceptions.UserServiceException;

import printer.Printer;

import printer.PrinterImpl;

import service.BookService;

import service.BookServiceImpl;

import service.UserService;

import service.UserServiceImpl;

public class Main {
    public static void main(String[] args) throws MainException {
        try {
            BookService bookService = new BookServiceImpl();
            UserService userService = new UserServiceImpl();
            Printer printer = new PrinterImpl();


            printer.printAllUsers(userService.readAllUsers());
            printer.printAllBooks(bookService.readAllBooks());

            printer.printUserObject(userService.read(1));
            printer.printBookObject(bookService.read(1));


            userService.add(new User(5, "Vika", "Vichik@gmail.com", 15));
            printer.printAllUsers(userService.readAllUsers());

            bookService.add(new Book(3, "Gone with the Wind", "Margaret Mitchell", 333));
            printer.printAllBooks(bookService.readAllBooks());

            printer.printBookObject(bookService.update(new Book(3, "Gone with the Wind", "Margaret Mitchell", 345)));

            printer.printUserObject(userService.update(new User(4, "Lera", "Lerka@gmail.com", 15)));

            userService.delete(new User(5, "Vika", "Vichik@gmail.com", 15));
            printer.printAllUsers(userService.readAllUsers());

            bookService.delete(new Book(2, "Gone with the Wind", "Margaret Mitchell", 333));
            printer.printAllBooks(bookService.readAllBooks());

        } catch (BookServiceException | UserServiceException e) {
            throw new MainException("упс, опять долбанное Exception " + e);
        }
    }
}