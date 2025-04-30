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
    public static final UserService USER_SERVICE = UserServiceImpl.getInstance();
    public static final BookService BOOK_SERVICE = BookServiceImpl.getInstance();
    public static final Printer PRINTER = PrinterImpl.getInstance();


    public static void main(String[] args) throws MainException {
        try {
            PRINTER.printAllUsers(USER_SERVICE.readAllUsers());
            PRINTER.printAllBooks(BOOK_SERVICE.readAllBooks());

            PRINTER.printUserObject(USER_SERVICE.read(1));
            PRINTER.printBookObject(BOOK_SERVICE.read(1));

            Book firstBook = BOOK_SERVICE.read(0);
            Book secondBook = BOOK_SERVICE.read(1);
            Book thirdBook = BOOK_SERVICE.read(2);

            User firstUser =USER_SERVICE.read(0);
            User secondUser = USER_SERVICE.read(1);
            User thirdUser = USER_SERVICE.read(2);
            User fourthUser = USER_SERVICE.read(3);
            User fifthUser = USER_SERVICE.read(4);


            USER_SERVICE.add(new User(5, "Vika", "Vichik@gmail.com", 15));
            PRINTER.printAllUsers(USER_SERVICE.readAllUsers());
            User sixthUser = USER_SERVICE.read(5);

            BOOK_SERVICE.add(new Book(3, "Gone with the Wind", "Margaret Mitchell", 333));
            PRINTER.printAllBooks(BOOK_SERVICE.readAllBooks());
            Book fourthBook = BOOK_SERVICE.read(3);

            PRINTER.printBookObject(BOOK_SERVICE.update(new Book(3, "Gone with the Wind", "Margaret Mitchell", 345)));

            PRINTER.printUserObject(USER_SERVICE.update(new User(4, "Lera", "Lerka@gmail.com", 15)));

            USER_SERVICE.delete(sixthUser.getId());
            PRINTER.printAllUsers(USER_SERVICE.readAllUsers());

            BOOK_SERVICE.delete(thirdBook.getId());
            PRINTER.printAllBooks(BOOK_SERVICE.readAllBooks());


            USER_SERVICE.rentBook(firstUser.getId(), fourthBook.getId());
            USER_SERVICE.rentBook(firstUser.getId(), firstBook.getId());
            PRINTER.printUserBooks(firstUser.getBooks());
            PRINTER.printUserObject(firstUser);

            USER_SERVICE.returnBook(firstUser.getId(),firstBook.getId());
            PRINTER.printUserBooks(firstUser.getBooks());

            USER_SERVICE.rentBook(secondUser.getId(), secondBook.getId());
            PRINTER.printUserBooks(secondUser.getBooks());
        } catch (BookServiceException | UserServiceException e) {
            throw new MainException("упс, опять долбанное Exception ", e);
        }
    }
}