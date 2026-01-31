package com.revhire.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revhire.dao.UserDao;
import com.revhire.model.Employer;
import com.revhire.model.JobSeeker;
import com.revhire.service.ProfileService;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private UserDao userDao;

    private ProfileService profileService;

    @Before
    public void setUp() {
        profileService = new ProfileService(userDao);
    }

    // ================= UPDATE EMPLOYER PROFILE =================

    @Test
    public void testUpdateEmployerProfile() {

        Employer employer = mock(Employer.class);

        ProfileService.updateEmployerProfile(employer);

        verify(userDao).updateEmployerProfile(employer);
    }

    // ================= UPDATE EDUCATION =================

    @Test
    public void testUpdateEducation() {

        JobSeeker seeker = mock(JobSeeker.class);

        profileService.updateEducation(seeker, "B.Tech");

        verify(seeker).setEducation("B.Tech");
    }

    // ================= UPDATE WORK EXPERIENCE =================

    @Test
    public void testUpdateWorkExperience() {

        JobSeeker seeker = mock(JobSeeker.class);

        profileService.updateWorkExperience(seeker, "3 years");

        verify(seeker).setWorkExperience("3 years");
    }

    // ================= UPDATE SKILLS =================

    @Test
    public void testUpdateSkills() {

        JobSeeker seeker = mock(JobSeeker.class);

        profileService.updateSkills(seeker, "Java, SQL");

        verify(seeker).setSkills("Java, SQL");
    }

    // ================= UPDATE CERTIFICATIONS =================

    @Test
    public void testUpdateCertifications() {

        JobSeeker seeker = mock(JobSeeker.class);

        profileService.updateCertifications(seeker, "AWS Certified");

        verify(seeker).setCertifications("AWS Certified");
    }

    // ================= VIEW PROFILE =================

    @Test
    public void testViewProfile() {

        JobSeeker seeker = mock(JobSeeker.class);

        when(seeker.getEmail()).thenReturn("test@test.com");
        when(seeker.getEducation()).thenReturn("B.Tech");
        when(seeker.getWorkExperience()).thenReturn("3 years");
        when(seeker.getSkills()).thenReturn("Java");
        when(seeker.getCertifications()).thenReturn("AWS");

        // should just execute without exception
        profileService.viewProfile(seeker);
    }
}
