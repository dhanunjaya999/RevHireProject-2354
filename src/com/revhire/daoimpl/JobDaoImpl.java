package com.revhire.daoimpl;

import com.revhire.dao.JobDao;
import com.revhire.model.Job;
import com.revhire.model.JobType;
import com.revhire.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

    private static final String INSERT_JOB =
        "INSERT INTO JOBS " +
        "(JOB_ID, TITLE, DESCRIPTION, SKILLS, EXPERIENCE_REQUIRED, LOCATION, SALARY, JOB_TYPE, IS_OPEN, , EMPLOYER_EMAIL			) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static final String SELECT_ALL =
        "SELECT * FROM JOBS";

    private static final String SELECT_BY_ID =
        "SELECT * FROM JOBS WHERE JOB_ID = ?";

    private static final String DELETE_JOB =
        "DELETE FROM JOBS WHERE JOB_ID = ?";
    private static final String UPDATE_JOB =
    	    "UPDATE JOBS SET TITLE=?, DESCRIPTION=?, SKILLS=?, EXPERIENCE_REQUIRED=?, LOCATION=?, SALARY=? WHERE JOB_ID=?";

    @Override
    public void save(Job job,String employerEmail) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_JOB);

            ps.setInt(1, job.getJobId());
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getDescription());
            ps.setString(4, job.getSkills());
            ps.setInt(5, job.getExperienceRequired());
            ps.setString(6, job.getLocation());
            ps.setDouble(7, job.getSalary());
            ps.setString(8, job.getJobType().name());
            ps.setString(9, job.isOpen() ? "Y" : "N");
            ps.setString(10, employerEmail);


            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Job findById(int jobId) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID);

            ps.setInt(1, jobId);

            ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    Job job = new Job(
                            rs.getInt("JOB_ID"),
                            rs.getString("TITLE"),
                            rs.getString("DESCRIPTION"),
                            rs.getString("SKILLS"),
                            rs.getInt("EXPERIENCE_REQUIRED"),
                            rs.getString("LOCATION"),
                            rs.getDouble("SALARY"),
                            JobType.valueOf(rs.getString("JOB_TYPE"))
                    );

                    if ("N".equals(rs.getString("IS_OPEN"))) {
                        job.closeJob();
                    }

                    return job;
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Job> findAll() {

        List<Job> jobs = new ArrayList<Job>();

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Job job = new Job(
                        rs.getInt("JOB_ID"),
                        rs.getString("TITLE"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("SKILLS"),
                        rs.getInt("EXPERIENCE_REQUIRED"),
                        rs.getString("LOCATION"),
                        rs.getDouble("SALARY"),
                        JobType.valueOf(rs.getString("JOB_TYPE"))
                );

                if ("N".equals(rs.getString("IS_OPEN"))) {
                    job.closeJob();
                }

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    @Override
    public void delete(int jobId) {

        try {Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_JOB);

            ps.setInt(1, jobId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    	@Override
    	public void update(Job job) {

    	    try {Connection con = DBConnectionUtil.getConnection();
    	         PreparedStatement ps = con.prepareStatement(UPDATE_JOB);

    	        ps.setString(1, job.getTitle());
    	        ps.setString(2, job.getDescription());
    	        ps.setString(3, job.getSkills());
    	        ps.setInt(4, job.getExperienceRequired());
    	        ps.setString(5, job.getLocation());
    	        ps.setDouble(6, job.getSalary());
    	        ps.setInt(7, job.getJobId());

    	        ps.executeUpdate();

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}
    	private static final String SELECT_BY_EMPLOYER =
    		    "SELECT * FROM JOBS WHERE EMPLOYER_EMAIL = ?";

    	@Override
    	public List<Job> findByEmployer(String employerEmail) {

    	    List<Job> jobs = new ArrayList<Job>();

    	    try {Connection con = DBConnectionUtil.getConnection();
    	         PreparedStatement ps =
    	                 con.prepareStatement(SELECT_BY_EMPLOYER);

    	        ps.setString(1, employerEmail);

    	        ResultSet rs = ps.executeQuery();

    	        while (rs.next()) {

    	            Job job = new Job(
    	                    rs.getInt("JOB_ID"),
    	                    rs.getString("TITLE"),
    	                    rs.getString("DESCRIPTION"),
    	                    rs.getString("SKILLS"),
    	                    rs.getInt("EXPERIENCE_REQUIRED"),
    	                    rs.getString("LOCATION"),
    	                    rs.getDouble("SALARY"),
    	                    JobType.valueOf(
    	                            rs.getString("JOB_TYPE").toUpperCase()
    	                    )
    	            );

    	            // IS_OPEN is stored as Y / N
    	            job.setOpen(
    	                    "Y".equalsIgnoreCase(rs.getString("IS_OPEN"))
    	            );

    	            jobs.add(job);
    	        }

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }

    	    return jobs;
    	}


}
