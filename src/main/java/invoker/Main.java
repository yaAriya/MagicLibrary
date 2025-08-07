package invoker;

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
        userService.initializeCash();
        bookService.initializeCash();


        //bookService.delete(0);
        //printer.printUserObject(userService.read(0));

        printer.printAllUsers(userService.readAllUsers());
        printer.printAllBooks(bookService.readAllBooks());


        //printer.printUserObject(firstUser);
        //printer.printBookObject(firstBook);

        //userService.add(new User(3, "Vladimir", "Vovchik@gmail.com", 15));
        //printer.printAllUsers(userService.readAllUsers());
        //User fifthUser = userService.read(3);

        //User firstUSer = userService.read(0);

        //bookService.add(new Book(3, "Gone with the Wind", "Margaret Mitchell", 333, firstUSer ));
        //printer.printAllBooks(bookService.readAllBooks());
        //Book fourthBook = bookService.read(3);

        //printer.printBookObject(bookService.update(new Book(3, "Gone with the Wind", "Margaret Mitchell", 345)));
        //printer.printUserObject(userService.update(new User(3, "Lera", "Lerka@gmail.com", 15)));


        //printer.printAllUsers(userService.readAllUsers());

        //bookService.delete(4);
        //printer.printAllBooks(bookService.readAllBooks());


        //userService.rentBook(firstUser.getId(), fourthBook.getId());
        //userService.rentBook(firstUser.getId(), firstBook.getId());
        //!printer.printUserBooks(firstUser.getBooks());
        //!printer.printUserObject(firstUser);

        /*userService.returnBook(firstUser.getId(), firstBook.getId());
        printer.printUserBooks(firstUser.getBooks());

        userService.rentBook(secondUser.getId(), secondBook.getId());
        printer.printUserBooks(secondUser.getBooks());

        printer.printUserBooks(firstUser.getBooks());
        userService.delete(firstUser.getId());
       // printer.printUserBooks(firstUser.getBooks());
        bookService.delete(firstBook.getId());
    */
    }
}