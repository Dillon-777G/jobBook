package edu.site.jobBook.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PostRepository postRepository;

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

    public List<Company> findCompaniesByPartialName(String partialName) {
        return companyRepository.findByNameContainingIgnoreCase(partialName);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setDescription(updatedCompany.getDescription());
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public List<Company> findAllCompanies() {
        return (List<Company>) companyRepository.findAll();
    }
    
    /* future references.
    * https://www.w3docs.com/snippets/java/how-do-i-update-an-entity-using-spring-data-jpa.html
    * https://medium.com/@bubu.tripathy/implementing-transactions-in-a-spring-boot-application-bc6b33e88557
    */
}