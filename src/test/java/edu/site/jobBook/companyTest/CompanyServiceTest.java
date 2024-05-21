// package edu.site.jobBook.companyTest;

// import edu.site.jobBook.company.Company;
// import edu.site.jobBook.company.CompanyRepository;
// import edu.site.jobBook.company.CompanyService;
// import edu.site.jobBook.post.Post;
// import edu.site.jobBook.post.PostRepository;
// import java.util.Optional;


// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Rollback;

// @SpringBootTest
// public class CompanyServiceTest {
    
//     @Autowired
//     private CompanyService companyService;

//     @Autowired
//     private CompanyRepository companyRepository;

//     @Autowired
//     private PostRepository postRepository;

//     @BeforeEach
//     public void resetDatabase() {
//         postRepository.deleteAll();
//         companyRepository.deleteAll();  // This clears the repository before each test
//     }

//     // @Test
//     // @Rollback
//     // public void testCreatePostForCompany() {
//     //     // Create a company
//     //     Company newCompany = new Company();
//     //     newCompany.setName("Company A");
//     //     newCompany.setDescription("Company A magic");
//     //     Company savedCompany = companyRepository.save(newCompany);

//     //     // Create a post for the company
//     //     Post newPost = new Post();
//     //     newPost.setCaption("Company A's first post");

//     //     Post savedPost = companyService.createPost(savedCompany.getId(), newPost);
//     //     assertNotNull(savedPost.getId());
//     //     assertEquals(savedCompany, savedPost.getCompany());

//     //     // Verify the post was saved correctly
//     //     Optional<Post> foundPost = postRepository.findById(savedPost.getId());
//     //     assertTrue(foundPost.isPresent());
//     //     assertEquals("Company A's first post", foundPost.get().getCaption());
//     //     assertEquals(savedCompany.getId(), foundPost.get().getCompany().getId());

//     //     // Verify the company's jobs and posts lists
//     //     assertTrue(savedCompany.getJobs().isEmpty(), "Jobs list should be empty");
//     //     assertTrue(savedCompany.getPosts().isEmpty(), "Posts list should be empty");
//     // }

// }