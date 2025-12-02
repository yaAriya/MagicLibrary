package invoker;

import applicationContext.ApplicationContextImpl;
import config.DatabaseConfig;
import entity.Book;
import entity.User;
import exceptions.ApplicationContextException;
import exceptions.UserDaoException;
import printer.Printer;
import service.BookService;
import service.UserService;


public class Main {
    private static UserService userService;
    private static BookService bookService;
    private static Printer printer;

    public void setBookService(BookService bookService){
        Main.bookService = bookService;
    }

    public void setUserService(UserService userService){
        Main.userService = userService;
    }

    public void setPrinter(Printer printer){
        Main.printer = printer;
    }




    public static void main(String[] args) throws ApplicationContextException {
        ApplicationContextImpl applicationContext = new ApplicationContextImpl();
        applicationContext.initializeContext();
        DatabaseConfig.testConnection();
        //userService.initializeCash(); в бук дао файл
        //bookService.initializeCash();

       /* User firstUser = new User(1, "Lisa", "Lisochka@gmail.com", 11);
        User secondUser = new User(4, "Lera", "Lerka@gmail.com", 15);
        userService.add(firstUser);
        userService.add(secondUser);
        printer.printAllUsers(userService.readAllUsers());

        Book firstBook = new Book(4, "Gone with the Wind", "Margaret Mitchell", 333);
        bookService.add(firstBook);
        printer.printAllBooks(bookService.readAllBooks());

        bookService.update((new Book(4, "Gone with the Wind", "Margaret Mitchell", 2000)));
        printer.printBookObject(bookService.read(4));*/
        /*userService.updateInDatabase( new User(1, "Lisa", "Lisochka@gmail.com", 11));
        printer.printAllUsersFromMap(userService.readAllUsersFromDatabase());*/
        //userService.delete(1);


        /*printer.printUserObject(userService.update(new User(0, "Vladimir", "Vovchik@gmail.com", 15)));
        printer.printBookObject(bookService.update(new Book(1, "Gone with the Wind", "Margaret Mitchell", 345)));*/
        /*printer.printUserObject(userService.update(new User(2, "Lera", "Lerka@gmail.com", 15)));

        bookService.delete(0);
        printer.printUserObject(userService.read(0));*/

       /*printer.printAllUsers(userService.readAllUsers());
       printer.printAllBooks(bookService.readAllBooks());*/


        //printer.printUserObject(firstUser);
        //printer.printBookObject(firstBook);

        //userService.add(new User(3, "Vladimir", "Vovchik@gmail.com", 15));
        //printer.printAllUsers(userService.readAllUsers());
        //User fifthUser = userService.read(3);

        //User firstUSer = userService.read(0);

        //bookService.add(new Book(3, "Gone with the Wind", "Margaret Mitchell", 333, firstUSer ));
        //printer.printAllBooks(bookService.readAllBooks());
        //Book fourthBook = bookService.read(3);


        //printer.printAllUsers(userService.readAllUsers());

        //bookService.delete(4);
        //printer.printAllBooks(bookService.readAllBooks());


        //userService.rentBook(firstUser.getId(), fourthBook.getId());
        //userService.rentBook(firstUser.getId(), firstBook.getId());
        //!printer.printUserBooks(firstUser.getBooks());
        //!printer.printUserObject(firstUser);
      /*  userService.returnBook(firstUser.getId(), firstBook.getId());
        printer.printUserBooks(firstUser.getBooks());

        userService.rentBook(secondUser.getId(), secondBook.getId());
        printer.printUserBooks(secondUser.getBooks());

        printer.printUserBooks(firstUser.getBooks());
        userService.delete(firstUser.getId());
       // printer.printUserBooks(firstUser.getBooks());
        bookService.delete(firstBook.getId());*/
    }
}