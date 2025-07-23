package service;

import dao.UserDao;

import dao.UserDaoImpl;

import enity.Book;

import enity.User;

import exceptions.*;

import validator.UserValidator;

import validator.Validator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl INSTANCE;
    private UserDao userDao;
    private BookService bookService;
    private Validator<User> userValidator;

    public static UserServiceImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserServiceImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private UserServiceImpl() {
    }

    private static void initializeDependencies(UserServiceImpl userService){
        userService.userDao = UserDaoImpl.getInstance();
        userService.bookService = BookServiceImpl.getInstance();
        userService.userValidator = UserValidator.getInstance();
    }

    public void initializeDataBase() throws UserServiceException {
        try {
            userDao.initializeDataBase();
        } catch (UserDaoException e){
            throw new UserServiceException();
        }
    }

    @Override
    public List<User> readAllUsers() throws UserServiceException {
        try {
            return userDao.readAllUsers();
        } catch (ObjectInitializeException | CloneNotSupportedException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            if (userValidator.validate(user) == true) {
                userDao.add(user);
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User update(User user) throws UserServiceException {
        try {
            if (userValidator.validate(user) == true) {
                return  userDao.update(user, user.getId());
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(long id) throws UserServiceException {
        try {
            if (userDao.read(id) != null) {
                return  userDao.read(id);
            } else {
                throw new EntityNotFoundException("Искаемый Вами пользователь не найден");
            }
        } catch (EntityNotFoundException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws UserServiceException {
        try {
            User readUser = read(id);
            if (id >= 0 && readUser.getBooks().size() == 0) {
                userDao.delete(readUser);
            } else {
                throw new InvalidEntityException("Увы, Вашего пользователя нельзя удалить");
            }
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void rentBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = read(userId);
            Book readBook = bookService.read(bookId);
            if (readUser == null || readBook == null || readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                throw new InvalidEntityException();
            } else {
                readUser.getBooks().add(readBook);
                update(readUser);

                readBook.setUser(readUser);
                bookService.update(readBook);
            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }

    }

    @Override
    public void returnBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = read(userId);
            Book readBook = bookService.read(bookId);
            if (readUser == null || readBook == null || !readUser.getBooks().contains(readBook) || readBook.getUser() == null) {
                throw new InvalidEntityException();
            } else {
                readUser.getBooks().remove(readBook);
                update(readUser);

                readBook.setUser(null);
                bookService.update(readBook);

            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException();
        }
    }
}