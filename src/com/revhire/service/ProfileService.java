package com.revhire.service;

import com.revhire.dao.UserDao;
import com.revhire.model.Employer;
import com.revhire.model.JobSeeker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProfileService.class);
    
    private static UserDao userDao;

    public ProfileService(UserDao userDao) {
        this.userDao = userDao;
    }

    public static void updateEmployerProfile(Employer employer) {
        userDao.updateEmployerProfile(employer);
    }

    /**
     * Update education details
     */
    public void updateEducation(JobSeeker jobSeeker, String education) {
        jobSeeker.setEducation(education);
        logger.info("Profile education updated for " + jobSeeker.getEmail());
    }

    /**
     * Update work experience details
     */
    public void updateWorkExperience(JobSeeker jobSeeker, String experience) {
        jobSeeker.setWorkExperience(experience);
        logger.info("Profile experience updated for " + jobSeeker.getEmail());
    }

    /**
     * Update skills
     */
    public void updateSkills(JobSeeker jobSeeker, String skills) {
        jobSeeker.setSkills(skills);
        logger.info("Profile skills updated for " + jobSeeker.getEmail());
    }

    /**
     * Update certifications
     */
    public void updateCertifications(JobSeeker jobSeeker, String certifications) {
        jobSeeker.setCertifications(certifications);
        logger.info("Profile certifications updated for " + jobSeeker.getEmail());
    }

    /**
     * View full profile
     */
    public void viewProfile(JobSeeker jobSeeker) {

        System.out.println("\n===== PROFILE DETAILS =====");
        System.out.println("Email: " + jobSeeker.getEmail());
        System.out.println("Education: " + jobSeeker.getEducation());
        System.out.println("Work Experience: " + jobSeeker.getWorkExperience());
        System.out.println("Skills: " + jobSeeker.getSkills());
        System.out.println("Certifications: " + jobSeeker.getCertifications());
        System.out.println("===========================");
    }
}
