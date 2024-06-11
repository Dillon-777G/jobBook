package edu.site.jobBook.jobTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.FileUpload.FileUploadService;
import edu.site.jobBook.job.JobApplication.JobApplication;
import edu.site.jobBook.job.JobApplication.JobApplicationRepository;
import edu.site.jobBook.job.JobApplication.JobApplicationService;

import edu.site.jobBook.user.AppUser;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getApplicationsByUserTest() {
        AppUser user = new AppUser();
        user.setUsername("testUser");

        List<JobApplication> expectedApplications = new ArrayList<>();
        when(jobApplicationRepository.findByUser(user)).thenReturn(expectedApplications);

        List<JobApplication> result = jobApplicationService.getApplicationsByUser(user);

        assertEquals(expectedApplications, result);
        verify(jobApplicationRepository, times(1)).findByUser(user);
    }

    @Test
    void hasUserAppliedForJobTest() {
        AppUser user = new AppUser();
        Job job = new Job();

        when(jobApplicationRepository.existsByUserAndJob(user, job)).thenReturn(true);
        boolean result = jobApplicationService.hasUserAppliedForJob(user, job);

        assertTrue(result);
        verify(jobApplicationRepository, times(1)).existsByUserAndJob(user, job);
    }

    @Test
    void applyForJobTest() {
        AppUser user = new AppUser();
        Job job = new Job();
        MultipartFile resume = mock(MultipartFile.class);

        when(jobApplicationRepository.existsByUserAndJob(user, job)).thenReturn(false);
        when(jobApplicationRepository.save(any(JobApplication.class))).thenAnswer(invocation -> {
            JobApplication application = invocation.getArgument(0);
            application.setId(1L);  // Mock setting the ID
            return application;
        });

        boolean result = jobApplicationService.applyForJob(user, job, resume);

        assertTrue(result);
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
        verify(fileUploadService, times(1)).store(eq(resume), eq(1L));  // Verify with the mocked ID
    }
    
    @Test
    void applyForJobAlreadyAppliedTest() {
        AppUser user = new AppUser();
        Job job = new Job();
        MultipartFile resume = mock(MultipartFile.class);

        when(jobApplicationRepository.existsByUserAndJob(user, job)).thenReturn(true);

        boolean result = jobApplicationService.applyForJob(user, job, resume);

        assertFalse(result);
        verify(jobApplicationRepository, times(0)).save(any(JobApplication.class));
        verify(fileUploadService, times(0)).store(any(MultipartFile.class), anyLong());
    }

}
