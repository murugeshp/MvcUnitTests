package com.murugesh1996.springmvc;

import com.murugesh1996.springmvc.models.CollegeStudent;
import com.murugesh1996.springmvc.repository.StudentDAO;
import com.murugesh1996.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    StudentAndGradeService studentService;

    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Murugesh', 'Palanisamy', 'Murugesh1996@gmail.com')");
    }

    @AfterEach
    public void setUpAfterTransaction(){
        jdbc.execute("DELETE FROM student");
    }

    @Test
    public void createStudentService(){
        studentService.createStudent("Murugesh", "palansiamy", "murugesh1996@gmail.com");
        CollegeStudent collegeStudent = studentDAO.findByEmailAddress("murugesh1996@gmail.com");
        assertEquals("murugesh1996@gmail.com", collegeStudent.getEmailAddress());
    }

    @Test
    public void isStudentNullCheck(){
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService(){
        assertTrue(studentDAO.findById(1).isPresent(),"Student is present");
        studentService.deleteStudent(1);
        assertFalse(studentDAO.findById(1).isPresent(),"Student is deleted");
    }

    @Sql("/insertData.sql")
    @Test
    public void getStudentGradebook(){
        Iterable<CollegeStudent> collegeStudentIterable = studentService.getGradebook();
        List<CollegeStudent> collegeStudents = new ArrayList<>();
        for(CollegeStudent collegeStudent: collegeStudentIterable){
            collegeStudents.add(collegeStudent);
        }
        assertEquals(3, collegeStudents.size());
    }

}
