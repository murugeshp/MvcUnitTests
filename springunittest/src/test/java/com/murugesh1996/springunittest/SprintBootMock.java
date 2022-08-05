package com.murugesh1996.springunittest;

import com.murugesh1996.springunittest.dao.ApplicationDao;
import com.murugesh1996.springunittest.models.CollegeStudent;
import com.murugesh1996.springunittest.models.StudentGrades;
import com.murugesh1996.springunittest.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = SpringUnitTestApplication.class)
public class SprintBootMock {

    private static int count = 0;

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach(){
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Roby");
        studentOne.setEmailAddress("eric.roby@luv2code_school.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsTestAddGrades(){
        when(applicationDao.addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults())).thenReturn(100.00);

        assertEquals(100.00, applicationService.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("Find GPA")
    @Test
    public void assertEqualsTestFindGPA(){
        when(applicationDao.findGradePointAverage(
                studentGrades.getMathGradeResults())).thenReturn(88.00);

        assertEquals(88.00, applicationService.findGradePointAverage(
                studentOne.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1)).findGradePointAverage(studentGrades.getMathGradeResults());
    }

    @DisplayName("Not Null")
    @Test
    public void assertEqualsNotNull(){
        when(applicationDao.checkNull(studentOne)).thenReturn(true);
        assertNotNull(applicationService.checkNull(studentOne));
    }

    @DisplayName("Throw Exception")
    @Test
    public void assertThrowException(){
        when(applicationDao.checkNull(studentOne)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> applicationService.checkNull(studentOne));
    }

    @DisplayName("Multiple stubbing")
    @Test
    public void stubbingConsecutiveCalls(){
        when(applicationDao.checkNull(studentOne)).thenThrow(new RuntimeException()).thenReturn("some test");
        assertThrows(RuntimeException.class, () -> applicationService.checkNull(studentOne));
        assertEquals("some test",applicationService.checkNull(studentOne));
    }
}
