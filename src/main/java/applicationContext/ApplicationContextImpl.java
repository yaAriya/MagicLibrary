package applicationContext;

import config.DatabaseConfig;
import converter.BookConverterImpl;
import converter.UserConverterImpl;
import dao.FileBasedBookDaoImpl;
import dao.FileBasedUserDaoImpl;
import dao.MySQLBasedBookDao;
import dao.MySQLBasedUserDao;
import invoker.Main;
import loader.PropertyLoader;
import mapper.BookMapper;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import printer.Printer;
import printer.PrinterImpl;
import reader.BookFileReaderImpl;
import reader.UserFileReaderImpl;
import service.BookServiceImpl;
import service.UserServiceImpl;
import util.DatabaseConnectionTester;
import validator.BookValidator;
import validator.UserValidator;
import writer.BookFileWriterImpl;
import writer.UserFileWriterImpl;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextImpl implements ApplicationContext {
    private static final Logger logger = LogManager.getLogger();
    private static final Map<String, Object> instancies = new HashMap<>();

    public void initializeContext() {
        logger.info("initialize context run");
        initializeDataAccessLayer();
        initializeValidationLayer();
        initializeBusinessLevel();
        initializeControllerLevel();

        dependencyInjectionDataAccessLayer();
        dependencyInjectionBusinessLayer();
        dependencyInjectionControllerLayer();
        logger.info("initialize context completed successful");
    }

    public static Main getMainInstance() {
        return (Main) instancies.get("Main");
    }

    private void initializeDataAccessLayer() {
        logger.debug("InitializeDataAccessLayer run");
        DatabaseConnectionTester databaseConnectionTester = new DatabaseConnectionTester();
        register(databaseConnectionTester);

        DatabaseConfig databaseConfig = new DatabaseConfig();
        register(databaseConfig);

        PropertyLoader propertyLoader = new PropertyLoader();
        register(propertyLoader);


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
        logger.debug("InitializeDataAccessLayer completed successful");
    }

    private void dependencyInjectionDataAccessLayer() {
        logger.debug("DependencyInjectionDataAccessLayer run");
        DatabaseConnectionTester databaseConnectionTester = ((DatabaseConnectionTester) instancies.get("DatabaseConnectionTester"));
        DatabaseConfig databaseConfig = ((DatabaseConfig) instancies.get("DatabaseConfig"));
        databaseConnectionTester.setDatabaseConfig(databaseConfig);
        databaseConfig.setPropertyLoader((PropertyLoader) instancies.get("PropertyLoader"));

        MySQLBasedBookDao mySQLBasedBookDao = ((MySQLBasedBookDao) instancies.get("MySQLBasedBookDao"));
        BookMapper bookMapper = ((BookMapper) instancies.get("BookMapper"));
        bookMapper.setUserMapper((UserMapper) instancies.get("UserMapper"));

        mySQLBasedBookDao.setBookMapper(bookMapper);
        mySQLBasedBookDao.setDatabaseConfig(databaseConfig);

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
        mySQLBasedUserDao.setDatabaseConfig(databaseConfig);

        UserConverterImpl userConverter = ((UserConverterImpl) instancies.get("UserConverterImpl"));
        UserFileReaderImpl userFileReader = ((UserFileReaderImpl) instancies.get("UserFileReaderImpl"));
        userFileReader.setUserConverter((UserConverterImpl) instancies.get("UserConverterImpl"));
        UserFileWriterImpl userFileWriter = ((UserFileWriterImpl) instancies.get("UserFileWriterImpl"));
        userFileWriter.setUserConverter(userConverter);
        FileBasedUserDaoImpl fileBasedUserDao = ((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        fileBasedUserDao.setUserFileReader(userFileReader);
        fileBasedUserDao.setUserFileWriter(userFileWriter);
        logger.debug("DependencyInjectionDataAccessLayer completed successful");
    }

    private void initializeValidationLayer() {
        logger.debug("InitializeValidationLayer run");
        BookValidator bookValidator = new BookValidator();
        register(bookValidator);

        UserValidator userValidator = new UserValidator();
        register(userValidator);
        logger.debug("InitializeValidationLayer completed successful");
    }

    private void initializeBusinessLevel() {
        logger.debug("InitializeBusinessLevel run");
        BookServiceImpl bookService = new BookServiceImpl();
        register(bookService);

        UserServiceImpl userService = new UserServiceImpl();
        register(userService);
        logger.debug("InitializeBusinessLevel completed successful");
    }

    private void dependencyInjectionBusinessLayer() {
        logger.debug("DependencyInjectionBusinessLayer run");
        BookServiceImpl bookService = ((BookServiceImpl) instancies.get("BookServiceImpl"));
        bookService.setBookDao((FileBasedBookDaoImpl) instancies.get("FileBasedBookDaoImpl"));
        bookService.setBookDao((MySQLBasedBookDao) instancies.get("MySQLBasedBookDao"));
        bookService.setBookValidator((BookValidator) instancies.get("BookValidator"));
        bookService.setUserService((UserServiceImpl) instancies.get("UserServiceImpl"));

        UserServiceImpl userService = ((UserServiceImpl) instancies.get("UserServiceImpl"));
        userService.setUserDao((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        userService.setUserDao((MySQLBasedUserDao) instancies.get("MySQLBasedUserDao"));
        userService.setUserValidator((UserValidator) instancies.get("UserValidator"));
        logger.debug("DependencyInjectionBusinessLayer completed successful");
    }

    private void initializeControllerLevel() {
        logger.debug("InitializeControllerLevel run");
        Printer printer = new PrinterImpl();
        register(printer);

        Main main = new Main();
        register(main);
        logger.debug("InitializeControllerLevel completed successful");
    }

    private void dependencyInjectionControllerLayer() {
        logger.debug("DependencyInjectionControllerLayer run");
        Main main = ((Main) instancies.get("Main"));
        main.setDatabaseConnectionTester((DatabaseConnectionTester) instancies.get("DatabaseConnectionTester"));
        main.setFileBasedBookDao((FileBasedBookDaoImpl) instancies.get("FileBasedBookDaoImpl"));
        main.setFileBasedUserDao((FileBasedUserDaoImpl) instancies.get("FileBasedUserDaoImpl"));
        main.setBookService((BookServiceImpl) instancies.get("BookServiceImpl"));
        main.setUserService((UserServiceImpl) instancies.get("UserServiceImpl"));
        main.setPrinter((PrinterImpl) instancies.get("PrinterImpl"));
        logger.debug("DependencyInjectionControllerLayer completed successful");
    }

    public void register(Object object) {
        String objectName = object.getClass().getSimpleName();
        instancies.put(objectName, object);
        logger.debug("Object register successful");
    }
}
