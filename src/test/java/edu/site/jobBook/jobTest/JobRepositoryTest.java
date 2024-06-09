package edu.site.jobBook.jobTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobDescription;
import edu.site.jobBook.job.JobDetails;
import edu.site.jobBook.job.JobRepository;
import edu.site.jobBook.job.JobStatus;

@DataJpaTest
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    //Commenting because data will be vary based on the data in the database and it may fail the test
    // @Test
    // public void findAllJobs() {
    //     List<Job> jobs = jobRepository.findAll();
    //     int totalJobsInitiated = 13; // Assuming 13 jobs are initiated
    //     assertEquals(totalJobsInitiated, jobs.size());
    // }

    @Test
    public void findJobById() {
        Job job = jobRepository.findById(1L).orElse(null); // Assuming job with ID 1 exists
        assertNotNull(job);
        assertEquals("Software Engineer", job.getTitle());
    }

    //Commenting because data will be vary based on the data in the database and it may fail the test
    // @Test
    // public void findJobsByCompany() {
    //     Company company = companyRepository.findById(1L).orElse(null);
        
    //     List<Job> jobs = jobRepository.findByCompany(company);
    //     int totalJobsByCompany = 3; // Assuming company 1 has 3 jobs
    //     assertEquals(totalJobsByCompany, jobs.size());
    // }

    @Test
    public void deleteJob(){
        jobRepository.deleteById(1L);
        Job job = jobRepository.findById(1L).orElse(null);
        assertEquals(null, job);
    }   

    @Test
    public void createNewJob(){
        Company company = companyRepository.findById(1L).orElse(null);
        
        //create a new job
        Job job = new Job();
        job.setTitle("Tester");
        job.setCompany(company);
        
        //create a new job description
        JobDescription jobDescription = new JobDescription();
        jobDescription.setOverview("This is a new job description");
        jobDescription.setResponsibilities("This is a new job responsibilities");
        jobDescription.setQualification("This is a new job qualification");
        jobDescription.setSkills("This is a new job skills");
        jobDescription.setBenefits("This is a new job benefits");
        jobDescription.setJob(job);

        //create a new job details
        JobDetails jobDetails = new JobDetails();
        jobDetails.setStartDate("2021-09-01");
        jobDetails.setExpiryDate("2021-09-30");
        jobDetails.setPostedDate("2021-08-01");
        jobDetails.setLocation("Chicago");
        jobDetails.setStatus(JobStatus.ACTIVE);
        jobDetails.setJob(job);

        assertNotNull(job);
        assertNotNull(jobDescription);
        assertNotNull(jobDetails);

        assertEquals("Tester", job.getTitle());
        assertEquals(1, job.getCompany().getId());

        assertEquals("This is a new job description", jobDescription.getOverview());
        assertEquals("This is a new job responsibilities", jobDescription.getResponsibilities());
        assertEquals("This is a new job qualification", jobDescription.getQualification());
        assertEquals("This is a new job skills", jobDescription.getSkills());
        assertEquals("This is a new job benefits", jobDescription.getBenefits());

        assertEquals("2021-09-01", jobDetails.getStartDate());
        assertEquals("2021-09-30", jobDetails.getExpiryDate());
        assertEquals("2021-08-01", jobDetails.getPostedDate());
        assertEquals("Chicago", jobDetails.getLocation());
        assertEquals(JobStatus.ACTIVE, jobDetails.getStatus());
    }

    @Test
    public void updateJob(){
        Job job = jobRepository.findById(1L).orElse(null);
        job.setTitle("Senior Software Developer");
        jobRepository.save(job);

        Job updatedJob = jobRepository.findById(1L).orElse(null);
        assertNotNull(updatedJob);
        assertEquals("Senior Software Developer", updatedJob.getTitle());
    }

    @Test
    public void updateJobDescription(){
        Job job = jobRepository.findById(1L).orElse(null);
        JobDescription jobDescription = job.getJobDescription();
        jobDescription.setOverview("This is an updated job description");
        jobDescription.setResponsibilities("This is an updated job responsibilities");
        jobDescription.setQualification("This is an updated job qualification");
        jobDescription.setSkills("This is an updated job skills");
        jobDescription.setBenefits("This is an updated job benefits");

        jobRepository.save(job);

        Job updatedJob = jobRepository.findById(1L).orElse(null);
        assertNotNull(updatedJob);
        assertEquals("This is an updated job description", updatedJob.getJobDescription().getOverview());
        assertEquals("This is an updated job responsibilities", updatedJob.getJobDescription().getResponsibilities());
        assertEquals("This is an updated job qualification", updatedJob.getJobDescription().getQualification());
        assertEquals("This is an updated job skills", updatedJob.getJobDescription().getSkills());
        assertEquals("This is an updated job benefits", updatedJob.getJobDescription().getBenefits());
    }

    @Test
    public void updateJobDetails(){
        Job job = jobRepository.findById(1L).orElse(null);
        JobDetails jobDetails = job.getJobDetails();
        jobDetails.setStartDate("2021-09-01");
        jobDetails.setExpiryDate("2021-09-30");
        jobDetails.setPostedDate("2021-08-01");
        jobDetails.setLocation("Chicago - Edited");
        jobDetails.setStatus(JobStatus.ACTIVE);

        jobRepository.save(job);

        Job updatedJob = jobRepository.findById(1L).orElse(null);
        assertNotNull(updatedJob);
        assertEquals("2021-09-01", updatedJob.getJobDetails().getStartDate());
        assertEquals("2021-09-30", updatedJob.getJobDetails().getExpiryDate());
        assertEquals("2021-08-01", updatedJob.getJobDetails().getPostedDate());
        assertEquals("Chicago - Edited", updatedJob.getJobDetails().getLocation());
        assertEquals(JobStatus.ACTIVE, updatedJob.getJobDetails().getStatus());
    }
}