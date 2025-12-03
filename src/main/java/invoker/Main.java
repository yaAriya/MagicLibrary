package invoker;

import applicationContext.ApplicationContextImpl;
import config.DatabaseConfig;
import dao.FileBasedBookDao;
import dao.FileBasedUserDao;
import entity.Book;
import entity.User;
import exceptions.BookDaoException;
import exceptions.UserDaoException;
import printer.Printer;
import service.BookService;
import service.UserService;



public class Main {
    private FileBasedBookDao fileBasedBookDao;
    private FileBasedUserDao fileBasedUserDao;
    private UserService userService;
    private BookService bookService;
    private Printer printer;

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


    public static void main(String[] args) throws BookDaoException, UserDaoException {
        ApplicationContextImpl applicationContext = new ApplicationContextImpl();
        applicationContext.initializeContext();

        Main mainApplication = ApplicationContextImpl.getMainInstance();

        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.testConnection();

        mainApplication.fileBasedUserDao.initializeCash();
        mainApplication.fileBasedBookDao.initializeCash();


        /*FileBasedUserDao userDao = new FileBasedUserDao();
        userDao.initializeCash();*/

        User firstUser = new User(1, "Lisa", "Lisochka@gmail.com", 11);
        User secondUser = new User(4, "Lera", "Lerka@gmail.com", 15);
        mainApplication.userService.add(firstUser);
        mainApplication.userService.add(secondUser);
        mainApplication.printer.printAllUsers(mainApplication.userService.readAllUsers());

        Book firstBook = new Book(4, "Gone with the Wind", "Margaret Mitchell", 333);
        mainApplication.bookService.add(firstBook);
        mainApplication.printer.printAllBooks(mainApplication.bookService.readAllBooks());

        mainApplication.bookService.update((new Book(4, "Gone with the Wind", "Margaret Mitchell", 2000)));
        mainApplication.printer.printBookObject(mainApplication.bookService.read(4));
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