package invoker;

import config.DatabaseConnectionTester;
import context.ApplicationContext;
import context.ApplicationContextImpl;
import dao.FileBasedBookDao;
import dao.FileBasedUserDao;
import entity.Book;
import entity.User;
import exceptions.ApplicationContextException;
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
import util.LogCleaner;
import validator.BookValidator;
import validator.Validator;

import java.util.List;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws UserDaoException, BookDaoException, ApplicationContextException {
        LOGGER.info("Process run");
        ApplicationContext applicationContext = new ApplicationContextImpl();
        applicationContext.initializeContext();

        DatabaseConnectionTester databaseConnectionTester = ((DatabaseConnectionTester) applicationContext.getBean("databaseConnectionTester"));
        Printer printer = ((Printer) applicationContext.getBean("printer"));
        BookService bookService = ((BookService) applicationContext.getBean("bookService"));
        UserService userService = ((UserService) applicationContext.getBean("userService"));
        FileBasedBookDao fileBasedBookDao = (FileBasedBookDao) applicationContext.getBean("fileBasedBookDao");
        FileBasedUserDao fileBasedUserDao = ((FileBasedUserDao) applicationContext.getBean("FileBasedUserDao"));

        LogCleaner logCleaner = new LogCleaner();
        logCleaner.clearLogFile("logs/app.log");

      /*  printer.printUserObject(userService.read(1));
        printer.printUserObject(userService.read(2));
        printer.printBookObject(bookService.read(1));
        printer.printBookObject(bookService.read(2));*/
    }
}