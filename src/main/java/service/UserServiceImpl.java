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
    private final UserDao USER_DAO;
    private final BookService BOOK_SERVICE;
    private final Validator<User> USER_VALIDATOR;

    public static UserServiceImpl getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserServiceImpl();
        }
        return INSTANCE;
    }

    public UserServiceImpl() {
        USER_DAO = UserDaoImpl.getInstance();
        BOOK_SERVICE = BookServiceImpl.getInstance();
        USER_VALIDATOR = UserValidator.getInstance();
    }

    @Override
    public List<User> readAllUsers() throws UserServiceException {
        try {
            return USER_DAO.readAllUsers();
        } catch (ObjectInitializeException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            if (USER_VALIDATOR.validate(user) == true) {
                USER_DAO.add(user);
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
            if (USER_VALIDATOR.validate(user) == true) {
                return USER_DAO.update(user, user.getId());
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
            if (USER_DAO.read(id) != null) {
                return USER_DAO.read(id);
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
            if (id >= 0 && readUser.getBooks().size() == 0) { // А есть ли он вообще проверить
                USER_DAO.delete(readUser);
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
            Book readBook = BOOK_SERVICE.read(bookId);
            if (readUser == null || readBook == null || readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                throw new InvalidEntityException();
            } else {
                readUser.getBooks().add(readBook);
                update(readUser);

                readBook.setUser(readUser);
                BOOK_SERVICE.update(readBook);
            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }

    }

    @Override
    public void returnBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = read(userId);
            Book readBook = BOOK_SERVICE.read(bookId);
            if (readUser == null || readBook == null || !readUser.getBooks().contains(readBook) || readBook.getUser() == null) {
                throw new InvalidEntityException();
            } else {
                readUser.getBooks().remove(readBook);
                update(readUser);

                readBook.setUser(null);
                BOOK_SERVICE.update(readBook);

            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException();
        }
    }
}