package com.murugesh1996.springmvc.service;

import com.murugesh1996.springmvc.models.CollegeStudent;
import com.murugesh1996.springmvc.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    StudentDAO studentDAO;

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
}
