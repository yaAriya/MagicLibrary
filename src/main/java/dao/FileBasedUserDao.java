package dao;

import exceptions.UserDaoException;

public interface FileBasedUserDao extends UserDao {
    void initializeCash() throws UserDaoException;
}
