package edu.site.jobBook.job.JobView;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobViewRepository extends CrudRepository<JobView,String>, PagingAndSortingRepository<JobView, String> {
    
}
