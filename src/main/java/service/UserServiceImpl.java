package service;

import dao.UserDao;
import dao.UserDaoImpl;
import entity.Book;
import entity.User;
import exceptions.*;
import validator.UserValidator;
import validator.Validator;

import java.util.List;
import java.util.Map;

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
    public Map<Long, User> readAllUsersFromDatabase() {
        return userDao.readAllUsersFromDatabase();
    }

    @Override
    public List<User> readAllUsersFromFile() throws UserServiceException {
        try {
            return userDao.readAllUsersFromFile();
        } catch (ObjectInitializeException e) {
            throw new UserServiceException(e);
        }
    }
    @Override
    public void addToDatabase(User user) throws UserServiceException {
        try {
            if (!userValidator.validate(user) && userDao.getUsers().contains(user)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
            userDao.addToDatabase(user);
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void addToFile(User user) throws UserServiceException {
        try {
            if (!userValidator.validate(user) && userDao.getUsers().contains(user)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            }
            userDao.addToFile(user);
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }


    @Override
    public User readFromDatabase(long id){
        try {
            if (userDao.readFromDatabase(id) == null) {
                throw new EntityNotFoundException("Искаемый Вами пользователь не найден");
            }
            return userDao.readFromDatabase(id);
        } catch (EntityNotFoundException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User readFromFile(long id) throws UserServiceException {
        try {
            if (userDao.readFromFile(id) == null) {
                throw new EntityNotFoundException("Искаемый Вами пользователь не найден");
            }
            return userDao.readFromFile(id);
        } catch (EntityNotFoundException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void updateInDatabase(User user){
        userDao.updateInDatabase(user);
    }

    @Override
    public void updateInFile(User user) throws UserServiceException {
        try {
            User oldUser = readFromFile(user.getId());
            if (!userValidator.validate(user)) {
                throw new InvalidEntityException("Параметры, введенные Вами некорректны");
            } else if (!oldUser.getBooks().isEmpty()) {
                throw new InvalidEntityException("Вы не можете обновить пользователя с арендованой книгой");
            } else if (!user.getBooks().isEmpty()) {
                throw new InvalidEntityException("Книги у обновляемого пользователя должны отсутствовать");
            }
             userDao.updateInFile(user);
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void deleteFromDatabase(long id){
        try {
            User readUser = readFromFile(id);
            if (id < 0 && !readUser.getBooks().isEmpty()) {
                throw new InvalidEntityException("Увы, Вашего пользователя нельзя удалить");
            }
            userDao.deleteFromDatabase(readUser);
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }
    }


    @Override
    public void deleteFromFile(long id) throws UserServiceException {
        try {
            User readUser = readFromFile(id);
            if (id < 0 && !readUser.getBooks().isEmpty()) {
                throw new InvalidEntityException("Увы, Вашего пользователя нельзя удалить");
            }
            userDao.deleteFromFile(readUser);
        } catch (InvalidEntityException | UserDaoException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void rentBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = readFromFile(userId);
            Book readBook = bookService.read(bookId);
            if (readUser == null || readBook == null) {
                throw new InvalidEntityException("Пользователь или книга не могут быть пустыми");
            } else if (readBook.getUser() != null || readUser.getBooks().contains(readBook)) {
                throw new InvalidEntityException("У книги уже есть пользователь или у пользователя уже арендована эта книга");
            }
            readUser.getBooks().add(readBook);
            updateInFile(readUser);

            readBook.setUser(readUser);
            bookService.update(readBook);
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }

    }

    @Override
    public void returnBook(long userId, long bookId) throws UserServiceException {
        try {
            User readUser = readFromFile(userId);
            Book readBook = bookService.read(bookId);
            if (readUser == null || readBook == null) {
                throw new InvalidEntityException("Пользователь или книга не могут быть пустыми");
            } else if (!readUser.getBooks().contains(readBook) || readBook.getUser() == null) {
                throw new InvalidEntityException("У книги нет пользователя или у пользователя не была арендована книга");
            }
            readUser.getBooks().remove(readBook);
            updateInFile(readUser);

            readBook.setUser(null);
            bookService.update(readBook);
        } catch (InvalidEntityException e) {
            throw new UserServiceException();
        }
    }
}