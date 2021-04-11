package by.teachmeskills.dao;

import by.teachmeskills.exception.DaoException;

public interface SelectStudentsDao {
    void selectStudentsAndGroups() throws DaoException;
    void selectStudentsUnder21() throws DaoException;
    void selectStudentsOrderByAge() throws DaoException;
    void selectStudentsOrderByAvgMark() throws DaoException;
    void updateVasia() throws DaoException;
    void selectGroupsAndAvgGroups()throws DaoException;
}
