package com.revhire.daoimpl;

import com.revhire.dao.NotificationDao;
import com.revhire.model.Notification;
import com.revhire.model.User;
import com.revhire.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotificationDaoImpl implements NotificationDao {

    private static final String INSERT_NOTIFICATION =
        "INSERT INTO NOTIFICATIONS " +
        "(NOTIFICATION_ID, USER_EMAIL, MESSAGE, CREATED_DATE) " +
        "VALUES (NOTIFY_SEQ.NEXTVAL, ?, ?, ?)";

    private static final String SELECT_BY_USER =
        "SELECT * FROM NOTIFICATIONS WHERE USER_EMAIL = ? " +
        "ORDER BY CREATED_DATE DESC";

    @Override
    public void save(User user, Notification notification) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(INSERT_NOTIFICATION);

            ps.setString(1, user.getEmail());
            ps.setString(2, notification.getMessage());
            ps.setTimestamp(3,
                    new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Notification> findByUser(User user) {

        List<Notification> notifications =
                new ArrayList<Notification>();

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(SELECT_BY_USER);

            ps.setString(1, user.getEmail());

            ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Notification notification =
                            new Notification(
                                    rs.getString("MESSAGE")
                            );
                    notifications.add(notification);
                }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }
}
