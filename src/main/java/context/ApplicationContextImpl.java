package context;

import config.DatabaseConfig;
import config.DatabaseConfigImpl;
import converter.BookConverter;
import converter.BookConverterImpl;
import converter.UserConverter;
import converter.UserConverterImpl;
import dao.*;
import invoker.Main;
import loader.DatabasePropertyLoader;
import mapper.BookMapper;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printer.Printer;
import printer.PrinterImpl;
import reader.BookFileReader;
import reader.BookFileReaderImpl;
import reader.UserFileReader;
import reader.UserFileReaderImpl;
import service.BookService;
import service.BookServiceImpl;
import service.UserService;
import service.UserServiceImpl;
import config.DatabaseConnectionTester;
import validator.BookValidator;
import validator.UserValidator;
import writer.BookFileWriter;
import writer.BookFileWriterImpl;
import writer.UserFileWriter;
import writer.UserFileWriterImpl;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationContextImpl.class);
    private final Map<String, Object> beans = new HashMap<>();

    public void initializeContext() {
        LOGGER.info("initialize context run");
        initializeDataAccessLayer();
        initializeValidationLayer();
        initializeBusinessLevel();
        initializeControllerLevel();

        dependencyInjectionDataAccessLayer();
        dependencyInjectionBusinessLayer();
        dependencyInjectionControllerLayer();
        LOGGER.info("initialize context completed successful");
    }

    private void initializeDataAccessLayer() {
        LOGGER.debug("InitializeDataAccessLayer run");
        DatabaseConnectionTester databaseConnectionTester = new DatabaseConnectionTester();
        register(databaseConnectionTester);

        DatabaseConfig databaseConfig = new DatabaseConfigImpl();
        register(databaseConfig);

        DatabasePropertyLoader databasePropertyLoader = new DatabasePropertyLoader();
        register(databasePropertyLoader);


        BookMapper bookMapper = new BookMapper();
        register(bookMapper);

        BookDao BookDao = new MySQLBasedBookDao();
        register(BookDao);


        BookConverter bookConverter = new BookConverterImpl();
        register(bookConverter);

        BookFileReader bookFileReader = new BookFileReaderImpl();
        register(bookFileReader);

        BookFileWriter bookFileWriter = new BookFileWriterImpl();
        register(bookFileWriter);

        FileBasedBookDao fileBasedBookDao = new FileBasedBookDaoImpl();
        register(fileBasedBookDao);


        UserMapper userMapper = new UserMapper();
        register(userMapper);

        UserDao userDao = new MySQLBasedUserDao();
        register(userDao);

        UserConverter userConverter = new UserConverterImpl();
        register(userConverter);

        UserFileReader userFileReader = new UserFileReaderImpl();
        register(userFileReader);

        UserFileWriter userFileWriter = new UserFileWriterImpl();
        register(userFileWriter);

        FileBasedUserDao fileBasedUserDao = new FileBasedUserDaoImpl();
        register(fileBasedUserDao);
        LOGGER.debug("InitializeDataAccessLayer completed successful");
    }

    private void dependencyInjectionDataAccessLayer() {
        LOGGER.debug("DependencyInjectionDataAccessLayer run");
        DatabaseConnectionTester databaseConnectionTester = ((DatabaseConnectionTester) beans.get("DatabaseConnectionTester"));
        DatabaseConfigImpl databaseConfigImpl = ((DatabaseConfigImpl) beans.get("DatabaseConfigImpl"));
        databaseConnectionTester.setDatabaseConfig(databaseConfigImpl);
        databaseConfigImpl.setPropertyLoader((DatabasePropertyLoader) beans.get("PropertyLoader"));

        MySQLBasedBookDao mySQLBasedBookDao = ((MySQLBasedBookDao) beans.get("MySQLBasedBookDao"));
        BookMapper bookMapper = ((BookMapper) beans.get("BookMapper"));
        bookMapper.setUserMapper((UserMapper) beans.get("UserMapper"));

        mySQLBasedBookDao.setBookMapper(bookMapper);
        mySQLBasedBookDao.setDatabaseConfig(databaseConfigImpl);

        BookConverterImpl bookConverter = ((BookConverterImpl) beans.get("BookConverterImpl"));
        bookConverter.setUserService((UserServiceImpl) beans.get("UserServiceImpl"));
        bookConverter.setUserDao((FileBasedUserDaoImpl) beans.get("FileBasedUserDaoImpl"));
        BookFileReaderImpl bookFileReader = ((BookFileReaderImpl) beans.get("BookFileReaderImpl"));
        bookFileReader.setBookConverter(bookConverter);
        BookFileWriterImpl bookFileWriter = ((BookFileWriterImpl) beans.get("BookFileWriterImpl"));
        bookFileWriter.setBookConverter(bookConverter);
        FileBasedBookDaoImpl fileBasedBookDao = ((FileBasedBookDaoImpl) beans.get("FileBasedBookDaoImpl"));
        fileBasedBookDao.setBookFileReader(bookFileReader);
        fileBasedBookDao.setBookFileWriter(bookFileWriter);


        MySQLBasedUserDao mySQLBasedUserDao = ((MySQLBasedUserDao) beans.get("MySQLBasedUserDao"));
        UserMapper userMapper = ((UserMapper) beans.get("UserMapper"));
        userMapper.setBookMapper((BookMapper) beans.get("BookMapper"));

        mySQLBasedUserDao.setUserMapper(userMapper);
        mySQLBasedUserDao.setDatabaseConfig(databaseConfigImpl);

        UserConverterImpl userConverter = ((UserConverterImpl) beans.get("UserConverterImpl"));
        UserFileReaderImpl userFileReader = ((UserFileReaderImpl) beans.get("UserFileReaderImpl"));
        userFileReader.setUserConverter((UserConverterImpl) beans.get("UserConverterImpl"));
        UserFileWriterImpl userFileWriter = ((UserFileWriterImpl) beans.get("UserFileWriterImpl"));
        userFileWriter.setUserConverter(userConverter);
        FileBasedUserDaoImpl fileBasedUserDao = ((FileBasedUserDaoImpl) beans.get("FileBasedUserDaoImpl"));
        fileBasedUserDao.setUserFileReader(userFileReader);
        fileBasedUserDao.setUserFileWriter(userFileWriter);
        LOGGER.debug("DependencyInjectionDataAccessLayer completed successful");
    }

    private void initializeValidationLayer() {
        LOGGER.debug("InitializeValidationLayer run");
        BookValidator bookValidator = new BookValidator();
        register(bookValidator);

        UserValidator userValidator = new UserValidator();
        register(userValidator);
        LOGGER.debug("InitializeValidationLayer completed successful");
    }

    private void initializeBusinessLevel() {
        LOGGER.debug("InitializeBusinessLevel run");
        BookService bookService = new BookServiceImpl();
        register(bookService);

        UserService userService = new UserServiceImpl();
        register(userService);
        LOGGER.debug("InitializeBusinessLevel completed successful");
    }

    private void dependencyInjectionBusinessLayer() {
        LOGGER.debug("DependencyInjectionBusinessLayer run");
        BookServiceImpl bookService = ((BookServiceImpl) beans.get("BookServiceImpl"));
        bookService.setBookDao((FileBasedBookDaoImpl) beans.get("FileBasedBookDaoImpl"));
        bookService.setBookDao((MySQLBasedBookDao) beans.get("MySQLBasedBookDao"));
        bookService.setBookValidator((BookValidator) beans.get("BookValidator"));
        bookService.setUserService((UserServiceImpl) beans.get("UserServiceImpl"));

        UserServiceImpl userService = ((UserServiceImpl) beans.get("UserServiceImpl"));
        userService.setUserDao((FileBasedUserDaoImpl) beans.get("FileBasedUserDaoImpl"));
        userService.setUserDao((MySQLBasedUserDao) beans.get("MySQLBasedUserDao"));
        userService.setUserValidator((UserValidator) beans.get("UserValidator"));
        LOGGER.debug("DependencyInjectionBusinessLayer completed successful");
    }

    private void initializeControllerLevel() {
        LOGGER.debug("InitializeControllerLevel run");
        Printer printer = new PrinterImpl();
        register(printer);

        Main main = new Main();
        register(main);
        LOGGER.debug("InitializeControllerLevel completed successful");
    }

    private void dependencyInjectionControllerLayer() {
        LOGGER.debug("DependencyInjectionControllerLayer run");
        Main main = ((Main) beans.get("Main"));
        main.setDatabaseConnectionTester((DatabaseConnectionTester) beans.get("DatabaseConnectionTester"));
        main.setFileBasedBookDao((FileBasedBookDaoImpl) beans.get("FileBasedBookDaoImpl"));
        main.setFileBasedUserDao((FileBasedUserDaoImpl) beans.get("FileBasedUserDaoImpl"));
        main.setBookService((BookServiceImpl) beans.get("BookServiceImpl"));
        main.setUserService((UserServiceImpl) beans.get("UserServiceImpl"));
        main.setPrinter((PrinterImpl) beans.get("PrinterImpl"));
        LOGGER.debug("DependencyInjectionControllerLayer completed successful");
    }

    @Override
    public void register(Object object) {
        String objectName = object.getClass().getSimpleName();
        beans.put(objectName, object);
        LOGGER.debug("Object register successful");
    }

    @Override
    public Object getBeans(String objectName) {
        return beans.get(objectName);
    }
}
