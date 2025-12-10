package service;

import dao.UserDao;
import entity.User;
import exceptions.*;
import validator.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private UserValidator userValidator;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
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
}