package edu.site.jobBook.job;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.apache.commons.lang3.StringUtils;

@Service
public class JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobService.class);
    
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public Page<Job> getAllJobs(Pageable pageable) {
        try {
            return jobRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error retrieving all jobs", e);
            throw new RuntimeException("Error retrieving all jobs", e);
        }
    }
    
    public List<Job> getAllJobs() {
        try {
            return jobRepository.findAll();
        } catch (Exception e) {
            logger.error("Error retrieving all jobs", e);
            throw new RuntimeException("Error retrieving all jobs", e);
        }
    }

    public Job getJobById(Long jobId) {
        try {
            return jobRepository.findById(jobId).orElseThrow(() -> new EntityNotFoundException("Job not found"));
        } catch (EntityNotFoundException e) {
            logger.error("Job not found with ID: {}", jobId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving job with ID: {}", jobId, e);
            throw new RuntimeException("Error retrieving job with ID: " + jobId, e);
        }
    }

    @Transactional
    public Job saveJob(Job job, Long companyId) {
        try {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            job.setCompany(company);

            if (job.getJobDescription() != null) {
                job.getJobDescription().setJob(job);
            }
            if (job.getJobDetails() != null) {
                job.getJobDetails().setJob(job);
            }

            return jobRepository.save(job);
        } catch (EntityNotFoundException e) {
            logger.error("Company not found with ID: {}", companyId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error saving job", e);
            throw new RuntimeException("Error saving job", e);
        }
    }

    @Transactional
    public Job updateJob(Job updatedJob, Long companyId) {
        try {
            Job existingJob = jobRepository.findById(updatedJob.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Job not found"));

            Company updatedCompany = companyRepository.findById(companyId)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));

            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setCompany(updatedCompany);

            JobDescription updatedJobDescription = updatedJob.getJobDescription();
            JobDescription existingJobDescription = existingJob.getJobDescription();
            if (existingJobDescription == null) {
                existingJobDescription = new JobDescription();
                existingJobDescription.setJob(existingJob);
                existingJob.setJobDescription(existingJobDescription);
            }
            existingJobDescription.setOverview(updatedJobDescription.getOverview());
            existingJobDescription.setResponsibilities(updatedJobDescription.getResponsibilities());
            existingJobDescription.setSkills(updatedJobDescription.getSkills());
            existingJobDescription.setQualification(updatedJobDescription.getQualification());
            existingJobDescription.setBenefits(updatedJobDescription.getBenefits());

            JobDetails updatedJobDetails = updatedJob.getJobDetails();
            JobDetails existingJobDetails = existingJob.getJobDetails();
            if (existingJobDetails == null) {
                existingJobDetails = new JobDetails();
                existingJobDetails.setJob(existingJob);
                existingJob.setJobDetails(existingJobDetails);
            }
            existingJobDetails.setStartDate(updatedJobDetails.getStartDate());
            existingJobDetails.setPostedDate(updatedJobDetails.getPostedDate());
            existingJobDetails.setExpiryDate(updatedJobDetails.getExpiryDate());
            existingJobDetails.setLocation(updatedJobDetails.getLocation());
            existingJobDetails.setStatus(updatedJobDetails.getStatus());

            return jobRepository.save(existingJob);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error updating job", e);
            throw new RuntimeException("Error updating job", e);
        }
    }

    public List<Company> getAllCompanies() {
        try {
            List<Company> companyList = new ArrayList<>();
            companyRepository.findAll().forEach(companyList::add);
            return companyList;
        } catch (Exception e) {
            logger.error("Error retrieving all companies", e);
            throw new RuntimeException("Error retrieving all companies", e);
        }
    }

    public void deleteJobById(Long jobId) {
        try {
            boolean exists = jobRepository.existsById(jobId);
            if (!exists) {
                throw new IllegalStateException("Job with id " + jobId + " does not exist");
            }
            jobRepository.deleteById(jobId);
        } catch (Exception e) {
            logger.error("Error deleting job with ID: {}", jobId, e);
            throw new RuntimeException("Error deleting job with ID: " + jobId, e);
        }
    }

    public List<Job> getJobsByCompany(Company company) {
        try {
            return jobRepository.findByCompany(company);
        } catch (Exception e) {
            logger.error("Error retrieving jobs by company", e);
            throw new RuntimeException("Error retrieving jobs by company", e);
        }
    }

    public JobDescription getJobDescription(Long jobId) {
        try {
            Job job = jobRepository.findById(jobId).orElseThrow(() -> new EntityNotFoundException("Job not found"));
            return job.getJobDescription();
        } catch (EntityNotFoundException e) {
            logger.error("Job not found with ID: {}", jobId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error retrieving job description for job with ID: {}", jobId, e);
            throw new RuntimeException("Error retrieving job description for job with ID: " + jobId, e);
        }
    }

    public Long getJobCountByStatus(JobStatus status) {
        return jobRepository.countByJobDetailsStatus(status);
    }

    public Page<Job> filterJobs(String title, String location, JobStatus status, String companyName, Pageable pageable) {
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(location) && status != null && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByTitleAndJobDetailsLocationAndJobDetailsStatusAndCompany_Name(title, location, status, companyName, pageable);
        } else if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(location) && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByTitleAndJobDetailsLocationAndCompany_Name(title, location, companyName, pageable);
        } else if (StringUtils.isNotBlank(title) && status != null && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByTitleAndJobDetailsStatusAndCompany_Name(title, status, companyName, pageable);
        } else if (StringUtils.isNotBlank(location) && status != null && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByJobDetailsLocationAndJobDetailsStatusAndCompany_Name(location, status, companyName, pageable);
        } else if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByTitleAndCompany_Name(title, companyName, pageable);
        } else if (StringUtils.isNotBlank(location) && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByJobDetailsLocationAndCompany_Name(location, companyName, pageable);
        } else if (status != null && StringUtils.isNotBlank(companyName)) {
            return jobRepository.findByJobDetailsStatusAndCompany_Name(status, companyName, pageable);
        } else if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(location) && status != null) {
            return jobRepository.findByTitleAndJobDetailsLocationAndJobDetailsStatus(title, location, status, pageable);
        } else if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(location)) {
            return jobRepository.findByTitleAndJobDetailsLocation(title, location, pageable);
        } else if (StringUtils.isNotBlank(title) && status != null) {
            return jobRepository.findByTitleAndJobDetailsStatus(title, status, pageable);
        } else if (StringUtils.isNotBlank(location) && status != null) {
            return jobRepository.findByJobDetailsLocationAndJobDetailsStatus(location, status, pageable);
        } else if (StringUtils.isNotBlank(title)) {
            return jobRepository.findByTitle(title, pageable);
        } else if (StringUtils.isNotBlank(location)) {
            return jobRepository.findByJobDetailsLocation(location, pageable);
        } else if (status != null) {
            return jobRepository.findByJobDetailsStatus(status, pageable);
        } else {
            return jobRepository.findAll(pageable);
        }
    }
    
    
    
}