package com.revhire.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.revhire.dao.UserDao;
import com.revhire.model.User;
import com.revhire.service.AuthService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private AuthService authService;

    @Before
    public void setUp() {
        authService = new AuthService(userDao);
    }

    // ================= REGISTER JOB SEEKER =================

    @Test
    public void testRegisterJobSeeker() {

        // Act
        authService.registerJobSeeker(
                "js@test.com",
                "pass123",
                "John",
                3
        );

        // Assert
        verify(userDao, times(1)).save((User) any());
    }

    // ================= REGISTER EMPLOYER =================

    @Test
    public void testRegisterEmployer() {

        // Act
        authService.registerEmployer(
                "emp@test.com",
                "admin123",
                "ABC Corp",
                "IT",
                "Bangalore"
        );

        // Assert
        verify(userDao, times(1)).save((User) any());
    }

    // ================= LOGIN SUCCESS =================

    @Test
    public void testLoginSuccess() {

        // Arrange
        User mockUser = mock(User.class);
        when(userDao.findByEmail("user@test.com"))
                .thenReturn(mockUser);
        when(mockUser.validatePassword("pass123"))
                .thenReturn(true);

        // Act
        User result =
                authService.login("user@test.com", "pass123");

        // Assert
        assertNotNull(result);
        verify(userDao).findByEmail("user@test.com");
    }

    // ================= LOGIN FAIL - WRONG PASSWORD =================

    @Test
    public void testLoginFailWrongPassword() {

        // Arrange
        User mockUser = mock(User.class);
        when(userDao.findByEmail("user@test.com"))
                .thenReturn(mockUser);
        when(mockUser.validatePassword("wrong"))
                .thenReturn(false);

        // Act
        User result =
                authService.login("user@test.com", "wrong");

        // Assert
        assertNull(result);
    }

    // ================= LOGIN FAIL - USER NOT FOUND =================

    @Test
    public void testLoginFailUserNotFound() {

        // Arrange
        when(userDao.findByEmail("no@test.com"))
                .thenReturn(null);

        // Act
        User result =
                authService.login("no@test.com", "pass");

        // Assert
        assertNull(result);
    }
}

