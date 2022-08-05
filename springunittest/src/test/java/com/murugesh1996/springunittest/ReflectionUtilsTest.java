package com.murugesh1996.springunittest;

import com.murugesh1996.springunittest.models.CollegeStudent;
import com.murugesh1996.springunittest.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringUnitTestApplication.class)
public class ReflectionUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach(){
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Roby");
        studentOne.setEmailAddress("eric.roby@luv2code_school.com");
        studentOne.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(studentOne, "id", 1);
        ReflectionTestUtils.setField(studentOne, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75))));
    }

    @Test
    public void getPrivateField(){
        assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
    }

    @Test
    public void invokePrivateMethod(){
        assertEquals("Eric 1",
                ReflectionTestUtils.invokeMethod(studentOne,"getFirstNameAndId"), "Fail private method not call");
    }
}
