package dao;

import exceptions.BookDaoException;

public interface FileBasedBookDao extends BookDao{
    void initializeCash() throws BookDaoException;
}
