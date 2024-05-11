package edu.site.jobBook.usertest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import edu.site.jobBook.user.UserRepository;
import edu.site.jobBook.user.UserPasswordService;
import edu.site.jobBook.user.password.PasswordRepository;
import edu.site.jobBook.user.User;
import edu.site.jobBook.user.password.Password;
import edu.site.jobBook.user.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserPasswordServiceTest {

    @Autowired
    private UserPasswordService userPasswordService;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PasswordRepository passwordRepository;

    @Test
    @Rollback
    public void testCreateUserWithPassword() {
        String username = "testuser";
        String rawPassword = "password123";
        Profile profile = Profile.builder()
                .firstName("Test")
                .lastName("User")
                .email("testuser@example.com")
                .username(username)
                .build();

        User createdUser = userPasswordService.createUserWithPassword(username, rawPassword, profile, "Regular");

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId()); 
        assertEquals(username, createdUser.getProfile().getUsername());

        User foundUser = userRepository.findById(createdUser.getId()).orElse(null);
        Password foundPassword = passwordRepository.findById(foundUser.getPassword().getId()).orElse(null);

        assertNotNull(foundUser);
        assertNotNull(foundPassword);
        assertTrue(foundPassword.getHash().equals(Password.hashPassword(rawPassword)));
    }
}
