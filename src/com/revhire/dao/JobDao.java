package com.revhire.dao;
import java.util.List;

import com.revhire.model.Job;

public interface JobDao {

    void save(Job job,String employerEmail);

    Job findById(int jobId);

    List<Job> findAll();

    void delete(int jobId);
    
    void update(Job job);
    
    List<Job> findByEmployer(String employerEmail);


}

