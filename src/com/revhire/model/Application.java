package com.revhire.model;

import java.util.Date;

public class Application {

    private Job job;
    private JobSeeker jobSeeker;
    private ApplicationStatus status;
    private Date appliedDate;
    private String coverLetter;

    public Application(Job job, JobSeeker jobSeeker, String coverLetter) {
        this.job = job;
        this.jobSeeker = jobSeeker;
        this.coverLetter = coverLetter;
        this.status = ApplicationStatus.APPLIED;
        this.appliedDate = new Date();
    }

    public Job getJob() {
        return job;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    public Date getAppliedDate() {
        return appliedDate;
    }

}
