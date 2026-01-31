package com.revhire.service;

import com.revhire.dao.JobDao;
import com.revhire.model.Employer;
import com.revhire.model.Job;
import com.revhire.model.JobType;

import java.util.ArrayList;
import java.util.List;

public class JobService {

	private JobDao jobDao;

	public JobService(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	public Job findJobById(int jobId) {
		return jobDao.findById(jobId);
	}

	public void createJob(Employer employer, Job job) {
	    jobDao.save(job, employer.getEmail());
	}


	public List<Job> getAllJobs() {
		return jobDao.findAll();
	}

	// 🔍 Job search with filters
	public List<Job> searchJobs(String title, String location, int experience,
			JobType jobType) {

		List<Job> result = new ArrayList<Job>();

		for (Job job : jobDao.findAll()) {

			if (!job.isOpen()) {
				continue;
			}

			if (title != null && !job.getTitle().equalsIgnoreCase(title)) {
				continue;
			}

			if (location != null
					&& !job.getLocation().equalsIgnoreCase(location)) {
				continue;
			}

			if (experience > job.getExperienceRequired()) {
				continue;
			}

			if (jobType != null && job.getJobType() != jobType) {
				continue;
			}

			result.add(job);
		}
		return result;
	}

	public List<Job> searchJobs(String role, String location,
			int minExperience, double minSalary, JobType jobType) {

		List<Job> result = new ArrayList<Job>();

		for (Job job : jobDao.findAll()) {

			if (role != null
					&& !job.getTitle().toLowerCase()
							.contains(role.toLowerCase())) {
				continue;
			}
			if (location != null
					&& !job.getLocation().equalsIgnoreCase(location)) {
				continue;
			}
			if (job.getExperienceRequired() < minExperience) {
				continue;
			}
			if (job.getSalary() < minSalary) {
				continue;
			}
			if (jobType != null && job.getJobType() != jobType) {
				continue;
			}
			result.add(job);
		}
		return result;
	}

	public List<Job> getJobsForEmployer(Employer employer) {
	    // for now return all jobs
	    // later you can filter by employer email
	    return jobDao.findAll();
	}

}
