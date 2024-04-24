package edu.site.jobBook.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> findCompanyByName(String company){
        return companyRepository.findBynameIgnoreCase(company);
    }

    public Optional<Company> findCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    /* future references.
    * https://www.w3docs.com/snippets/java/how-do-i-update-an-entity-using-spring-data-jpa.html
    * https://medium.com/@bubu.tripathy/implementing-transactions-in-a-spring-boot-application-bc6b33e88557
    */
}