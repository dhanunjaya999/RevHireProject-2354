package com.revhire.service;

import com.revhire.dao.JobDao;
import com.revhire.model.Application;
import com.revhire.model.ApplicationStatus;
import com.revhire.model.Employer;
import com.revhire.model.Job;

import java.util.List;

public class EmployerJobService {

    private JobDao jobDao;

    public EmployerJobService(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    /**
     * Employer creates a new job
     */

    public void postJob(Employer employer, Job job) {
        jobDao.save(job, employer.getEmail());
    }

    /**
     * View all jobs posted by employer
     */
    public List<Job> viewMyJobs(Employer employer) {
        return employer.getJobs();
    }

    /**
     * Edit job details (basic fields only)
     */
    public void editJob(Job job,
                        String title,
                        String description,
                        String skills,
                        int experience,
                        String location,
                        double salary) {

        job.setTitle(title);
        job.setDescription(description);
        job.setSkills(skills);
        job.setExperienceRequired(experience);
        job.setLocation(location);
        job.setSalary(salary);
    }

    /**
     * Close a job
     */
    public void closeJob(Job job) {
        job.closeJob();
    }

    /**
     * Reopen a job
     */
    public void reopenJob(Job job) {
        job.openJob();
    }

    /**
     * Delete job
     */
    public void deleteJob(int jobId) {
        jobDao.delete(jobId);
    }

    /**
     * View job statistics
     */
    public void viewJobStatistics(Job job) {

        int total = 0;
        int shortlisted = 0;
        int rejected = 0;

        for (Application app : job.getApplications()) {
            total++;

            if (app.getStatus() == ApplicationStatus.SHORTLISTED) {
                shortlisted++;
            }
            else if (app.getStatus() == ApplicationStatus.REJECTED) {
                rejected++;
            }
        }

        System.out.println("\n=== Job Statistics ===");
        System.out.println("Job ID: " + job.getJobId());
        System.out.println("Total Applications: " + total);
        System.out.println("Shortlisted: " + shortlisted);
        System.out.println("Rejected: " + rejected);
        System.out.println("======================");
    }
    
    public List<Job> getJobsForEmployer(Employer employer) {
        return jobDao.findAll();
    }
    public Job findJobById(int jobId) {
        return jobDao.findById(jobId);
    }
    public void updateJob(Job job) {
        jobDao.update(job);
    }


}
