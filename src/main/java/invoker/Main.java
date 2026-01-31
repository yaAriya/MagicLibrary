package invoker;

import config.DatabaseConnectionTester;
import context.ApplicationContext;
import context.ApplicationContextImpl;
import dao.FileBasedBookDao;
import dao.FileBasedUserDao;
import entity.Book;
import exceptions.BookDaoException;
import exceptions.DatabaseConnectionTesterException;
import exceptions.UserDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printer.Printer;
import printer.PrinterImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import validator.BookValidator;
import validator.Validator;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private DatabaseConnectionTester databaseConnectionTester;
    private FileBasedBookDao fileBasedBookDao;
    private FileBasedUserDao fileBasedUserDao;
    private UserService userService;
    private BookService bookService;
    private Printer printer;

    public static void main(String[] args) throws DatabaseConnectionTesterException, UserDaoException, BookDaoException {
        LOGGER.info("Process run");
        ApplicationContext applicationContext = new ApplicationContextImpl();
        applicationContext.initializeContext();

        Main app = new Main();

        app.databaseConnectionTester = ((DatabaseConnectionTester) applicationContext.getBeans("DatabaseConnectionTester"));
        app.bookService = ((BookService) applicationContext.getBeans("BookService"));
        app.userService = ((UserService) applicationContext.getBeans("UserService"));
        app.fileBasedBookDao = (FileBasedBookDao) applicationContext.getBeans("FileBasedBookDao");
        app.fileBasedUserDao = ((FileBasedUserDao) applicationContext.getBeans("FileBasedUserDao"));

        //databaseConnectionTester = (DatabaseConnectionTester) applicationContext.getBeans("DatabaseConnectionTester");
        /*Printer printer = (Printer) applicationContext.getBeans("Printer");
        BookService bookService = (BookService) applicationContext.getBeans("BookService");
        UserService userService = (UserService) applicationContext.getBeans("UserService");
        FileBasedBookDao bookDao = (FileBasedBookDao) applicationContext.getBeans("FileBasedBookDao");
        FileBasedUserDao userDao = (FileBasedUserDao) applicationContext.getBeans("FileBasedUserDao");*/

        //databaseConnectionTester.

        app.databaseConnectionTester.validateTestConnection();
       /* userDao.initializeCash();
        bookDao.initializeCash();*/



        app.bookService.add(new Book("Pride and produce", "Jane Austen", 145));
        app.bookService.add(new Book("Gone with the Wind", "Margaret Mitchell", 2000));

        app.printer.printAllBooks(app.bookService.readAllBooks());




      /*  User firstUser = new User("Lisa", "Lisochka@gmail.com", 11);
        User secondUser = new User("Lera", "Lerka@gmail.com", 15);
        userService.add(firstUser);
        userService.add(secondUser);
        userService.read(firstUser.getId());
        userService.readAllUsers();
        printer.printAllUsers(userService.readAllUsers());

        Book firstBook = new Book("Gone with the Wind", "Margaret Mitchell", 333);
        bookService.add(firstBook);
        printer.printAllBooks(bookService.readAllBooks());

        bookService.update((new Book(1, "Gone with the Wind", "Margaret Mitchell", 2000)));
        printer.printBookObject(bookService.read(1));

        userService.delete(2);
        printer.printAllUsers(userService.readAllUsers());


        .bookService.rentBook(1,1);
        //.bookService.read(1);
        .bookService.returnBook(1,1);
        .printer.printBookObject(.bookService.read(1));*/

    }

    public void setDatabaseConnectionTester(DatabaseConnectionTester databaseConnectionTester) {
        this.databaseConnectionTester = databaseConnectionTester;
    }

    public void setFileBasedBookDao(FileBasedBookDao fileBasedBookDao) {
        this.fileBasedBookDao = fileBasedBookDao;
    }

    public void setFileBasedUserDao(FileBasedUserDao fileBasedUserDao) {
        this.fileBasedUserDao = fileBasedUserDao;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}