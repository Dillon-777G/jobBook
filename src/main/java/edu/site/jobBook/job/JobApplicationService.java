package edu.site.jobBook.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.site.jobBook.user.AppUser;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    @Autowired
    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<JobApplication> getApplicationsByUser(AppUser user) {
        return jobApplicationRepository.findByUser(user);
    }

    public boolean hasUserAppliedForJob(AppUser user, Job job) {
        return jobApplicationRepository.existsByUserAndJob(user, job);
    }

    public boolean applyForJob(AppUser user, Job job) {
        if (hasUserAppliedForJob(user, job)) {
            return false;
        }

        try{
        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(JobApplicationStatus.APPLIED);
        application.setApplicationDate(new Date());

        jobApplicationRepository.save(application);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

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