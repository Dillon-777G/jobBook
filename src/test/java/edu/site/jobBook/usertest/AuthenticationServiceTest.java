package edu.site.jobBook.usertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import edu.site.jobBook.user.password.*;
import edu.site.jobBook.user.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordRepository passwordRepository;

    @BeforeEach
    public void setup() {
        passwordRepository.deleteAll();
    }

    @Test
    public void testAuthenticateSuccess() {
        Profile profile = new Profile("John", "Doe", "john.doe@example.com", "john_doe");
        User user = new User();
        user.setProfile(profile);
        user.setUserType("standard");

        user = userService.createUserWithPassword(user, "securePassword123");

        boolean isAuthenticated = authenticationService.authenticate("john_doe", "securePassword123");
        assertTrue(isAuthenticated, "Authentication should succeed with correct password");
    }

    @Test
    public void testAuthenticateFailure() {
        // Create user and set password
        Profile profile = new Profile("Jane", "Doe", "jane.doe@example.com", "jane_doe");
        User user = new User();
        user.setProfile(profile);
        user.setUserType("standard");


        user = userService.createUserWithPassword(user, "securePassword123");


        boolean isAuthenticated = authenticationService.authenticate("jane_doe", "wrongPassword");
        assertFalse(isAuthenticated, "Authentication should fail with incorrect password");
    }
}
