package edu.site.jobBook.jobTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobRepository;
import edu.site.jobBook.job.JobService;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetJobById() {
        // Mock data
        Company company = new Company(1,"ACME Corp", "Tech Company", null, null);
        Job mockJob = new Job(1L, "Software Engineer", company,null,null);
        
        when(jobRepository.findById(1L)).thenReturn(Optional.of(mockJob));

        // Call the service method
        Job result = jobService.getJobById(1L);

        // Assertions
        assertEquals("Software Engineer", result.getTitle());
    }

    @Test
    public void testSaveJob() {
        // Mock data
        Company company = new Company(1,"ACME Corp", "Tech Company", null, null);
        Job mockJob = new Job(1L, "Software Engineer", company,null,null);

        // Mock the save method of jobRepository
        when(jobRepository.save(mockJob)).thenReturn(mockJob);

        // Call the service method
        Job savedJob = jobService.saveJob(mockJob);

        // Verify that the job was saved correctly
        assertNotNull(savedJob);
        assertEquals("Software Engineer", savedJob.getTitle());
    }

    @Test
    public void testGetJobsByCompany() {
        // Mock data
        Company company = new Company(1,"ACME Corp", "Tech Company", null, null);
        List<Job> mockJobs = new ArrayList<>();
        mockJobs.add(new Job(1L, "Software Engineer", company,null,null));
        mockJobs.add(new Job(2L, "Data Analyst", company,null,null));
        when(jobRepository.findByCompany(company)).thenReturn(mockJobs);

        // Call the service method
        List<Job> result = jobService.getJobsByCompany(company);

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Software Engineer", result.get(0).getTitle());
        assertEquals("Data Analyst", result.get(1).getTitle());
    }
}


