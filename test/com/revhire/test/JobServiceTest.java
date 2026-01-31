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
import com.revhire.model.Employer;
import com.revhire.model.Job;
import com.revhire.model.JobType;
import com.revhire.service.JobService;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

    @Mock
    private JobDao jobDao;

    private JobService jobService;

    @Before
    public void setUp() {
        jobService = new JobService(jobDao);
    }

    // ================= FIND BY ID =================

    @Test
    public void testFindJobById() {

        Job job = mock(Job.class);
        when(jobDao.findById(1)).thenReturn(job);

        Job result = jobService.findJobById(1);

        assertNotNull(result);
        verify(jobDao).findById(1);
    }

    // ================= CREATE JOB =================

    @Test
    public void testCreateJob() {

        Employer employer = mock(Employer.class);
        Job job = mock(Job.class);

        when(employer.getEmail()).thenReturn("emp@test.com");

        jobService.createJob(employer, job);

        verify(jobDao).save(job, "emp@test.com");
    }

    // ================= GET ALL JOBS =================

    @Test
    public void testGetAllJobs() {

        when(jobDao.findAll())
                .thenReturn(Arrays.asList(mock(Job.class)));

        List<Job> jobs = jobService.getAllJobs();

        assertEquals(1, jobs.size());
    }

    // ================= SEARCH =================

    @Test
    public void testSearchJobs_FilterByTitleAndLocation() {

        Job job = mock(Job.class);

        when(job.isOpen()).thenReturn(true);
        when(job.getTitle()).thenReturn("Java Developer");
        when(job.getLocation()).thenReturn("Bangalore");
        when(job.getExperienceRequired()).thenReturn(2);
        when(job.getJobType()).thenReturn(JobType.FULL_TIME);

        when(jobDao.findAll()).thenReturn(Arrays.asList(job));

        List<Job> result =
                jobService.searchJobs(
                        "Java Developer",
                        "Bangalore",
                        1,
                        JobType.FULL_TIME
                );

        assertEquals(1, result.size());
    }

    @Test
    public void testSearchJobs_ClosedJobIgnored() {

        Job job = mock(Job.class);
        when(job.isOpen()).thenReturn(false);

        when(jobDao.findAll()).thenReturn(Arrays.asList(job));

        List<Job> result =
                jobService.searchJobs(null, null, 0, null);

        assertEquals(0, result.size());
    }

    @Test
    public void testSearchJobsWithSalaryAndExperience() {

        Job job = mock(Job.class);

        when(job.getTitle()).thenReturn("Backend Engineer");
        when(job.getLocation()).thenReturn("Hyderabad");
        when(job.getExperienceRequired()).thenReturn(3);
        when(job.getSalary()).thenReturn(80000.0);
        when(job.getJobType()).thenReturn(JobType.FULL_TIME);

        when(jobDao.findAll()).thenReturn(Arrays.asList(job));

        List<Job> result =
                jobService.searchJobs(
                        "Backend",
                        "Hyderabad",
                        2,
                        50000,
                        JobType.FULL_TIME
                );

        assertEquals(1, result.size());
    }

    @Test
    public void testSearchJobs_FailsOnSalary() {

        Job job = mock(Job.class);

        when(job.getSalary()).thenReturn(30000.0);
        when(job.getExperienceRequired()).thenReturn(5);

        when(jobDao.findAll()).thenReturn(Arrays.asList(job));

        List<Job> result =
                jobService.searchJobs(
                        null,
                        null,
                        2,
                        50000,
                        null
                );

        assertEquals(0, result.size());
    }
}
