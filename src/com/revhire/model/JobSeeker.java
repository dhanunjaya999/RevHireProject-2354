package com.revhire.model;

import java.util.ArrayList;
import java.util.List;

public class JobSeeker extends User {

    private String name;
    private int experienceYears;

    // 🔹 PROFILE DATA
    private String education;
    private String workExperience;
    private String skills;
    private String certifications;

    private Resume resume;
    private List<Application> applications =
            new ArrayList<Application>();

    public JobSeeker(String email, String password,
                     String name, int experienceYears) {
        super(email, password, UserType.JOB_SEEKER);
        this.name = name;
        this.experienceYears = experienceYears;
        this.resume = new Resume();
    }

    public Resume getResume() {
        return resume;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    // 🔹 PROFILE GETTERS / SETTERS

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }
}
