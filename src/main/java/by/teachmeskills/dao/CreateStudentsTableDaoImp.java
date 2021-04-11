package by.teachmeskills.dao;

import by.teachmeskills.exception.DaoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateStudentsTableDaoImp implements CreateStudentsTableDao {
    private static final String CREATE_GROUPS = "CREATE TABLE student_groups (" +
            "group_id INT(10) AUTO_INCREMENT PRIMARY KEY," +
            "group_name VARCHAR(20) NOT NULL)";
    private static final String CREATE_STUDENTS = "CREATE TABLE students (" +
            "student_id INT AUTO_INCREMENT PRIMARY KEY," +
            "student_name VARCHAR(30) NOT NULL," +
            "student_age INT," +
            "group_id INT," +
            "FOREIGN KEY (group_id) REFERENCES student_groups (group_id)" +
            ")";
    private static final String CREATE_MARKS = "CREATE TABLE student_marks (" +
            "marks_id INT AUTO_INCREMENT PRIMARY KEY," +
            "student_id INT NOT NULL," +
            "mark INT," +
            "FOREIGN KEY (student_id) REFERENCES students (student_id)" +
            ")";
    private static final String DROP_GROUPS = "DROP TABLE IF EXISTS student_groups;";
    private static final String DROP_STUDENTS = "DROP TABLE IF EXISTS students";
    private static final String DROP_MARKS = "DROP TABLE IF EXISTS student_marks;";

    @Override
    public void createTables() throws DaoException {
        execute(CREATE_GROUPS, CREATE_STUDENTS, CREATE_MARKS);
    }

    @Override
    public void dropTablesIfExist() throws DaoException {
        execute(DROP_MARKS, DROP_STUDENTS, DROP_GROUPS);
    }

    private static void execute(String dropGroups, String dropStudents, String dropMarks) throws DaoException {
        try (Connection connection = MySqlConnection.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(dropGroups);
            statement.execute(dropStudents);
            statement.execute(dropMarks);
            connection.commit();

        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}
