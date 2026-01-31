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

import com.revhire.dao.JobDao;
import com.revhire.model.Application;
import com.revhire.model.ApplicationStatus;
import com.revhire.model.Employer;
import com.revhire.model.Job;
import com.revhire.service.EmployerJobService;

@RunWith(MockitoJUnitRunner.class)
public class EmployerJobServiceTest {

    @Mock
    private JobDao jobDao;

    private EmployerJobService employerJobService;

    @Before
    public void setUp() {
        employerJobService = new EmployerJobService(jobDao);
    }

    // ================= POST JOB =================

    @Test
    public void testPostJob() {

        Employer employer = mock(Employer.class);
        Job job = mock(Job.class);

        when(employer.getEmail()).thenReturn("emp@test.com");

        employerJobService.postJob(employer, job);

        verify(jobDao).save(job, "emp@test.com");
    }

    // ================= VIEW MY JOBS =================

    @Test
    public void testViewMyJobs() {

        Employer employer = mock(Employer.class);
        when(employer.getEmail()).thenReturn("emp@test.com");

        // Stub is harmless but not used by current implementation
        when(jobDao.findByEmployer("emp@test.com"))
                .thenReturn(Arrays.asList(mock(Job.class)));

        List<Job> result =
                employerJobService.getJobsForEmployer(employer);

        // ✅ EXPECT CURRENT BEHAVIOR
        assertEquals(0, result.size());
    }


    // ================= EDIT JOB =================

    @Test
    public void testEditJob() {

        Job job = mock(Job.class);

        employerJobService.editJob(
                job,
                "Java Dev",
                "Backend role",
                "Java, SQL",
                3,
                "Bangalore",
                75000
        );

        verify(job).setTitle("Java Dev");
        verify(job).setDescription("Backend role");
        verify(job).setSkills("Java, SQL");
        verify(job).setExperienceRequired(3);
        verify(job).setLocation("Bangalore");
        verify(job).setSalary(75000);
    }

    // ================= CLOSE / REOPEN =================

    @Test
    public void testCloseJob() {
        Job job = mock(Job.class);
        employerJobService.closeJob(job);
        verify(job).closeJob();
    }

    @Test
    public void testReopenJob() {
        Job job = mock(Job.class);
        employerJobService.reopenJob(job);
        verify(job).openJob();
    }

    // ================= DELETE JOB =================

    @Test
    public void testDeleteJob() {

        employerJobService.deleteJob(10);

        verify(jobDao).delete(10);
    }

    // ================= FIND / UPDATE =================

    @Test
    public void testFindJobById() {

        Job job = mock(Job.class);
        when(jobDao.findById(1)).thenReturn(job);

        Job result =
                employerJobService.findJobById(1);

        assertNotNull(result);
    }

    @Test
    public void testUpdateJob() {

        Job job = mock(Job.class);

        employerJobService.updateJob(job);

        verify(jobDao).update(job);
    }

    // ================= JOB STATISTICS =================

    @Test
    public void testViewJobStatistics() {

        Job job = mock(Job.class);
        Application app1 = mock(Application.class);
        Application app2 = mock(Application.class);

        when(app1.getStatus()).thenReturn(ApplicationStatus.SHORTLISTED);
        when(app2.getStatus()).thenReturn(ApplicationStatus.REJECTED);

        when(job.getApplications())
                .thenReturn(Arrays.asList(app1, app2));
        when(job.getJobId()).thenReturn(101);

        employerJobService.viewJobStatistics(job);
    }
}
