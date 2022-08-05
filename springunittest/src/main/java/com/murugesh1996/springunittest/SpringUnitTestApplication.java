package com.murugesh1996.springunittest;

import com.murugesh1996.springunittest.dao.ApplicationDao;
import com.murugesh1996.springunittest.models.CollegeStudent;
import com.murugesh1996.springunittest.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringUnitTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUnitTestApplication.class, args);
	}

	/* New for Section 2.2 */
	@Bean(name = "applicationExample")
	ApplicationService getApplicationService() {
		return new ApplicationService();
	}

	/* New for Section 2.2 */
	@Bean(name = "applicationDao")
	ApplicationDao getApplicationDao() {
		return new ApplicationDao();
	}

	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}

}
