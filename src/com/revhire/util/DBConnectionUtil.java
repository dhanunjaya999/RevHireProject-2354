package com.revhire.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    // 🔹 CHANGE THESE VALUES
    private static final String DRIVER =
            "oracle.jdbc.driver.OracleDriver";

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1522:XE";

    private static final String USERNAME = "revhireproject";
    private static final String PASSWORD = "revhire123";

    static {
        try {
            Class.forName(DRIVER);
           
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
        }
    }

    private DBConnectionUtil() {
        // Prevent object creation
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
    }
}
