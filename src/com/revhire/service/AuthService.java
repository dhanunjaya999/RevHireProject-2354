package com.revhire.service;

import com.revhire.dao.UserDao;
import com.revhire.model.Employer;
import com.revhire.model.JobSeeker;
import com.revhire.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    private UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerJobSeeker(String email, String password,
                                  String name, int experienceYears) {

        JobSeeker jobSeeker =
                new JobSeeker(email, password, name, experienceYears);

        userDao.save(jobSeeker);
        logger.info("JobSeeker registered: " + email);
    }

    public void registerEmployer(String email, String password,
                                 String companyName, String industry,
                                 String location) {

        Employer employer =
                new Employer(email, password, companyName, industry, location);

        userDao.save(employer);
        logger.info("Employer registered: " + email);
    }

    public User login(String email, String password) {

        User user = userDao.findByEmail(email);

        if (user != null && user.validatePassword(password)) {
            logger.info("Login successful: " + email);
            return user;
        }

        logger.warn("Login failed: " + email);
        return null;
    }
}
