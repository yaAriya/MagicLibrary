package applicationContext;

import converter.BookConverterImpl;
import converter.UserConverterImpl;
import dao.*;
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

    public void initializeContext() {
        initializeDataAccessLayer();
        initializeValidationLayer();
        businessLevel();
        controllerLevel();

        dependencyInjectionDataAccessLayer();
        dependencyInjectionBusinessLayer();
        dependencyInjectionControllerLayer();
    }

    public static Main getMainInstance() {
        return (Main) instancies.get("Main");
    }

    private void initializeDataAccessLayer() {
        BookMapper bookMapper = new BookMapper();
        register(bookMapper);

        MySQLBasedBookDao mySQLBasedBookDao = new MySQLBasedBookDao();
        register(mySQLBasedBookDao);


        BookConverterImpl bookConverter = new BookConverterImpl();
        register(bookConverter);

        BookFileReaderImpl bookFileReader = new BookFileReaderImpl();
        register(bookFileReader);

        BookFileWriterImpl bookFileWriter = new BookFileWriterImpl();
        register(bookFileWriter);

        FileBasedBookDaoImpl fileBasedBookDao = new FileBasedBookDaoImpl();
        register(fileBasedBookDao);


        UserMapper userMapper = new UserMapper();
        register(userMapper);

        MySQLBasedUserDao mySQLBasedUserDao = new MySQLBasedUserDao();
        register(mySQLBasedUserDao);

        UserConverterImpl userConverter = new UserConverterImpl();
        register(userConverter);

        UserFileReaderImpl userFileReader = new UserFileReaderImpl();
        register(userFileReader);

        UserFileWriterImpl userFileWriter = new UserFileWriterImpl();
        register(userFileWriter);

        FileBasedUserDaoImpl fileBasedUserDao = new FileBasedUserDaoImpl();
        register(fileBasedUserDao);
    }

    private void dependencyInjectionDataAccessLayer() {
        MySQLBasedBookDao mySQLBasedBookDao = ((MySQLBasedBookDao) instancies.get("MySQLBasedBookDao"));
        BookMapper bookMapper = ((BookMapper)instancies.get("BookMapper"));
        mySQLBasedBookDao.setBookMapper(bookMapper);

        BookConverterImpl bookConverter = ((BookConverterImpl) instancies.get("BookConverterImpl"));
        bookConverter.setUserService((UserServiceImpl) instancies.get("UserServiceImpl"));
        bookConverter.setUserDao((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        BookFileReaderImpl bookFileReader = ((BookFileReaderImpl) instancies.get("BookFileReaderImpl"));
        bookFileReader.setBookConverter(bookConverter);
        BookFileWriterImpl bookFileWriter = ((BookFileWriterImpl) instancies.get("BookFileWriterImpl"));
        bookFileWriter.setBookConverter(bookConverter);
        FileBasedBookDaoImpl fileBasedBookDao = ((FileBasedBookDaoImpl) instancies.get("FileBasedBookDaoImpl"));
        fileBasedBookDao.setBookFileReader(bookFileReader);
        fileBasedBookDao.setBookFileWriter(bookFileWriter);


        MySQLBasedUserDao mySQLBasedUserDao = ((MySQLBasedUserDao) instancies.get("MySQLBasedUserDao"));
        UserMapper userMapper = ((UserMapper) instancies.get("UserMapper"));
        mySQLBasedUserDao.setUserMapper(userMapper);

        UserConverterImpl userConverter = ((UserConverterImpl) instancies.get("UserConverterImpl"));
        UserFileReaderImpl userFileReader = ((UserFileReaderImpl) instancies.get("UserFileReaderImpl"));
        userFileReader.setUserConverter((UserConverterImpl) instancies.get("UserConverterImpl"));
        UserFileWriterImpl userFileWriter = ((UserFileWriterImpl) instancies.get("UserFileWriterImpl"));
        userFileWriter.setUserConverter(userConverter);
        FileBasedUserDaoImpl fileBasedUserDao = ((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        fileBasedUserDao.setUserFileReader(userFileReader);
        fileBasedUserDao.setUserFileWriter(userFileWriter);
    }

    private void initializeValidationLayer() {
        BookValidator bookValidator = new BookValidator();
        register(bookValidator);

        UserValidator userValidator = new UserValidator();
        register(userValidator);
    }

    private void businessLevel() {
        BookServiceImpl bookService = new BookServiceImpl();
        register(bookService);

        UserServiceImpl userService = new UserServiceImpl();
        register(userService);
    }

    private void dependencyInjectionBusinessLayer() {
        BookServiceImpl bookService = ((BookServiceImpl) instancies.get("BookServiceImpl"));
        bookService.setBookDao((MySQLBasedBookDao) instancies.get("MySQLBasedBookDao"));
        bookService.setBookDao((FileBasedBookDaoImpl) instancies.get("FileBasedBookDaoImpl"));
        bookService.setBookValidator((BookValidator) instancies.get("BookValidator"));

        UserServiceImpl userService = ((UserServiceImpl) instancies.get("UserServiceImpl"));
        userService.setUserDao((MySQLBasedUserDao) instancies.get("MySQLBasedUserDao"));
        userService.setUserDao((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        userService.setUserValidator((UserValidator) instancies.get("UserValidator"));
        userService.setBookService(bookService);
    }

    private void controllerLevel() {
        Printer printer = new PrinterImpl();
        register(printer);

        Main main = new Main();
        register(main);
    }

    private void dependencyInjectionControllerLayer() {
        Main main = ((Main) instancies.get("Main"));
        main.setFileBasedBookDao((FileBasedBookDaoImpl) instancies.get("FileBasedBookDaoImpl"));
        main.setFileBasedUserDao((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        main.setBookService((BookServiceImpl) instancies.get("BookServiceImpl"));
        main.setUserService((UserServiceImpl) instancies.get("UserServiceImpl"));
        main.setPrinter((PrinterImpl) instancies.get("PrinterImpl"));
    }

    public void register(Object object) {
        String objectName = object.getClass().getSimpleName();
        instancies.put(objectName, object);
    }
}
