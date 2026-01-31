package com.revhire.service;

import com.revhire.dao.ApplicationDao;
import com.revhire.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicantSearchService {

    private ApplicationDao applicationDao;

    public ApplicantSearchService(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    // ✅ Filter by experience
    public List<JobSeeker> filterByExperience(int jobId, int minExperience) {

        List<JobSeeker> result = new ArrayList<JobSeeker>();

        List<Application> applications =
                applicationDao.findByJobId(jobId);

        for (Application app : applications) {
            JobSeeker seeker = app.getJobSeeker();
            if (seeker.getExperienceYears() >= minExperience) {
                result.add(seeker);
            }
        }
        return result;
    }

    // ✅ Filter by skill
    public List<JobSeeker> filterBySkill(int jobId, String skill) {

        List<JobSeeker> result = new ArrayList<JobSeeker>();

        for (Application app : applicationDao.findByJobId(jobId)) {
            JobSeeker seeker = app.getJobSeeker();
            if (seeker.getSkills() != null &&
                seeker.getSkills().toLowerCase()
                        .contains(skill.toLowerCase())) {
                result.add(seeker);
            }
        }
        return result;
    }

    // ✅ Filter by education
    public List<JobSeeker> filterByEducation(int jobId, String education) {

        List<JobSeeker> result = new ArrayList<JobSeeker>();

        for (Application app : applicationDao.findByJobId(jobId)) {
            JobSeeker seeker = app.getJobSeeker();
            if (seeker.getEducation() != null &&
                seeker.getEducation().toLowerCase()
                        .contains(education.toLowerCase())) {
                result.add(seeker);
            }
        }
        return result;
    }

    // ✅ Filter by application date
    public List<JobSeeker> filterByApplicationDate(int jobId, Date fromDate) {

        List<JobSeeker> result = new ArrayList<JobSeeker>();

        for (Application app : applicationDao.findByJobId(jobId)) {
            if (app.getAppliedDate().after(fromDate)) {
                result.add(app.getJobSeeker());
            }
        }
        return result;
    }
}
