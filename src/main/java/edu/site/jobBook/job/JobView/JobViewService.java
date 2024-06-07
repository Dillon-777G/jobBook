package edu.site.jobBook.job.JobView;

import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import edu.site.jobBook.job.JobService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;


@Service
public class JobViewService {

    private final JobViewRepository jobViewRepository;
    private static final Logger logger = LoggerFactory.getLogger(JobViewService.class);

    public JobViewService(JobViewRepository jobViewRepository) {
        this.jobViewRepository = jobViewRepository;
    }

    public void incrementJobViewCount(String jobId,String jobTitle) {
        JobView jobView = jobViewRepository.findById(jobId).orElse(new JobView(jobId,jobTitle, 0));
        jobView.setViewCount(jobView.getViewCount() + 1);
        jobViewRepository.save(jobView);
    }

    public Long getJobViewCount(String jobId, String jobTitle) {
        JobView jobView = jobViewRepository.findById(jobId).orElse(new JobView(jobId, jobTitle, 0));
        return jobView.getViewCount();
    }

    //  public Map<String, Long> getAllJobViews() {
    //     Iterable<JobView> jobViews = jobViewRepository.findAll();
    //     Map<String, Long> viewCounts = new HashMap<>();
    //     jobViews.forEach(jobView -> viewCounts.put(jobView.getJobId(), jobView.getViewCount()));
    //     return viewCounts;
    // }

    public List<JobView> getAllJobViews() {
        return (List<JobView>) jobViewRepository.findAll();
    }

    public Page<JobView> getAllJobViewsOrderByDescViewCOunt(Pageable pageable) {
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "viewCount");
            Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
          
            return jobViewRepository.findAll(sortedPageable);
        } catch (Exception e) {
            logger.error("Error retrieving all jobs", e);
            throw new RuntimeException("Error retrieving all jobs", e);
        }
    }
}
