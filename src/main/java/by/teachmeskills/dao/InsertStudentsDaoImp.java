package by.teachmeskills.dao;

import by.teachmeskills.entity.Student;
import by.teachmeskills.exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class InsertStudentsDaoImp implements InsertStudentsDao {
    private static final String INSERT_GROUPS = "INSERT INTO student_groups (group_name) VALUES (?)";
    private static final String INSERT_STUDENTS = "INSERT INTO students (student_name,student_age,group_id) VALUES" +
            "(?, ?, ?)";
    private static final String FIND_GROUP_ID = "SELECT group_id FROM student_groups " +
            "WHERE group_name = ?";
    private static final String INSERT_MARKS = "INSERT INTO student_marks (student_id,mark) VALUES" +
            "(?, ?)";


    @Override
    public void saveAllStudents(List<Student> students) throws DaoException {
        saveGroup(students);
        saveStudent(students);
        saveMarks(students);
    }

    private static void saveGroup(List<Student> students) throws DaoException {
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(INSERT_GROUPS)
        ) {
            con.setAutoCommit(false);
            try {
                SortedSet<String> groups = new TreeSet<>();
                for (Student student : students) {
                    groups.add(student.getGroup());
                }
                for (String groupName : groups) {
                    stmt.setString(1,groupName);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                con.commit();
            }catch (SQLException ex){
                con.rollback();
                throw ex;
            }
        }catch (SQLException ex){
            throw new DaoException();
        }
    }

    private static void saveStudent(List<Student> students) throws DaoException {
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(INSERT_STUDENTS)
        ) {
            con.setAutoCommit(false);
            try {
                for (Student student : students) {
                    stmt.setString(1, student.getName());
                    stmt.setInt(2, student.getAge());
                    stmt.setInt(3, getGroupId(student.getGroup()));
                    stmt.addBatch();
                }
                stmt.executeBatch();
                con.commit();
            }catch (SQLException ex){
                con.rollback();
                throw ex;
            }
        }catch (SQLException ex){
            throw new DaoException();
        }
    }

    public static int getGroupId(String groupName) throws SQLException {
        try (Connection connection = MySqlConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(FIND_GROUP_ID)
        ){
            stmt.setString(1,groupName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("group_id");
            }
        }
        return 0;
    }

    private static void saveMarks(List<Student> students) throws DaoException {
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(INSERT_MARKS)
        ) {
            con.setAutoCommit(false);
            try {
                int student_id = 1;
                for (Student student : students) {
                    for (Integer mark : student.getMarks()) {
                        stmt.setInt(1,student_id);
                        stmt.setInt(2,mark);
                        stmt.addBatch();
                    }
                    student_id++;

                }
                stmt.executeBatch();
                con.commit();
            }catch (SQLException ex){
                con.rollback();
                throw ex;
            }

        }catch (SQLException ex){
            throw new DaoException();
        }
    }
}
