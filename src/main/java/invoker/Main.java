package invoker;

import enity.Book;

import enity.User;

import exceptions.BookDaoException;

import exceptions.UserDaoException;

import printer.Printer;

import printer.PrinterImpl;

import service.BookService;

import service.BookServiceImpl;

import service.UserService;

import service.UserServiceImpl;


public class Main {
    public static UserService userService = UserServiceImpl.getInstance();
    public static BookService bookService = BookServiceImpl.getInstance();
    public static Printer printer = PrinterImpl.getInstance();


    public static void main(String[] args) throws UserDaoException, BookDaoException {
        userService.initializeDataBase();
        bookService.initializeDataBase();

        printer.printAllUsers(userService.readAllUsers());
        printer.printAllBooks(bookService.readAllBooks());

        Book firstBook = bookService.read(0);
        Book secondBook = bookService.read(1);
        Book thirdBook = bookService.read(2);

        User firstUser = userService.read(0);
        User secondUser = userService.read(1);
        User thirdUser = userService.read(2);
        User fourthUser = userService.read(3);

        printer.printUserObject(firstUser);
        printer.printBookObject(firstBook);

        userService.delete(1);
        printer.printAllUsers(userService.readAllUsers());

        userService.add(new User(4, "Vika", "Vichik@gmail.com", 15));
        printer.printAllUsers(userService.readAllUsers());

        userService.add(new User(5, "Vladimir", "Vovchik@gmail.com", 15));
        printer.printAllUsers(userService.readAllUsers());

        bookService.add(new Book(3, "Gone with the Wind", "Margaret Mitchell", 333));
        printer.printAllBooks(bookService.readAllBooks());
        Book fourthBook = bookService.read(3);

        printer.printBookObject(bookService.update(new Book(3, "Gone with the Wind", "Margaret Mitchell", 345)));

        printer.printUserObject(userService.update(new User(4, "Lera", "Lerka@gmail.com", 15)));

        bookService.delete(thirdBook.getId());
        printer.printAllBooks(bookService.readAllBooks());


        userService.rentBook(firstUser.getId(), fourthBook.getId());
        userService.rentBook(firstUser.getId(), firstBook.getId());
        printer.printUserBooks(firstUser.getBooks());
        printer.printUserObject(firstUser);

        userService.returnBook(firstUser.getId(), firstBook.getId());
        printer.printUserBooks(firstUser.getBooks());

        userService.rentBook(secondUser.getId(), secondBook.getId());
        printer.printUserBooks(secondUser.getBooks());

        userService.delete(firstUser.getId());
        bookService.delete(firstBook.getId());
    }
}