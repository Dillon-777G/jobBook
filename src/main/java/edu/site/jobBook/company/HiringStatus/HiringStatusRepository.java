package edu.site.jobBook.company.HiringStatus;

import java.util.List;

import org.springframework.data.repository.CrudRepository;;

public interface HiringStatusRepository  extends CrudRepository<CompanyHiringStatus, Long> {
    List<CompanyHiringStatus> findByHiringStatus(HiringStatus hiringStatus);
}
