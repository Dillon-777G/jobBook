package edu.site.jobBook.job.JobView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Sort;

@Service
public class JobViewService {

    private final JobViewRepository jobViewRepository;
    private static final Logger logger = LoggerFactory.getLogger(JobViewService.class);

    public JobViewService(JobViewRepository jobViewRepository) {
        this.jobViewRepository = jobViewRepository;
    }

    // increment the view count of a job
    public void incrementJobViewCount(String jobId,String jobTitle) {
        JobView jobView = jobViewRepository.findById(jobId).orElse(new JobView(jobId,jobTitle, 0));
        jobView.setViewCount(jobView.getViewCount() + 1);
        jobViewRepository.save(jobView);
        logger.info("Incremented view count for job: {}", jobId);
    }

    // get the view count of a job
    public Long getJobViewCount(String jobId, String jobTitle) {
        JobView jobView = jobViewRepository.findById(jobId).orElse(new JobView(jobId, jobTitle, 0));
        logger.info("Retrieved view count for job: {}", jobId);
        return jobView.getViewCount();
    }

    // get all job views
    public List<JobView> getAllJobViews() {
        logger.info("Retrieved all job views");
        return (List<JobView>) jobViewRepository.findAll();
    }

    // get all job views ordered by view count
    public Page<JobView> getAllJobViewsOrderByDescViewCOunt(Pageable pageable) {
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "viewCount");
            Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
          
            logger.info("Retrieved all job views ordered by view count");
            return jobViewRepository.findAll(sortedPageable);
        } catch (Exception e) {
            logger.error("Error retrieving all jobs", e);
            logger.error("Error retrieving all jobs", e);
            throw new RuntimeException("Error retrieving all jobs", e);
        }
    }
}
