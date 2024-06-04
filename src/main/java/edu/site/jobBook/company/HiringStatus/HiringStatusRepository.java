package edu.site.jobBook.company.HiringStatus;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

//https://stackoverflow.com/questions/56499565/how-to-use-findall-on-crudrepository-returning-a-list-instead-of-iterable
//Have to decide what to do with that.
public interface HiringStatusRepository extends CrudRepository<CompanyHiringStatus, Long> {
    List<CompanyHiringStatus> findByHiringStatus(HiringStatus hiringStatus);
}

