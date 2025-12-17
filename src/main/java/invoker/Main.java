package invoker;

import applicationContext.ApplicationContextImpl;
import dao.FileBasedBookDao;
import dao.FileBasedUserDao;
import entity.Book;
import entity.User;
import exceptions.BookDaoException;
import exceptions.DatabaseConnectionTesterException;
import exceptions.UserDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printer.Printer;
import service.BookService;
import service.UserService;
import util.DatabaseConnectionTester;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private DatabaseConnectionTester databaseConnectionTester;
    private FileBasedBookDao fileBasedBookDao;
    private FileBasedUserDao fileBasedUserDao;
    private UserService userService;
    private BookService bookService;
    private Printer printer;

    public void setDatabaseConnectionTester(DatabaseConnectionTester databaseConnectionTester){
        this.databaseConnectionTester = databaseConnectionTester;
    }

    public void setFileBasedBookDao(FileBasedBookDao fileBasedBookDao){
        this.fileBasedBookDao = fileBasedBookDao;
    }

    public void setFileBasedUserDao(FileBasedUserDao fileBasedUserDao){
        this.fileBasedUserDao = fileBasedUserDao;
    }

    public void setBookService(BookService bookService){
        this.bookService = bookService;
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public void setPrinter(Printer printer){
        this.printer = printer;
    }


    public static void main(String[] args) throws BookDaoException, DatabaseConnectionTesterException {
        logger.info("Process run");
        ApplicationContextImpl applicationContext = new ApplicationContextImpl();
        applicationContext.initializeContext();

        Main mainApplication = ApplicationContextImpl.getMainInstance();

        /*DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.testConnection();*/

        mainApplication.databaseConnectionTester.validateTestConnection();
        //mainApplication.fileBasedUserDao.initializeCash();
        //mainApplication.fileBasedBookDao.initializeCash();

        User firstUser = new User("Lisa", "Lisochka@gmail.com", 11);
        User secondUser = new User("Lera", "Lerka@gmail.com", 15);
        mainApplication.userService.add(firstUser);
        mainApplication.userService.add(secondUser);
        mainApplication.printer.printAllUsers(mainApplication.userService.readAllUsers());

       /* Book firstBook = new Book("Gone with the Wind", "Margaret Mitchell", 333);
        mainApplication.bookService.add(firstBook);
        mainApplication.printer.printAllBooks(mainApplication.bookService.readAllBooks());

  *//*      mainApplication.bookService.update((new Book(1, "Gone with the Wind", "Margaret Mitchell", 2000)));
        mainApplication.printer.printBookObject(mainApplication.bookService.read(1));

        mainApplication.userService.delete(2);
        mainApplication.printer.printAllUsers(mainApplication.userService.readAllUsers());*//*


        mainApplication.bookService.rentBook(1,1);
        //mainApplication.bookService.read(1);
        mainApplication.bookService.returnBook(1,1);
        mainApplication.printer.printBookObject(mainApplication.bookService.read(1));*/

    }
}