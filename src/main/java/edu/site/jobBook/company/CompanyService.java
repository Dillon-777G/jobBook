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

    public Post createPost(Long companyId, Post post) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        post.setCompany(company);
        post.setUser(null);  
        return postRepository.save(post);
    }

    /* future references.
    * https://www.w3docs.com/snippets/java/how-do-i-update-an-entity-using-spring-data-jpa.html
    * https://medium.com/@bubu.tripathy/implementing-transactions-in-a-spring-boot-application-bc6b33e88557
    */
}