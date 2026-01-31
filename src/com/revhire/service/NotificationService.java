package com.revhire.service;

import com.revhire.dao.NotificationDao;
import com.revhire.model.Notification;
import com.revhire.model.User;

import java.util.List;

public class NotificationService {

    private NotificationDao notificationDao;

    public NotificationService(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public void notifyUser(User user, String message) {
        Notification notification = new Notification(message);
        notificationDao.save(user, notification);
    }

    public List<Notification> getNotifications(User user) {
        return notificationDao.findByUser(user);
    }
}
