package service;

import dao.UserDao;

import dao.UserDaoImpl;

import enity.User;

import exceptions.EntityNotFoundException;

import exceptions.InvalidEntityException;

import exceptions.ObjectInitializeException;

import exceptions.UserServiceException;

import validator.UserValidator;

import validator.UserValidatorImpl;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserValidator userValidator;

    public UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
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
                throw new InvalidEntityException();
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
                throw new InvalidEntityException();
            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(int id) throws UserServiceException {
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
    public void delete(User user) throws UserServiceException {
        try {
            if (userValidator.validate(user) == true) {
                userDao.delete(user);
            } else {
                throw new InvalidEntityException();
            }
        } catch (InvalidEntityException e) {
            throw new UserServiceException(e);
        }
    }
}
