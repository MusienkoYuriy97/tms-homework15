package by.teachmeskills;

import by.teachmeskills.dao.*;
import by.teachmeskills.entity.Student;
import by.teachmeskills.entity.StudentUnmarshall;
import by.teachmeskills.exception.DaoException;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {

            List<Student> students = StudentUnmarshall.unmarshallStudent();

            //если таблица существет то удаляем и записываем данные
            CreateStudentsTableDao csti = new CreateStudentsTableDaoImp();
            csti.dropTablesIfExist();
            csti.createTables();

            //сохраняем все данные о студентах
            InsertStudentsDao sdi = new InsertStudentsDaoImp();
            sdi.saveAllStudents(students);

            //Выводим результаты
            SelectStudentsDao ssd = new SelectStudentsDaoImp();
            ssd.selectStudentsAndGroups();
            ssd.selectStudentsUnder21();
            ssd.selectStudentsOrderByAge();
            ssd.selectStudentsOrderByAvgMark();
            ssd.updateVasia();
            ssd.selectGroupsAndAvgGroups();
        } catch (DaoException exception) {
            exception.printStackTrace();
        }
    }
}
