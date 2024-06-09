package edu.site.jobBook.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.site.jobBook.company.HiringStatus.CompanyHiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatus;
import edu.site.jobBook.company.HiringStatus.HiringStatusRepository;
import edu.site.jobBook.job.Job;
import edu.site.jobBook.job.JobRepository;
import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository; // <== Repository for Company entity

    @Autowired
    private PostRepository postRepository; // <== Repository for Post entity

    @Autowired
    private JobRepository jobRepository; // <== Repository for Job entity

    @Autowired
    private HiringStatusRepository hiringStatusRepository; // <== Repository for HiringStatus entity

    public CompanyHiringStatus getHiringStatus(long companyId) { // <== Method to get hiring status
        return hiringStatusRepository.findById(companyId).orElse(new CompanyHiringStatus(companyId, HiringStatus.UNKNOWN));
    }

    public List<CompanyHiringStatus> getCompaniesHiring() { // <== Method to get companies that are hiring
        return hiringStatusRepository.findByHiringStatus(HiringStatus.HIRING);
    }

    public Optional<CompanyHiringStatus> findHiringStatusByCompanyId(Long companyId) { // <== Method to find hiring status by company ID
        return hiringStatusRepository.findById(companyId);
    }

    public Company addCompany(Company company) { // <== Method to add a new company
        logger.info("Adding company: {}", company.getName());
        return companyRepository.save(company);
    }

    public Optional<Company> findCompanyByName(String company) { // <== Method to find company by name
        logger.info("Finding company by name: {}", company);
        return companyRepository.findBynameIgnoreCase(company);
    }

    public Optional<Company> findCompanyById(Long id) { // <== Method to find company by ID
        logger.info("Finding company by id: {}", id);
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            logger.info("Company found: {}", company.get());
        } else {
            logger.warn("Company not found with id: {}", id);
        }
        return company;
    }

    public void deleteCompany(Long id) { // <== Method to delete a company
        logger.info("Deleting company with id: {}", id);
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            // Delete all jobs related to the company
            List<Job> jobs = company.get().getJobs();
            for (Job job : jobs) {
                jobRepository.delete(job);
            }
            // Delete all posts related to the company
            List<Post> posts = company.get().getPosts();
            for (Post post : posts) {
                postRepository.delete(post);
            }
            // Now delete the company
            companyRepository.deleteById(id);
            logger.info("Company deleted successfully with id: {}", id);
        } else {
            logger.warn("Company not found with id: {}", id);
        }
    }

    public List<Company> findCompaniesByPartialName(String partialName) { // <== Method to find companies by partial name
        logger.info("Finding companies by partial name: {}", partialName);
        return companyRepository.findByNameContainingIgnoreCase(partialName);
    }

    public Company updateCompany(long id, Company updatedCompany) { // <== Method to update a company
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        // Update the company's details
        company.setName(updatedCompany.getName());
        company.setDescription(updatedCompany.getDescription());

        // Update posts
        if (updatedCompany.getPosts() != null) {
            updateCompanyPosts(company, updatedCompany.getPosts());
        }

        // Update jobs
        if (updatedCompany.getJobs() != null) {
            updateCompanyJobs(company, updatedCompany.getJobs());
        }

        return companyRepository.save(company);
    }

    private void updateCompanyPosts(Company company, List<Post> updatedPosts) { // <== Method to update company's posts
        List<Post> existingPosts = company.getPosts();

        // Remove posts that are no longer in the updated list
        existingPosts.removeIf(post -> !updatedPosts.contains(post));

        // Add or update posts
        for (Post updatedPost : updatedPosts) {
            if (!existingPosts.contains(updatedPost)) {
                existingPosts.add(updatedPost);
                updatedPost.setCompany(company);
            } else {
                // Update existing post details
                Post existingPost = existingPosts.get(existingPosts.indexOf(updatedPost));
                existingPost.setCaption(updatedPost.getCaption());
                existingPost.setImage(updatedPost.getImage());
                existingPost.setLikes(updatedPost.getLikes());
                existingPost.setShares(updatedPost.getShares());
                existingPost.setUser(updatedPost.getUser());
                existingPost.setComments(updatedPost.getComments());
            }
        }
    }

    private void updateCompanyJobs(Company company, List<Job> updatedJobs) { // <== Method to update company's jobs
        List<Job> existingJobs = company.getJobs();

        // Remove jobs that are no longer in the updated list
        existingJobs.removeIf(job -> !updatedJobs.contains(job));

        // Add or update jobs
        for (Job updatedJob : updatedJobs) {
            if (!existingJobs.contains(updatedJob)) {
                existingJobs.add(updatedJob);
                updatedJob.setCompany(company);
            } else {
                // Update existing job details
                Job existingJob = existingJobs.get(existingJobs.indexOf(updatedJob));
                existingJob.setTitle(updatedJob.getTitle());
                existingJob.setJobDescription(updatedJob.getJobDescription());
                existingJob.setJobDetails(updatedJob.getJobDetails());
            }
        }
    }

    public Page<Company> findAllCompaniesPage(Pageable pageable) { // <== Method to find all companies with pagination
        try {
            return companyRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error retrieving all jobs", e);
            throw new RuntimeException("Error retrieving all jobs", e);
        }
    }

    public List<Company> findAllCompanies() { // <== Method to find all companies
        logger.info("Finding all companies");
        return (List<Company>) companyRepository.findAll();
    }

    public List<Job> findJobsByCompanyId(Long companyId) { // <== Method to find jobs by company ID
        return jobRepository.findByCompanyId(companyId);
    }

    public List<Post> findPostsByCompanyId(Long companyId) { // <== Method to find posts by company ID
        return postRepository.findByCompanyId(companyId);
    }
}