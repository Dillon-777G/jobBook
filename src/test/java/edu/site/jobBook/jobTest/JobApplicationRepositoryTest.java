package edu.site.jobBook.jobTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobRepository;
import edu.site.jobBook.job.JobApplication.JobApplication;
import edu.site.jobBook.job.JobApplication.JobApplicationRepository;
import edu.site.jobBook.job.JobApplication.JobApplicationStatus;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DataJpaTest
public class JobApplicationRepositoryTest {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    public void setUp() {
        // Clean up the database before each test
        userRepository.deleteAll();

        // Add some test data
        AppUser user1 = AppUser.builder()
                .username("testuser1")
                .password("password1")
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser user2 = AppUser.builder()
                .username("testuser2")
                .password("password2")
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void testSaveJobApplication_Success() {
        // Assuming necessary test data setup
        Job job = jobRepository.findById(1L).orElse(null);
        AppUser user = userRepository.findByUsername("testuser1");

        // Create a new JobApplication entity
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setUser(user);
        application.setApplicationDate(new Date());
        application.setStatus(JobApplicationStatus.APPLIED);

        // Save the JobApplication
        JobApplication savedApplication = jobApplicationRepository.save(application);

        // Verify that the JobApplication was saved successfully
        assertNotNull(savedApplication);
        assertNotNull(savedApplication.getId());
        assertEquals(job, savedApplication.getJob());
        assertEquals(user, savedApplication.getUser());
        assertEquals(JobApplicationStatus.APPLIED, savedApplication.getStatus());
    }

    @Test
    public void testFindByUser() {
        AppUser user = userRepository.findByUsername("testuser1");

        Job job1 = jobRepository.findById(1L).orElse(null); // Assuming job with ID 1 exists
        Job job2 = jobRepository.findById(2L).orElse(null); // Assuming job with ID 1 exists

        JobApplication application1 = new JobApplication(null,job1, user, new Date(), JobApplicationStatus.APPLIED);
        JobApplication application2 = new JobApplication(null,job2, user, new Date(), JobApplicationStatus.APPLIED);
        
        jobApplicationRepository.save(application1);
        jobApplicationRepository.save(application2);

        // Test the findByUser method
        List<JobApplication> applications = jobApplicationRepository.findByUser(user);

        // Assertions
        assertEquals(2, applications.size());
        // Add more assertions if needed
    }

    @Test
    public void testExistsByUserAndJob() {
        // Create test data
        AppUser user = userRepository.findByUsername("testuser1");
        Job job1 = jobRepository.findById(1L).orElse(null);
        JobApplication application1 = new JobApplication(null,job1, user, new Date(), JobApplicationStatus.APPLIED);
        jobApplicationRepository.save(application1);

        // Test the existsByUserAndJob method
        boolean exists = jobApplicationRepository.existsByUserAndJob(user, job1);

        // Assertions
        assertTrue(exists);
    }

}
