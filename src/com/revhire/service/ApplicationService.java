package com.revhire.service;

import com.revhire.dao.ApplicationDao;
import com.revhire.dao.UserDao;
import com.revhire.model.*;

import java.util.ArrayList;
import java.util.List;

public class ApplicationService {

    private ApplicationDao applicationDao;
    private NotificationService notificationService;
    private UserDao userDao;

    // ✅ Constructor injection (CORRECT)
    public ApplicationService(ApplicationDao applicationDao,
                              NotificationService notificationService,
                              UserDao userDao) {
        this.applicationDao = applicationDao;
        this.notificationService = notificationService;
        this.userDao = userDao;
    }

    // ======================================================
    // APPLY FOR JOB
    // ======================================================
    public void applyForJob(Job job, JobSeeker jobSeeker, String coverLetter) {

        Application application =
                new Application(job, jobSeeker, coverLetter);

        applicationDao.save(application);

        notificationService.notifyUser(
                jobSeeker,
                "Applied for job: " + job.getTitle()
        );
    }

    // ======================================================
    // VIEW APPLICATIONS (JOB SEEKER)
    // ======================================================
    public List<Application> viewApplications(JobSeeker jobSeeker) {
        return applicationDao.findByJobSeeker(jobSeeker);
    }

    // ======================================================
    // WITHDRAW APPLICATION
    // ======================================================
    public void withdrawApplication(Application application, String reason) {

        application.setStatus(ApplicationStatus.WITHDRAWN);

        notificationService.notifyUser(
                application.getJobSeeker(),
                "Application withdrawn. Reason: " + reason
        );
    }

    // ======================================================
    // VIEW APPLICATIONS FOR JOB (EMPLOYER)
    // ======================================================
    public List<Application> getApplicationsForJob(int jobId) {
        return applicationDao.findByJobId(jobId);
    }

    // ======================================================
    // FILTER APPLICANTS BY EXPERIENCE
    // ======================================================
    public List<JobSeeker> filterApplicantsByExperience(int jobId,
                                                        int minExperience) {

        List<JobSeeker> result = new ArrayList<JobSeeker>();

        List<Application> applications =
                applicationDao.findByJobId(jobId);

        for (Application app : applications) {
            JobSeeker js = app.getJobSeeker();
            if (js.getExperienceYears() >= minExperience) {
                result.add(js);
            }
        }
        return result;
    }

    // ======================================================
    // SHORTLIST / REJECT APPLICATION  (FIXED ✅)
    // ======================================================
    public void updateApplicationStatus(int jobId,
                                        String email,
                                        ApplicationStatus status,
                                        String comment) {

        int rows =
                applicationDao.updateStatus(jobId, email, status);

        if (rows == 0) {
            throw new IllegalArgumentException(
                    "Invalid Job ID or candidate has not applied."
            );
        }

        // ✅ fetch REAL user from DB (FK safe)
        User user = userDao.findByEmail(email);

        if (user != null) {
            notificationService.notifyUser(
                    user,
                    "Application status updated to " + status +
                    ". Comment: " + comment
            );
        }
    }
}
