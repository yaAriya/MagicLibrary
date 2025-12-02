package applicationContext;

import converter.BookConverterImpl;
import converter.UserConverterImpl;
import dao.*;
import exceptions.ApplicationContextException;
import invoker.Main;
import mapper.BookMapper;
import mapper.UserMapper;
import printer.Printer;
import printer.PrinterImpl;
import reader.BookFileReaderImpl;
import reader.UserFileReaderImpl;
import service.BookServiceImpl;
import service.UserServiceImpl;
import validator.BookValidator;
import validator.UserValidator;
import writer.BookFileWriterImpl;
import writer.UserFileWriterImpl;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {
    private static final Map<String, Object> instancies = new HashMap<>();

    private void selectBookDaoImplementation() {
        BookDao selectedBookDao;
        boolean selectedBookDaoImplementation = true;

        if (selectedBookDaoImplementation) {
            selectedBookDao = new MySQLBasedBookDao();
            register(selectedBookDao);
        } else {
            selectedBookDao = new FileBasedBookDao();
            register(selectedBookDao);
        }
    }

    private void selectUserDaoImplementation() {
        UserDao selectedUserDao;
        boolean selectedUserDaoImplementation = true;

        if (selectedUserDaoImplementation) {
            selectedUserDao = new MySQLBasedUserDao();
            register(selectedUserDao);
        } else {
            selectedUserDao = new FileBasedUserDao();
            register(selectedUserDao);
        }
    }

    @Override
    public void initializeContext() throws ApplicationContextException {
        initializeDataAccessLayer();
        initializeValidationLayer();
        businessLevel();
        controllerLevel();
    }

    private void initializeDataAccessLayer() throws ApplicationContextException {
        selectBookDaoImplementation();

        if (getInstance("MySQLBasedBookDao") != null) {
            BookMapper bookMapper = new BookMapper();
            register(bookMapper);

            MySQLBasedBookDao bookDao = (MySQLBasedBookDao) getInstance("MySQLBasedBookDao");
            bookDao.setBookMapper(bookMapper);
        } else {
            BookConverterImpl bookConverter = new BookConverterImpl();
            register(bookConverter);

            BookFileReaderImpl bookFileReader = new BookFileReaderImpl();
            register(bookFileReader);
            bookFileReader.setBookConverter(bookConverter);

            BookFileWriterImpl bookFileWriter = new BookFileWriterImpl();
            register(bookFileWriter);
            bookFileWriter.setBookConverter(bookConverter);

            FileBasedBookDao bookDao = (FileBasedBookDao) getInstance("FileBasedBookDao");
            bookDao.setBookFileReader(bookFileReader);
            bookDao.setBookFileWriter(bookFileWriter);
        }

        selectUserDaoImplementation();

        if (getInstance("MySQLBasedUserDao") != null) {
            UserMapper userMapper = new UserMapper();
            register(userMapper);

            MySQLBasedUserDao userDao = (MySQLBasedUserDao) getInstance("MySQLBasedUserDao");
            userDao.setUserMapper(userMapper);
        } else {
            UserConverterImpl userConverter = new UserConverterImpl();
            register(userConverter);

            UserFileReaderImpl userFileReader = new UserFileReaderImpl();
            register(userFileReader);
            userFileReader.setUserConverter(userConverter);

            UserFileWriterImpl userFileWriter = new UserFileWriterImpl();
            register(userFileWriter);
            userFileWriter.setUserConverter(userConverter);

            FileBasedUserDao userDao = (FileBasedUserDao) getInstance("FileBasedUserDao");
            userDao.setUserFileReader(userFileReader);
            userDao.setUserFileWriter(userFileWriter);
        }
    }

    private void initializeValidationLayer() {
        BookValidator bookValidator = new BookValidator();
        register(bookValidator);

        UserValidator userValidator = new UserValidator();
        register(userValidator);
    }

    private void businessLevel() throws ApplicationContextException {
        BookServiceImpl bookService = new BookServiceImpl();
        register(bookService);

        if(getInstance("MySQLBasedBookDao") != null) {
            bookService.setBookDao((MySQLBasedBookDao) getInstance("MySQLBasedBookDao"));
        } else {
            bookService.setBookDao((FileBasedBookDao) getInstance("FileBasedBookDao"));
        }
        bookService.setBookValidator((BookValidator) getInstance("BookValidator"));



        UserServiceImpl userService = new UserServiceImpl();
        register(userService);

        if(getInstance("MySQLBasedUserDao") != null) {
            userService.setUserDao((MySQLBasedUserDao) getInstance("MySQLBasedUserDao"));
        } else {
            userService.setUserDao((FileBasedUserDao) getInstance("FileBasedUserDao"));
        }
        userService.setUserValidator((UserValidator) getInstance("UserValidator"));
        userService.setBookService(bookService);
    }

    private void controllerLevel() throws ApplicationContextException {
        Printer printer = new PrinterImpl();
        register(printer);

        Main main = new Main();
        register(main);
        main.setBookService((BookServiceImpl) getInstance("BookServiceImpl"));
        main.setUserService((UserServiceImpl) getInstance("UserServiceImpl"));
        main.setPrinter(printer);

    }

    @Override
    public void register(Object object) {
        String objectName = object.getClass().getSimpleName();
        instancies.put(objectName, object);

    }

    @Override
    public Object getInstance(String className) throws ApplicationContextException {
        Object instance = instancies.get(className);
        if (instance == null) {
            throw new ApplicationContextException();
        }
        return instance;
    }

}
