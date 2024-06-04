package edu.site.jobBook.jobTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import edu.site.jobBook.company.CompanyRepository;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobApplicationStatus;
import edu.site.jobBook.job.JobDescription;
import edu.site.jobBook.job.JobDetails;
import edu.site.jobBook.job.JobRepository;
import edu.site.jobBook.job.JobService;
import edu.site.jobBook.job.JobStatus;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CompanyRepository companyRepository;

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

        // Mock the findById method of companyRepository
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        

        // Mock the save method of jobRepository
        when(jobRepository.save(mockJob)).thenReturn(mockJob);

        // Call the service method
        Job savedJob = jobService.saveJob(mockJob, 1L);
        // Job savedJob = jobService.saveJob(mockJob);

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

    @Test
    public void testUpdateJob_Success() {
        // Mock data
        Company existingCompany = new Company(1L, "Existing Company", "Tech Company", null, null);
        Company updatedCompany = new Company(2L, "Updated Company", "Tech Company", null, null);

        Job existingJob = new Job(1L, "Existing Job", existingCompany, null, null);
        Job updatedJob = new Job(1L, "Updated Job", updatedCompany, null, null);

        JobDescription updatedJobDescription = new JobDescription();
        updatedJobDescription.setOverview("Updated Overview");
        updatedJobDescription.setResponsibilities("Updated Responsibilities");
        updatedJobDescription.setSkills("Updated Skills");
        updatedJobDescription.setQualification("Updated Qualification");
        updatedJobDescription.setBenefits("Updated Benefits");

        JobDetails updatedJobDetails = new JobDetails();
        updatedJobDetails.setStartDate("2024-06-10");
        updatedJobDetails.setPostedDate("2024-06-09");
        updatedJobDetails.setExpiryDate("2024-06-30");
        updatedJobDetails.setLocation("Updated Location");
        updatedJobDetails.setStatus(JobStatus.ACTIVE);

        updatedJob.setJobDescription(updatedJobDescription);
        updatedJob.setJobDetails(updatedJobDetails);

        // Mock the findById method of jobRepository
        when(jobRepository.findById(1L)).thenReturn(Optional.of(existingJob));

        // Mock the findById method of companyRepository
        when(companyRepository.findById(2L)).thenReturn(Optional.of(updatedCompany));

        // Call the service method
        jobService.updateJob(updatedJob, 2L);

        Job updated = jobService.getJobById(1L);

        // Assertions
        assertEquals("Updated Job", updated.getTitle());
        assertEquals(updatedCompany, updated.getCompany());
        assertEquals("Updated Overview", updated.getJobDescription().getOverview());
        assertEquals("Updated Responsibilities", updated.getJobDescription().getResponsibilities());
        assertEquals("Updated Skills", updated.getJobDescription().getSkills());
        assertEquals("Updated Qualification", updated.getJobDescription().getQualification());
        assertEquals("Updated Benefits", updated.getJobDescription().getBenefits());
        assertEquals("2024-06-10", updated.getJobDetails().getStartDate());
        assertEquals("2024-06-09", updated.getJobDetails().getPostedDate());
        assertEquals("2024-06-30", updated.getJobDetails().getExpiryDate());
        assertEquals("Updated Location", updated.getJobDetails().getLocation());
        assertEquals(JobStatus.ACTIVE, updated.getJobDetails().getStatus());
    }

    @Test
    public void testGetAllCompanies() {
        // Mock data
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1L, "ACME Corp", "Tech Company", null, null));
        companies.add(new Company(2L, "XYZ Inc", "Software Company", null, null));
        
        // Mock the findAll method of companyRepository
        when(companyRepository.findAll()).thenReturn(companies);

        // Call the service method
        List<Company> result = jobService.getAllCompanies();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("ACME Corp", result.get(0).getName());
        assertEquals("XYZ Inc", result.get(1).getName());
    }

    @Test
    public void testDeleteJobById_JobExists() {
        // Mock the existsById method of jobRepository
        when(jobRepository.existsById(1L)).thenReturn(true);
        doNothing().when(jobRepository).deleteById(1L);

        // Call the service method
        jobService.deleteJobById(1L);

        // Verify that the job was deleted
        verify(jobRepository, times(1)).existsById(1L);
        verify(jobRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetJobDescription_JobExists() {
        // Mock the findById method of jobRepository
        Job mockJob = new Job();
        mockJob.setId(1L);
        mockJob.setTitle("Software Engineer");

        JobDescription newJobDescription = new JobDescription();
        newJobDescription.setOverview("Overview");
        newJobDescription.setResponsibilities("Responsibilities");
        newJobDescription.setSkills("Skills");
        newJobDescription.setQualification("Qualification");
        newJobDescription.setBenefits("Benefits");

        mockJob.setJobDescription(newJobDescription);
        when(jobRepository.findById(1L)).thenReturn(Optional.of(mockJob));

        // Call the service method
        JobDescription jobDescription = jobService.getJobDescription(1L);

        // Verify that the correct job description is returned
        assertNotNull(jobDescription);
        assertEquals("Overview", jobDescription.getOverview());
        assertEquals("Responsibilities", jobDescription.getResponsibilities());
        assertEquals("Skills", jobDescription.getSkills());
        assertEquals("Qualification", jobDescription.getQualification());
        assertEquals("Benefits", jobDescription.getBenefits());
    }
    
}