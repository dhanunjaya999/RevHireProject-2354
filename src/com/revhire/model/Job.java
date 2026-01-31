package com.revhire.model;

import java.util.ArrayList;
import java.util.List;

public class Job {

    private int jobId;
    private String title;
    private String description;
    private String skills;
    private int experienceRequired;
    private String location;
    private double salary;
    private JobType jobType;
    private boolean isOpen = true;

    private List<Application> applications =
            new ArrayList<Application>();

    public Job(int jobId, String title, String description,
               String skills, int experienceRequired,
               String location, double salary, JobType jobType) {

        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.skills = skills;
        this.experienceRequired = experienceRequired;
        this.location = location;
        this.salary = salary;
        this.jobType = jobType;
    }

    // ✅ REQUIRED GETTERS
    public int getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getExperienceRequired() {
        return experienceRequired;
    }

    public JobType getJobType() {
        return jobType;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void closeJob() {
        isOpen = false;
    }

    public void openJob() {
        isOpen = true;
    }
    public void reopenJob() {
        isOpen = true;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setExperienceRequired(int experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setOpen(boolean open) {
        this.isOpen = open;
    }


    public void setSalary(double salary) {
        this.salary = salary;
    }
    public String getDescription() { return description; }
    public String getSkills() { return skills; }
    public double getSalary() { return salary; }


}
