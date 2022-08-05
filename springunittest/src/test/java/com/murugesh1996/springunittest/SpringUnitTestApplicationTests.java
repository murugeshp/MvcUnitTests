package com.murugesh1996.springunittest;

import com.murugesh1996.springunittest.models.CollegeStudent;
import com.murugesh1996.springunittest.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringUnitTestApplicationTests {

	private static int count;

	@Autowired
	ApplicationContext context;

	@Value("${info.school.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@BeforeEach
	public void beforeEach(){
		count = count + 1;
		System.out.println("Testing: " + appInfo + " which is " + appDescription +
				"  Version: " + appVersion + ". Execution of test method " + count);
		student.setFirstname("Eric");
		student.setLastname("Roby");
		student.setEmailAddress("eric.roby@luv2code_school.com");
		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
		student.setStudentGrades(studentGrades);
	}

	@DisplayName("Add grade results for Student grades")
	@Test
	public void addGradeResultsForStudentGrades(){
		assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}

	@DisplayName("Add grade results for Student grades not equals")
	@Test
	public void addGradeResultsForStudentGradesNotEqual(){
		assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}

	@DisplayName("Is grade greater true")
	@Test
	public void isGradeGreaterStudentGrades(){
		assertTrue(studentGrades.isGradeGreater(90,75),"failure - should be true");
	}

	@DisplayName("Check null for student grades")
	@Test
	public void checkNullForStudentGrades(){
		assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()),"ojbject should not be null");
		//assertNotNull(studentGrades.checkNull(null),"ojbject should not be null");
	}

	@DisplayName("Verify students are prototypes")
	@Test
	public void verifyStudentsArePrototypes() {
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);

		assertNotSame(student, studentTwo);
	}


	@DisplayName("Find grade points averrage")
	@Test
	public void findGradePointAverage(){
		assertAll("Testing all assertEquals",
				() -> assertEquals(353.25,studentGrades.addGradeResultsForSingleClass(
						student.getStudentGrades().getMathGradeResults())),
				() -> assertEquals(88.31, studentGrades.findGradePointAverage(
						student.getStudentGrades().getMathGradeResults())),
				() -> assertTrue(studentGrades.isGradeGreater(90,75),"failure - should be true")
		);
	}

}
