package by.teachmeskills.dao;

import by.teachmeskills.entity.Student;
import by.teachmeskills.exception.DaoException;
import java.util.List;

public interface InsertStudentsDao {
    void saveAllStudents(List<Student> students) throws DaoException;
}
