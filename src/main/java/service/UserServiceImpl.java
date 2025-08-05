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

    public static UserServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl();
            initializeDependencies(INSTANCE);
        }
        return INSTANCE;
    }

    private UserServiceImpl() {
    }

    private static void initializeDependencies(UserServiceImpl userService) {
        userService.userDao = UserDaoImpl.getInstance();
        userService.bookService = BookServiceImpl.getInstance();
        userService.userValidator = UserValidator.getInstance();
    }

    @Override
    public void initializeCash() throws UserServiceException {
        try {
            userDao.initializeCash();
        } catch (UserDaoException e) {
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
            if (userValidator.validate(user) == false && userDao.getUsers().contains(user)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
            userDao.add(user);
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(long id) throws UserServiceException {
        try {
            if (userDao.read(id) == null) {
                throw new EntityNotFoundException("Искаемый Вами пользователь не найден");
            }
            return userDao.read(id);
        } catch (EntityNotFoundException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User update(User user) throws UserServiceException {
        try {
            if (userValidator.validate(user) == false) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");

            }
            return userDao.update(user, user.getId());
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }


    @Override
    public void delete(long id) throws UserServiceException {
        try {
            User readUser = read(id);
            if (id < 0 && readUser.getBooks().size() != 0) {
                throw new InvalidEntityException("Увы, Вашего пользователя нельзя удалить");
            }
            userDao.delete(readUser);
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void rentBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = read(userId);
            Book readBook = bookService.read(bookId);
            if (readUser == null || readBook == null) {
                throw new InvalidEntityException("Пользователь или книга не могут быть пустыми");
            } else if (readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                throw new InvalidEntityException("У книги уже есть пользователь или у пользователя уже арендована эта книга");
            }
            readUser.getBooks().add(readBook);
            update(readUser);

            readBook.setUser(readUser);
            bookService.update(readBook);
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }

    }

    @Override
    public void returnBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = read(userId);
            Book readBook = bookService.read(bookId);
            if (readUser == null || readBook == null) {
                throw new InvalidEntityException("Пользователь или книга не могут быть пустыми");
            } else if (!readUser.getBooks().contains(readBook) || readBook.getUser() == null) {
                throw new InvalidEntityException("У книги нет пользователя или у пользователя не была арендована книга");
            }
            readUser.getBooks().remove(readBook);
            update(readUser);

            readBook.setUser(null);
            bookService.update(readBook);
        } catch (InvalidEntityException e) {
            throw new UserServiceException();
        }
    }
}