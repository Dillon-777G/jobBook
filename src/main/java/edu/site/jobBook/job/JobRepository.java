package edu.site.jobBook.job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.site.jobBook.company.Company;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompany(Company company);
    List<Job> findByCompanyId(Long companyId);
    Long countByJobDetailsStatus(JobStatus status);

    // Filter by title, location, status, and company name
    Page<Job> findByTitleAndJobDetailsLocationAndJobDetailsStatusAndCompany_Name(
            String title, String location, JobStatus status, String companyName, Pageable pageable);

    // Filter by title, location, and company name
    Page<Job> findByTitleAndJobDetailsLocationAndCompany_Name(
            String title, String location, String companyName, Pageable pageable);

    // Filter by title, status, and company name
    Page<Job> findByTitleAndJobDetailsStatusAndCompany_Name(
            String title, JobStatus status, String companyName, Pageable pageable);

    // Filter by location, status, and company name
    Page<Job> findByJobDetailsLocationAndJobDetailsStatusAndCompany_Name(
            String location, JobStatus status, String companyName, Pageable pageable);

    // Filter by title and company name
    Page<Job> findByTitleAndCompany_Name(String title, String companyName, Pageable pageable);

    // Filter by location and company name
    Page<Job> findByJobDetailsLocationAndCompany_Name(String location, String companyName, Pageable pageable);

    // Filter by status and company name
    Page<Job> findByJobDetailsStatusAndCompany_Name(JobStatus status, String companyName, Pageable pageable);

      // Filter by title, location, and status
      Page<Job> findByTitleAndJobDetailsLocationAndJobDetailsStatus(
        String title, String location, JobStatus status, Pageable pageable);

        // Filter by title and location
        Page<Job> findByTitleAndJobDetailsLocation(
                String title, String location, Pageable pageable);

        // Filter by title and status
        Page<Job> findByTitleAndJobDetailsStatus(
                String title, JobStatus status, Pageable pageable);

        // Filter by location and status
        Page<Job> findByJobDetailsLocationAndJobDetailsStatus(
                String location, JobStatus status, Pageable pageable);

        // Filter by title
        Page<Job> findByTitle(String title, Pageable pageable);

        // Filter by location
        Page<Job> findByJobDetailsLocation(String location, Pageable pageable);

        // Filter by status
        Page<Job> findByJobDetailsStatus(JobStatus status, Pageable pageable);
}