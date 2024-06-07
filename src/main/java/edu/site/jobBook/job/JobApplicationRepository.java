package edu.site.jobBook.job;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.site.jobBook.user.AppUser;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUser(AppUser user);
    boolean existsByUserAndJob(AppUser user, Job job);
    
    @Query("SELECT ja.job.id, COUNT(*) FROM JobApplication ja GROUP BY ja.job.id")
    List<Object[]> countJobApplicationsByJobId();
    
    
}
