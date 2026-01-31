package com.revhire.service;

import com.revhire.model.JobSeeker;
import com.revhire.model.Resume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResumeService {

    private static final Logger logger =
            LoggerFactory.getLogger(ResumeService.class);

    /**
     * Create or update resume objective
     */
    public void updateObjective(JobSeeker jobSeeker, String objective) {
        Resume resume = jobSeeker.getResume();
        resume.setObjective(objective);
        logger.info("Resume objective updated for " + jobSeeker.getEmail());
    }

    /**
     * Create or update education section
     */
    public void updateEducation(JobSeeker jobSeeker, String education) {
        Resume resume = jobSeeker.getResume();
        resume.setEducation(education);
        logger.info("Resume education updated for " + jobSeeker.getEmail());
    }

    /**
     * Create or update experience section
     */
    public void updateExperience(JobSeeker jobSeeker, String experience) {
        Resume resume = jobSeeker.getResume();
        resume.setExperience(experience);
        logger.info("Resume experience updated for " + jobSeeker.getEmail());
    }

    /**
     * Create or update skills section
     */
    public void updateSkills(JobSeeker jobSeeker, String skills) {
        Resume resume = jobSeeker.getResume();
        resume.setSkills(skills);
        logger.info("Resume skills updated for " + jobSeeker.getEmail());
    }

    /**
     * Create or update projects section
     */
    public void updateProjects(JobSeeker jobSeeker, String projects) {
        Resume resume = jobSeeker.getResume();
        resume.setProjects(projects);
        logger.info("Resume projects updated for " + jobSeeker.getEmail());
    }

    /**
     * View full resume (textual)
     */
    public void viewResume(JobSeeker jobSeeker) {
        Resume resume = jobSeeker.getResume();

        System.out.println("\n===== RESUME =====");
        System.out.println("Objective: " + resume.getObjective());
        System.out.println("Education: " + resume.getEducation());
        System.out.println("Experience: " + resume.getExperience());
        System.out.println("Skills: " + resume.getSkills());
        System.out.println("Projects: " + resume.getProjects());
        System.out.println("==================");
    }
}
