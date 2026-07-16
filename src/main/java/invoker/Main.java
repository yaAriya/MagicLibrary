package invoker;

import config.ApplicationConfig;
import entity.Book;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import printer.Printer;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Process run");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
       /* String[] beanNames = context.getBeanDefinitionNames();
        for (String name : beanNames) {
            System.out.println(name);
        }*/
        UserService userService = (UserService) context.getBean("userServiceImpl");
        Printer printer = (Printer) context.getBean("printerImpl");
        BookService bookService = (BookService) context.getBean("bookServiceImpl");

        bookService.rentBook(1,1);
        printer.printUserObject(userService.read(1));
        printer.printBookObject(bookService.read(1));
        printer.printUserBooks(userService.read(1).getBooks());
    }
}