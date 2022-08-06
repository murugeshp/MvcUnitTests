package com.murugesh1996.springmvc;

import com.murugesh1996.springmvc.models.CollegeStudent;
import com.murugesh1996.springmvc.models.HistoryGrade;
import com.murugesh1996.springmvc.models.MathGrade;
import com.murugesh1996.springmvc.models.ScienceGrade;
import com.murugesh1996.springmvc.repository.HistoryGradeDAO;
import com.murugesh1996.springmvc.repository.MathGradeDAO;
import com.murugesh1996.springmvc.repository.ScienceGradeDAO;
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

    @Autowired
    MathGradeDAO mathGradeDAO;

    @Autowired
    ScienceGradeDAO scienceGradeDAO;

    @Autowired
    HistoryGradeDAO historyGradeDAO;

    @BeforeEach
    public void setUpDatabase(){
        jdbc.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Murugesh', 'Palanisamy', 'Murugesh1996@gmail.com')");

        jdbc.execute("insert into math_grade(id, student_id, grade) values (1, 1, 100.00)");
        jdbc.execute("insert into science_grade(id, student_id, grade) values (1, 1, 100.00)");
        jdbc.execute("insert into history_grade(id, student_id, grade) values (1, 1, 100.00)");
    }

    @AfterEach
    public void setUpAfterTransaction(){

        jdbc.execute("DELETE FROM student");
        jdbc.execute("DELETE FROM math_grade");
        jdbc.execute("DELETE FROM science_grade");
        jdbc.execute("DELETE FROM history_grade");
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

    @Test
    public void createGradeService(){
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(90.50, 1, "science"));
        assertTrue(studentService.createGrade(95.00, 1, "history"));
        Iterable<MathGrade> mathGrades = mathGradeDAO.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDAO.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeDAO.findGradeByStudentId(1);
        assertTrue(mathGrades.iterator().hasNext(), "Student has math grade");
        assertTrue(scienceGrades.iterator().hasNext(), "Student has science grade");
        assertTrue(historyGrades.iterator().hasNext(), "Student has history grade");
    }

    @Test
    public void createGradeServiceReturnFalse(){
        assertFalse(studentService.createGrade(110.50, 1, "math"));
        assertFalse(studentService.createGrade(80.50, 2, "math"));
        assertFalse(studentService.createGrade(-80.50, 1, "math"));
        assertFalse(studentService.createGrade(95.00, 1, "social"));
    }

}
