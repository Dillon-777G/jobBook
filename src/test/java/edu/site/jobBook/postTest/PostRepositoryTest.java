package edu.site.jobBook.postTest;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.company.CompanyRepository;
import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;
import edu.site.jobBook.user.AppUser;
import edu.site.jobBook.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the tables
        postRepository.deleteAll();
        userRepository.deleteAll();
        companyRepository.deleteAll();

        // Confirm deletion
        assertThat(postRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).isEmpty();
        assertThat(companyRepository.findAll()).isEmpty();
    }

    @Test
    public void testFindByUser() {
        // Create and save the user
        AppUser user = AppUser.builder()
                .username("uniqueTestUser1")
                .password("password1")
                .roles(Set.of("ROLE_USER"))
                .build();
        user = userRepository.save(user);

        // Create and save the company
        Company company = Company.builder()
                .name("uniqueTestCompany1")
                .description("Company Description 1")
                .build();
        company = companyRepository.save(company);

        // Fetch the saved entities to ensure they are persisted
        user = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        company = companyRepository.findById(company.getId()).orElseThrow(() -> new RuntimeException("Company not found"));

        // Create and save the posts
        Post post1 = Post.builder()
                .caption("Post 1")
                .user(user)
                .company(company)
                .likes(10)
                .shares(2)
                .build();

        Post post2 = Post.builder()
                .caption("Post 2")
                .user(user)
                .company(company)
                .likes(20)
                .shares(4)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        // Run the test
        List<Post> posts = postRepository.findByUser(user);
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    public void testFindByCompanyId() {
        // Create and save the user
        AppUser user = AppUser.builder()
                .username("uniqueTestUser2")
                .password("password2")
                .roles(Set.of("ROLE_USER"))
                .build();
        user = userRepository.save(user);

        // Create and save the company
        Company company = Company.builder()
                .name("uniqueTestCompany2")
                .description("Company Description 2")
                .build();
        company = companyRepository.save(company);

        // Fetch the saved entities to ensure they are persisted
        user = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        company = companyRepository.findById(company.getId()).orElseThrow(() -> new RuntimeException("Company not found"));

        // Create and save the posts
        Post post1 = Post.builder()
                .caption("Post 1")
                .user(user)
                .company(company)
                .likes(10)
                .shares(2)
                .build();

        Post post2 = Post.builder()
                .caption("Post 2")
                .user(user)
                .company(company)
                .likes(20)
                .shares(4)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        // Run the test
        List<Post> posts = postRepository.findByCompanyId(company.getId());
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(2);
    }
}
