package service;

import dao.UserDao;

import dao.UserDaoImpl;

import enity.Book;

import enity.User;

import exceptions.EntityNotFoundException;

import exceptions.InvalidEntityException;

import exceptions.ObjectInitializeException;

import exceptions.UserServiceException;

import validator.UserValidator;
import validator.UserValidatorImpl;

//import validator.Validator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final BookService bookService;
    private final UserValidator userValidator;

    public UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
        bookService = BookServiceImpl.getInstance();
        userValidator = UserValidatorImpl.getInstance();
    }

    @Override
    public List<User> readAllUsers() throws UserServiceException {
        try {
            return userDao.readAllUsers();
        } catch (ObjectInitializeException e) {
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
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User update(User user) throws UserServiceException {
        try {
            if (userValidator.validate(user) == true) {
                return userDao.update(user, user.getId());
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(long id) throws UserServiceException {
        try {
            if (userDao.read(id) != null) {
                return userDao.read(id);
            } else {
                throw new EntityNotFoundException("Искаемый Вами объект не найден");
            }
        } catch (EntityNotFoundException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws UserServiceException {
        try {
            if (id >= 0) {
                userDao.delete(read(id));
            } else {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
        } catch (InvalidEntityException e) {
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