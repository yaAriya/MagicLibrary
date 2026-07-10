package invoker;

import context.ApplicationContext;
import context.ApplicationContextImpl;
import dao.*;
import exceptions.ApplicationContextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printer.Printer;
import service.BookService;
import service.UserService;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws ApplicationContextException {
        LOGGER.info("Process run");
        ApplicationContext applicationContext = new ApplicationContextImpl();
        applicationContext.initializeContext();


        Printer printer = ((Printer) applicationContext.getBean("printer"));
        BookService bookService = ((BookService) applicationContext.getBean("bookService"));
        UserService userService = ((UserService) applicationContext.getBean("userService"));
        FileBasedBookDao fileBasedBookDao = (FileBasedBookDao) applicationContext.getBean("fileBasedBookDao");
        FileBasedUserDao fileBasedUserDao = ((FileBasedUserDao) applicationContext.getBean("FileBasedUserDao"));
    }
}