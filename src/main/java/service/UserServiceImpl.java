package service;

import dao.UserDao;
import entity.User;
import exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.UserValidator;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
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
            logger.info("Reading all users");
            return userDao.readAllUsers();
        } catch (ObjectInitializeException | UserDaoException e) {
            logger.error("Reading all users was failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            if (!userValidator.validate(user) && userDao.readAllUsers().contains(user)) {
                logger.error("User validation was failed during addition");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            for (User tempUser : userDao.readAllUsers()) {
                if (tempUser.getId() == user.getId()) {
                    logger.error("Duplicate user id");
                    throw new InvalidEntityException("A user with this id already exists");
                }
            }
            userDao.add(user);
        } catch (InvalidEntityException | UserDaoException e) {
            logger.error("Adding users was failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(long id) throws UserServiceException {
        try {
            if (userDao.read(id) == null) {
                logger.error("User is not found");
                throw new EntityNotFoundException("The user you are looking for has not been found");
            }
            return userDao.read(id);
        } catch (EntityNotFoundException | UserDaoException e) {
            logger.error("Reading users was failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void update(User user) throws UserServiceException {
        try {
            User oldUser = read(user.getId());
            if (!userValidator.validate(user)) {
                logger.error("Incorrect parameters from the user");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            } else if (!oldUser.getBooks().isEmpty()) {
                logger.error("The old user has books");
                throw new InvalidEntityException("You cannot update a user with a rented book");
            } else if (!user.getBooks().isEmpty()) {
                logger.error("The new user has books");
                throw new InvalidEntityException("The updated user should not have any books");
            }
            userDao.update(user);
        } catch (InvalidEntityException | UserDaoException e) {
            logger.error("Updating users was failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws UserServiceException {
        try {
            User readUser = read(id);
            if (id < 0 && !readUser.getBooks().isEmpty()) {
                logger.error("Deleted user has books");
                throw new InvalidEntityException("The deleted user should not have any books");
            }
            userDao.delete(id);
        } catch (InvalidEntityException | UserDaoException e) {
            logger.error("Deleting users was failed");
            throw new UserServiceException(e);
        }
    }
}