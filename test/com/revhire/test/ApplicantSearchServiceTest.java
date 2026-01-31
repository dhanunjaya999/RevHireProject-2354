package com.revhire.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revhire.dao.ApplicationDao;
import com.revhire.model.Application;
import com.revhire.model.JobSeeker;
import com.revhire.service.ApplicantSearchService;

@RunWith(MockitoJUnitRunner.class)
public class ApplicantSearchServiceTest {

    @Mock
    private ApplicationDao applicationDao;

    @InjectMocks
    private ApplicantSearchService searchService;

    @Before
    public void setUp() {
        searchService = new ApplicantSearchService(applicationDao);
    }

    // ================= FILTER BY EXPERIENCE =================

    @Test
    public void testFilterByExperience() {

        JobSeeker seeker = mock(JobSeeker.class);
        when(seeker.getExperienceYears()).thenReturn(4);

        Application app = mock(Application.class);
        when(app.getJobSeeker()).thenReturn(seeker);

        when(applicationDao.findByJobId(1))
                .thenReturn(Arrays.asList(app));

        List<JobSeeker> result =
                searchService.filterByExperience(1, 3);

        assertEquals(1, result.size());
    }

    // ================= FILTER BY SKILL =================

    @Test
    public void testFilterBySkill() {

        JobSeeker seeker = mock(JobSeeker.class);
        when(seeker.getSkills()).thenReturn("Java, SQL");

        Application app = mock(Application.class);
        when(app.getJobSeeker()).thenReturn(seeker);

        when(applicationDao.findByJobId(1))
                .thenReturn(Arrays.asList(app));

        List<JobSeeker> result =
                searchService.filterBySkill(1, "java");

        assertEquals(1, result.size());
    }

    // ================= FILTER BY EDUCATION =================

    @Test
    public void testFilterByEducation() {

        JobSeeker seeker = mock(JobSeeker.class);
        when(seeker.getEducation()).thenReturn("B.Tech Computer Science");

        Application app = mock(Application.class);
        when(app.getJobSeeker()).thenReturn(seeker);

        when(applicationDao.findByJobId(1))
                .thenReturn(Arrays.asList(app));

        List<JobSeeker> result =
                searchService.filterByEducation(1, "computer");

        assertEquals(1, result.size());
    }

    // ================= FILTER BY APPLICATION DATE =================

    @Test
    public void testFilterByApplicationDate() {

        Date fromDate = new Date(System.currentTimeMillis() - 86400000);

        Application app = mock(Application.class);
        when(app.getAppliedDate())
                .thenReturn(new Date());

        JobSeeker seeker = mock(JobSeeker.class);
        when(app.getJobSeeker()).thenReturn(seeker);

        when(applicationDao.findByJobId(1))
                .thenReturn(Arrays.asList(app));

        List<JobSeeker> result =
                searchService.filterByApplicationDate(1, fromDate);

        assertEquals(1, result.size());
    }
}

