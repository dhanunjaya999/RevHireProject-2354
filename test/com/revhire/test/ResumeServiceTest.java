package com.revhire.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revhire.model.JobSeeker;
import com.revhire.model.Resume;
import com.revhire.service.ResumeService;

@RunWith(MockitoJUnitRunner.class)
public class ResumeServiceTest {

    private ResumeService resumeService;

    @Mock
    private JobSeeker jobSeeker;

    @Mock
    private Resume resume;

    @Before
    public void setUp() {
        resumeService = new ResumeService();
        when(jobSeeker.getResume()).thenReturn(resume);
        when(jobSeeker.getEmail()).thenReturn("test@test.com");
    }

    // ================= UPDATE OBJECTIVE =================

    @Test
    public void testUpdateObjective() {

        resumeService.updateObjective(jobSeeker, "Software Engineer role");

        verify(resume).setObjective("Software Engineer role");
    }

    // ================= UPDATE EDUCATION =================

    @Test
    public void testUpdateEducation() {

        resumeService.updateEducation(jobSeeker, "B.Tech CSE");

        verify(resume).setEducation("B.Tech CSE");
    }

    // ================= UPDATE EXPERIENCE =================

    @Test
    public void testUpdateExperience() {

        resumeService.updateExperience(jobSeeker, "3 years Java");

        verify(resume).setExperience("3 years Java");
    }

    // ================= UPDATE SKILLS =================

    @Test
    public void testUpdateSkills() {

        resumeService.updateSkills(jobSeeker, "Java, Spring");

        verify(resume).setSkills("Java, Spring");
    }

    // ================= UPDATE PROJECTS =================

    @Test
    public void testUpdateProjects() {

        resumeService.updateProjects(jobSeeker, "RevHire Portal");

        verify(resume).setProjects("RevHire Portal");
    }

    // ================= VIEW RESUME =================

    @Test
    public void testViewResume() {

        when(resume.getObjective()).thenReturn("Engineer");
        when(resume.getEducation()).thenReturn("B.Tech");
        when(resume.getExperience()).thenReturn("3 years");
        when(resume.getSkills()).thenReturn("Java");
        when(resume.getProjects()).thenReturn("Job Portal");

        // Should run without exception
        resumeService.viewResume(jobSeeker);
    }
}

