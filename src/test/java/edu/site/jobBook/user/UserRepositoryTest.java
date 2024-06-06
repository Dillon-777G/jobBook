package edu.site.jobBook.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the database before each test
        userRepository.deleteAll();

        // Add some test data
        AppUser user1 = AppUser.builder()
                .username("testuser1")
                .password("password1")
                .roles(Set.of("ROLE_USER"))
                .build();

        AppUser user2 = AppUser.builder()
                .username("testuser2")
                .password("password2")
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void testFindByUsername() {
        AppUser foundUser = userRepository.findByUsername("testuser1");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testuser1");
    }

    @Test
    public void testFindAll() {
        List<AppUser> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting(AppUser::getUsername).containsExactlyInAnyOrder("testuser1", "testuser2");
    }
}
