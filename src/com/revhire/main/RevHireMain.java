package com.revhire.main;

import com.revhire.dao.*;
import com.revhire.daoimpl.*;
import com.revhire.model.*;
import com.revhire.service.*;
import com.revhire.util.ValidationUtil;

import java.util.List;
import java.util.Scanner;

public class RevHireMain {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// ========= DAO =========
		UserDao userDao = new UserDaoImpl();
		JobDao jobDao = new JobDaoImpl();
		ApplicationDao applicationDao = new ApplicationDaoImpl();
		NotificationDao notificationDao = new NotificationDaoImpl();

		// ========= SERVICES =========
		AuthService authService = new AuthService(userDao);
		JobService jobService = new JobService(jobDao);
		NotificationService notificationService = new NotificationService(notificationDao);
		
		ApplicationService applicationService =new ApplicationService(applicationDao, notificationService, userDao);

		ResumeService resumeService = new ResumeService();
		ProfileService profileService =new ProfileService(userDao);
		EmployerJobService employerJobService = new EmployerJobService(jobDao);
		ApplicantSearchService applicantSearchService = new ApplicantSearchService(applicationDao);

		// ========= MAIN MENU =========
		while (true) {
			System.out.println("\n===== REVHIRE JOB PORTAL =====");
			System.out.println("1. Register Job Seeker");
			System.out.println("2. Register Employer");
			System.out.println("3. Login");
			System.out.println("4. Exit");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				registerJobSeeker(authService);
				break;

			case 2:
				registerEmployer(authService);
				break;

			case 3:
				User user = login(authService);
				if (user instanceof JobSeeker) {
					jobSeekerMenu((JobSeeker) user, jobService,
							applicationService, resumeService, profileService,
							notificationService);
				} else if (user instanceof Employer) {
					employerMenu(
					        (Employer) user,
					        employerJobService,
					        applicantSearchService,
					        applicationService,
					        profileService
					);
				}
				break;

			case 4:
				System.out.println("Thank you for using RevHire!");
				System.exit(0);

			default:
				System.out.println("Invalid choice.");
			}
		}
	}

	// =========================================================
	// JOB SEEKER MENU
	// =========================================================
	private static void jobSeekerMenu(JobSeeker js, JobService jobService,
			ApplicationService applicationService, ResumeService resumeService,
			ProfileService profileService,
			NotificationService notificationService) {

		while (true) {
			System.out.println("\n--- JOB SEEKER MENU ---");
			System.out.println("1. Update Profile");
			System.out.println("2. Update Resume");
			System.out.println("3. View All Jobs");
			System.out.println("4. Search Jobs");
			System.out.println("5. Apply for Job");
			System.out.println("6. View My Applications");
			System.out.println("7. Withdraw Application");
			System.out.println("8. View Notifications");
			System.out.println("9. Logout");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				profileService.updateEducation(js, input("Education: "));
				profileService.updateWorkExperience(js,
						input("Work Experience: "));
				profileService.updateSkills(js, input("Skills: "));
				profileService.updateCertifications(js,
						input("Certifications: "));
				System.out.println("Profile updated.");
				break;

			case 2:
				resumeService.updateObjective(js, input("Objective: "));
				resumeService.updateEducation(js, input("Education: "));
				resumeService.updateExperience(js, input("Experience: "));
				resumeService.updateSkills(js, input("Skills: "));
				resumeService.updateProjects(js, input("Projects: "));
				resumeService.viewResume(js);
				break;

			case 3:
				List<Job> jobs = jobService.getAllJobs();
				if (jobs.isEmpty()) {
					System.out.println("No jobs available.");
				} else {
					for (Job job : jobs) {
						System.out.println(job.getJobId() + " | "
								+ job.getTitle() + " | " + job.getLocation());
					}
				}
				break;

			case 4:
				String role = input("Job Role (press enter to skip): ");
				String location = input("Location (press enter to skip): ");
				int exp = Integer
						.parseInt(input("Min Experience (0 to skip): "));
				double salary = Double
						.parseDouble(input("Min Salary (0 to skip): "));

				List<Job> filteredJobs = jobService.searchJobs(
						role.isEmpty() ? null : role, location.isEmpty() ? null
								: location, exp, salary, null);

				if (filteredJobs.isEmpty()) {
					System.out.println("No jobs found.");
				} else {
					for (Job j : filteredJobs) {
						System.out.println(j.getJobId() + " | " + j.getTitle()
								+ " | " + j.getLocation());
					}
				}
				break;

			case 5:
				int jobId = Integer.parseInt(input("Enter Job ID to apply: "));
				Job job = jobService.findJobById(jobId);

				if (job == null) {
					System.out.println("Invalid Job ID.");
				} else if (!job.isOpen()) {
					System.out.println("Job is closed.");
				} else {
					applicationService.applyForJob(job, js, null // cover letter
																	// optional
							);
					System.out.println("Applied for job successfully.");
				}
				break;

			case 6:
				List<Application> apps = applicationService
						.viewApplications(js);
				if (apps.isEmpty()) {
					System.out.println("No applications found.");
				} else {
					for (Application app : apps) {
						System.out.println(app.getJob().getJobId() + " | "
								+ app.getJob().getTitle() + " | Status: "
								+ app.getStatus());
					}
				}
				break;
			case 7:
				List<Application> myApps = applicationService
						.viewApplications(js);

				if (myApps.isEmpty()) {
					System.out.println("No applications to withdraw.");
					break;
				}

				for (Application a : myApps) {
					System.out.println(a.getJob().getJobId() + " | "
							+ a.getJob().getTitle() + " | " + a.getStatus());
				}

				int withdrawJobId = Integer
						.parseInt(input("Enter Job ID to withdraw: "));
				String confirm = input("Are you sure? (yes/no): ");

				if ("yes".equalsIgnoreCase(confirm)) {
					String reason = input("Reason (optional): ");

					for (Application a : myApps) {
						if (a.getJob().getJobId() == withdrawJobId) {
							applicationService.withdrawApplication(a, reason);
							System.out.println("Application withdrawn.");
							break;
						}
					}
				} else {
					System.out.println("Withdrawal cancelled.");
				}
				break;

			case 8:
				List<Notification> notes = notificationService
						.getNotifications(js);
				if (notes.isEmpty()) {
					System.out.println("No notifications.");
				} else {
					for (Notification n : notes) {
						System.out.println(n.getMessage());
					}
				}
				break;

			case 9:
				return;

			default:
				System.out.println("Invalid option.");
			}
		}
	}

	// =========================================================
	// EMPLOYER MENU
	// =========================================================
	private static void employerMenu(Employer employer,
            EmployerJobService employerJobService,
            ApplicantSearchService applicantSearchService,
            ApplicationService applicationService,
            ProfileService profileService) {


		while (true) {

			System.out.println("\n--- EMPLOYER MENU ---");
			System.out.println("1. Post Job");
			System.out.println("2. View Jobs");
			System.out.println("3. Edit Job");
			System.out.println("4. Close / Reopen Job");
			System.out.println("5. Delete Job");
			System.out.println("6. View Job Statistics");
			System.out.println("7. View Applicants for Job");
			System.out.println("8. Shortlist / Reject Applicants");
			System.out.println("9. Search Applicants");
			System.out.println("10. Update Company Profile");
			System.out.println("11. Logout");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			// 1️⃣ POST JOB
			case 1:
			    JobType jobType = null;

			    while (jobType == null) {
			        try {
			            jobType = JobType.valueOf(
			                    input("Job Type (FULL_TIME/PART_TIME/CONTRACT): ")
			                            .trim()
			                            .toUpperCase()
			            );
			        } catch (IllegalArgumentException e) {
			            System.out.println("❌ Invalid job type. Please try again.");
			        }
			    }

			    Job job = new Job(
			            (int) (Math.random() * 10000),
			            input("Job Title: "),
			            input("Description: "),
			            input("Skills: "),
			            Integer.parseInt(input("Experience Required: ")),
			            input("Location: "),
			            Double.parseDouble(input("Salary: ")),
			            jobType
			    );

			    employerJobService.postJob(employer, job);
			    System.out.println("✅ Job posted successfully.");
			    break;


			case 2:
			    List<Job> jobs = employerJobService.getJobsForEmployer(employer);

			    if (jobs.isEmpty()) {
			        System.out.println("No jobs posted.");
			    } else {
			        for (Job j : jobs) {
			            System.out.println(
			                    j.getJobId() + " | " +
			                    j.getTitle() + " | " +
			                    (j.isOpen() ? "OPEN" : "CLOSED")
			            );
			        }
			    }
			    break;

			case 3:
			    int editId = Integer.parseInt(input("Enter Job ID to edit: "));

			    Job jobToEdit = employerJobService.findJobById(editId);

			    if (jobToEdit == null) {
			        System.out.println("❌ Job not found.");
			        break;
			    }

			    jobToEdit.setTitle(input("New Title: "));
			    jobToEdit.setDescription(input("New Description: "));
			    jobToEdit.setSkills(input("New Skills: "));
			    jobToEdit.setExperienceRequired(
			            Integer.parseInt(input("New Experience: "))
			    );
			    jobToEdit.setLocation(input("New Location: "));
			    jobToEdit.setSalary(
			            Double.parseDouble(input("New Salary: "))
			    );

			    employerJobService.updateJob(jobToEdit);
			    System.out.println("✅ Job updated successfully.");

			    break;


			 // 4️⃣ CLOSE / REOPEN JOB
			case 4:
			    int statusId = Integer.parseInt(input("Enter Job ID: "));

			    Job jobToUpdate = employerJobService.findJobById(statusId);

			    if (jobToUpdate == null) {
			        System.out.println("❌ Job not found.");
			        break;
			    }

			    if (jobToUpdate.isOpen()) {
			        jobToUpdate.closeJob();
			        employerJobService.updateJob(jobToUpdate);
			        System.out.println("🔒 Job closed.");
			    } else {
			        jobToUpdate.reopenJob();
			        employerJobService.updateJob(jobToUpdate);
			        System.out.println("🔓 Job reopened.");
			    }

			    break;

			 // 5️⃣ DELETE JOB
			case 5:
			    int deleteId =
			            Integer.parseInt(input("Enter Job ID to delete: "));

			    Job jobToDelete =
			            employerJobService.findJobById(deleteId);

			    if (jobToDelete == null) {
			        System.out.println("❌ Job not found.");
			        break;
			    }

			    String confirm =
			            input("Are you sure you want to delete this job? (yes/no): ");

			    if ("yes".equalsIgnoreCase(confirm)) {
			        employerJobService.deleteJob(deleteId);
			        System.out.println("🗑 Job deleted successfully.");
			    } else {
			        System.out.println("Delete cancelled.");
			    }

			    break;

			 // 6️⃣ JOB STATISTICS
			case 6:
			    List<Job> jobs1 =
			            employerJobService.getJobsForEmployer(employer);

			    if (jobs1.isEmpty()) {
			        System.out.println("No jobs found.");
			    } else {
			        for (Job j : jobs1) {
			            employerJobService.viewJobStatistics(j);
			        }
			    }
			    break;

			case 7:
			    int jobId =
			            Integer.parseInt(input("Enter Job ID: "));

			    List<Application> applications =
			            applicationService.getApplicationsForJob(jobId);

			    if (applications.isEmpty()) {
			        System.out.println("No applicants found for this job.");
			    } else {
			        for (Application app : applications) {
			            System.out.println(
			                    app.getJobSeeker().getEmail() +
			                    " | Status: " + app.getStatus() +
			                    " | Applied on: " + app.getAppliedDate()
			            );
			        }
			    }
			    break;



			 // 8️⃣ SHORTLIST / REJECT
			case 8:

			    int appJobId = Integer.parseInt(input("Enter Job ID: "));
			    String email = input("Candidate Email: ");

			    // 👇👇👇 PASTE YOUR STATUS CODE HERE 👇👇👇
			    ApplicationStatus status = null;

			    while (status == null) {

			        System.out.println("Choose status:");
			        System.out.println("1. Shortlist");
			        System.out.println("2. Reject");

			        String choice1 = input("Enter choice (1/2): ").trim();

			        if ("1".equals(choice1)) {
			            status = ApplicationStatus.SHORTLISTED;
			        } else if ("2".equals(choice1)) {
			            status = ApplicationStatus.REJECTED;
			        } else {
			            System.out.println("❌ Invalid choice. Try again.");
			        }
			    }
			    // 👆👆👆 END OF STATUS BLOCK 👆👆👆

			    String comment = input("Comment: ");

			    
			    
			    try {
			        applicationService.updateApplicationStatus(
			                appJobId,
			                email,
			                status,
			                comment
			        );
			        System.out.println("✅ Application status updated.");
			    } catch (IllegalArgumentException e) {
			        System.out.println("❌ " + e.getMessage());
			    }

			    System.out.println("✅ Application status updated.");
			    break;

			 // 9️⃣ SEARCH APPLICANTS
			case 9:
			    int filterJobId =
			            Integer.parseInt(input("Enter Job ID: "));
			    int minExp =
			            Integer.parseInt(input("Min Experience: "));

			    List<JobSeeker> seekers =
			            applicantSearchService
			                    .filterByExperience(filterJobId, minExp);

			    if (seekers.isEmpty()) {
			        System.out.println("No applicants found.");
			    } else {
			        for (JobSeeker js : seekers) {
			            System.out.println(
			                    js.getEmail() +
			                    " | Experience: " +
			                    js.getExperienceYears()
			            );
			        }
			    }
			    break;


			 // 🔟 UPDATE COMPANY PROFILE
			case 10:
			    employer.setCompanyName(input("Company Name: "));
			    employer.setIndustry(input("Industry: "));
			    employer.setLocation(input("Location: "));

			    ProfileService.updateEmployerProfile(employer);

			    System.out.println("✅ Company profile updated successfully.");
			    break;

			// 1️⃣1️⃣ LOGOUT
			case 11:
				return;

			default:
				System.out.println("❌ Invalid option.");
			}
		}
	}

	// =========================================================
	// COMMON METHODS
	// =========================================================
	private static void registerJobSeeker(AuthService authService) {

		String email;

		// 🔁 keep asking until valid Gmail
		while (true) {
			email = input("Email (Gmail only): ");
			if (ValidationUtil.isValidGmail(email)) {
				break;
			}
			System.out.println("❌ Invalid Gmail address. Please try again.");
		}

		String password = input("Password: ");
		String name = input("Name: ");
		int experience = Integer.parseInt(input("Experience Years: "));

		authService.registerJobSeeker(email, password, name, experience);
		System.out.println("✅ Job Seeker registered successfully.");
	}

	private static void registerEmployer(AuthService authService) {

		String email;

		// 🔁 keep asking until valid Gmail
		while (true) {
			email = input("Email (Gmail only): ");
			if (ValidationUtil.isValidGmail(email)) {
				break;
			}
			System.out.println("❌ Invalid Gmail address. Please try again.");
		}

		String password = input("Password: ");
		String companyName = input("Company Name: ");
		String industry = input("Industry: ");
		String location = input("Location: ");

		authService.registerEmployer(email, password, companyName, industry,
				location);

		System.out.println("✅ Employer registered successfully.");
	}

	private static User login(AuthService authService) {

		String email;

		// 🔁 keep asking until valid Gmail
		while (true) {
			email = input("Email (Gmail only): ");
			if (ValidationUtil.isValidGmail(email)) {
				break;
			}
			System.out.println("❌ Invalid Gmail address. Please try again.");
		}

		String password = input("Password: ");

		User user = authService.login(email, password);

		if (user == null) {
			System.out.println("❌ Invalid credentials.");
		} else {
			System.out.println("✅ Login successful.");
		}

		return user;
	}

	private static String input(String message) {
		System.out.print(message);
		return sc.nextLine();
	}

}
