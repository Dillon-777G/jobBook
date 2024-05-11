package edu.site.jobBook.jobTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobController;
import edu.site.jobBook.job.JobService;

public class JobControllerTest {
    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    public void testGetJobById() throws Exception {
        // Mock data
        Company company = new Company(1L, "ACME Corp", "Tech Company");
        Job mockJob = new Job(1L, "Software Engineer", company, null, null);
        when(jobService.getJobById(1L)).thenReturn(mockJob);

        // Perform GET request
        mockMvc.perform(get("/api/jobs/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Software Engineer"));
    }

    @Test
    public void testGetJobByIdNotFound() throws Exception {
        // Mock data
        when(jobService.getJobById(1L)).thenReturn(null);

        // Perform GET request for non-existent job
        mockMvc.perform(get("/api/jobs/1"))
            .andExpect(status().isNotFound());
    }

        @Test
    public void testCreateJob() throws Exception {
        // Mock data
        Company company = new Company(1L, "ACME Corp", "Tech Company");
        Job mockJob = new Job(1L, "Software Engineer", company, null, null);
        when(jobService.saveJob(any(Job.class))).thenReturn(mockJob);

        // Perform POST request
        mockMvc.perform(post("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Software Engineer\", \"company\": {\"name\": \"ACME Corp\", \"description\": \"Tech Company\"}}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Software Engineer"));
    }
}
