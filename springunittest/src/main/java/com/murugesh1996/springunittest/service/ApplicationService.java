package com.murugesh1996.springunittest.service;

import com.murugesh1996.springunittest.dao.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    public double addGradeResultsForSingleClass(List<Double> numbers) {
        return applicationDao.addGradeResultsForSingleClass(numbers);
    }

    public double findGradePointAverage (List<Double> grades ) {
        return applicationDao.findGradePointAverage(grades);
    }

    public Object checkNull(Object obj) {
        return applicationDao.checkNull(obj);
    }

}
