package edu.site.jobBook.job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.site.jobBook.company.Company;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompany(Company company);
}