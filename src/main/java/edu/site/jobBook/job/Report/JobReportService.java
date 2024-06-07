package edu.site.jobBook.job.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.site.jobBook.job.JobApplicationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobReportService {
    private final JobApplicationRepository jobApplicationRepository;
    

    @Autowired
    public JobReportService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<Object[]> getApplicationsCountByJob() {
        return jobApplicationRepository.countJobApplicationsByJobId();
    }
}
