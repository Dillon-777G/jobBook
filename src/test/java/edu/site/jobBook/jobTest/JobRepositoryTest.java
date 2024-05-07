package edu.site.jobBook.jobTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobRepository;

@SpringBootTest
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void resetDatabase() {
         jobRepository.deleteAll();
         companyRepository.deleteAll(); 
    }

    @Test
    @Rollback
    public void searchAllJobsUnderCompany() {

        Company company = new Company();
        company.setName("Test Company");
        company.setDescription("Test Company Description");
        // company.setId(11);

        Company savedCompany = companyRepository.save(company);        

        // Create a job
        Job newJob = new Job();
        newJob.setTitle("Job 1");
        newJob.setDescription("Job 1 Description");
        newJob.setCompany(company);

        Job newJob2 = new Job();
        newJob2.setTitle("Job 2");
        newJob2.setDescription("Job 2 Description");
        newJob2.setCompany(company);

        // Save the job
        Job savedJob = jobRepository.save(newJob);
        Job savedJob2 = jobRepository.save(newJob2);

        List<Job> jobs = jobRepository.findByCompany(company);

        assertEquals(jobs.size(),2);
    }

}
