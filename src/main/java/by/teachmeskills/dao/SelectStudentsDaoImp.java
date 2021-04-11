package by.teachmeskills.dao;

import by.teachmeskills.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectStudentsDaoImp implements SelectStudentsDao{
    private static final String SELECT_STUDENTS_AND_GROUPS = "SELECT g.group_name,COUNT(\"students\")" +
            "FROM student_groups g " +
            "INNER JOIN students s " +
            "WHERE s.group_id = g.group_id " +
            "group by g.group_name";
    private static final String SELECT_STUDENTS_UNDER21 = "SELECT g.group_name,s.student_name,s.student_age " +
            "FROM student_groups g " +
            "INNER JOIN students s " +
            "ON s.group_id = g.group_id " +
            "WHERE s.student_age < 21";
    private static final String SELECT_STUDENTS_ORDER_BY_AGE = "SELECT g.group_name,s.student_name,s.student_age " +
            "FROM student_groups g " +
            "INNER JOIN students s " +
            "ON s.group_id = g.group_id " +
            "order by s.student_age";

    private static final String SELECT_STUDENTS_ORDER_BY_AVG_MARK = "SELECT m.student_id,s.student_name, g.group_name, avg(m.mark) as 'student_average' " +
            "FROM student_marks m " +
            "INNER JOIN students s " +
            "ON s.student_id = m.student_id " +
            "INNER JOIN student_groups g " +
            "ON s.group_id = g.group_id " +
            "group by m.student_id " +
            "order by avg(m.mark)";
    private static final String UPDATE_VASIA = "UPDATE students s " +
            "SET s.group_id = ? " +
            "WHERE s.student_name = 'Вася' and s.group_id = ?";

    private static final String SELECT_GROUP_AND_AVG_GROUPS = "SELECT  g.group_name, avg(m.mark) as 'group_average' " +
            "FROM student_marks m " +
            "INNER JOIN students s " +
            "ON s.student_id = m.student_id " +
            "INNER JOIN student_groups g " +
            "ON s.group_id = g.group_id " +
            "group by g.group_name";

    @Override
    public void selectStudentsAndGroups() throws DaoException{
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_STUDENTS_AND_GROUPS)){
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                System.out.printf("В группе - %s учится %s студентов\n",
                        rs.getString("g.group_name"),
                        rs.getInt("COUNT(\"students\")"));
            }
            System.out.println();
        } catch (SQLException ex){
            throw new DaoException();
        }
    }

    @Override
    public void selectStudentsUnder21() throws DaoException{
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_STUDENTS_UNDER21)){
            ResultSet rs = stmt.executeQuery();

            System.out.println("Список всех студетов с возрастом до 21 года:");
            while (rs.next()){
                System.out.printf("имя студента: %s; возраст: %s; группа: %s.\n",
                        rs.getString("s.student_name"),
                        rs.getInt("s.student_age"),
                        rs.getString("g.group_name"));
            }
            System.out.println();
        } catch (SQLException ex){
            throw new DaoException();
        }
    }

    @Override
    public void selectStudentsOrderByAge() throws DaoException{
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_STUDENTS_ORDER_BY_AGE)){
            ResultSet rs = stmt.executeQuery();

            System.out.println("Список всех студетов(+групп) отсортированных по возрату:");
            while (rs.next()){
                System.out.printf("имя студента: %s; возраст: %s; группа: %s.\n",
                        rs.getString("s.student_name"),
                        rs.getInt("s.student_age"),
                        rs.getString("g.group_name"));
            }
            System.out.println();
        } catch (SQLException ex){
            throw new DaoException();
        }
    }

    public void selectStudentsOrderByAvgMark() throws DaoException{
        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_STUDENTS_ORDER_BY_AVG_MARK)){
            ResultSet rs = stmt.executeQuery();

            System.out.println("Список всех студетов отсортированных по среднему баллу:");
            while (rs.next()){
                System.out.printf("имя студента: %s; средний балл: %.2f; группа: %s.\n",
                        rs.getString("s.student_name"),
                        rs.getDouble("student_average"),
                        rs.getString("g.group_name"));
            }
            System.out.println();
        } catch (SQLException ex){
           throw new DaoException();
        }
    }

    @Override
    public void updateVasia() throws DaoException {

        try(Connection con = MySqlConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE_VASIA)){
            stmt.setInt(1, InsertStudentsDaoImp.getGroupId("С40"));
            stmt.setInt(2, InsertStudentsDaoImp.getGroupId("С42"));
            int rs = stmt.executeUpdate();
            System.out.println("Вася успешно переведен из С42 в С40");
            System.out.println();
        } catch (SQLException ex){
            throw new DaoException();
        }
    }

    public void selectGroupsAndAvgGroups() throws DaoException {
        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_GROUP_AND_AVG_GROUPS)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Список всех групп и средние баллы по группам:");
            while (rs.next()) {
                System.out.printf("Группа: %s; средний балл всех студентов в группе: %.2f.\n",
                        rs.getString("g.group_name"),
                        rs.getDouble("group_average"));
            }
            System.out.println();
        } catch (SQLException ex) {
            throw new DaoException();
        }

    }
}
