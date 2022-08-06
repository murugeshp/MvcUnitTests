package com.murugesh1996.springmvc.service;

import com.murugesh1996.springmvc.models.CollegeStudent;
import com.murugesh1996.springmvc.models.HistoryGrade;
import com.murugesh1996.springmvc.models.MathGrade;
import com.murugesh1996.springmvc.models.ScienceGrade;
import com.murugesh1996.springmvc.repository.HistoryGradeDAO;
import com.murugesh1996.springmvc.repository.MathGradeDAO;
import com.murugesh1996.springmvc.repository.ScienceGradeDAO;
import com.murugesh1996.springmvc.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    @Qualifier("mathGrades")
    MathGrade mathGrade;

    @Autowired
    @Qualifier("scienceGrades")
    ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    HistoryGrade historyGrade;

    @Autowired
    MathGradeDAO mathGradeDAO;

    @Autowired
    ScienceGradeDAO scienceGradeDAO;

    @Autowired
    HistoryGradeDAO historyGradeDAO;

    public void createStudent(String firstName, String lastName, String email){
        CollegeStudent collegeStudent = new CollegeStudent( firstName,  lastName,  email);
        collegeStudent.setId(0);
        studentDAO.save(collegeStudent);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDAO.findById(id);
        if(student.isPresent())
            return true;
        return false;
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id))
            studentDAO.deleteById(id);
    }

    public Iterable<CollegeStudent> getGradebook() {
        return studentDAO.findAll();
    }

    public boolean createGrade(double grade, int id, String gradeType) {
        if(!checkIfStudentIsNull(id))
            return false;
        if(grade>0 && grade<100)
            if(gradeType.equals("math")){
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(id);
                mathGradeDAO.save(mathGrade);
                return true;
            }else if(gradeType.equals("science")){
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(id);
                scienceGradeDAO.save(scienceGrade);
                return true;
            }else if(gradeType.equals("history")){
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(id);
                historyGradeDAO.save(historyGrade);
                return true;
            }
        return false;
    }
}
