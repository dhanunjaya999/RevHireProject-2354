package com.revhire.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revhire.dao.NotificationDao;
import com.revhire.model.Notification;
import com.revhire.model.User;
import com.revhire.service.NotificationService;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    @Mock
    private NotificationDao notificationDao;

    @InjectMocks
    private NotificationService notificationService;

    @Before
    public void setUp() {
        notificationService = new NotificationService(notificationDao);
    }

    // ================= NOTIFY USER =================

    @Test
    public void testNotifyUser() {

        User user = mock(User.class);

        notificationService.notifyUser(user, "Test message");

        verify(notificationDao, times(1))
                .save(eq(user), any(Notification.class));
    }

    // ================= GET NOTIFICATIONS =================

    @Test
    public void testGetNotifications() {

        User user = mock(User.class);
        Notification notification = mock(Notification.class);

        when(notificationDao.findByUser(user))
                .thenReturn(Arrays.asList(notification));

        List<Notification> result =
                notificationService.getNotifications(user);

        assertEquals(1, result.size());
        verify(notificationDao).findByUser(user);
    }
}

