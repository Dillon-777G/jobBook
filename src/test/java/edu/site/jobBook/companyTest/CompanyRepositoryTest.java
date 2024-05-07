package edu.site.jobBook.companyTest;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import edu.site.jobBook.post.PostRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class CompanyRepositoryTest {
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void resetDatabase() {
        postRepository.deleteAll();
        // Delete all companies
        companyRepository.deleteAll(); 
    }

    @Test
    @Rollback
    public void testAddAndFindCompany() {
        // Create a company
        Company newCompany = new Company();
        newCompany.setName("Company A");
        newCompany.setDescription("Company A magic");

        // Save the company
        Company savedCompany = companyRepository.save(newCompany);
        assertNotNull(savedCompany.getId());

        // Get the company
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());
        assertTrue(foundCompany.isPresent());

        //Test result
        assertEquals("Company A", foundCompany.get().getName());
        assertEquals("Company A magic", foundCompany.get().getDescription());
    }

    @Test
    @Rollback
    public void testAddAndCount(){

        long count = 0;
        //Create a company
        Company company1 = new Company();
        company1.setName("Company A");
        company1.setDescription("Desc A");

        //Save the company
        companyRepository.save(company1);

        //Test result
        count = companyRepository.count();
        assertEquals(1, count);

        //Create a company
        Company company2 = new Company();
        company2.setName("Company B");
        company2.setDescription("Desc B");

        //Save the company
        companyRepository.save(company2);

        //Test result
        count = companyRepository.count();
        assertEquals(2, count);
    }
}
