package com.revhire.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revhire.dao.ApplicationDao;
import com.revhire.dao.UserDao;
import com.revhire.model.*;
import com.revhire.service.ApplicationService;
import com.revhire.service.NotificationService;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationDao applicationDao;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserDao userDao;

    private ApplicationService applicationService;

    @Before
    public void setUp() {
        applicationService =
                new ApplicationService(
                        applicationDao,
                        notificationService,
                        userDao
                );
    }

    // ================= APPLY FOR JOB =================

    @Test
    public void testApplyForJob() {

        Job job = mock(Job.class);
        JobSeeker seeker = mock(JobSeeker.class);

        when(job.getTitle()).thenReturn("Java Dev");

        applicationService.applyForJob(job, seeker, "Cover Letter");

        verify(applicationDao, times(1))
                .save(any(Application.class));

        verify(notificationService)
                .notifyUser(seeker, "Applied for job: Java Dev");
    }

    // ================= VIEW APPLICATIONS =================

    @Test
    public void testViewApplications() {

        JobSeeker seeker = mock(JobSeeker.class);

        when(applicationDao.findByJobSeeker(seeker))
                .thenReturn(Arrays.asList(mock(Application.class)));

        List<Application> result =
                applicationService.viewApplications(seeker);

        assertEquals(1, result.size());
    }

    // ================= WITHDRAW APPLICATION =================

    @Test
    public void testWithdrawApplication() {

        Application application = mock(Application.class);
        JobSeeker seeker = mock(JobSeeker.class);

        when(application.getJobSeeker()).thenReturn(seeker);

        applicationService.withdrawApplication(application, "Not interested");

        verify(application)
                .setStatus(ApplicationStatus.WITHDRAWN);

        verify(notificationService)
                .notifyUser(seeker,
                        "Application withdrawn. Reason: Not interested");
    }

    // ================= GET APPLICATIONS FOR JOB =================

    @Test
    public void testGetApplicationsForJob() {

        when(applicationDao.findByJobId(1))
                .thenReturn(Arrays.asList(mock(Application.class)));

        List<Application> result =
                applicationService.getApplicationsForJob(1);

        assertEquals(1, result.size());
    }

    // ================= UPDATE APPLICATION STATUS =================

    @Test
    public void testUpdateApplicationStatus_success() {

        when(applicationDao.updateStatus(
                1,
                "user@test.com",
                ApplicationStatus.SHORTLISTED))
                .thenReturn(1);

        User user = mock(User.class);
        when(userDao.findByEmail("user@test.com"))
                .thenReturn(user);

        applicationService.updateApplicationStatus(
                1,
                "user@test.com",
                ApplicationStatus.SHORTLISTED,
                "Good profile"
        );

        verify(notificationService)
                .notifyUser(
                        user,
                        "Application status updated to SHORTLISTED. Comment: Good profile"
                );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateApplicationStatus_invalidJobOrUser() {

        when(applicationDao.updateStatus(
                99,
                "fake@test.com",
                ApplicationStatus.REJECTED))
                .thenReturn(0);

        applicationService.updateApplicationStatus(
                99,
                "fake@test.com",
                ApplicationStatus.REJECTED,
                "Not suitable"
        );
    }

    // ================= FILTER BY EXPERIENCE =================

    @Test
    public void testFilterApplicantsByExperience() {

        JobSeeker seeker = mock(JobSeeker.class);
        when(seeker.getExperienceYears()).thenReturn(5);

        Application app = mock(Application.class);
        when(app.getJobSeeker()).thenReturn(seeker);

        when(applicationDao.findByJobId(1))
                .thenReturn(Arrays.asList(app));

        List<JobSeeker> result =
                applicationService.filterApplicantsByExperience(1, 3);

        assertEquals(1, result.size());
    }
}
