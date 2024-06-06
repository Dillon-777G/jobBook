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

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import edu.site.jobBook.company.Company;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobApplicationService;
import edu.site.jobBook.job.JobController;
import edu.site.jobBook.job.JobService;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserActivityService;
import edu.site.jobBook.user.UserRepository;

public class JobControllerTest {
    
    private MockMvc mockMvc;

    @Mock
    private JobService jobService;

    @Mock
    private JobApplicationService jobApplicationService;

    @Mock
    private UserRepository userRepository;

    // Mock UserActivityService
    @Mock
    private UserActivityService userActivityService;

    // Mock Authentication and SecurityContext
    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Principal principal;

    @InjectMocks
    private JobController jobController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        
        AppUser appUser = new AppUser();
        appUser.setId(1L);  // Ensure ID is set
        appUser.setUsername("user");
        when(authentication.getPrincipal()).thenReturn(appUser);

        
        setField(jobController, "userActivityService", userActivityService);

      
        when(principal.getName()).thenReturn("user");

        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
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


        when(jobService.getJobById(jobId)).thenReturn(job);
        when(jobApplicationService.hasUserAppliedForJob(any(AppUser.class), any(Job.class))).thenReturn(false);

        mockMvc.perform(get("/jobs/{id}", jobId))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("job"))
            .andExpect(model().attribute("job", job))
            .andExpect(model().attributeExists("jobAlreadyApplied"));
    }

    @Test
    public void testMyJobs() throws Exception {
        AppUser user = new AppUser();
        user.setId(1L);  // Ensure ID is set
        user.setUsername("user");
        when(authentication.getPrincipal()).thenReturn(user);

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(jobApplicationService.getApplicationsByUser(user)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/jobs/myjobs").principal(principal))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("totalApplications"))
            .andExpect(model().attribute("totalApplications", 0))
            .andExpect(model().attributeExists("jobApplications"));
    }
}