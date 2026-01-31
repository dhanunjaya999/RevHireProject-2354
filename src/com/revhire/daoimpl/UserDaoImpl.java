package com.revhire.daoimpl;

import com.revhire.dao.UserDao;
import com.revhire.model.*;
import com.revhire.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER =
        "INSERT INTO USERS " +
        "(EMAIL, PASSWORD, USER_TYPE, NAME, EXPERIENCE_YEARS, COMPANY_NAME, INDUSTRY, LOCATION) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_EMAIL =
        "SELECT * FROM USERS WHERE EMAIL = ?";

    @Override
    public void save(User user) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_USER);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword()); // package-level access
            ps.setString(3, user.getUserType().name());

            if (user instanceof JobSeeker) {
                JobSeeker js = (JobSeeker) user;
                ps.setString(4, js.getEmail());
                ps.setInt(5, js.getExperienceYears());
                ps.setString(6, null);
                ps.setString(7, null);
                ps.setString(8, null);
            }
            else if (user instanceof Employer) {
                Employer emp = (Employer) user;
                ps.setString(4, null);
                ps.setInt(5, 0);
                ps.setString(6, emp.getCompanyName());
                ps.setString(7, emp.getIndustry());
                ps.setString(8, emp.getLocation());
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByEmail(String email) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_EMAIL);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String userType = rs.getString("USER_TYPE");

                if ("JOB_SEEKER".equals(userType)) {
                    return new JobSeeker(
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("NAME"),
                        rs.getInt("EXPERIENCE_YEARS")
                    );
                }
                else if ("EMPLOYER".equals(userType)) {
                    return new Employer(
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("COMPANY_NAME"),
                        rs.getString("INDUSTRY"),
                        rs.getString("LOCATION")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String UPDATE_EMPLOYER =
    	    "UPDATE USERS SET COMPANY_NAME=?, INDUSTRY=?, LOCATION=? WHERE EMAIL=?";

    	@Override
    	public void updateEmployerProfile(Employer employer) {

    	    try {Connection con = DBConnectionUtil.getConnection();
    	         PreparedStatement ps =
    	                 con.prepareStatement(UPDATE_EMPLOYER);

    	        ps.setString(1, employer.getCompanyName());
    	        ps.setString(2, employer.getIndustry());
    	        ps.setString(3, employer.getLocation());
    	        ps.setString(4, employer.getEmail());

    	        ps.executeUpdate();

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}

}
