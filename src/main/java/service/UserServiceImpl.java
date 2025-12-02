package service;

import dao.BookDao;
import dao.UserDao;
import entity.Book;
import entity.User;
import exceptions.*;
import validator.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserDao userDao;
    private static UserValidator userValidator;
    private static BookService bookService;

    public void setUserDao(UserDao userDao){
        UserServiceImpl.userDao = userDao;
    }

    public void setUserValidator(UserValidator userValidator){
        UserServiceImpl.userValidator = userValidator;
    }

    public void setBookService(BookService bookService){
        UserServiceImpl.bookService = bookService;
    }

    @Override
    public List<User> readAllUsers() throws UserServiceException {
        try {
            return userDao.readAllUsers();
        } catch (ObjectInitializeException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            if (!userValidator.validate(user) && userDao.readAllUsers().contains(user)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
            for (User tempUser : userDao.readAllUsers()) {
                if (tempUser.getId() == user.getId()) {
                    throw new InvalidEntityException("Пользователь с таким айди уже существует");
                }
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
    public void update(User user) throws UserServiceException {
        try {
            User oldUser = read(user.getId());
            if (!userValidator.validate(user)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            } else if (!oldUser.getBooks().isEmpty()) {
                throw new InvalidEntityException("Вы не можете обновить пользователя с арендованой книгой");
            } else if (!user.getBooks().isEmpty()) {
                throw new InvalidEntityException("Книги у обновляемого пользователя должны отсутствовать");
            }
            userDao.update(user);
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws UserServiceException {
        try {
            User readUser = read(id);
            if (id < 0 && !readUser.getBooks().isEmpty()) {
                throw new InvalidEntityException("Увы, Вашего пользователя нельзя удалить");
            }
            userDao.delete(id);
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