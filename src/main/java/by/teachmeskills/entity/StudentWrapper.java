package by.teachmeskills.entity;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "students")
public class StudentWrapper {

    private List<Student> students;

    public StudentWrapper(List<Student> students) {
        this.students = students;
    }

    public StudentWrapper() {
    }


    @XmlElement(name = "student")
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "StudentWrapper{" +
                "students=" + students +
                '}';
    }
}
