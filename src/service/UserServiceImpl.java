package service;

import dao.UserDao;

import dao.UserDaoImpl;

import enity.User;

import exceptions.EntityNotFoundException;

import exceptions.ObjectInitializeException;

import exceptions.UserServiceException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
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
            if (user != null) {
                userDao.add(user);
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User upDate(User user, int index) throws UserServiceException {
        try {
            if (userDao.upDate(user, index) != null) {
                return userDao.upDate(user, index);
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(int userID) throws UserServiceException {
        try {
            if (userDao.read(userID) != null) {
                return userDao.read(userID);
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(User user) throws UserServiceException {
        try {
            if (user != null) {
                userDao.delete(user);
            } else {
                throw new EntityNotFoundException("Удаляемый Вами пользователь не найден");
            }
        } catch (EntityNotFoundException e) {
            throw new UserServiceException(e);
        }
    }
}
