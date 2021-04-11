package by.teachmeskills.entity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentUnmarshall {
    private static final String FILE_NAME = "students.xml";

    public static List<Student> unmarshallStudent(){
        List<Student> students = new ArrayList<>();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StudentWrapper studentWrapper = (StudentWrapper) unmarshaller.unmarshal(new File(FILE_NAME));
            for (Student student : studentWrapper.getStudents()) {
                students.add(student);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return students;
    }
}
