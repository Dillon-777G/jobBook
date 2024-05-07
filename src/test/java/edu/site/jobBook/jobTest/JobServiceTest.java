package edu.site.jobBook.jobTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobRepository;
import edu.site.jobBook.job.JobService;

public class JobServiceTest {

    private JobService jobService;
    private JobRepository jobRepository;


    @BeforeEach
    public void setUp() {
        jobService = new JobService(jobRepository);
     }

}


