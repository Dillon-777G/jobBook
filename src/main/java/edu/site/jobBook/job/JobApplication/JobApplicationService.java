package edu.site.jobBook.job.JobApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.FileUpload.FileUploadService;
import edu.site.jobBook.user.AppUser;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    private final JobApplicationRepository jobApplicationRepository;
    private final FileUploadService fileUploadService;

    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository, FileUploadService fileUploadService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.fileUploadService = fileUploadService;
    }

    // Get all job applications for a user
    public List<JobApplication> getApplicationsByUser(AppUser user) {
        logger.info("Getting job applications for user: " + user.getUsername());
        return jobApplicationRepository.findByUser(user);
    }

    // Check if a user has applied for a job
    public boolean hasUserAppliedForJob(AppUser user, Job job) {
        return jobApplicationRepository.existsByUserAndJob(user, job);
    }

    // Apply for a job
    public boolean applyForJob(AppUser user, Job job, MultipartFile resume) {
        if (hasUserAppliedForJob(user, job)) {
            return false;
        }

        try {
            JobApplication application = new JobApplication();
            application.setUser(user);
            application.setJob(job);
            application.setStatus(JobApplicationStatus.APPLIED);
            application.setApplicationDate(new Date());

            JobApplication jobApplication = jobApplicationRepository.save(application);

            fileUploadService.store(resume, jobApplication.getId());
            logger.info("Job application submitted successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error submitting job application: " + e.getMessage());
            return false;
        }
    }

    // Get job applications filtered by status
    public List<JobApplication> getFilteredApplications(AppUser username, String filter) {
        List<JobApplication> allApplications = jobApplicationRepository.findByUser(username);

        // Filter job applications based on the specified filter
        switch (filter) {
            case "APPLIED":
                return allApplications.stream()
                        .filter(app -> app.getStatus() == JobApplicationStatus.APPLIED)
                        .collect(Collectors.toList());
            case "UNDER_REVIEW":
                return allApplications.stream()
                        .filter(app -> app.getStatus() == JobApplicationStatus.UNDER_REVIEW)
                        .collect(Collectors.toList());
            case "REJECTED":
                return allApplications.stream()
                        .filter(app -> app.getStatus() == JobApplicationStatus.REJECTED)
                        .collect(Collectors.toList());
            default:
                // Return all applications if filter value is invalid or not provided
                return allApplications;
        }
    }

}
