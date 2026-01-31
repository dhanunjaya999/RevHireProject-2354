package com.revhire.daoimpl;

import com.revhire.dao.ApplicationDao;
import com.revhire.model.*;
import com.revhire.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

    // ✅ SEQUENCE USED HERE
    private static final String INSERT_APP =
        "INSERT INTO APPLICATIONS " +
        "(APPLICATION_ID, JOB_ID, JOB_SEEKER_EMAIL, STATUS, APPLIED_DATE, COVER_LETTER) " +
        "VALUES (APP_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_JOBSEEKER =
        "SELECT * FROM APPLICATIONS WHERE JOB_SEEKER_EMAIL = ?";

    private static final String SELECT_ALL =
        "SELECT * FROM APPLICATIONS";

    @Override
    public void save(Application application) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_APP);

            ps.setInt(1, application.getJob().getJobId());
            ps.setString(2, application.getJobSeeker().getEmail());
            ps.setString(3, application.getStatus().name());
            ps.setTimestamp(4,
                    new Timestamp(application.getAppliedDate().getTime()));
            ps.setString(5, null); // cover letter optional

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Application> findByJobSeeker(JobSeeker jobSeeker) {

        List<Application> applications = new ArrayList<Application>();

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(SELECT_BY_JOBSEEKER);

            ps.setString(1, jobSeeker.getEmail());

            ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Job job = new Job(
                            rs.getInt("JOB_ID"),
                            null,
                            null,
                            null,
                            0,
                            null,
                            0,
                            JobType.FULL_TIME
                    );

                    Application app =
                            new Application(job, jobSeeker, null);

                    app.setStatus(
                            ApplicationStatus.valueOf(
                                    rs.getString("STATUS"))
                    );

                    applications.add(app);
                }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }

    @Override
    public List<Application> findAll() {

        List<Application> applications = new ArrayList<Application>();

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("JOB_ID"),
                        null,
                        null,
                        null,
                        0,
                        null,
                        0,
                        JobType.FULL_TIME
                );

                JobSeeker js =
                        new JobSeeker(
                                rs.getString("JOB_SEEKER_EMAIL"),
                                null,
                                null,
                                0
                        );

                Application app =
                        new Application(job, js, null);

                app.setStatus(
                        ApplicationStatus.valueOf(
                                rs.getString("STATUS"))
                );

                applications.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }

    private static final String SELECT_BY_JOB =
    	    "SELECT * FROM APPLICATIONS WHERE JOB_ID = ?";

    	@Override
    	public List<Application> findByJobId(int jobId) {

    	    List<Application> applications = new ArrayList<Application>();

    	    try {Connection con = DBConnectionUtil.getConnection();
    	         PreparedStatement ps =
    	                 con.prepareStatement(SELECT_BY_JOB);

    	        ps.setInt(1, jobId);

    	         ResultSet rs = ps.executeQuery();

    	            while (rs.next()) {

    	                Job job = new Job(
    	                        jobId,
    	                        null, null, null, 0, null, 0,
    	                        JobType.FULL_TIME
    	                );

    	                JobSeeker js =
    	                        new JobSeeker(
    	                                rs.getString("JOB_SEEKER_EMAIL"),
    	                                null, null, 0
    	                        );

    	                Application app =
    	                        new Application(job, js, null);

    	                app.setStatus(
    	                        ApplicationStatus.valueOf(
    	                                rs.getString("STATUS"))
    	                );

    	                applications.add(app);
    	            }
    	        

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }

    	    return applications;
    	}

    	private static final String UPDATE_STATUS =
    		    "UPDATE APPLICATIONS SET STATUS=? WHERE JOB_ID=? AND JOB_SEEKER_EMAIL=?";

    		@Override
    		public int updateStatus(int jobId,
    		                        String email,
    		                        ApplicationStatus status) {

    		    try {Connection con = DBConnectionUtil.getConnection();
    		         PreparedStatement ps =
    		                 con.prepareStatement(UPDATE_STATUS);

    		        ps.setString(1, status.name());
    		        ps.setInt(2, jobId);
    		        ps.setString(3, email);

    		        return ps.executeUpdate(); // ✅ rows affected

    		    } catch (Exception e) {
    		        throw new RuntimeException(e);
    		    }
    		}



}
