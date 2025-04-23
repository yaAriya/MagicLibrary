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

            Book firstBook = bookService.read(0);
            Book secondBook = bookService.read(1);
            Book thirdBook = bookService.read(2);

            User firstUser = userService.read(0);
            User secondUser = userService.read(1);
            User thirdUser = userService.read(2);
            User fourthUser = userService.read(3);
            User fifthUser = userService.read(4);


            userService.add(new User(5, "Vika", "Vichik@gmail.com", 15));
            printer.printAllUsers(userService.readAllUsers());
            User sixthUser = userService.read(5);

            bookService.add(new Book(3, "Gone with the Wind", "Margaret Mitchell", 333));
            printer.printAllBooks(bookService.readAllBooks());
            Book fourthBook = bookService.read(3);

            printer.printBookObject(bookService.update(new Book(3, "Gone with the Wind", "Margaret Mitchell", 345)));

            printer.printUserObject(userService.update(new User(4, "Lera", "Lerka@gmail.com", 15)));

            userService.delete(sixthUser.getId());
            printer.printAllUsers(userService.readAllUsers());

            bookService.delete(thirdBook.getId());
            printer.printAllBooks(bookService.readAllBooks());


            userService.rentBook(firstUser.getId(), fourthBook.getId());
            userService.rentBook(firstUser.getId(), firstBook.getId());
            printer.printUserBooks(firstUser.getBooks());
            printer.printUserObject(firstUser);

            userService.returnBook(firstUser.getId(),firstBook.getId());
            printer.printUserBooks(firstUser.getBooks());

            userService.rentBook(secondUser.getId(), secondBook.getId());
            printer.printUserBooks(secondUser.getBooks());
        } catch (BookServiceException | UserServiceException e) {
            throw new MainException("упс, опять долбанное Exception ", e);
        }
    }
}