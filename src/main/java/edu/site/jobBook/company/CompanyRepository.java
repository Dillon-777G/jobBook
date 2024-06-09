package edu.site.jobBook.company;

import java.util.List;
import java.util.Optional;

// import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long>{
    //https://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
    public Optional<Company> findBynameIgnoreCase(String name); //i get it but dont get it.

    Page<Company> findAll(Pageable pageable);
    List<Company> findByNameContainingIgnoreCase(String partialName);
}