package com.murugesh1996.springmvc;

import com.murugesh1996.springmvc.models.CollegeStudent;
import com.murugesh1996.springmvc.repository.StudentDAO;
import com.murugesh1996.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    StudentAndGradeService studentCreateServiceMock;

    @Autowired
    StudentDAO studentDAO;

    @BeforeAll
    public static void setUp(){
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Murugesh");
        request.setParameter("lastname", "Palanisamy");
        request.setParameter("emailAddress", "Murugesh1996@gmail.com");
    }

    @BeforeEach
    public void beforeEach(){
        jdbc.execute("insert into student(id, firstname, lastname, email_address) " +
                "values (1, 'Murugesh', 'Palanisamy', 'Murugesh2022@gmail.com')");
    }

    @AfterEach
    public void setUpAfterTransaction(){
        jdbc.execute("DELETE FROM student");
    }

    @Test
    public void getStudentHttpRequest() throws Exception{
        CollegeStudent studentOne = new CollegeStudent("Murugesh", "Palanisamy",
                "Murugesh1996@gmail.com");
        CollegeStudent studentTwo = new CollegeStudent("Shivani", "Murugesh",
                "Shivani@gmail.com");
        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));
        when(studentCreateServiceMock.getGradebook()).thenReturn(collegeStudentList);
        assertIterableEquals(collegeStudentList, studentCreateServiceMock.getGradebook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception{

        CollegeStudent studentOne = new CollegeStudent("Murugesh", "Palanisamy",
                "Murugesh1996@gmail.com");
        CollegeStudent studentTwo = new CollegeStudent("Shivani", "Murugesh",
                "Shivani@gmail.com");
        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));
        when(studentCreateServiceMock.getGradebook()).thenReturn(collegeStudentList);
        assertIterableEquals(collegeStudentList, studentCreateServiceMock.getGradebook());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                        .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

        CollegeStudent collegeStudent = studentDAO.findByEmailAddress("Murugesh1996@gmail.com");
        assertNotNull(collegeStudent, "Student should be found");
    }

    @Test
    public void deleteStudentHttpRequest() throws Exception{
        assertTrue(studentDAO.findById(1).isPresent(),"Student is present");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/delete/student/{id}", 1))
                        .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
        assertFalse(studentDAO.findById(1).isPresent(),"Student is present");
    }

    @Test
    public void deleteStudentHttpRequestErrorPage() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/delete/student/{id}", 0))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");
    }
}
