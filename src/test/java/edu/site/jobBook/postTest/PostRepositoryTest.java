package edu.site.jobBook.postTest;

import edu.site.jobBook.company.Company;
import edu.site.jobBook.post.Post;
import edu.site.jobBook.post.PostRepository;
import edu.site.jobBook.user.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private AppUser user;
    private Company company;

    @BeforeEach
    public void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setUsername("testUser");

        company = new Company();
        company.setId(1L);
        company.setName("testCompany");

        Post post1 = Post.builder()
                .caption("Post 1")
                .user(user)
                .company(company)
                .build();

        Post post2 = Post.builder()
                .caption("Post 2")
                .user(user)
                .company(company)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
    }

    @Test
    public void testFindByUser() {
        List<Post> posts = postRepository.findByUser(user);
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    public void testFindByCompanyId() {
        List<Post> posts = postRepository.findByCompanyId(company.getId());
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(2);
    }
}
