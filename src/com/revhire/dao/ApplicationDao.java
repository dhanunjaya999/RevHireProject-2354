package com.revhire.dao;

import java.util.List;

import com.revhire.model.Application;
import com.revhire.model.ApplicationStatus;
import com.revhire.model.JobSeeker;

public interface ApplicationDao {

    void save(Application application);

    List<Application> findByJobSeeker(JobSeeker jobSeeker);

    List<Application> findAll();
    
    List<Application> findByJobId(int jobId);
    int updateStatus(int jobId, String email, ApplicationStatus status);



}
