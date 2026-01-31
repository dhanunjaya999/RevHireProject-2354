package com.revhire.model;

import java.util.ArrayList;
import java.util.List;

public class Employer extends User {

    private String companyName;
    private String industry;
    private String location;
    private List<Job> jobs = new ArrayList<Job>();

    public Employer(String email, String password,
                    String companyName, String industry, String location) {
        super(email, password, UserType.EMPLOYER);
        this.companyName = companyName;
        this.industry = industry;
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public String getIndustry() {
        return industry;
    }

    public String getLocation() {
        return location;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public void setLocation(String location) {
        this.location = location;
    }


}

