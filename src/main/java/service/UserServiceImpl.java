package service;

import dao.UserDao;
import entity.User;
import exceptions.EntityNotFoundException;
import exceptions.InvalidEntityException;
import exceptions.ObjectInitializeException;
import exceptions.UserServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import validator.UserValidator;

import java.util.List;

@Service
@Transactional
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

    @Transactional
    @Override
    public List<User> readAllUsers() throws UserServiceException {
        try {
            LOGGER.info("Reading all users");
            return userDao.readAllUsers();
        } catch (ObjectInitializeException | DataAccessException e) {
            LOGGER.error("Reading all users failed", e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public void add(User user) throws UserServiceException {
        try {
            List<User> users = userDao.readAllUsers();
            if (!userValidator.validate(user) && users.contains(user)) {
                LOGGER.error("User validation failed during addition");
                throw new InvalidEntityException("The parameters you entered are incorrect");
            }
            for (User tempUser : users) {
                if (tempUser.getId() == user.getId()) {
                    LOGGER.error("Duplicate user id");
                    throw new InvalidEntityException("A user with this id already exists");
                }
            }
            userDao.add(user);
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Adding users failed", e);
            throw new UserServiceException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User read(long id) throws UserServiceException {
        try {
            if (userDao.read(id) == null) {
                LOGGER.error("User is not found");
                throw new EntityNotFoundException("The user you are looking for has not been found");
            }
            return userDao.read(id);
        } catch (EntityNotFoundException | DataAccessException e) {
            LOGGER.error("Reading users failed", e);
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
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Updating users failed", e);
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
        } catch (InvalidEntityException | DataAccessException e) {
            LOGGER.error("Deleting users failed", e);
            throw new UserServiceException(e);
        }
    }
}