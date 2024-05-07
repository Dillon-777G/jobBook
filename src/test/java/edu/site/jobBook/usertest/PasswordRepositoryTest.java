package edu.site.jobBook.usertest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import edu.site.jobBook.user.User;
import edu.site.jobBook.user.Profile;
import edu.site.jobBook.user.password.Password;
import edu.site.jobBook.user.password.PasswordRepository;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class PasswordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PasswordRepository passwordRepository;

    @Test
    public void testSavePassword() {
        // Create and set up a new User entity
        Profile profile = new Profile("John", "Doe", "john.doe@example.com", "john_doe");
        User user = new User();
        user.setProfile(profile);
        user.setUserType("standard");
        user = entityManager.persistAndFlush(user);

        Password password = new Password();
        password.setUser(user);
        password.setPassword("securePassword123");

        Password savedPassword = entityManager.persistFlushFind(password);

        assertNotNull(savedPassword, "The saved password should not be null.");
        assertNotNull(savedPassword.getId(), "The saved password's ID should not be null.");
        assertEquals(password.getHash(), savedPassword.getHash(), "The hashes should match.");
    }

    @Test
    @Rollback
    public void testFindByUsername() {
        // Create and set up a new User entity
        Profile profile = new Profile("John", "Doe", "john.doe@example.com", "john_doe");
        User user = new User();
        user.setProfile(profile);
        user.setUserType("standard");

        user = entityManager.persistAndFlush(user);

        Password password = new Password();
        password.setUser(user);
        password.setPassword("securePassword123");


        entityManager.persistAndFlush(password);


        Optional<Password> foundPassword = passwordRepository.findByUser_Profile_Username("john_doe");

        assertTrue(foundPassword.isPresent(), "Password should be found with the username 'john_doe'");
        assertEquals(password.getHash(), foundPassword.get().getHash(), "The hashes should match");
    }
}
