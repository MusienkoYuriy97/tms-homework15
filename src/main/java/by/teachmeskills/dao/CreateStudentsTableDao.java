package by.teachmeskills.dao;

import by.teachmeskills.exception.DaoException;

public interface CreateStudentsTableDao {
    void createTables() throws DaoException;
    void dropTablesIfExist() throws DaoException;
}
