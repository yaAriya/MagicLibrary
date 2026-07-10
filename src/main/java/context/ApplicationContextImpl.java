package context;

import converter.BookConverter;
import converter.BookConverterImpl;
import converter.UserConverter;
import converter.UserConverterImpl;
import dao.*;
import exceptions.*;
import loader.DatabasePropertyLoader;
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

    public void initializeContext() throws ApplicationContextException {
        LOGGER.info("initialize context run");
        initializeDataAccessLayer();
        initializeValidationLayer();
        initializeBusinessLevel();
        initializeControllerLevel();

        injectDataAccessLayerDependencies();
        injectBusinessLayerDependencies();

        /*try {
        FileBasedBookDaoImpl fileBasedBookDao = ((FileBasedBookDaoImpl) beans.get("fileBasedBookDao"));
        FileBasedUserDaoImpl fileBasedUserDao = ((FileBasedUserDaoImpl) beans.get("fileBasedUserDao"));
        fileBasedBookDao.initializeCash();
        fileBasedUserDao.initializeCash();


        } catch (BookDaoException | UserDaoException e) {
            throw new ApplicationContextException(e);
        }*/
        LOGGER.info("initialize context completed successful");
    }

    private void initializeDataAccessLayer() {
        LOGGER.debug("InitializeDataAccessLayer run");
        DatabasePropertyLoader databasePropertyLoader = new DatabasePropertyLoader();
        register("databasePropertyLoader", databasePropertyLoader);


        BookDao bookDao = new MySQLBasedBookDao();
        register("mySQLBasedBookDao", bookDao);


        BookConverter bookConverter = new BookConverterImpl();
        register("bookConverter", bookConverter);

        BookFileReader bookFileReader = new BookFileReaderImpl();
        register("bookFileReader", bookFileReader);

        BookFileWriter bookFileWriter = new BookFileWriterImpl();
        register("bookFileWriter", bookFileWriter);

        FileBasedBookDao fileBasedBookDao = new FileBasedBookDaoImpl();
        register("fileBasedBookDao", fileBasedBookDao);


        UserDao userDao = new MySQLBasedUserDao();
        register("mySQLBasedUserDao", userDao);

        UserConverter userConverter = new UserConverterImpl();
        register("userConverter", userConverter);

        UserFileReader userFileReader = new UserFileReaderImpl();
        register("userFileReader", userFileReader);

        UserFileWriter userFileWriter = new UserFileWriterImpl();
        register("userFileWriter", userFileWriter);

        FileBasedUserDao fileBasedUserDao = new FileBasedUserDaoImpl();
        register("fileBasedUserDao", fileBasedUserDao);
        LOGGER.debug("InitializeDataAccessLayer completed successful");
    }

    private void injectDataAccessLayerDependencies() {
        LOGGER.debug("DependencyInjectionDataAccessLayer run");

        BookConverterImpl bookConverter = ((BookConverterImpl) beans.get("bookConverter"));
        bookConverter.setUserService((UserServiceImpl) beans.get("userService"));
        bookConverter.setUserDao((FileBasedUserDaoImpl) beans.get("fileBasedUserDao"));
        BookFileReaderImpl bookFileReader = ((BookFileReaderImpl) beans.get("bookFileReader"));
        bookFileReader.setBookConverter(bookConverter);
        BookFileWriterImpl bookFileWriter = ((BookFileWriterImpl) beans.get("bookFileWriter"));
        bookFileWriter.setBookConverter(bookConverter);
        FileBasedBookDaoImpl fileBasedBookDao = ((FileBasedBookDaoImpl) beans.get("fileBasedBookDao"));
        fileBasedBookDao.setBookFileReader(bookFileReader);
        fileBasedBookDao.setBookFileWriter(bookFileWriter);


        UserConverterImpl userConverter = ((UserConverterImpl) beans.get("userConverter"));
        UserFileReaderImpl userFileReader = ((UserFileReaderImpl) beans.get("userFileReader"));
        userFileReader.setUserConverter((UserConverterImpl) beans.get("userConverter"));
        UserFileWriterImpl userFileWriter = ((UserFileWriterImpl) beans.get("userFileWriter"));
        userFileWriter.setUserConverter(userConverter);
        FileBasedUserDaoImpl fileBasedUserDao = ((FileBasedUserDaoImpl) beans.get("fileBasedUserDao"));
        fileBasedUserDao.setUserFileReader(userFileReader);
        fileBasedUserDao.setUserFileWriter(userFileWriter);
        LOGGER.debug("DependencyInjectionDataAccessLayer completed successful");
    }

    private void initializeValidationLayer() {
        LOGGER.debug("InitializeValidationLayer run");
        BookValidator bookValidator = new BookValidator();
        register("bookValidator", bookValidator);

        UserValidator userValidator = new UserValidator();
        register("userValidator", userValidator);
        LOGGER.debug("InitializeValidationLayer completed successful");
    }

    private void initializeBusinessLevel() {
        LOGGER.debug("InitializeBusinessLevel run");
        BookService bookService = new BookServiceImpl();
        register("bookService", bookService);

        UserService userService = new UserServiceImpl();
        register("userService", userService);
        LOGGER.debug("InitializeBusinessLevel completed successful");
    }

    private void injectBusinessLayerDependencies() {
        LOGGER.debug("DependencyInjectionBusinessLayer run");
        BookServiceImpl bookService = ((BookServiceImpl) beans.get("bookService"));
        bookService.setBookDao((FileBasedBookDaoImpl) beans.get("fileBasedBookDao"));
        bookService.setBookDao((MySQLBasedBookDao) beans.get("mySQLBasedBookDao"));
        bookService.setBookValidator((BookValidator) beans.get("bookValidator"));
        bookService.setUserService((UserServiceImpl) beans.get("userService"));

        UserServiceImpl userService = ((UserServiceImpl) beans.get("userService"));
        userService.setUserDao((FileBasedUserDaoImpl) beans.get("fileBasedUserDao"));
        userService.setUserDao((MySQLBasedUserDao) beans.get("mySQLBasedUserDao"));
        userService.setUserValidator((UserValidator) beans.get("userValidator"));
        LOGGER.debug("DependencyInjectionBusinessLayer completed successful");
    }

    private void initializeControllerLevel() {
        LOGGER.debug("InitializeControllerLevel run");
        Printer printer = new PrinterImpl();
        register("printer", printer);
        LOGGER.debug("InitializeControllerLevel completed successful");
    }

    @Override
    public void register(String name, Object object) {
        beans.put(name, object);
        LOGGER.debug("Object register successful");
    }

    @Override
    public Object getBean(String objectName) {
        return beans.get(objectName);
    }
}
