package edu.site.jobBook.job;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class JobService {

    //trouble shooting
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    // private  final JobRepository jobRepository;

    // @Autowired
    // public JobService(JobRepository jobRepository) {
    //     this.jobRepository = jobRepository;
    // }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public Job saveJob(Job job, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
        job.setCompany(company);

        return jobRepository.save(job);
    }

    // public Job saveJob(Job job) {
    //     return jobRepository.save(job);
    // }

    public void deleteJobById(Long jobId) {
        boolean exists = jobRepository.existsById(jobId);
        if (!exists) {
            throw new IllegalStateException("Job with id " + jobId + " does not exist");
        }
        jobRepository.deleteById(jobId);
    }

    public Job updateJob(Job job) {
     
        return jobRepository.save(job);
    }

    public List<Job> getJobsByCompany(Company company) {
        return jobRepository.findByCompany(company);
    }

    
}
