package com.revhire.dao;

import java.util.List;

import com.revhire.model.Notification;
import com.revhire.model.User;

public interface NotificationDao {

    void save(User user, Notification notification);

    List<Notification> findByUser(User user);
}

