package service;

import dao.UserDao;
import entity.User;
import exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import validator.UserValidator;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

/*    public void initializeCache(List<Book> books) {
        try {
           userDao.initializeCache(books);
        } catch (UserDaoException e) {
            throw new UserServiceException(e);
        }
    }*/

    @Override
    public List<User> readAllUsers() throws UserServiceException {
        try {
            LOGGER.info("Reading all users");
            return userDao.readAllUsers();
        } catch (ObjectInitializeException | UserDaoException e) {
            LOGGER.error("Reading all users failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            if (!userValidator.validate(user) && userDao.readAllUsers().contains(user)) {
                LOGGER.error("User validation failed during addition");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            for (User tempUser : userDao.readAllUsers()) {
                if (tempUser.getId() == user.getId()) {
                    LOGGER.error("Duplicate user id");
                    throw new InvalidEntityException("A user with this id already exists");
                }
            }
            userDao.add(user);
        } catch (InvalidEntityException | UserDaoException e) {
            LOGGER.error("Adding users failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public User read(long id) throws UserServiceException {
        try {
            if (userDao.read(id) == null) {
                LOGGER.error("User is not found");
                throw new EntityNotFoundException("The user you are looking for has not been found");
            }
            return userDao.read(id);
        } catch (EntityNotFoundException | UserDaoException e) {
            LOGGER.error("Reading users failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void update(User user) throws UserServiceException {
        try {
            if (!userValidator.validate(user)) {
                LOGGER.error("Incorrect parameters from the user");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            userDao.update(user);
        } catch (InvalidEntityException | UserDaoException e) {
            LOGGER.error("Updating users failed");
            throw new UserServiceException(e);
        }
    }

    @Override
    public void delete(long id) throws UserServiceException {
        try {
            User readUser = read(id);
            if (id < 0 && !readUser.getBooks().isEmpty()) {
                LOGGER.error("Deleted user has books");
                throw new InvalidEntityException("The deleted user should not has any books");
            }
            userDao.delete(id);
        } catch (InvalidEntityException | UserDaoException e) {
            LOGGER.error("Deleting users failed");
            throw new UserServiceException(e);
        }
    }
}