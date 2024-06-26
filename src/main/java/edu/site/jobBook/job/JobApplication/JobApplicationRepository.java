package edu.site.jobBook.job.JobApplication;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.user.AppUser;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUser(AppUser user);
    boolean existsByUserAndJob(AppUser user, Job job);
}
