package edu.site.jobBook.jobTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import edu.site.jobBook.job.JobApplicationService;
import edu.site.jobBook.job.JobController;
import edu.site.jobBook.job.JobService;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserRepository;

public class JobControllerTest {
    
    private MockMvc mockMvc;

    @Mock
    private JobService jobService;

    @Mock
    private JobApplicationService jobApplicationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JobController jobController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    public void testGetAllJobs() throws Exception {
        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Job 1");

        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Job 2");

        List<Job> jobs = Arrays.asList(job1, job2);

        when(jobService.getAllJobs()).thenReturn(jobs);

        mockMvc.perform(get("/jobs"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("jobs"))
            .andExpect(model().attribute("jobs", jobs))
            .andExpect(model().attributeExists("selectedJob"));
    }

    @Test
    public void testGetAllJobsEmpty() throws Exception {
        when(jobService.getAllJobs()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/jobs"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("jobs"))
            .andExpect(model().attribute("jobs", Collections.emptyList()))
            .andExpect(model().attributeDoesNotExist("selectedJob"));
    }

    @Test
    public void testGetJobDetails() throws Exception {
        Long jobId = 1L;
        Job job = new Job();
        job.setId(jobId);
        job.setTitle("Job 1");

        Principal principal = () -> "user";

        when(jobService.getJobById(jobId)).thenReturn(job);
        when(jobApplicationService.hasUserAppliedForJob(any(AppUser.class), any(Job.class))).thenReturn(false);

        mockMvc.perform(get("/jobs/{id}", jobId).principal(principal))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("job"))
            .andExpect(model().attribute("job", job))
            .andExpect(model().attributeExists("jobAlreadyApplied"));
    }

    @Test
    public void testMyJobs() throws Exception {
        Principal principal = () -> "user";

        AppUser user = new AppUser();
        user.setUsername("user");

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(jobApplicationService.getApplicationsByUser(user)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/jobs/myjobs").principal(principal))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("totalApplications"))
            .andExpect(model().attribute("totalApplications", 0))
            .andExpect(model().attributeExists("jobApplications"));
    }
}