package dao;

import entity.Book;
import entity.User;
import exceptions.UserDaoException;

public interface FileBasedUserDao extends UserDao {
    void initializeCash() throws UserDaoException;

    void addBookToUser(User user, Book book) throws UserDaoException;
}
